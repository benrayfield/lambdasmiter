/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter;

public interface VM{
	
	/*Progress toward it (but not compiling yet 2020-12-19) wont leave anyone confused what its meant to be,
	this design: A function is a tuple of size at least 2,
	where tuple.get(0) is a list of double (if contained other tuples, would not pass bytecode verify),
	and tuple.get(1) is any data such as curried params of that function.
	If opcodes want to see that tuple.get(1) or anything else in the function,
	they can get the function itself from top of stack (at time function call starts, after which it may overwrite that).
	..
	OLD:
	FIXME, for a lambda to accumulate data inside it,
	need it to be pair of something and tupleOfBytecode,
	or could define tupleOfBytecode's first index to be that something
	(such as occamsfuncer curries 15 params so can store 14 params deep)
	or other things to store.
	But since I dont want verified bytecode to have to be reverified
	and to have to copy it to new tuple whenever the "something"/curries/etc
	happens ina  different combo, just use
	VM.bytecodeAtTopOfMetastack = [stuff, [...tupleOfBytecode...]] aka a pair of that,
	instead of VM.bytecodeAtTopOfMetastack = [...tupleOfBytecode...].
	Therefore could use [stuff, [...tupleOfBytecode...]] as a lambda
	to call on lambda to get lambda andOr other structures that are
	similar to lambdas except they use a bigger range on top of stack
	than currying kind of lambdas do such as for optimized matmul.
	TODO how will opcodes see "stuff"? Does it get pushed on stack?
	Is there opcode to get the current VM.bytecodeAtTopOfMetastack
	andOr VM.bytecodeAtTopOfMetastack[0]?
	Put the bytecode at VM.bytecodeAtTopOfMetastack[0],
	and there may or may not be a VM.bytecodeAtTopOfMetastack[1] and [2] and so on,
	as its a tuple of size at least 1 and is normally a pair (tuple size 2).
	*/
	
	
	/** The param tops... tops.size()>=needMinTupleSize(tops.get(tops.size()-1)),
	or maybe it should be designed for get(0) to be stack top instead of the last thing in tuple (reverse?).
	needMinTupleSize is a (todo create that func) function that tells what size of params
	a Tuple as a UnaryOperator<Tuple> needs. For example, complexNumMultiply needs at least size 5
	since itself and 4 doubles are the top 5 things on stack,
	so complexNumMultiply.apply(tuple(0,1,0,1,complexNumMultiply))
	returns a tuple(-1,0,0,0,0) since sqrt(-1)^2 = -1. It might also leave those 3 things above it
	instead of overwriting them with 3 padded 0s, but the important thing is the (-1,0).
	Therefore needMinTupleSize(complexNumMultiply)=5.
	needMinTupleSize is derived from bytecode by which rel stack indexs it reads and writes,
	based on the ins and outs sizes in each opcode such as Op.mul pops 2 and pushes 1,
	but FIXME the *copy* ops, such as copyStackTopToMidStack, depend on hsp-lsp
	as they will allow the copy whenever the requested range is in the allowed range.
	Therefore, if a Tuple (used as bytecode) is supposed to have a smaller needMinTupleSize
	than "whatever biggest range is currently allowed", there needs to be a way to define that
	in the bytecode. It could, for example, be put in the opcode itself since most opcodes
	only use 8 bits but are stored in a double, so could take 23 bits above those 8
	and interpret those 23 bits as needMinTupleSize.
	Or maybe needMinTupleSize should be something that happens at the start of the bytecode
	and is pushed onto metastack along with other things Op.tighten handles?
	I want to keep the bytecode extremely simple and have each opcode be independent of all other opcodes,
	not having a constant pool or anything like that.
	needMinTupleSize will be cached in Tuple,
	similar to those 2 booleans for is it valid bytecode (true false haventcheckedyet),
	but it has to be calculated the first time.
	<br><br>
	funcall(stack[lsp..hsp]) -> stack[lsp..hsp] of same size.
	stack[hsp] (or is top of stack hsp-1?) is the lambda function aka tuple of bytecode.
	Calling a function on stack does not change lsp or hsp as viewed
	before and after the call, but often does change during the call.
	<br><br>
	This is very slow due to the tuple in and tuple out being objects on heap
	instead of just that range on stack, but this is the normal way
	to START a call which uses that optimization for the many calls deep inside it.
	<br><br>
	For example, given a tuple of bytecode representing occamsfuncer
	(which is a universal lambda function and pattern calculus function),
	can do anything occamsfuncer can do, using a tuple of size 1 in and size 1 out
	(todo verify its represented that way vs tuples size 2 or something like that,
	as occamsfuncer has more than just bytecode, it also has a pair forest of curries)
	since occamsfuncer uses currying instead tuples.
	*/
	public Tuple funcall(Tuple tops);
	
