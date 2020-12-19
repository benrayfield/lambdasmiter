/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter;

public enum Op{
	
	/** does not modify whats on stack top. just reads it and compares its double value to 0 (tuples count as 0 here).
	<br><br>
	It jumps if stack top is 0 (or is a tuple, whose double value is always 0).
	I chose that instead of jump if nonzero, cuz only about 1 in 2^63.99 double values are 0,
	and jumping tends to cause cache misses (inefficient), so in randomly generated code,
	I want it to jump less often. All code halts, even if that code is randomly generated,
	which will be very useful for AI research, and getting ever more strategic,
	code that thinks about and designs code.
	BUT maybe Tuple should not return 0 as a double, for that same reason,
	but I'm concerned that if tuple returns something other than 0 it might interfere with autoboxing optimization.
	Maybe tuple as a double should return its own length? A constant is less likely to interfere with autoboxing.
	Maybe it should always return 1? Or Infinity? Certainly not NaN cuz thats often slow.
	*/
	jump(1,1,false,false),
	
	verifyFunctionContainingBytecode(1,1,false,false),
	
	pushGas(0,1,false,true),
	
	/** FIXME maybe should merge with call
	If not merge this with call (so dont need separate op), then this is similar to occamsfuncer nondet spend op.
	*/
	//spend(),
	
	/** FIXME dont use nulls for ins and outs here? */
	smite(null,null,false,false),
	
	/**"TODO How SMITE will be detected see comment below"
	OR, should isSmited be a register similar to isDirty?
	/*How SMITE will be detected.
	X is bytecode running now. X puts Y at top of stack (just the 1 top stack index).
	X's next opcode is CALL.
	The call ends.
	X sees either 0 or 1 at top of stack. 1 if it worked as normal. 0 if it was smited.
	X can either ignore that or use it to JUMP (which jumps if stack top is 0, else goes forward 1 opcode as usual),
	so X can jump based on if the call was smited or not.
	If its 0 then [if allowDirty register is true, then isDirty register is automatically set to true,
	else !allowDirty so infiniteloop/smite X which the caller of X will see 0 meaning X was smited,
	but only if the caller of X has allowDirty==true (as it can be tightened from true to false but not false to true,
	deeper in stack)].
	<br><br>
	Example:
	Top 5 things on stack: [0 1 0 1 complexNumMultiply] --CALL--> [-1 0 0 0 didTheCallWork]
	where didTheCallWork is 0 if was smited or is 1 if worked, so hopefully its [-1 0 0 0 1]
	then pop3 to get [-1 0] aka the complexNumber -1 cuz sqrt(-1)*sqrt(-1)=-1.
	(TODO should it be real imaginary, or imaginary real? probably real imaginary since thats how most people write it.
	and, i did it both ways in the example above, fix it)
	<br><br>
	Its a natural place to put the didTheCallWork bit since it goes the same place the function was.
	*/
	callWithoutTighten(null,null,true,false),
	
	/** tightenConstraintsDeeperOnStack.
	The constraints this tightens any or all of are:
	<br><br>
	-- VM.allowDirty can change from true (nondeterministic) to false (deterministic),
		but cant change from false to true except by returning back to where it already was true.
	<br><br>
	-- VM.minGas can increase but not decrease, so (VM.gas-VM.minGas) can decrease but not increase,
		which means at some point on the stack it wants to only allow a deeper call access to part of that gas
		and to get back from that whatever it doesnt use.
	<br><br>
	-- VM.lsp can increase but not decrease, so (VM.hsp-VM.lsp) can decrease but not increase except by opcodes
		which increase and decrease hsp but those guarantee that (VM.hsp-VM.lsp) is the same before and after every call.
		(VM.hsp-VM.lsp) being same before and after every call may be annoying or inefficient like if you do
		complexNumMultiply you want to put in 4 doubles and get back 2 doubles
		(plus the complexNumMultiply lambda itself is above them on the stack)
		so there has to be padding such as stack[lsp..hsp],
		where complexNumMultiply is an anonymous generated and verified bytecode (not built in):
		[x.real x.imaginary y.real y.imaginary complexNumMultiply] (just the highest 5 Number on stack, no var names)
		-> [z.real z.imaginary 0 0 0] then pop3 so [z.real z.imaginary] is on top.
		complexNumMultiply is not allowed to pop3. Only the caller of complexNumMultiply can,
		and that change of hsp by -3 has to be restored to whatever it was when the current call started,
		so the caller lower on stack sees their same (VM.hsp-VM.lsp) before and after calling this current calculation,
		which they may have TIGHTEN (this op) to hsp-lsp=5 (or any hsp-lsp>=5) just before calling this current calculation.
	<br><br>
	Constraints are all relaxed to maximum possibilities in VM.clear(double)
	which is normally called every 1./60 second between video frames of a game or
	between each 2 consecutive interactions with the outside world.
	It can be very infrequent (days, if its a large batch to finish by then,
	though you might need more bits of precision of the gas counter if its more than minutes)
	or even faster (few microseconds, if its doing a very small amount of work),
	depending what you're using it for.
	<br><br>
	Cant relax constraints but can tighten them, which applies only in calls deeper on stack,
	and when those return (either normally or by running out of gas), the constraints at this stack height
	are back to how they were before the call, but only below VM.lsp cuz stack[lsp..hsp] may have been modified,
	and in pure lambda math that is modelled as whats here on stack is whatever that deeper call returned,
	even if it didnt get all its work done, like a mutable range of 1 million Numbers can be read and written
	by deeper calls, and each of those calls is modelled as a function of 1 million Numbers to 1 million Numbers,
	which this current calculation being done on stack is replaced by it similar to lispProgn.
	This current calculation can be reproduced by things which occurred either earlier or lower than lsp on stack.
	The mutable stack is an optimization of pure lambdas which have nothing mutable about them at all,
	and if you want to use them without that optimization, then use Tuple as UnaryOperator<Tuple>
	where the input and output Tuples are stack[lsp..hsp] and are always the same size
	and you can call it on a bigger or equal stack[lsp..hsp] range than that but not a smaller one,
	and actually you can call it on a smaller one but it just wont eval and will do a no-op or infinite loop
	or something like that (todo choose design of what it must deterministicly do if stack[lsp..hsp]
	is smaller than the range it reads/writes.
	<br><br>
	TODO I'm undecided if this should happen in the CALL and FORKMN opcodes,
	vs if it should be a separate opcode.
	*
	tighten(TODO...),
	*/
	callAndTighten(null,null,true,false),
	
	/** a kind of call */
	forkNM(null,null,true,false),
	
	
	
	
	
	
	
	
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
	If false, then gas is computed and prepaid before running the op (instead of during).
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
		this.isTuring = isTuring;
		this.isDirty = isDirty;
	}
	
}
//////////////



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
*
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
*/