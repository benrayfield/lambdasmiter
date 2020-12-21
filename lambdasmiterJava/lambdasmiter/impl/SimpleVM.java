/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter.impl;
import java.util.Arrays;
import java.util.List;
import lambdasmiter.*;

public strictfp class SimpleVM implements VM{
	
	/** instruction pointer. points into a tuple. if op is CALL then wherever it is in that tuple,
	it jumps from there to the next tuple pushed above it (probably in some other stack than the main stack, todo design),
	starting at index 0 in that next higher tuple, and (todo get rid of op RETURN?) and instead it returns when at
	the last index in that next higher tuple, pops that tuple, and continues at the same index in the first tuple.
	Gas, lgas, lsp, hsp, etc must be tracked considered in that secondary stack, especially if its smited
	aka gives up early due to not enough gas, and considering that opForkMN can recursively fork the stack,
	and from each fork can fork again in different turingComplete ways, so forking needs to be considered too,
	though I might just start with optimizing just 1 level of forking such as is good enough for GPU matmul
	or computing pixels of 3d fractals etc.
	*/
	protected int ip;
	
	/** high stack pointer. constack.lsp <= hsp, and in the simplest case you only need those 2 (not the 4) vars:
	or when using Op.forkMN (and maybe all the time? todo choose design) theres 2 more kinds stack pointers:
	<br><br>
	lspRead <= lspReadwrite <= hsp <= ksp.
	<br><br>
	numstack[lspRead..lspReadwrite] is read only and can be read by many threads.
	<br><br>
	numstack[lspReadwrite..hsp] is memory local to a thread (like opencl ndrange private memory depending on get_global_id)
		and can be read and written by that thread without any of the other threads seeing it as they have their own memory.
	<br><br>
	numstack[hsp..ksp] is memory not yet allocated but can be allocated by pushing things onto numstack,
		 but only allowed to push up to ksp. ksp can decrease (in callAndTighten) but not increase,
		 until return to where it was already higher.
	<br><br>
	Below lspRead and above ksp, are not allowed to read or write, except after return the constraints may be looser.
	*/
	private int hsp;
	
	/** function stack pointer. funstack[fsp] is function at top of function stack.
	funstack[fsp].get(0) is bytecode.
	funstack[fsp].get(1) is other data stored in function, such as curried params but could be anything.
	*/
	protected int fsp;
	
	/** Amount of compute resources available for this VM session, which is between each 2 consecutive calls of VM.clear(long).
	constack.lgas<=gas. In Op.forkNM, each of N threads gets floor((gas-lgas)/N) gas,
	which means lgas increases so that gas-lgas equals that.
	Those may be greenThreads (can run in a single OS thread) or OS threads or GPU threads or any kind of parallel.
	*/
	protected long gas;
	
	/** number stack. contains Tuple and java.lang.Double to use autoboxing optimization. No nulls. */
	protected final Number[] numstack;
	
	/** constraint stack. Top of constraint stack, which is only changed at the start and end of Op.tighten
	(or TODO it might be callAndTighten, as compared to callWithoutTighten which doesnt change constraints)
	*/
	protected Constraint constack;
	
	/** stack of functions, which are any tuple of size at least 2. See comment of cache_bytecode for details. */
	protected final Number[] funstack;
	
	/** Same as funstack[fsp].get(0), the bytecode of the top function on the stack.
	funstack[fsp].get(1) is other data in that function such as curried params or could be anything,
	any way you might design bytecode to use that data. Remember, code and data are both Number,
	and Number is either double or list of Number, and Number is immutable.
	<br><br>
	top function in the <int,int,node> stack, or something like that <...whatgoeshere?...>
	which ip points into.
	FIXME need stack of these cuz lambda calling lambda happens on stack when opCALL andOr when opForkMN.
	*
	protected Tuple bytecode;
	FIXME functions will be represented as tuple(bytecodeTuple,data) aka pair of those 2 things,
	since to represent lambdas currying params, accumulating data other than their bytecode,
	they need a place to store that other than in the stack,
	so you can for example call a lambda on a lambda to find/create a lambdas,
	such as (S I I) is a lambda that calls its param on itself.
	But this "Tuple bytecode" var is still a good optimization, and just replace it with
	topFunctionOnStack.get(0) whenever topFunctionOnStack changes.
	<br><br>
	TODO bytecode verify fails if its not all made of doubles. Or should bytecode be able to push a literal tuple
	like it pushes a literal double, which a tuple viewed by Number.doubleValue() is a nonnegative double
	(todo choose a design, such as is it always 0, always 1, or always tuple size?).
	<br><br>
	TODO optimize this by getting the Number[] (all doubles, else would not pass bytecode verify)
	out of NumberArrayTuple?
	*/
	protected List<Double> cache_bytecode;
	
	/** same as constack.lsp *
	protected int cache_lsp;
	*/
	
	
	/** becomes true if any nondeterminism happens, such as reading amount of gas available,
	or such as running out of gas and catch/else to whatever to do if run out of gas at that point in stack,
	BUT just having it ready to catch/else if it runs out of gas, but not running out of gas, is deterministic
	since anyone anywhere in the internet can repeat and verify that calculation if you share the call which led to it.
	*/
	protected boolean dirty = false;
	
	protected static final Op[] ops;
	static{
		ops = Op.values();
		if(ops.length > 256) throw new RuntimeException(
			"For efficiency of CPU cache and predictive assembly instructions preloading and microcode"
			+" etc (happens automatically when higher level languages are compiled)"
			+" and simplicity of formal-verification, number of op types must fit in a byte,"
			+" other than if a double opcode is nonnegative that opcode is pushed onto stack as a literal"
			+" (and if you want a negative literal nl, use 2 opcodes -nl then Op.neg). ops.length="+ops.length);
	}
	
	public boolean changeInStackHeight(double opcode){
		if(opcode >= 0) return 1; //push opcode onto numstack
		Op op = ops[(byte)opcode];
		return op.outs-op.ins;
	}
	
	public int opOuts(double opcode){
		if(opcode >= 0) return 1; //push opcode onto numstack
		Op op = ops[(byte)opcode];
		return op.outs-op.ins;
	}
	
	public int opIns(double opcode){
		if(opcode >= 0) return 1; //push opcode onto numstack
		Op op = ops[(byte)opcode];
		return op.ins;
	}
	
	/** in units of Number, not units of memory
	but can estimate memory as this times a little more than 64 bits
	as most of it is autoboxed doubles. You might want a much bigger stack
	up to maxPossibleStackSizeIn1Array Numbers,
	since its the only mutable memory usable by opcodes.
	<br><br>
	The heap can be any size since its a forest not an array.
	*/
	private static final int defaultStackSize = 1<<24;
	
	private static final int defaultFunstackSize = defaultStackSize>>2;
	
	/** 1<<30 cuz mInteger.MAX_VALUE? or maybe stack size should have to be a powOf2 for mask optimization
	in which case this would be 1<<30 aka an approx 8gB stack,
	though in other implementations of the VM it could use multiple arrays
	such as Number[(int)(longIndex>>30)][(int)(longIndex&0x3fffffff)] in which case it could
	have as big a stack as you want but it would be slower.
	Or you could use a Map<Long,Number> but that would be much slower.
	<br><br>
	The heap can be any size since its a forest not an array.
	*/
	private static final int maxPossibleStackSizeIn1Array = 1<<30; //Integer.MAX_VALUE;
	
	public SimpleVM(){
		this(defaultStackSize, defaultFunstackSize);
	}
	
	public SimpleVM(int stackSize, int funstackSize){
		//stackMask = stackSize-1;
		if((stackSize&(stackSize-1)) != 0) throw new RuntimeException("stackSize="+stackSize+" is not a powOf2");
		this.numstack = new Number[stackSize];
		funstack = new Number[funstackSize];
		Arrays.fill(numstack, 0.);
	}
	

	public Tuple funcall(Tuple stackTop){
		throw new RuntimeException("TODO copy stackTop onto stackTop, set lsp and hsp, run nextState() in a loop until it returns back to that same size (maybe check for Op.ret as many times as Op.call etc), then return tuple of whats in stack[lsp..hsp] which is the same size of tuple in and out.");
	}
	
	public void clear(long fillGasUpTo){
		if(fillGasUpTo > VM.maxPossibleGas) throw new RuntimeException(
			"too much gas requested: "+fillGasUpTo+" max allowed is "+VM.maxPossibleGas);
		//you can, for example, in the first call increase lgas so gas-lgas is a very small amount of gas available,
		//but todo find a way to do that without causing dirty==true,
		//so maybe the amount of gas to set it to should be a param of clear func.
		//gas = VM.maxPossibleGas;
		gas = fillGasUpTo;
		funstack[fsp = 0] = pair(tuple(Op.ret.ordinal()),0.); //a function is tuple(bytecode,anyData)
		cache_bytecode = (List<Double>) get(funstack[fsp],0); //current bytecode is always at funstack[fsp]
		ip = 0;
		dirty = false;
		
		constack = new Constraint(null, true, 0L, 0, 0); //FIXME lsp and hsp may be offby1?;
		
		/*
		lowGas = 0;
		Arrays.fill(numstack, 0, hsp, 0.);
		lsp = hsp = 0; //FIXME 1? Or whats on stack when its as empty as it should ever be?
		//bytecode = emptyTuple; //FIXME should this be 2 doubles: noop and ret? Or just 1: ret?
		*/
	}
	
	protected static Number get(Number parent, int childIndex){
		if(parent instanceof List) return ((List<Number>)parent).get(childIndex);
		return 0.; //FIXME whats default value for trying to get a child at index that doesnt exist there?
	}
	
	public boolean nextState(){
		$(); //FIXME pay more in some ops that copy a variable size range or other variable but limited to bigO of some linear part of stack.
		//gas--; //"FIXME smite/backOut if gas would go below lowGas, and different ops take different amounts of gas."
		double opcode = cache_bytecode.get(ip).doubleValue();
		int addToIp = 1;
		boolean smite = false;
		if(opcode >= 0){ //push that literal double. For negative literal, next opcode should be NEG.
			pushD(opcode);
		}else{ //normal opcode
			Op op = ops[(byte)opcode];
			dirty |= op.isDirty;
			switch(op){
			case callWithoutTighten: //CALL (the lambda on top of stack on the tuple stack[lsp..hsp]) with current constraint
				throw new RuntimeException("TODO");
				
			case callAndTighten: //CALL (the lambda on top of stack on the tuple stack[lsp..hsp]) and TIGHTEN constraint deeper on stack
				throw new RuntimeException("TODO");
				
			case jumpIf0:
				//If stack
				//jump inside same bytecode. Add to current ip some number inside the bits of the opcode.
				//get the 23 bits just above the byte op, as signed int23,
				//so bytecode can be at most double[1<<23].
				if(peekD()==0){
					addToIp = (((int)opcode)<<1)>>9; //int23
				}
			case jumpSwitch:
				//For possible future expansion, see comment of Op.jumpSwitch.
				//For now, smite so when the behavior changes in a future version, it appears that it just got
				//more efficient like it did not run out of gas so was not smited. As long as it
				//never returns 2 different things for the same input while !isDirty, its valid lambda math.
				smite = true;
			break;case neg: //NEG(x)
				pushD(-popD());
			break;case mul: //multiply(x,y)
				pushD(popD()*popD());
			break;case add: //plus(x,y)
				pushD(popD()+popD());
			break;case mod: //double%double  TODO % and / together, 2 in 2 out? many hardwares are faster for that, in theory.
				pushD(popD()%popD());
			break;case div: //double/double
				pushD(popD()/popD());
			break;case oneDiv: //1/double
				pushD(1./popD());
			break;case min: //min(x,y)
				double a = popD(), b = popD();
				pushD(a<b ? a : b);
			break;case max: //max(x,y)
				a = popD();
				b = popD();
				pushD(a<b ? b : a);
			break;case copyStackToHeap: //copy from mutable stack to immutable heap (or find equal content in heap), a range in stack, to a whole tuple in heap
				if(1<2) throw new RuntimeException("TODO optimize by doing infloop or noop or return constant from here");
			break;case copyHeapToStack: //copy from immutable heap to mutable stack, a range in each
				if(1<2) throw new RuntimeException("TODO optimize by doing infloop or noop or return constant from here");
			break;case copyStackTopToMidStack:
				if(1<2) throw new RuntimeException("TODO optimize by doing infloop or noop or return constant from here");
			break;case pushGas: //WARNING: NONDETERMINISTIC, so mark that nondeterminism happened from here on
				pushD(gas-constack.lgas);
				dirty = true;
			break;case verifyFunctionContainingBytecode: //verify (function containing) bytecode at top of stack and push 0 or 1 on stack if it fails vs passes.
				//This will happen automatically the first time, then cache it, for each tuple called as a lambda.
				//See NumberArrayTuple.cache_isCertainlyVerified and NumberArrayTuple.cache_isCertainlyFailedVerify.
				//If this returns 0 (is not valid bytecode), its still a valid function but just doesnt run the bytecode when called
				//and instead does some default behavior (todo choose a design, maybe return 0, or smite,
				//or identityFunc, or return itself, or noop, etc???).
				pushD(isFunctionContainingValidBytecode(pop()) ? 1 : 0);
				
				
				/*Number n = pop();
				//returns isValidBytecode from cache if not the first time
				boolean verify = (n instanceof Tuple) ? ((Tuple)n).isValidBytecode() : false;
				pushD(verify ? 1 : 0);
				//if(1<2) throw new RuntimeException("TODO");
				*/
				
			/*opForkMN
			
			break;case 'E':
				pushD(Math.exp(popD()));
			break;case 'e':
				pushD(Math.log(popD()));
			break;case 'h':
				pushD(Math.tanh(popD())); //tanh is range -1 to 1 and is a scaled sigmoid which is 0 to 1
			break;case '':
				pushD(Math.tanh(popD())); //tanh is range -1 to 1 and is a scaled sigmoid which is 0 to 1
			*/
			//default:
				//noop
				//throw new RuntimeException("TODO optimize by doing infloop or noop or return constant from here");
			break;case dup:
				push(peek());
			break;case callForkNM:
				if(1<2) throw new RuntimeException("TODO");
			break;case nandOf2Bits:
				pushD(1-popZ()*popZ());
			break;case nodeType:
				if(1<2) throw new RuntimeException("TODO");
			break;case noop:
				if(1<2) throw new RuntimeException("TODO");
			break;case pair:
				push(pair(pop(),pop()));
				if(1<2) throw new RuntimeException("TODO");
			/*break;case pop:
				pop();
			break;case popN:
				int n = getRelJump(opcode);
				//while(n-- > 0) pop();
				Arrays.fill(numstack, hsp-n, hsp, 0.);
			break;case pushNZeros:
			*/
			break;case pushOrPopN:
				//TODO optimize. shouldnt have to call Arrays.fill to just pop1 pop3 etc. Should have opcodes for those small ones.
				int n = getRelJump(opcode);
				if(n < 0) Arrays.fill(numstack, hsp+n, hsp, 0.); //popN, set to 0s to allow garbcol. fixme off by 1?
				else Arrays.fill(numstack, hsp, hsp+n, 0.); //pushN n 0s. fixme off by 1?
				hsp += n;
			break;case pushIsVerifiedOrNot:
				pushZ(isFunctionContainingValidBytecode(peek())); //TODO optimize. could do this in less calls.
			break;case ret:
				if(1<2) throw new RuntimeException("TODO");
			break;case smite:
				//smite();
				smite = true;
			break;case tupleLen:
				Number num = peek();
				pushD((num instanceof Tuple) ? ((Tuple)num).size() : 1); //FIXME is tupleLen of a double 1? Or should it be 0?
			}
		}
		ip += addToIp;
		//TODO I'm undecided if will do it by throw this or by recursive if/else,
		//but either way it will be seen on numstack as a function is replaced by didItWork which is 0 if smite vs 1 if worked.
		//Also, should nextState() always return boolean, or should it also be able to throw?
		//If throw, theres often still more work to do, just lower on stack.
		"FIXME if(smite) throw throwWhenSmite;"
		return hasWork();
	}
	
	protected static final RuntimeException throwWhenSmite = new RuntimeException("smite");
	
	/*protected void smite(){
		throw new RuntimeException("TODO");
	}*/
	
	/** anything else just does some default behavior, maybe return 0 or smite (todo choose design),
	so every Number is a valid function, but not every Number is a nontrivial function (runs bytecode).
	*/
	public boolean isFunctionContainingValidBytecode(Number function){
		return (function instanceof Tuple) && isValidBytecode(((Tuple)function).get(0));
	}
	
	public boolean isValidBytecode(Number n){
		return (n instanceof Tuple) && ((Tuple)n).isValidBytecode();
	}
	
	protected void pushD(double d){
		numstack[hsp++] = d;
	}
	
	protected void pushZ(boolean z){
		pushD(z ? 1. : 0.)
	}
	
	protected double popZ(){
		return popD()==0 ? 0. : 1.;
	}
	
	protected double popD(){
		double ret = numstack[hsp].doubleValue();
		numstack[hsp--] = 0.; //allow garbcol, which could be huge if its a tuple, but this is wasted if its a double.
		return ret;
	}
	
	protected double peekD(){
		return numstack[hsp].doubleValue();
	}
	
	protected void push(Number n){
		numstack[hsp++] = n;
	}
	
	protected Number peek(){
		return numstack[hsp];
	}
	
	protected Number pop(){
		Number ret = numstack[hsp];
		numstack[hsp--] = 0.; //allow garbcol, which could be huge if its a tuple, but this is wasted if its a double.
		return ret;
	}
	
	public final Tuple emptyTuple = tuple();
	
	public Tuple tuple(Number... childs){
		//FIXME do lazy dedup. Or perfect dedup right away (more expensive)?
		return new NumberArrayTuple(childs);
	}

	public Tuple dedup(Tuple tuple){
		throw new RuntimeException("TODO");
	}

	public Tuple stack(){
		throw new RuntimeException("TODO");
	}

	public boolean hasWork(){
		throw new RuntimeException("TODO");
	}

	public void $(long payGas){
		throw new RuntimeException("TODO");
	}

}
////////////////////////////////////////




/** true if stack top as double is nonzero *
protected boolean peekZ(){
	return stack[hsp].doubleValue()!=0;
}*/

/** returns 0 or 1 depending if the top of the stack, as double, is nonzero *
protected double peekBit(){
	return stack[hsp].doubleValue()==0 ? 0 : 1;
}*/




/** stack.length-1, where stack.length is a powOf2.
I'm undecided if will use this vs if just check for IndexOutOfBoundsException,
and I dont want to create bugs where something out of range can overwrite
something in a valid range.
*
private final int stackMask;
*/