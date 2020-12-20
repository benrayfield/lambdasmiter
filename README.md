# lambdasmiter
Pure functional VM which is a universal lambda function (combinator) and a pattern calculus function (TODO 4 independent realtime interoperable VMs to start with: java, browserJavascript, python, android) will derive its own prog lang syntaxes. A number is immutable and is a float64 or a list of numbers. A lambda is a number. Few microseconds bytecode verifier. Like SQL, always halts (smites lag and infinite loops and other halting problem things that plague the software industry). GPU. Music tools. Gaming-low-lag. Screen blit. Fork millions of threads. Generated lambdas + bigdata safely share online at 10% lightspeed.

(incomplete but based on occamsfuncer I know it will work, but am uncertain how efficient, but benchmarked java.lang.Number autoboxing double and Tuple in the same Number[] stack as few million tuples created per second and 50 million tuples read per second and 300 million doubles per second, for just reading and writing the stack, but not in the VM yet (see SimpleVM.nextState), and planning to hook it into https://github.com/benrayfield/lazycl/ later which I've clocked at 1.1+ teraflops for low IO or 0.1 teraflops for matrix multiply, and I've clocked music tools at 60 million (double,double)->double ops per second in a forest of arbitrary musical instrument calculations anything can be built from such as in my audivolv musical instrument evolver and other experiments. This lambdasmiterVM is kind of the "glue code" for a bunch of other stuff I've made, to port it into a unified pure functional piece of raw computing power and simplicity and scientific repeatability and low lag shareability.

There will be a variety of realtime interoperable (at gaming-low-lag) lambdasmiterVMs including javascript, java, python, android, number crunching clouds, andOr variety of other systems, all able to share generated lambda functions with eachother safely by automatic low lag formal-verification of their possible behaviors, that it will stay within the global sandbox and will always halt (like SQL always halts but more advanced and more precise control of resource limits) within the allowed amount of allocating more memory and number of compute cycles which at each height on stack can Op.tighten those constraints which can only loosen back after it returns, either normally or by running out of given compute resources, on microsecond scale. What one lambdasmiterVM can do, all lambdasmiterVMs can do, but at different efficiency for different kinds of calculations such as some computers have strong GPU while others have 64 CPUs and some have more or less memory etc. Its a precise spec for a model of computing for them to interoperate without so much as a blip in a live sound transform between speakers and microphone if you throw a function of a new musical instrument across the internet which gets automaticly bytecode verified to always halt (like SQL) and hot swaps it with another musical instrument thats literally still vibrating in the air between your speakers and microphone, before that echo goes quiet it swaps it, and you wont even be able to detect that it was hot swapped, like you're holding an electric guitar (I've literally plugged an electric guitar into analog microphone hole of computer, though never been good at playing it (listen to this amateur music I made years ago when I was experimenting with music tools more than number crunching and AI... https://soundcloud.com/ben-rayfield-550270977/audivolv016 I've been there but I'm not saying use this software cuz of my music skills, or how interesting of content I've made, instead (when I get it working better) it should be useful cuz it makes nearly anything possible (which I can actually prove using lambda math, for example I could prove to scientific standards that every physics simulation that ever has, does, or could exist, is within that space of possibilities), a space for millions of people to explore possibilities together, beyond what even I can imagine even though I'm designing the prototypes), maybe we could help eachother with new more interesting sound transforms, AI thought processes, etc, to make it easier and more game-like to have musical fun together, fractals, AIs building AIs, etc... Professional musicians at live performances, such as at the largest of concerts, would probably be satisfied with the extremely low lag and turing complete flexibility within the literal space of all possible musical instruments reachable near instantly, IN LINUX which has practically low SourceDataLine and TargetDataLine sound lag in java (or other non-java systems maybe, dont trust WebAudioAPI for low lag but its ScriptNode is turing complete flexible, but others may be satisfied with weaker systems as lambdasmiterVMs will in theory be available in potentially any and every system someone wants one in, very simple to build to interoperate with eachother in realtime, but for serious music tools you'd want linux, and may in theory support up to thousands of analog holes at once if you network many tiny simple computers together or maybe up to 20 on a single ordinary computer thru usb3 or usbc etc) but back to the example of guitar sound transform hot swapping...) and at 10% lightspeed a different instrument pushes it out of your hands and arrives in your hands as if you're smoothly playing one continuous sound. And similarly for GPU number crunching (0.1 second delay for first compile but after that .001 to .01 seconds per opencl ndrange kernel call or clqueue of multiple calls), and same for nearly anything that people might build and play with together, both serious tools and fun artistic things. Security level is planned to be enough to withstand AI botnets, out of order events such as https://en.wikipedia.org/wiki/Relativity_of_simultaneity or just general disorganization, and can operate securely even if 100% of computers are rootkitted it will at worst crawl to a halt or epsilon speed past halted as it is based on patterns of information instead of trust in some lower level of system below it (pattern calculus), especially that no attack on the system will cause it to attack back (which seems very likely due to how it is intentionally designed to drain all forms of social-capital into freeing eachother of dependence on all data-silos and specific hardwares etc, toward the greater good of the users, for example) (and I personally will have no ability to remote control your lambdasmiterVMs, as I hope to asap create a balance ecosystem of realtime interoperable lambdasmiterVMs that nobody can control OTHER THAN to compute exactly the lambda math they are designed to) as it is too easy to be tricked in the space of all possibilities (and its just more efficient to build more neutral tools faster than they can be destroyed, similar to the extremely reduced evolutionary fitness of "poison ivy" due to attacking those who touch it vs more neutral plants, keeping in mind that the "creatures" of "conways game of life" and their sparse dimensional forms up to useful software constructs, are better modelled by population dynamics and gametheory and evolution than by ordinary software theory in this system even though it can very directly compute the 1 trillionth digit of pi or whatever you need), though I cant speak for the actions of systems which are already under malicious control what that malicious control may choose to do that this system does not tell it to), while being unable to create a botnet itself, though it could simulate them within the global sandbox safely similar to how  https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life can compute anything possible to compute and has gliders and other replicators, little creatures that build more of eacdhother and variations of eachother, except unlike conways game of life, this is sparse dimensional so has same bigO as a RandomAccessMachine.

This is loosely based on a working model of self-modifying computing in occamsfuncer which has testcases working up to calling a derived equals function on itself and what that returns call it on the equals again equals(equals)(equals) it it says true, but occamsfuncer is much harder to optimize than this and I need to get something online. Smites infinite loops at few microseconds time precision instead of the usual multiple seconds that it takes to back out of code that gets out of control.
Always halts, but is not always turingComplete, but the only turingCompleteness sacrificed is that turingCompleteness says it can use as much memory and compute cycles as it wants, vs in this model of computing, things lower on the stack can further limit the number of compute cycles and amount more of memory allowed in deeper calls on the stack, which can each tighten such limits but cant loosen them, and branch one way or another depending if a call finished normally vs gave up early due to not enough compute resources. Always halts such as within 0.02 seconds if you want it to guarantee halting before the next video frame of a game is displayed.

LambdasmiterVMs can in theory continue operating on USB sticks shared in person and plugging any random 2 of them together in the same computer, in many combos similar to a liquid mixing, even if the entire internet goes down, even if every single piece of tech on the planet is destroyed and we are living back in the days of cavemen, lambdasmiterVM could in theory run on pen and paper its so simple, or drawings on cave walls, though very very slow (but still faster than other ways of doing math with pen and paper, like maybe you could teach 5 year olds to compute exponents by repeated squaring with pen and paper, using this kind of math, faster than any known other way to compute exponents by repeated squaring on pen and paper but same bigO as the usual methods in computers), but much faster than any other system that I'm aware of could run on cave paintings or usb sticks. On the other hand, its bytecode verifier and throwing functions across the internet is planned to be so fast that it is actually affected by time dilation, not during the time of input and output and computing tiny pieces of work in individual devices but making up for that in that the internet is already on the scale of fractions of lightspeed in some cases, such as gamers often feel in many games that light moves 186 miles per millisecond.

```
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

UPDATE to bytecode verifying, see VM.java for half the bytecode verifier already built...
Bytecode verifying plan[[[
/** always quickly returns, and even faster from cache if its not the first call */
public boolean isValidBytecode(){
if(cache_isCertainlyVerified) return true;
if(cache_isCertainlyFailedVerify) return false;
throw new RuntimeException("TODO verify (see SimpleVM.nextState opVerify, which should call this, then cache in 1 of those 2 booleans");
//its verified if from tuple[0] to tuple[tuple.length-1], every double's int23 (above the low byte)
//at tuple[i] is i+int23 (signed int23) and that is in range 0 to tuple.length-1,
//and all paths in that where each index branches to 1 or 2 other indexs,
//summing the changes in stack height (like multiply pops 2 and pushes 1 so is -1 to stack height)
//on all paths, leads to the same constant stack height (rel to stack height at [0])
//at each index. If theres multiple paths to get from [i] to [j] then they have to all
//sum the same difference in stack height, so each index has a specific stack height thats
//stackHeight of [0] plus its own stack height,
//and the last index branches to the first index but never actually goes there
//as the last opcode must be Op.ret to return.
//Does not verify anything about gas as thats enforced in VM.nextState().
//Verifies VM.lsp and VM.hsp. Every op must not read or write anything on stack below VM.lsp
//nor above VM.hsp (or equal to it?) and can choose to increase VM.lsp (up to current hsp)
//but cant choose to decrease lsp (only happens when RETURN, so lsp and hsp are same before and after a call.
//If you want to push a large array onto stack, you have to loop around multiple funcs
//that calls itself that calls itself... and so on, adding a constant height to stack each time,
//but choosing to keep lsp the same, so hsp-lsp increases,
//and inside that most inner call, it can read and write in that large block of memory
//such as blitting rectangles of doubleARGB (32 bit color, ignoring high bits) pixels,
//matmul by Op.forkNM, etc.
//
//TODO I'd like to find some way to not have to recurse so deep, like an op that just
//pushes a large amount of data to stack, such as push1 push256 push64k push16m etc
//or have more bits of opcode than just those 256 so can do it all in 1 opcode,
//yes... that would work. Something like that. Cuz stack height is still the same.
//You just cant push a variable amount on stack based on content of the stack,
//unless the bytecode has a few sizes such as log number of areas in the bytecode
//that are basically log number of different amounts to push on stack, log number
//of different functions.
//
//But its not as big a problem as it sounds, since you can just generate new bytecode
//and verify it and jump to it in a few microseconds in theory,
//and the system will support exabytes of bytecode streaming all around the internet sparsely at gaming low lag
//(which you dont need to sign with a digital signature since its verified by its possible behaviors not by trust,
//especially that its verified to stay within global sandbox and to always halt, like SQL always halts but more advanced,
//which for example means that even though it can contain a simulated botnet like conways game of life
//contains gliders and other replicators, it can not itself be a botnet and does not give execute permission to anything).
//Thats why I keep the bytecode very simple.
}
]]]

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
	even if it didnt get all its work done, like a mutable range of 1 million Nummbers can be read and written
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
	*/
	tighten(TODO...),
	
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
		this.isDirty = isDirty;
	}
	
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
```

Registers in the whole system in a double[], which can be struct-like or number,
	and maybe some struct-like javascript objects as optimizations of that:

UPDATE: these are not the exact registers but similar, see SimpleVM.java for progress on that...

ip - stack instruction pointer.

sp - stack pointer

gas - amount of computing resources (adjusting cost ratio of allocBitOfMem vs oneComputeCycle based on supply and demand)

ipull - stream instruction pointer, an index into a double[] (aka Float64Array) on heap which increments and returns the next double at each call of a certain opcode which occurs on the stack, so 2 levels of instructionPointer: ip and sip (TODO think of better names for these registers).

ipullFrom - ipullFrom(ipull) is the current stream instruction. This call is stateless, as is ipullFrom(ipull-1). ipullFrom is an immutable double[].

"so the stack can kind of print itself from that single pass over the double[] opcodes" (in theory, TODO).

All registers are writable by the double/float64 instruction at *ip, such as pushing 10 megabytes onto the stack with or without clearing it to 0s
or such as generating another tuple (such as a screen sized array of doubleARGB pixels) then generating a photoshop-like op
then "jumping to" by writing the ipullFrom register and writing 0 to the ipull register, jumping to another stream of opcodes "a photoshop-like op"
which automatically triggers attempted formal-verification of pointer arithmetic to prove it will always halt else "smite itself"
which may find a solution or not but either way will not itself use more computing resources than caller allowed, and so on,
or similarly for evolving musical instruments or minigames.

Imagine formal-verification like your bank lets you tell them how much money you have, but only if you can prove that you would not lie to them in this specific case (even if you lie 99% of the time), or more practically among the interactions of many opensource AIs, and the entire space of possibilities of you and them can be explored sparsely together, and as every hardware probably has an error at some time or another at least rarely, all calculations are repeatable and peer-reviewable, including graphics blits etc, within a public or private space among those who make such statements to eachother and prove or do not prove various things to eachother, as eachother may be motivated to interact in some way or another.

Only has pure math functions, so there is no namespace visible above or below the current stack frame except what is derived such as treemaps or linkedlists.

Will be fast enough to render 3d fractals in a canvas ( like at https://observablehq.com/@robertleeplummerjr/gpu-js-example-mandelbulb ) and to generate new musical instruments which speakers and microphones analog holes can have instruments plugged into, bumpmapped polygons (but far less number of them), high dimensional graphics (miegakure is cool but I think we need tetrations of number of dimensions), screen blitting, etc. But it wont be the fastest system for branching. Its designed with extremely parallel computing as higher priority.

Syntax might look like...
```
S(T)(T)(x)
	returns x, for any x.

callParamOnItself = someOtherFunc({...inlineFunc},5,2,S(T)(T)(x))(someMoreStuff)
callParamOnItself(S(T)(T))
	returns S(T)(T), cuz S(T)(T) is an identityFunc.
callParamOnItself(S(T))
	returns S(T)(S(T))

//FIXME
matmulSequential = {.....
	#top
	sp--
	#ab
	#bc
	#aSize
	#bSize
	#cSize
	#ac //matrix to return
	sp += mul(aSize,cSize) //FIXME is that aSize or *aSize?
	#acEnd
	sp--
	#a
	#b
	#c
	a = 0 //FIXME it appears these should be "ab = *sp; sp++"
	#loopA
		c = 0
		#loopC
			b = 0
			#loopB
				TODO sum mul(*a,*c). FIXME is that the right way of *x *y *z etc?
				TODO use a local var and copy it once into ac? or sum into ac directly?
				FIXME conditional branching, when does ip just increase by 1 and pass the loops
				b++
				b-bSize
			?loopB
			c++
			c-cSize
		?loopC
		a-aSize //FIXME sp++? sp++; *sp?
	?loopA //ip = jumpIf0(*sp,loopA) //else ip+1 as usual
	//return a (tuple size 1 containing a) tuple of size aSize*cSize, copied (or found duplicate of) onto heap and therefore immutable.
	//This is a ptr on stack to that on heap.
	ret = copy(ac,acEnd)
	sp = top //pop whatever pushed
	*(sp-1) = ret //TODO this is inconvenient to have to write function syntax for basic math, but can explore other syntaxs after the bytecode works.
.}
```

Add another register???, which points into a heap array and gives you the next double in it every time a certain op is called,
so the stack can kind of print itself from that single pass over the double[] opcodes aka {...}, while executing and pushing the rest of itself onto stack.
Opcodes are like InputStreamOfDouble, which are streamed onto the stack after the stack finishes executing the last opcode,
which is in theory much faster for very small funcs than copying the whole double[] onto the stack and trying to not overwrite the next opcodes
when a variable size block of memory is pushed, its size depending on computed values such as matmulSequential
pushes a matrix onto stack of size depending on the 2 parameter matrix before loading the opcodes of how to multiply them.
Opcodes currently on the stack and instruction pointer pointing into the stack at them,
may print any turing complete software onto the stack and jump into it, aka self modifying assembly-like code,
except there will be automatic high level views that look like normal lines of code which can be looked into at more or less detail
down to each opcode being a float64 as the whole system runs in a memory space of float64[] but maybe with optimizations of struct-like javascript objects.


Gamers and computing theorists... a theoretical (still lots of work to do) limit->turingComplete universal function where every call pair halts and with recursive timing you can use at the precision of sound vibrations or better. (maybe... appears could be done... lisp-like assembly-like pure-functional human-readable GPU compileable gaming-low-lag kind of float64[] based bytecode/heap/stack with complete reflection ability to the extent of blitting between someone's GPU and someone on another computer's speakers or microphone etc while being at least an approximation of a univeral lambda function and pattern calculus function, where the lambda is the unit of computing instead of the byte... see readme example)  with formally-verified pointers) A universal function for browser javascript loosely based on occamsfuncer. Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition. Get creative. Have fun. Build things together, defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. Can in theory smite through 100,000 infinite loops per second (compared to the usual 0 to 0.1 per second) then get back to your cat memes and multiplayer gaming experiments without missing a step, without delaying the next video frame of the game, without your mouse or text cursor feeling jumpy, and especially for AI experiments where AIs write experimental code to create new AIs and people and AIs play and build together and share lambdas across the internet similar to how most apps share byte streams, except the lambda, not the byte, is the unit of computing here. Its a workaround for the halting problem which smites any lambda call reliably when its about to, or sometimes can be known that it would later, use more memory or compute cycles etc than its allocated thousands per second of such recursive limits within limits per stack height, in a subset of pure functional browser javascript with GPU optimizations and gaming low lag canvas pixels and game controllers and textareas and p2p etc, and whole or sparse system state (stateless lambdas, including Float64Array, in lazy merkle forest) is saveable and loadable in byte arrays, and random or malicious or just fun experimental code shared across the internet can be run safely together in a tiny fraction of a second.

...todo merge the 1 above and 1 below paragraphs, much duplication.

Smites infinite-loops etc like in godel and halting problem theory, 1 million times faster than the current average solution that kind of problem (.00001 second (3000 lightmeters) vs 10 seconds (3 billion lightmeters), your browser tab waits to ask if you want to close a buggy nonresponsive webpage), in theory. Code incomplete, theory workable, earlier experiments succeeded and imply this is a good next step. A universal function for browser javascript loosely based on occamsfuncer (which is already well tested but too slow). About the name, smite is 1 of the opcodes (similar to process killing 1..2^n hypervisors or vmwares (each between around 30 bytes to unlimited) (as if it never existed but its return value still does, kind of like multiverse fork the current reality is the most basic thing to do.
(maybe... appears could be done... lisp-like assembly-like pure-functional human-readable GPU compileable gaming-low-lag kind of float64[] based bytecode/heap/stack with complete reflection ability to the extent of blitting between someone's GPU and someone on another computer's speakers or microphone etc while being at least an approximation of a universal lambda function and pattern calculus function, where the lambda is the unit of computing instead of the byte... see readme example)  with formally-verified pointers). Without formally verified pointers or whatever form the code ends up being, it wouldnt reliably and deterministicly copy lambda functions across the internet (TODO) even though they would still be limited to emulated double[] memory ranges. A universal function for browser javascript loosely based on occamsfuncer. Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition. Get creative. Have fun. Build things together, defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. Can in theory smite through 100,000 infinite loops per second (compared to the usual 0 to 0.1 per second) then get back to your cat memes and multiplayer gaming experiments without missing a step, without delaying the next video frame of the game, without your mouse or text cursor feeling jumpy, and especially for AI experiments where AIs write experimental code to create new AIs and people and AIs play and build together and share lambdas across the internet similar to how most apps share byte streams, except the lambda, not the byte, is the unit of computing here. Its a workaround for the halting problem which smites any lambda call reliably when its about to, or sometimes can be known that it would later, use more memory or compute cycles etc than its allocated thousands per second of such recursive limits within limits per stack height, in a subset of pure functional browser javascript with GPU optimizations and gaming low lag canvas pixels and game controllers and textareas and p2p etc, and whole or sparse system state (stateless lambdas, including Float64Array, in lazy merkle forest) is saveable and loadable in byte arrays, and random or malicious or just fun experimental code shared across the internet can be run safely together in a tiny fraction of a second.


Code incomplete, theory workable, earlier experiments succeeded and imply this is a good next step. A universal function for browser javascript loosely based on occamsfuncer (which is already well tested but too slow). Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition. Get creative. Have fun. Build things together, defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. May become a language simpler than and more pure-functional than python. Unknown. Every possible call of function on function to find/create function, always halts within recursively defined (each stack frame can tighten limits but cant loosen them) limits of computeCycles, memory, and other resources, and is turingComplete except that it lacks infinite compute cycles and infinite memory, and every possible function, data, state of the system, etc, can be saved/loaded in whole or sparsely, and securely by lazy merkle forest, as bytes, as long as it is halted, such as (in theory) streaming games, sound, video, experimental evolved functions.

The problem this is meant to solve: A single bit being 0 when it should have been 1, or being 1 when it should have been 0, can crash the whole browser tab and take 10 seconds to recover, after which the user will probably leave. There are many millions of such bits. There are many pieces of code, made by many different people, that were not all designed to work with eachother, modifying those millions of bits, in a browser tab. Over the years, these js libraries have been fixed when they are observed to break in some combo or another with eachother, but the desire for new features often overpowers the desire to fix bugs especially if those bugs occur infrequently. Its common to go to some website that appears to be working but if you look in browser console (ctrl+shift+i) theres a bunch of errors, cuz often people only fix the errors that stop work from getting done, then pass those errors onto other systems which are further built on cuz its not even our fault someone passed their errors onto us, and before you know it, you're paying a huge team of programmers to create a 5 megabyte file that all it does is say "hello world" and nearly all of the code is to deal with the accumulation of errors and workarounds for them. You have to be very careful for not a single bit to be wrong. Its like a minefield. And theres libraries used together that just want most of the bits to be right most of the time. Very restrictive strategies evolved to deal with that, like https://en.wikipedia.org/wiki/Same-origin_policy and dataUrls not being clickable in most systems https://en.wikipedia.org/wiki/Data_URI_scheme and digital-signatures on code asking to trust them to walk through your local minefield and on https://jsfiddle.net/ you cant make a multiplayer game, cuz these bunch of systems are scared of what might happen if multiple minefields are used together. You have to carefully choose which code to use with which other code. Lambdasmiter is about, lets not have minefields at all. Each bit is written at most once (which gets rid of all the bugs of one piece of code surprising another piece of code by changing the value of bits), counted at the end of each atomic op (such as a gpu or webasm call), and can be read by any code after that. Only atomic ops which can prove their max memory and compute cycles (so even if theres a virus, it has to be a well behaved virus and not infect anything except what memory and time it is offered, after which it must have no effect on anything except other code may choose to read whatever bits it generated, gets rid of all infinite loops and nonhalting code). Any turingComplete system can be built from that. So if you grab 1000 pieces of random code from 1000 places that were not designed to work together other than they implement this spec, then there is no possible combo of calling them on eachother which has an infinite loop, crashes the browser tab, etc, and recursively they can call eachother like allocating a greenthread VM at every push of the stack, or multiple of those for parallel forking, with same or tighter limits on compute cycles and memory in that recursion until it either finishes within those limits or is smited aka back out early aka gives up, says so to the current stack frame, and it branches to the else/catch, instead of crashing. Like try{ ... halting problem ... }catch(ran out of gas){ ... do something else ... }. This means a 5 year old could write more reliable code (such as by drag and drop lambda onto lambda to find or create lambda, visually) in this system than a professional programmer in most systems. The whole internet is in near gridlock (like a traffic jam) cuz of this general kind of problems. I literally once heard a plan to spend 9 months to change a single bit times many copies of that data structure, and there were many meetings about it with like 50 people in them which I was as needed in some of those meetings. This gridlock, its so bad of a problem, that it gridlocks eachother from even seeing most of the effects it causes across other systems to other systems to back to a local system. I think it can be fixed.

Can in theory smite through 100,000 infinite loops per second (compared to the usual 0 to 0.1 per second) then get back to your cat memes and multiplayer gaming experiments without missing a step, without delaying the next video frame of the game, without your mouse or text cursor feeling jumpy, and especially for AI experiments where AIs write experimental code to create new AIs and people and AIs play and build together and share lambdas across the internet similar to how most apps share byte streams, except the lambda, not the byte, is the unit of computing here. Its a workaround for the halting problem which smites any lambda call reliably when its about to, or sometimes can be known that it would later, use more memory or compute cycles etc than its allocated thousands per second of such recursive limits within limits per stack height, in a subset of pure functional browser javascript with GPU optimizations and gaming low lag canvas pixels and game controllers and textareas and p2p etc, and whole or sparse system state (stateless lambdas, including Float64Array, in lazy merkle forest) is saveable and loadable in byte arrays, and random or malicious or just fun experimental code shared across the internet can be run safely together in a tiny fraction of a second. To measure how many infinite loops per second it can "smite / give up on / back out of", you could read how much "gas" is available, and fork nearly half of it (or any chosen amount) to each of 2 recursions, and return from that (2 exponent) how many recursions deep it goes summing the left child's recursions plus the right child's recursions and maybe plus one for here. Similarly, you could run some code with at most 80% of the available gas (excluding whatever the calls lower on stack do not allow here to use) and if that code returns and has only spent 5% of that gas, then whatever is left is still available here for further calls.

UPDATE 2020-12-7: am considering defining purefunctional controlflow by direct access to emulated stackPointer (sp) and instructionPointer (ip), where a node is either a Float64Array or float64 or pair of nodes (every function and data has that form), something like this... and keep in mind that each position in memory can be either a double or a pointer at a node and can call lambdas on eachother right beside assembly-like code, which in theory should never be able to have an infinite loop etc cuz every such opcode/smallpieceofhumanreadablecode automatically decrements and checks for gas>=minGas would still be true if it did this thing...

There is no interaction between namespaces of higher and lower stackFrames. stackFrames are entirely independent. In the example below,
the fn value of someFunctionDefinedHere cant see x y etc, nor can code at that level see its vars,
which have no effect on global id or how  those in general farther away see eachother (reliably normed form).

```
//Every time you see a an address (such as a variable name x or (x+2) = ..., it normally refers to an index in a double[] or some more sparse datastruct for bigger spaces
fn
	x = sp++
	y = sp++
	//arraySize5
	sp += 5
	//#aLoop
	someFunctionDefinedHere =
		fn
			while
			while
				while
					if
					...
			if
			while
			etc
	aLoop = sp++
		...loop stuff...
		someFunctionDefinedHere = someFunctionDefinedHere(someFunctionDefinedHere)
		anInnerLoop = sp++
			(x+2) = *y //x[2] = y
			...loop stuff...
		ip = anInnerLoop
		...loop stuff...
	ip = aLoop
	//would have very short name instead of copyMemRangeToFindDuplicateOrCreateNode, and likely some syntax to not have to write the aLoop-x part?
	//its inconvenient to have to write whatever comes after a var, since might insert something between those later.
	snapshotOfX = copyMemRangeToFindDuplicateOrCreateNode(x,aLoop-x)
	j = cons('this is a snapshot of x')(snapshotOfX)
	getSnapshotOfX = R(j) //FIXME where did R come from? theres no namespaces here except within local stack frame (whats in fn)
	//snapshotOfX = copyMemRangeToFindDuplicateOrCreateNode(x,aLoop-x)
	//is the wrong range (something inserted between) but
	//would copy any range within the allowed stackFrame only
	
	

There are 3 object types:
1.23 double. A function that some values of are opcodes, and the others do some arbitrary thing like always return 0 or maybe should infloop (TODO choose design).
<>   halted callpair. Has cur>0 such as S(T) has cur of 2, and S(T)(T) has cur of 1, and S(T)(T)(x) returns x.
[]   tuple (of doubles andOr callpairs andOr tuples, aka any object type). [7, 3, 2, 10, 12](3) returns 10. len([7, 3, 2, 10, 12]) returns 5.
	{...} is a syntax for ip and sp based code thats actually stored in a <> which contains a [] somewhere inside as the opcodes or maybe a [] directly.
	"hello world" is a syntax for a tuple of doubles, and those doubles are 1<<256 times those character values (todo choose utf8, utf16, or utf20),
		so the type of being a string or just some doubles is mostly made clear, but not completely, by if all chars are that, or maybe plus 2^52 or something like that.

z(a,b,c) is a call that hasnt halted yet, but may instantly halt aka become <...> or [...] or 4.363 etc or not.
	Every function has cur, inTupleSize, outTupleSize. The *tupleSize is needed for stack optimizations, as those params go on stack and are put back on stack when it returns.
	



//FIXME
matmulSequential = {.....
	ab = sp++
	bc = sp++
	aSize = sp++
	bSize = sp++
	cSize = sp++
	ac = sp; //matrix to return
	sp += mul(aSize,cSize) //FIXME is that aSize or *aSize?
	acEnd = sp //FIXME there seems to be 2 varnames at same stack index: a and acEnd. Technically it doesnt need var names at all, but people need them.
	a = sp++
	b = sp++
	c = sp++
	a = 0 //FIXME it appears these should be "ab = *sp; sp++"
	loopA = sp++
		c = 0
		loopC = sp++
			b = 0
			loopB = sp++
				TODO sum mul(*a,*c). FIXME is that the right way of *x *y *z etc?
				TODO use a local var and copy it once into ac? or sum into ac directly?
				FIXME conditional branching, when does ip just increase by 1 and pass the loops
				b++
			ip = if(b-bSize,loopB,ip+1)
			c++
		ip = if(c-cSize,loopC,ip+1)
		a++
	ip = if(a-aSize,loopA,ip+1) //TODO this is too much to write
	//return a (tuple size 1 containing a) tuple of size aSize*cSize, copied (or found duplicate of) onto heap and therefore immutable.
	//This is a ptr on stack to that on heap.
	ret = copy(ac,acEnd)
	sp = ab //pop whatever pushed
	*(-(sp,1)) = ret //TODO this is inconvenient to have to write function syntax for basic math, but can explore other syntaxs after the bytecode works.
.}


matmulParallel = {.....
	TODO
.}

mandelbrot = {....
	complexA = sp+=2
	complexB = sp+=2
	
	TODO
..} //returns complexnum, a tuple size 2 of doubles


S = {. //S is a lambda of 3 curried params as in SKICalculus, not tupling them together
	FIXME should this be a {...} vs <...> and an S opcode? Can {...} efficiently do currying?
.}

```
It seems very similar to python but I'd like to see it compiled to a 50kB executable file instead of megabytes, and more importantly no existing language seems to guarantee that no possible calculation can infinite loop etc, and in this system, a namespace exists only within a stack frame, not visible below or above it on the stack, and is just a convenient way to refer to pointers onto stack.
("MicroPython uses a couple hundred kB of ROM; which is amazingly small considering how much Python that includes. Snek is a much less capable language, but it can squeeze down to about 32kB of ROM if you leave out the math functions." -- https://lwn.net/Articles/810201/) but still, I dont want to pay for recursive namespaces when I'm just trying to blit 3d mandelbrot/mandelbulb/juliafractal onto the screen.


TODO define the "standard functions" that happen in funcall(funcallNode,funcallNode) in this mutable plugins map...
const funcallPlugins = {};

//todo include another matmul in its own js file that includes GPU*.js or inlines that and at the end overwrites funcallPlugins.matmul,
//but until thats loaded, there will be a slower implementation of it here (TODO).
funcallPlugins.matmul = (vm, aFuncall)=>{ throw 'TODO' };

funcallPlugins.concat = (vm, aFuncall)=>{ throw 'TODO' };

//TODO write a function of any byte[] of webasm code to another byte[] of webasm code which decrements a "gas" counter every step and returns early if its 0,'
//especially with the optimization of checking then subtracting n to the counter before n steps or n * m for 2 inner loops of size n and m etc.
//It should be (in theory, todo verify theory and test) guaranteed that no possible 2 lambdas one called on the other, even in plugins,
//can use more gas than allocated. This of course comes at a cost of efficiency, but cpu code takes the biggest loss, while gpu code in some cases runs equally fast.
funcallPlugins.webasm = (vm, aFuncall)=>{ throw 'TODO' };

//todo copy my experimental music tools code here which stores opcodes in int (2 uint12 pointers up to double[4096]
//and 1 uint8 opcode choosing multiply, plus, negate, sine, tanh, etc) which each are a (double,double)->double op in a binary forest,
//that reads 2 lower double[] indexs and writes the current index in a linear pass of a double[], and should be much faster than WebAudioAPI ScriptNode
//and can in theory read microphone holes (such as an electric guitar plugged into one, an EEG electrode plugged into the other, etc)
//and write speakers in WebAudioAPI or (if user hooks it in) a local server running JSoundCard
//for lower lag (especially low lag in linux, which is the only way it would be fast enough for a professional musician to want to use it in a live show).
funcallPlugins.acyclicflow = (vm, aFuncall)=>{ throw 'TODO' };

(TODO rewrite disorganized text below...) About the name, smite is 1 of the opcodes (similar to process killing 1..2^n hypervisors or vmwares (each between around 30 bytes to unlimited) (as if it never existed but its return value still does, kind of like multiverse fork the current reality is the most basic thing to do, or in some ways like a nondeterministic tetration-sparse-variable-recursion-depth-of-nondeterministic-turing machine), except it costs about 10 microseconds (the time for light to move 3000 meters, which means you could in some cases see with the naked eye 2 distant physical objects on the ground such as on 2 mountain tops, 2 objects that your local computer would allocate and delete/smite a nano-VM before light from either of those 2 mountain tops sees eachother that you happen to be on the large obtuse-triangle side between them), and is equally easy to fork an exponential number of them) of this so far theoretical system, along with wallet and spend opcodes (locally, not implying blockchain) for the allocation of resources of the local computer among possible thoughts. Get creative. Have fun. Build things together (fractals, *.io games, graphs of math, evolvable musical instruments virtual or plugged into analog sound holes, tools for the more advanced creation of memes, simple games of 50 players shareable by qrcode or url, throwing  pieces of neuralnets around between a bunch of people and computers like copy/pasting but faster, creation of thousands of dimensions and permutations of them or forkEditing of nearly anything any time anywhere in any way that the system has a complete math model of, or more often just generally screwing around like in a hackerspace), defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition (such as browser tab running an infinite loop will after ~10 seconds say its not responding do you want to close the tab, or like Ethereum syncs every 15 seconds, or like a realtime-operating-system for critical machines except its compiler is many times slower than it reacts at runtime, or like https://en.wikipedia.org/wiki/Green_threads (the pure interpreted mode cant do so much as a single AND OR NAND etc without forking 2 greenthreads of the 2 child bits, but atomic ops (such as a time and memory block of webasm) can be as few or many threads as they want) except this system can do an exponential number of them like they're part of a branching stack, and it is literally the plan for this system to run on millions of threads if you count just 1 or a few cpu threads (such as some browsers have 1 thread per tab) and thousands of gpu threads per computer across the internet, some alone and some wanting to do things together).