	/** empties the VM and refills gas.
	This is normally called once per video frame of a game (such as 1./60 seconds,
	you use statistics to estimate how much gas it can spend in that time)
	or whatever this lambdasmiterVM
	is being used for it interacts with the outside world between each 2 calls of this.
	*/
	public void clear(long fillGasUpTo_maxIs2Exponent53);
	//public void clear(double fillGasUpTo);
	//TODO put Tuple on stack instead of just emptying it? the counterpart of stack()?
			
	/** copies stack to a Tuple, or may be lazyEvaled if find some kind of optimization.
	This is normally called only after a loop of nextState() ends as it has all returned
	and the last return value is whats left on the stack.
	If you call this before that happens, then theres data missing
	as it has to remember lsp and hsp and stack of bytecode etc.
	*/
	public Tuple stack();
	
	/** same as $(1). Easier to optimize. */
	public default void $(){ $(1L); }
	
	/** pay this much gas else smite. todo check by if/else vs throw a constant RuntimeException (in a final var)? */
	public void $(long payGas);
	
	/** lazyDeduped or perfectDedupedInstantly. Not deduping at all would
	probably create alot of problems like it does in occamsfuncer but I'm
	not sure since it can share vars on mutable stack temporarily.
	*/
	public Tuple tuple(Number... childs);
	
	public default Tuple pair(Number x, Number y){
		return tuple(x,y); //TODO optimize by not creating array?
	}
	
	/** for convenience of deduping any object without checking if its double vs Tuple,
	even though the doubles vs java.lang.Double will be handled automatically by autoboxing,
	not necessarily deduped automatically but we should not store
	a map of java.lang.Double to java.lang.Double cuz that would be very inefficient.
	*
	public Number dedup(Number n);
	*/
	
	public Tuple dedup(Tuple tuple);
	
	public default Tuple tupleDedup(Number... childs){
		return dedup(tuple(childs));
	}
	
	/** does next piece of work then returns hasWork() aka is there more work to do in later calls */
	public boolean nextState();
	
	/** Usually is last return value of nextState(), but you might have done push or pop etc since then,
	or nextState may have never been called since last clear(...) (aka resets the VM).
	True if theres more work for nextState() to do, so call it again if you want,
	else just dont do the work, either way the VM doesnt care, like you could clear(...) the VM or continue that work.
	*/
	public boolean hasWork();
	
	/** is it a tuple of at least size 2 whose first Number isValidBytecode?
	Every Number is a valid function that will run if called on another function,
	but not every Number can run bytecode. Invalid bytecode never runs.
	*/
	public default boolean isFunctionContainingValidBytecode(Number function){
		return (function instanceof Tuple) && ((Tuple)function).size()>=2 && isValidBytecode(((Tuple)function).get(0));
	}
	
