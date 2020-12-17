package lambdasmiter;

import lambdasmiter.impl.Tuple;

public interface VM{
	
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
	
	
	/** funcall(stack[lsp..hsp]) -> stack[lsp..hsp] of same size.
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
	public Tuple funcall(Tuple stackTop);
	
	/** empties the VM and refills gas.
	This is normally called once per video frame of a game (such as 1./60 seconds,
	you use statistics to estimate how much gas it can spend in that time)
	or whatever this lambdasmiterVM
	is being used for it interacts with the outside world between each 2 calls of this.
	*/
	public void clear(double fillGasUpTo);
	//TODO put Tuple on stack instead of just emptying it? the counterpart of stack()?
			
	/** copies stack to a Tuple, or may be lazyEvaled if find some kind of optimization.
	This is normally called only after a loop of nextState() ends as it has all returned
	and the last return value is whats left on the stack.
	If you call this before that happens, then theres data missing
	as it has to remember lsp and hsp and stack of bytecode etc.
	*/
	public Tuple stack();
	
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
	
	public void nextState();
	
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
