# lambdasmiter
This is loosely based on a working model of self-modifying computing in occamsfuncer which has testcases working up to calling a derived equals function on itself and what that returns call it on the equals again equals(equals)(equals) it it says true, but occamsfuncer is much harder to optimize than this and I need to get something online. Smites infinite loops at few microseconds time precision instead of the usual multiple seconds that it takes to back out of code that gets out of control.
Always halts, but is not always turingComplete, but the only turingCompleteness sacrificed is that turingCompleteness says it can use as much memory and compute cycles as it wants, vs in this model of computing, things lower on the stack can further limit the number of compute cycles and amount more of memory allowed in deeper calls on the stack, which can each tighten such limits but cant loosen them, and branch one way or another depending if a call finished normally vs gave up early due to not enough compute resources. Always halts such as within 0.02 seconds if you want it to guarantee halting before the next video frame of a game is displayed.

Registers in the whole system in a double[], which can be struct-like or number,
	and maybe some struct-like javascript objects as optimizations of that:

ip - stack instruction pointer.

sp - stack pointer

gas - amount of computing resources (adjusting cost ratio of allocBitOfMem vs oneComputeCycle based on supply and demand)

ipull - stream instruction pointer, an index into a double[] (aka Float64Array) on heap which increments and returns the next double at each call of a certain opcode which occurs on the stack, so 2 levels of instructionPointer: ip and sip (TODO think of better names for these registers).

ipullFrom - ipullFrom(ipull) is the current stream instruction.

"so the stack can kind of print itself from that single pass over the double[] opcodes" (in theory, TODO).

All registers are writable by the double/float64 instruction at *ip, such as pushing 10 megabytes onto the stack with or without clearing it to 0s
or such as generating another tuple (such as a screen sized array of doubleARGB pixels) then generating a photoshop-like op
then "jumping to" by writing the ipullFrom register and writing 0 to the ipull register, jumping to another stream of opcodes "a photoshop-like op"
which automatically triggers attempted formal-verification of pointer arithmetic to prove it will always halt else "smite itself"
which may find a solution or not but either way will not itself use more computing resources than caller allowed, and so on,
or similarly for evolving musical instruments or minigames.

Only has pure math functions, so there is no namespace visible above or below the current stack frame except what is derived such as treemaps or linkedlists.

Will be fast enough to render 3d fractals in a canvas and to generate new musical instruments which speakers and microphones analog holes can have instruments plugged into, polygons, high dimensional graphics, screen blitting, etc. But it wont be the fastest system for branching. Its designed with extremely parallel computing as higher priority.

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
