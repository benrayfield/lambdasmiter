package lambdasmiter;

public enum Op{
	
	/** does not modify whats on stack top. just reads it and compares its double value to 0 (tuples count as 0 here) */
	jump(1,1,false,false),
	
	verifyBytecode(1,1,false,false),
	
	pushGas(0,1,false,true),
	
	/** FIXME maybe should merge with call
	If not merge this with call (so dont need separate op), then this is similar to occamsfuncer nondet spend op.
	*/
	//spend(),
	
	/** FIXME dont use nulls for ins and outs here? */
	smite(null,null,false,false),
	
	call(null,null,true,false),
	
	forkNM(null,null,true,false),
	
	/** the lambda S from SKICalculus aka Lx.Ly.Lz.xz(yz),
	or todo at least a stack based form of it.
	x = pop(); y = pop(); z = pop(); push(call(call(x,z),call(y,z))); or something like that?
	but might want the pairakatuplesize2<stuff,bytecode> representation of funcs instead
	andOr somehow related to this. TODO finish designing it.
	<br><br>
	The K/T normally used with S (like in unlambda and occamsfuncer)
	would be to just read the top, or is it second from top, of stack, or something like that?
	<br><br>
	Together these are turingComplete, and will be very useful for organizing the more optimized lambdas
	that are functions of tuple to tuple.
	<br><br>
	But I might still want a form of pairakatuplesize2<stuff,bytecode> of these basic parts of lambdas.
	<br><br>
	WARNING: the S lambda is the main reason occamsfuncer needs the very expensive dedup.
	*
	S(3,1,true,false),
	*/
	FIXME if this is an op, then it has to be designed very carefully similar to the CALL and forkNM ops
	cuz it is subject to halting problem, while most ops have a cost limited by either constant or at worst stack size.
	So maybe S should be derived from call, using bytecode, to not complicate the VM.
	But its extremely useful, combined with K/T its turing complete, so maybe its worth that complexity.
	But S is normally used with either 0 1 or 2 lambdas curried with it, and it only does interesting things
	on the third curry, so maybe pairakatuplesize2<stuff,bytecode> is the most natural form for it,
	where "stuff" would contain those 0 1 or 2 curried lambdas (or other tuple kind of optimized lambdas).
	I want S etc so I can dragAndDrop lambda onto lambda to find/create lambda instead of having
	to think about stack and tuples. I want to use the system as if its made only of lambdas
	that curry 1 thing at a time and return 1 thing, leaving tuples as optimizations or stuff deeper
	inside those simpler lambdas.
	They are the high level thoughts to organize the low level number crunching.
	
	
	
	
	
	
	
	
	
	//ops that have bigO of at worst stack size (most of them have bigO of constant) below...
	
	copyStackTopToMidStack(null,null,false,false),
	
	copyHeapToStack(null,null,false,false),
	
	/** FIXME I was about to write ins=null and outs=1 cuz it copies a range of stack to 1 new tuple on top of stack,
	but that seems to imply that stack height is reduced by 1-varSize, but stack height instead increases by 1.
	*/
	copyStackToHeap(null,null,false,false),
	//lsmOpPushFromMidStack, is this the same as lsmOpPushFromMidStack?

	pair(2,1,false,false),
	
	//TODO various lsmOps for 32 bit logic, including and or not xor >> >>> << rotate nand nor minority majority
	
	//TODO various lsmOps for 32 bit arithmetic such as plus mult negate mod etc, which may be optimized in Int32Array in js
	
	nandOf2Bits(2,1,false,false),
	
	//FIXME maybe ops should use the 24 extra bits, other than the 8 bits to choose op type,
	//to choose how much to pop or push all 0s, instead of having log number of some op types in this enum.
	
	pop(1,0,false,false),
	
	pop4(1,0,false,false),
	
	pop16(1,0,false,false),
	
	pop64(1,0,false,false),
	
	pop256(1,0,false,false),
	
	pop1k(1,0,false,false),
	
	pop4k(1,0,false,false),
	
	pop16k(1,0,false,false),
	
	pop64k(1,0,false,false),
	
	pop256k(1,0,false,false),
	
	pop1m(1,0,false,false),
	
	pop4m(1,0,false,false),
	
	pop16m(1,0,false,false),
	
	pop64m(1,0,false,false),
	
	pop256m(1,0,false,false),
	
	pop1g(1,0,false,false),
	