	//TODO default implementation of this in the VM interface,
	//OLD... and just call this by super.isValidBytecode to save it in the 2 booleans of caching it in NumberArrayTuple.
	//If param n is valid bytecode then it is tuple(bytecodeDoubles,anything) or more than size 2 tuple like that,
	//where bytecodeDoubles contains only doubles (no tuples) and checks things about the specific combo of doubles.
	//Actually, this will be the function.get(0) which is the bytecodeDoubles.
	public default boolean isValidBytecode(Number n){
		$();
		if(!(n instanceof Tuple)) return false;
		Tuple bytecode = (Tuple)n;
		$(bytecode.size()); //FIXME pay for memory of int[] (return it to gas at end?) and the compute cycles of this linear pass through it
		if(bytecode.size() == 0) return false; //smallest valid bytecode is size 1
		if(bytecode.get(bytecode.size()-1) is not Op.ret) return false;
		if(bytecode.size() > VM.maxPossibleBytecodeLen) return false;
		int[] relStackHeight = new int[bytecode.size()];
		//Each opcode at an index (opcodeIndex) jumps to 2 possible indexs (dont actually need to compute the % but in abstract math
		//to make sure the directedGraph is closed):
		//(opcodeIndex+1)%bytecode.size() or opcodeIndex+getRelJump(bytecode.get(opcodeIndex))
		//where getRelJump returns a signed int23 (or int22? or exactly how big?) and both of those must be in 0..bytecode.size()-1,
		//and last opcode must be Op.ret, and considering lsp and hsp.
		//Does not verify gas cuz thats enforced in VM.nextState(). Might create halting problem paradoxes to do it in the lambda itself?
		//Either way, its probably far more efficient and secure to compute it in nextState(),
		//but the main reason to do it in nextState is it allows different optimizations to charge different amounts of gas
		//for the same calculation, such as GPU vs CPU vs many CPUs in parallel vs number crunching clouds
		//vs you could even in theory compute it on pen and paper its so simple
		//and run a lambdasmiterVM through the snailmail or on a bunch of USB memory sticks people exchange
		//in person for example if the whole internet were to go down the networking layer still would exist in usb sticks,
		//though in that case would probably want to use a less expensive secureHash algorithm to generate ids than sha3_256
		//for example, which you can use whatever algorithm you like or multiple algorithms since an id generator
		//is just another function derived within the system that looks at another function and returns a bitstring (a function)
		//that some people or computers may choose to use as an id, if they believe its secure against hash collisions.
		for(int opcodeIndex=0; opcodeIndex<relStackHeight.length-1; opcodeIndex++){ //the ip++ jump (or say it as lack of a jump)
			Number opcodeNumber = bytecode.get(opcodeIndex);
			//If you want literal tuples, put them somewhere inside function.get(1). Bytecode goes in function.get(0).
			if(!(opcodeNumber instanceof Double)) return false;
			double opcode = bytecode.get(opcodeIndex).doubleValue();
			int ins = opIns(opcode), outs = opOuts(opcode), changeInStackHeight = outs-ins;
			relStackHeight[opcodeIndex+1] = relStackHeight[opcodeIndex]+changeInStackHeight; 
		}
		//FIXME verify "push and pop an int23 amount"
		for(int opcodeIndex=0; opcodeIndex<relStackHeight.length; opcodeIndex++){
			//the opcodeIndex+getRelJump(bytecode.get(opcodeIndex)) jump which happens if numstack to is 0 when Op.jump, else ip++.
			double opcode = bytecode.get(opcodeIndex).doubleValue();
			int jumpToOpcodeIndex = opcodeIndex+getRelJump(opcode);
			int correctStackHeigtA = relStackHeight[jumpToOpcodeIndex]; //computed in the "ip++ jump" loop
			int ins = opIns(opcode), outs = opOuts(opcode), changeInStackHeight = outs-ins;
			int correctStackHeightB = relStackHeight[opcodeIndex]+changeInStackHeight;
			if(correctStackHeigtA != correctStackHeightB) {
				//2 paths found to jumpToOpcodeIndex which sum different stack heights by their sequences of opcodes
				//each as outs-ins. TODO also check lsp.
				return false;
			}
		}
		
		//FIXME is NaN and -Infinity allowed? Dont want to check for NaN (opcode!=opcode) cuz its slow,
		//and (todo verify) i dont think it will hurt anything to have nans as long as can still cast it to int (todo verify).
		//FIXME must be able to push a literal NaN onto stack,
		//though could just create an opcode to do that or push 2 0s and divide them.
		
		//FIXME few more things to check, but those 2 loops are the slowest part
		//(or maybe equally slow, a few more loops or things to do in those loops).
		return true;
	}
	
	public default int getRelJump(double opcode){
		return (((int)opcode)<<1)>>9; //signed int23 just above the low byte,
		//which will be used as rel jump here, or other things for opcodes other than Op.jump, such as push and pop an int23 amount.
	}
	
	/** this is abstract only cuz it needs a cache of Op[256] from Op.values() to be efficient */
	public abstract boolean changeInStackHeight(double opcode);
	
	/** this is abstract only cuz it needs a cache of Op[256] from Op.values() to be efficient */
	public abstract int opOuts(double opcode);
	
