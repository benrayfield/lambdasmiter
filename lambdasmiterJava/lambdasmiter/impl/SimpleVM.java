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
	
	private static final Op[] ops;
	static{
		ops = Op.values();
		if(ops.length > 256) throw new RuntimeException(
			"For efficiency of CPU cache and predictive assembly instructions preloading and microcode"
			+" etc (happens automatically when higher level languages are compiled)"
			+" and simplicity of formal-verification, number of op types must fit in a byte,"
			+" other than if a double opcode is nonnegative that opcode is pushed onto stack as a literal"
			+" (and if you want a negative literal nl, use 2 opcodes nl then Op.neg). ops.length="+ops.length);
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
	
	public void nextState(){
		gas--; "FIXME smite/backOut if gas would go below lowGas, and different ops take different amounts of gas."
		double opcode = bytecode.get(ip).doubleValue();
		int addToIp = 1;
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
				
			case jump:
				//If stack
				//jump inside same bytecode. Add to current ip some number inside the bits of the opcode.
				//get the 23 bits just above the byte op, as signed int23,
				//so bytecode can be at most double[1<<23].
				if(peekD()==0){
					addToIp = (((int)opcode)<<1)>>9; //int23
				}
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
				pushD(gas-lowGas);
				dirty = true;
			break;case verifyBytecode: //verify bytecode at top of stack and push 0 or 1 on stack if it fails vs passes.
				//This will happen automatically the first time, then cache it, for each tuple called as a lambda.
				//See Tuple.cache_isCertainlyVerified and Tuple.cache_isCertainlyFailedVerify.
				Number n = pop();
				//returns isValidBytecode from cache if not the first time
				boolean verify = (n instanceof Tuple) ? ((Tuple)n).isValidBytecode() : false;
				pushD(verify ? 1 : 0);
				//if(1<2) throw new RuntimeException("TODO");
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
				if(1<2) throw new RuntimeException("TODO");
			break;case forkNM:
				if(1<2) throw new RuntimeException("TODO");
			break;case nandOf2Bits:
				if(1<2) throw new RuntimeException("TODO");
			break;case nodeType:
				if(1<2) throw new RuntimeException("TODO");
			break;case noop:
				if(1<2) throw new RuntimeException("TODO");
			break;case pair:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop16:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop16k:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop16m:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop1g:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop1k:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop1m:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop256:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop256k:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop256m:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop4:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop4k:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop4m:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop64:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop64k:
				if(1<2) throw new RuntimeException("TODO");
			break;case pop64m:
				if(1<2) throw new RuntimeException("TODO");
			break;case pushIsVerifiedOrNot:
				if(1<2) throw new RuntimeException("TODO");
			break;case ret:
				if(1<2) throw new RuntimeException("TODO");
			break;case smite:
				if(1<2) throw new RuntimeException("TODO");
			break;case tupleLen:
				if(1<2) throw new RuntimeException("TODO");
			}
		}
		ip += addToIp;
	}
	
	
	
	
	protected void pushD(double d){
		stack[hsp++] = d;
	}
	
	protected double popD(){
		return stack[hsp--].doubleValue();
	}
	
	protected double peekD(){
		return stack[hsp].doubleValue();
	}
	
	protected Number pop(){
		return stack[hsp--];
	}
	
	public final Tuple emptyTuple = tuple();
	
	public Tuple tuple(Number... childs){
		//FIXME do lazy dedup. Or perfect dedup right away (more expensive)?
		return new Tuple(childs);
	}

	public Tuple dedup(Tuple tuple){
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