	/** push(peek()) */
	dup(1,2,false,false),
	
	noop(0,0,false,false),
	
	pushIsVerifiedOrNot(1,1,false,false),
	
	tupleLen(1,1,false,false),
	
	nodeType(1,1,false,false),
	
	/** FIXME this might be replaced by reaching the last index of a tuple used as opcode,
	or maybe this ret op would be at that last index.
	FIXME i dont know if this is the right number of ins and outs, but I do know its some constant.
	*/
	ret(0,0,false,false),
	
	add(2,1,false,false),
	
	mul(2,1,false,false),
	
	div(2,1,false,false),
	
	oneDiv(1,1,false,false),
	
	mod(2,1,false,false),
	
	/** push(-pop()) */
	neg(1,1,false,false),
	
	//todo lsmOps for copying between lsmLowSp+[0..63] and sp-[0..63] and top of stack, or something like that
	
	min(2,1,false,false),
	
	max(2,1,false,false);
	
	//TODO truncateIntoMinAndMax isInfiniteOrNan etc
	
	/** UPDATE: use null to mean dynamic on that part.
	isDynamic || (VM.hsp-VM.lsp <= ins) (fixme, is this offby1?).
	isDynamic OR Change in stack height = outs-ins.
	If isDynamic then ignore ins and outs, which are both set to -1.
	TODO have 2 isDynamic fields, as some ops are dynamic only on the ins side or outs side
	and constant size on the other side, but for now (2020-12-17) those are marked as isDynamic.
	*/
	public final Integer ins;
	
	/** see comment of ins */
	public final Integer outs;
	
	/** variable size ins andOr variable size outs, such as copying a range between stack and heap *
	public final boolean isDynamic;
	*/
	
	/** true if the call can cause a turing complete thing to happen before it ends
	therefore gas has to be checked during it and use metastack parts such as remembering
	lsp and hsp and how many bytecodes calling bytecodes deep it is.
	If false, then gas is computed and prepad before running the op (instead of during).
	Either way, everything always halts and never uses more compute resources
	(gas, representing memory allocated more than there is now, compute cycles,
	and other kinds of compute resources, whatever resources the local kind of lambdadmiterVM
	may need to choose which lambda calls get how much of what kinds as different computers
	have different efficiencies for different kinds of calculations like some have
	a very strong GPU while others have a weak GPU and lots of CPUs
	and some have more or less memory, network bandwidth to/from places known to be in global sandbox,
	caching things on harddrive vs memory vs network,
	calling of cloud number crunching services (if they can do exactly the calculation requested
	without depending on state such as internet addresses as it has to be repeatable in
	every lambdasmiterVM even if they dont have access to the same hardware or systems
	else set the dirty bit (see occamsfuncer mutableWrapperLambda theory for digsig way
	of using pubkey as a lambda so its past actions can be repeated as verified by sigs
	even if it cant necessarily do any calls that havent been observed so far so clouds and other
	mutable systems like game controllers and sensors might easier fit in there), etc).
	*/
	public final boolean isTuring;
	
	/** If false, is deterministic and repeatable by anyone who has any call in the long history that led to this possible state.
	If true, then its not repeatable, such as depends on nondeterministic optimizations of
	how much gas certain calculations cost like lazy dedup of tuples within tuples deeply
	only pays memory cost when allocate a new tuple or a smaller cost to lookup the existing tuple of same content,
	or that different computers have a different cost ratio of calculations done on CPU vs GPU
	andOr other costs tuned to the actual compute resources available.
	Its isDirty to read how much gas is available.
	Its isDirty to be smited (give up on a calculation for being too expensive, back out of it)
	for running out of gas (at some stack height, which lower on stack does catch/else for what to do if runs out of gas).
	Its NOT isDirty to call spend which is like try/catch for running out of gas IF it does not run out of gas
	cuz thats a repeatable calculation by any other computer which makes that call and does not run out of gas
	even if they had different amounts of gas and different costs for the calculation,
	they both get the same id for every input and output and function in the system
	so they can share lambdas across the internet and do calculations together without things breaking.
	<br><br>
	isDirty sticks to any calculations resulting from an isDirty calculation even if those later ops arent isDirty.
	*/
	public final boolean isDirty;
	
	Op(Integer ins, Integer outs, boolean isTuring, boolean isDirty){
		this.ins = ins;
		this.outs = outs;
		this.isDirty = isDirty;
	}
	
}