	/** this is abstract only cuz it needs a cache of Op[256] from Op.values() to be efficient */
	public int opIns(double opcode);
	
	/** These (push pop etc) are potentially unsafe when not done by VM, so just
	use Tuple as UnaryOperator<Tuple> to call functions on functions to create/find functions,
	and let VM handle the details and optimizations.
	
	/** param is double or Tuple. FIXME what happens if stack gets full?
	Copy it to a stack twice as big? Or use gas to make it ever more expensive to push things as it gets fuller
	so it will always run out of gas before running out of stack?
	*
	public void push(Number n);
	*/
	
	/** returns double or Tuple. FIXME what if stack is empty? *
	public Number pop();
	
	/** returns double or Tuple. FIXME what if stack is empty? *
	public Number peek();
	*/
	
	/** does the CALL opcode, which is same as using Tuple as UnaryOperator<Tuple> on the Tuple returned by stack(),
	except does not copy the stack to a Tuple before (for param of UnaryOperator) or after (the stack after UnaryOperator acts on it,
	what the UnaryOperator would return except thats just down to lsp, below which that function call is independent of.
	TODO easy way to set gas without clear(double) the whole VM?
	FIXME this is only allowed if nextState() has no more work to do (is halted, nothing below to return to,
	the whole contents of stack below are what the outermost call returned the stack stays that way until given further work).
	*
	public void call(){
		TODO
	}*/
	
	/*I dont know how to do this without it being in a Tuple of bytecode since it might recurse and not know where to return back to,
	so just let that be handled inside VM, and instead just provide a CALL op, which is all the user of a VM needs to do
	after pushing things onto stack to start the first call.
	public void unsafe_doOp(double opcode);
	*/
	
	/** all opcode types except when an opcode is a nonnegative double
	it pushes itself onto stack. All other opcodes are negative doubles.
	FIXME should opcode 0.0 be such a literal to push onto stack or usable as (byte)0 opcode type? 
	*/
	public static final int maxPossibleOpcodeTypes = 256;
	
	public static final int maxPossibleBytecodeLen = 1<<23;
	
	/** double can exactly represent all integers in range plus/minus 2 exponent 53,
	and it must always be an integer so it doesnt counterfeit gas by intentional roundoff
	such as it could use a small amount of gas which roundoffs to the exact same amount of gas left,
	therefore going into an infinite loop or nonhalter, therefore contradicting the design
	that every call halts and within gas limits such as within .01 seconds before
	the next video frame of a game or some other chosen limit per call deeper on stack.
	Gas is normally reset/clear() after each next video frame of a game or simulation
	or whatever next interaction with the outside world. 
	*/
	public final double maxPossibleGas = Math.pow(2, 53);
	
	/*TODO get list of opcodes and designs from mindmap
	
	TODO what datastruct for node? A node is a double or a tuple of nodes.
	For now, lets do it the slow way where every node is an object,
	but will be far more optimized later.
	*
	
	
	/** low stack pointer, below sp *
	int lowSp;
	
	/** main/high stack pointer *
	int sp
	
	double gas
	
	double lowGas
	
	/** mutable stack. immutable heap. *
	Node[] stack
	
	
	bunch of Nodes (every Node is immutable) on heap, but basically they get garbcoled if they're not reachable from stack.
	
	public void nextState();
	
	/** tuple in. tuple out.
	public Node call(Node tupleIn)
	*/
			
	/** Self contained function, not depending on other systems which use that function.
	a certain universal lambda function thats also a pattern calculus function
	and always curries 15 params.
	*
	public Number occamsfuncer();
	
	/** Self contained function, not depending on other systems which use that function.
	A certain universal lambda function: Lf.f(Lx.Ly.Lz.xz(yz))(La.Lb.a) aka Lf.fSK
	*
	public Number iota();
	
	/** Self contained function, not depending on other systems which use that function.
	Nock is the core function used by urbit, which everything in urbit,
	except the namespace and ethereum parts, is derived from.
	A certain function... TODO im unsure what kind of function it is.
	Since urbit is based on unsigned bigints,
	store 32 bits in each double and use tuple of those as bigint.
	*
	public Number urbitnock();
	
	//public Number rule110();
	
	/** This might be so complex that theres more than 1 obvious way to implement it.
	Self contained function, not depending on other systems which use that function. *
	public Number webasm();
	*/

}
