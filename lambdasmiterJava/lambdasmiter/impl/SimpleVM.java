package lambdasmiter.impl;

import java.util.Arrays;

import lambdasmiter.Op;
import lambdasmiter.VM;

public strictfp class SimpleVM implements VM{
	
	/** low stack pointer, below highSp */
	private int lsp;
	
	/** normal/high stack pointer */
	private int hsp;
	
	/** instruction pointer. points into a tuple. if op is CALL then wherever it is in that tuple,
	it jumps from there to the next tuple pushed above it (probably in some other stack than the main stack, todo design),
	starting at index 0 in that next higher tuple, and (todo get rid of op RETURN?) and instead it returns when at
	the last index in that next higher tuple, pops that tuple, and continues at the same index in the first tuple.
	Gas, minGas, lsp, hsp, etc must be tracked considered in that secondary stack, especially if its smited
	aka gives up early due to not enough gas, and considering that opForkMN can recursively fork the stack,
	and from each fork can fork again in different turingComplete ways, so forking needs to be considered too,
	though I might just start with optimizing just 1 level of forking such as is good enough for GPU matmul
	or computing pixels of 3d fractals etc.
	*/
	private int ip;
	
	/** FIXME in opForkMN, gas-lowGas must be split N ways equally between N parallel calls */
	private double gas, lowGas;
	
	/** contains Tuple and java.lang.Double to use autoboxing optimization. No nulls. */
	private final Number[] stack;
	
	/** stack.length-1, where stack.length is a powOf2.
	I'm undecided if will use this vs if just check for IndexOutOfBoundsException,
	and I dont want to create bugs where something out of range can overwrite
	something in a valid range.
	*
	private final int stackMask;
	*/
	
	/** top function in the <int,int,node> stack, or something like that <...whatgoeshere?...>
	which ip points into.
	FIXME need stack of these cuz lambda calling lambda happens on stack when opCALL andOr when opForkMN.
	*/
	private Tuple bytecode;
	
	/** becomes true if any nondeterminism happens, such as reading amount of gas available,
	or such as running out of gas and catch/else to whatever to do if run out of gas at that point in stack,
	BUT just having it ready to catch/else if it runs out of gas, but not running out of gas, is deterministic
	since anyone anywhere in the internet can repeat and verify that calculation if you share the call which led to it.
	*/
	private boolean dirty = false;
	
	private static final Op[] ops;
	static{
		ops = Op.values();
		if(ops.length > 127) throw new RuntimeException("could go up to 256 but only 128 if using signed byte, cuz am using optimization of cast to byte only instead of cast to byte then &0xff, or could use more of the bits in double (than 7 or 8) for opcode type, but trying to keep it very small, as most languages VMs use a byte for op type. ops.length="+ops.length);
	}
	
	/** in units of Number, not units of memory
	but can estimate memory as this times a little more than 64 bits
	as most of it is autoboxed doubles. You might want a much bigger stack
	up to maxPossibleStackSizeIn1Array Numbers,
	since its the only mutable memory usable by opcodes. 
	<br><br>
	The heap can be any size since its a forest not an array.
	*/
	private static final int defaltStackSize = 1<<24;
	
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
		this(defaltStackSize);
	}
	
	public SimpleVM(int stackSize){
		//stackMask = stackSize-1;
		if((stackSize&(stackSize-1)) != 0) throw new RuntimeException("stackSize="+stackSize+" is not a powOf2");
		this.stack = new Number[stackSize];
		Arrays.fill(stack, 0.);
	}
	

	public Tuple funcall(Tuple stackTop){
		throw new RuntimeException("TODO copy stackTop onto stackTop, set lsp and hsp, run nextState() in a loop until it returns back to that same size (maybe check for Op.ret as many times as Op.call etc), then return tuple of whats in stack[lsp..hsp] which is the same size of tuple in and out.");
	}
	
	public void clear(double fillGasUpTo){
		if(fillGasUpTo > VM.maxPossibleGas) throw new RuntimeException(
			"too much gas requested: "+fillGasUpTo+" max allowed is "+VM.maxPossibleGas);
		//you can, for example, in the first call increase minGas so gas-minGas is a very small amount of gas available,
		//but todo find a way to do that without causing dirty==true,
		//so maybe the amount of gas to set it to should be a param of clear func.
		//gas = VM.maxPossibleGas;
		gas = fillGasUpTo;
		lowGas = 0;
		Arrays.fill(stack, 0, hsp, 0.);
		lsp = hsp = 0; //FIXME 1? Or whats on stack when its as empty as it should ever be?
		//bytecode = emptyTuple; //FIXME should this be 2 doubles: noop and ret? Or just 1: ret?
		bytecode = tuple(Op.ret.ordinal());
		ip = 0;
		dirty = false;
	}
	
	public void nextState(){
		gas--; //FIXME smite/backOut if gas would go below lowGas, and different ops take different amounts of gas.
		double opcode = bytecode.get(ip).doubleValue();
		int addToIp = 1;
		if(opcode >= 0){ //push that literal double. For negative literal, next opcode should be NEG.
			pushD(opcode);
		}else{ //normal opcode
			Op op = ops[(byte)opcode];
			dirty |= op.isDirty;
			switch(op){
			case call: //CALL (the lambda on top of stack on the tuple stack[lsp..hsp])
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
	
	/** true if stack top as double is nonzero *
	protected boolean peekZ(){
		return stack[hsp].doubleValue()!=0;
	}*/
	
	/** returns 0 or 1 depending if the top of the stack, as double, is nonzero *
	protected double peekBit(){
		return stack[hsp].doubleValue()==0 ? 0 : 1;
	}*/

}
