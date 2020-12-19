//TODO create it all in a single var (or const) called lambdasmiter which is itself contained in a (...) like... (and its the only thing in the js file)...
//TODO should lambdasmiter be a factory, of 0 params, so you can generate a new one for each testcase of it (but otherwise you'd only want 1).
//TODO should lambdasmiter take a param of funcallPlugins, and no further plugins are allowed after that?
//Plugins may have stateful optimizations such as GPU.js maybe has cache of compiled gpu code etc?
//Maybe should just stick to the 1 lambdasmiter, which is the VM with stateful memory and compute cycle stats, inside of which theres a stateless universalFunc.
const lambdasmiter = ((function(){
	this.description = "lambdasmiter - A universal function for browser javascript loosely based on occamsfuncer (which is already well tested but too slow). Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition (such as browser tab running an infinite loop will after ~10 seconds say its not responding do you want to close the tab, or like Ethereum syncs every 15 seconds, or like a realtime-operating-system for critical machines except its compiler is many times slower than it reacts at runtime). Get creative. Have fun. Build things together, defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. Can in theory smite through 100,000 infinite loops per second (compared to the usual 0 to 0.1 per second) then get back to your cat memes and multiplayer gaming experiments without missing a step, without delaying the next video frame of the game, without your mouse or text cursor feeling jumpy, and especially for AI experiments where AIs write experimental code to create new AIs and people and AIs play and build together and share lambdas across the internet similar to how most apps share byte streams, except the lambda, not the byte, is the unit of computing here. Its a workaround for the halting problem which smites any lambda call reliably when its about to, or sometimes can be known that it would later, use more memory or compute cycles etc than its allocated thousands per second of such recursive limits within limits per stack height, in a subset of pure functional browser javascript with GPU optimizations and gaming low lag canvas pixels and game controllers and textareas and p2p etc, and whole or sparse system state (stateless lambdas, including Float64Array, in lazy merkle forest) is saveable and loadable in byte arrays, and random or malicious or just fun experimental code shared across the internet can be run safely together in a tiny fraction of a second. To measure how many infinite loops per second it can "smite / give up on / back out of", you could read how much "gas" is available, and fork nearly half of it (or any chosen amount) to each of 2 recursions, and return from that (2 exponent) how many recursions deep it goes summing the left child's recursions plus the right child's recursions and maybe plus one for here. Similarly, you could run some code with at most 80% of the available gas (excluding whatever the calls lower on stack do not allow here to use) and if that code returns and has only spent 5% of that gas, then whatever is left is still available here for further calls.";
	this.authors = ['BenFRayfield'];
	this.license = 'MIT';
	'use strict';
	
	//Make sure no code can directly access these vars. Code will be in a sandbox inside the browser, not able to eval or load things from urls etc,
	//other than what can be proven to be in sandbox, such as matrix multiply using GPU.js, bit blits to view in canvas, digital signatures, etc.
	//For example, to display a Float64Array on a canvas using each float64/double as its "cast to int" value.
	this.gas = 1e9;
	//gas >= minGas, else give up and instantly pop in stack, signaling not enough compute resources, and branch the other way.
	//Anywhere on stack can choose to raise minGas up to the current gas, but it cant lower it. Thats why it starts at 0,
	//and when pop back to that part of stack, minGas is whatever it was before that call deeper in stack raised it (so it auto lowers to where it was).
	this.minGas = 0;
	//In theory you only need 1 kind of gas for any number of compute resources,
	//using a "free market trading ratio" between memory and compute cycles etc, like at some time it might cost 5.3 units of gas per bit of memory
	//and 1 unit of gas per compute cycle, by varying that ratio by how much is available at the time. Similar for the resource of compiling GPU.js code etc.

	//bits of memory allowed to be allocated by Eval, including any memory that COULD be garbcoled (garbage collected) by javascript even if it hasnt yet.
	//This number is chosen arbitrarily to start to allocate some constant amount of memory to the system, regardless of how much memory is actually available to javascript.
	this.memAvail = 100*1000*1000*8;
	this.memUsed = 0;
	//memUsed+memAvail is total allowed by this system.
	
})());







TODO define the way of importing plugins...
Plugins are each a funcallNode containing a string of code (starting with "javascript:" or any language, but just javascript for now)
of a function, but only those the VM trusts can run,
such as containing the code string of GPU.js, but WebAudioAPI cant be in a plugin since plugins do not write to the outside world,
but since it is planned to use WebAudioAPI, define an IOPlugin as a separate kind of thing than an InternalPlugin.
IOPlugins cant be called by lambdas but can call lambdas and use their return values.
IOPlugins would include htmlCanvas, WebAudioAPI, reading mouse position, networking, etc.
A InternalPlugin is an atomic op, a function of funcallNode to funcallNode (which each may be a vararg call), within memory and computeCycles etc limits.
Controlflow other than plugins starts with the basics of SKI_Calculus similar to occamsfuncer, iota, unlambda, etc.
Since an InternalPlugin is an atomic op, it cant call any other plugins or functions.


TODO is there a way to still prove memory and computecycle limits if an InternalPlugin can call lambdas (such as other InternalPlugins),
which would be useful for defining many times faster kinds of controlflow such as new syntaxs of loops without having to use so much SKI_CALCULUS between plugins???
No, it cant be done for plugins that compute in mutable ways inside the atomic op that is themself, like wrapping a string of js code or webasm etc,
those are mutable calculations, but yes if the plugin is made of immutable parts but then its not much use to be a plugin as it would be very slow probably.


Maybe should use callquads (see occamsfuncer) but still having Float64Array as leafs (instead of occamsfuncer having only 1 leaf which all paths lead to)
and include an equals function (instead of occamsfuncer derived equals using simpler ops including to get the left child, right child, and check if param is the leaf or not.
With callquads, there is no stack, only heap, as the 2 extra childs per node are pieces of stack and cacheKey.
Would need to choose which ops to do on Float64Arrays and small binforests containing them as (node,node)->node where node is either a Float64Array or a pair of nodes.
Choosing which ops, is confusing me.



Could all the ops (other than callquad, ski_calculus, etc) be done by webasm+webgpu? Whatever it is, has to work in browser javascript without browser plugins.
2020-12-7[
https://en.wikipedia.org/wiki/WebGPU#Implementation
"Both Chrome and Firefox support WebGPU with SPIR-V, with work ongoing for the WGSL front-end. Safari supports WebGPU with WSL."
but that doesnt appear to be true at https://webkit.org/demos/webgpu/
https://tvm.apache.org/2020/05/14/compiling-machine-learning-to-webassembly-and-webgpu
]


Yes, do use callquads. TODO.


If start with only webasm (add GPU.js etc later), that would be enough to make the mandelbrot/julia fractals you can play with using the mouse,
a fun proofOfConcept, and could forkEdit it. Webasm would only be used on Float64Array->Float64Array
which would contain the code and data somewhere, which could be built by ski_calculus with a concat function etc.
But not just any Float64Array would be allowed as it has to be both valid webasm and count down the gas at Float64Array[0].

Or could start with just evalling strings of js, but only those generated in a way that proves they count down a gas var and end if its 0
and dont allocate any memory.


Or start with just the dataStructure of (a node is either a Float64Array or a pair of nodes)
and write a function that checks if a string of js code is a function(node)->node that obeys the gas limit
by only allocating new node using a certain function, not calling any other functions except the nodes themselves
(which may contain such strings of other js code and its compiled form cached), etc?
That could do "mandelbrot/julia fractals you can play with using the mouse".
Before every line of code, it could call pay(1) which would throw if not have enough to pay that,
or harder to detect but before n lines of code pay(n) instead of that, or before a double loop pay(x*y) etc.
Start with pay(1).
..
Since the js code is verified, it can use the js stack (not callquad, just pairs and arrays),
it can call other such js code (in the array of a node, with cached compiled form) before it returns,
and it can allocate Float64Array and modify it BEFORE its returned but after that its frozen,
so any Float64Array from the js code it calls is already frozen when it sees it.

Maybe use string instead of Float64Array, to get started, as its already immutable and can be used as uint16s
which are enough for sound amplitudes and 15 bit canvas color.
So a node is either a string or a pair of node? No, the conversion in js is expensive. Use Float64Array.

(todo write shorter names of these)
x(y) would call x on y, whatever js code may be in x.
cons(x,y) would pay for that pair's new memory.
pay() would pay 1.
calloc(numDoubles) would allocate a mutable Float64Array, viewed as frozen after its returned.
verifyCode(node) returns 1 or 0 depending if its a Float64Array of string of valid js code and valid within these rules.
spend(maxGas,lazyCall) returns lazyCall(0) if it costs at most maxGas, else returns 0 (todo or throws?).
or spend(maxGas,x,y) returns x(y) if has enough gas.
and how to limit the stack depth?
wallet() returns gas-minGas aka the amount of gas available here.
A mutable Float64Array can only go in a varName with a certain prefix, like mut_abc = calloc(numDoubles),
and similar for other mutable vars. Anything returned from an x(y) can go in a var that implies immutable like abc.
...
Or simpler, dont use any javascript statements at all like x = y+z, and instead do it all as functions that pay gas etc,
like...
loop(x,loop(...),z) for some meaning of x and z, but having var names there seems like it could cause a problem.
Like such function calls define a stack frame, but I want the efficiency of how js uses mutable vars with Float64Array that it allocates inside that stack frame.
mapGet(x,y) x(y) so dont realy need mapGet just use x(y).
mapPut(x,y,z) forkedited x with x(y)->z
...
stackFrame(...what goes here?...) would be a call that returns a node, and uses the efficiency of js looping
and mutably using a Float64Array etc inside the stackframe but not after it returns.




No function should take more than 1 param. Like stackframe(x)(y)(z) instead of stackFrame(x,y,z),
and like spend(12000)(stackFrame(x)(y))(z) runs stackFrame(x)(y)(z) with at most 12000 gas cost.
L(stackFrame(x)(y))->stackFrame(x)
R(stackFrame(x)(y))->y
L(z)(R(z))->z for any z, for small constant gas cost which may of course run out of gas so not return z, but IF it returns then its z.

U = theUniversalFunction, which can define different opcodes than in occamsfuncer especially to include an opcode that can do the
mutable looping while modifying a Float64Array or combos of multiple such vars in a stackFrame then returning an immutable result,
where a node is either a Float64Array or pair of nodes.

Display similar to SExpressions (sexp).

Its still true that infinite loops etc are not possible in this system if the VM itself is not buggy,
and it can do anything a turingComplete system can do except within limits of memory and computeCycles.

Whats most confusing me right now is what function should stackFrame be?
What kinds of things are normally in a js stack frame and would look like human readable code?

That js stack frame is all I need to get the mandelbrot/julia canvas reacting to mouse movements in realtime,
which is a demo I already have working using plain js code but want to port it to this system.


stackFrame...
Include only WHILE loops which check if a certain var is nonnull/nonzero/etc cuz they're simplest (at least for now)?
can declare vars using LET.
can call a var on another var like x(y)->z.
can use basic math ops on a pair of expressions, like (x+1)*y BUT todo verify they are doubles before doing that,
or maybe should do that with func like mul(x)(y)... no dont want that on the heap, mul(x,y) could avoid the heap but may be too slow.
can alloc a Float64Array, and can write it if it was allocated here.
must return exactly 1 node (a node is either a Float64Array or a pair of nodes).

stackFrame will be a node that, like any other, cant infloop nomatter what params its called on.

stackFrame's n curried params should look like indented code with loops in loops and if/else etc, like a normal function.

stackFrame would be either vararg or take a linkedlist param.

Any halted function andOr data is a node and can therefore be saved/loaded on harddrive, shared on internet, etc,
including functions generated by anonymous functions at runtime.


stackFrame...

x.y are not allowed, as thats a mutable var inside x, unless x is a Float64Array it can be modified within the stackframe that created it.
Instead, use forkEditable immutable treemaps, made of nodes.


A loop is kind of like a GOTO except just a more intuitive syntax for it.
But need stackFrames curried params to look like indented human readable code.


stackFrame's params maybe should have var names in them like lisptokens, but index into the stackframe is more efficient,
but cant use just indexs cuz it wont be human readable.

browser says...
i = 50
50
while(i) i = i-1;
0

//fixme takes 1 param? vararg? 2 params at least, 1 maybe should be a map of namespace, and the other the normal param?
//Or get that from currying? Also need ptr to self like in occamsfuncer bigcall1 to bigcall7
fn
	ns = ...TODO get namespace param...; //instead of cons(x)(y) its ns('cons')(x)(y) but thats too long to type and read and maps are slow.
	param = ...TODO get other param...;
	self = ...TODO see occamsfuncer recur...;
	sum = 0
	anotherFunction = //cant refer to any var names in the outer fn. every fn is independent.
		fn
			if
			while
				while
			etc
	aMutableArray = ns.calloc(567)
	cons = ns.cons
	wal = -1;
	put = ns.put
	i = 50
	someFunc = ...TODO...;
	while i
		j = i
		while j
			sum += i*j
			jIsEven = 1-(j%2)
			if jIsEven
				sum += 42
			sum += aMutableArray(j)
			someFunc = someFunc(someFunc)
			j += someFunc(55*i)
			aMutableArray = ns.put(aMutableArray)(j)(0) //would compile to aMutableArray[j] = 0. TODO find a shorter way to write that.
			--j
		wal = wallet(0) //the 0 param is ignored, but a function must take a param. returns current gas-minGas.
		--i
	ns.cons(aMutableArray)(37) //return value. nothing important about 37, just an example.

//all named funcs must be among the params, cuz theres no namespace outside of a stackFrame.


Replace all those while statements with while(true)... and use break?



Syntax for cons(x)(y)...??
x@y
(x@y)@z
[[x y] z] ?

All functions are anonymous. Var names only exist within a stack frame, not visible to lower or higher stack frames.



Or... Instead of calloc(567), push 567 number of 0s onto stack, and a var name refers to the first of those.
and similar for other var names. They all refer to a specific stack index.
Whenever that var is used as a param of something, its copied to a Float64Array thats used as immutable
(which may be reused if hasnt changed since that happened last time?).
???

Everything in that indented code of functions making and using functions, is implied to call pay(1) etc before basically every line (or more optimized ways later),
but that part isnt displayed.





fn
	aMutableArray = stackPtr
	pushZeros 567
	x = 7
	But how do we know aMutableArray is size 567?
	push 8
	eight = peek //stackPtr-1



fn
	//Whenever #something is written, it is defined as the current value of stackPtr.
	#aMutableArray
	pushZeros 567
	#eight
	push 8
	#aFuncXYZ
	push
		fn //aFuncXYZ = this function
			if
			while
				while
			etc
	#aLoop
		#innerLoop
			
		goto innerLoop
	goto aLoop
	fixme need conditional jumps not just goto
	
The only things that can be pushed onto stack are node, which is either a Float64Array, a float64, or a pair of nodes.
Every fn statement is such a pair with stuff deeper inside.

write it as a sequence of tokens...
Include increaseIndentLevelByOne (lets abbrev that as @) and newline (lets abbrev that as N) in the tokens...
fn
	//Whenever #something is written, it is defined as the current value of stackPtr.
	#aMutableArray
	pushZeros 567
	#eight
	push 8
	#aFuncXYZ
	push
		fn //aFuncXYZ = this function
			if
			while
				while
			etc
	#aLoop
		#innerLoop
			
		goto innerLoop
	goto aLoop
tokens...
fn N
"Whenever #something is written, it is defined as the current value of stackPtr."  N
@	#aMutableArray N
@	pushZeros 567 N
@	#eight N
@	push 8 N
@	#aFuncXYZ N
@	push N
@	@	fn "aFuncXYZ = this function" N
@	@	@	if N
@	@	@	while N
@	@	@	@	while N
@	@	@	etc N
@	#aLoop N
@	@	#innerLoop N
@	@	@	 N
@	@	goto innerLoop N
@	goto aLoop N

Putting that Float64Array that looks like a string literal, without marking it as a comment, might break something?

tokens...
fn N
"Whenever #something is written, it is defined as the current value of stackPtr."  N
@	#aMutableArray N
@	pushZeros 567 N
@	#eight N
@	push 8 N
@	#aFuncXYZ N
@	push N
@	@	fn "aFuncXYZ = this function" N
@	@	@	if N
@	@	@	while N
@	@	@	@	while N
@	@	@	etc N
@	#aLoop N
@	@	#innerLoop N
@	@	@	 N
@	@	goto innerLoop N
@	goto aLoop N

A token is either like a lispToken (some kind of literal) or a node (node is a Float64Array or a float64 or a pair of nodes).
Could mark some as node vs syntax token by using a linkedlist of pair of 0_or_1 and the_syntax_token_or_reference_to_node.







fn
	//Whenever #something is written, it is defined as the current value of stackPtr.
	#aMutableArray
	pushZeros 567
	#eight
	push 8
	#aFuncXYZ
	push
		fn //aFuncXYZ = this function
			if
			while
				while
					aMutableArray[eight] = eight*eight
					//*(aMutableArray+*eight) = (*eight) multiply (*eight), or something like that
			etc
	#aLoop
		#innerLoop
			
		goto innerLoop
	goto aLoop



TODO include instructionPointer (ip) and stackPointer (sp) as 2 vars at the start of every fn, which user level code can see?

fn
	x = sp
	#arraySize5
	sp += 5
	//#aLoop
	aLoop = sp
		...loop stuff...
	ip = aLoop
TODO define while if etc in terms of a fn syntax that uses sp and ip for controlflow, and maybe also * and &.
Must be formalVerified, or statistically approaching it, to obey the gas and minGas logic.



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
			*(x+2) = *y //x[2] = y
			...loop stuff...
		ip = anInnerLoop
		...loop stuff...
	ip = aLoop
	//would have very short name instead of copyMemRangeToFindDuplicateOrCreateNode, and likely some syntax to not have to write the aLoop-x part?
	//its inconvenient to have to write whatever comes after a var, since might insert something between those later.
	snapshotOfX = copyMemRangeToFindDuplicateOrCreateNode(x,aLoop-x)
	j = cons('this is a snapshot of x')(snapshotOfX)
	getSnapshotOfX = R(j) //FIXME where did R come from? theres no namespaces here except within local stack frame (whats in fn)

It seems very similar to python but I'd like to see it compiled to a 50kB executable file instead of megabytes, and more importantly no existing language seems to guarantee that no possible calculation can infinite loop etc, and in this system, a namespace exists only within a stack frame, not visible below or above it on the stack, and is just a convenient way to refer to pointers onto stack.
("MicroPython uses a couple hundred kB of ROM; which is amazingly small considering how much Python that includes. Snek is a much less capable language, but it can squeeze down to about 32kB of ROM if you leave out the math functions." -- https://lwn.net/Articles/810201/) but still, I dont want to pay for recursive namespaces when I'm just trying to blit 3d mandelbrot/mandelbulb/juliafractal onto the screen.



In most programming ip would never point into sp, and some hardware even prevents it unless you click a "date execution prevention" checkbox or something like that,
but that just means would have to run it in a VM made in any language instead of being 1 compiler level below it. Things still tend to be nearly as fast
since memory IO is usually the bottleneck in that kind of thing, instead of small pieces of logic that fit in L1 cache.









//The below text is meant to be interpreted after seeing how fun it is to play with, in some of its open-ended turingComplete combos, in a browser,
//and naturally becoming curious how it works. Toward that, it is (TODO) divided into well defined tiny pieces that know and can strongly
//verify they are compatible or incompatible, not by any kind of authority but by the patterns of information of
//what eachother may or may not be on not-too-deep-recursively observing
//eachother (meaning that pieces of math are observing other pieces of math, similar to godel or halting theory).
//TODO rewrite this paragraph too. Its too long and includes things not relevant to this js library.

//TODO rewrite and simplify the below text, cuz its too long and confusing.

//TODO design a universal function for javascript, loosely based on occamsfuncer, but mostly sacrificing some of precision and repeatability for efficiency,
//(which maybe people should be happy that it is based on a precise theory, even though only loosely based, instead of working toward such a theory to figure out later,
//aka I know why its a good idea to do it this way or some similar way and such pointers to earlier code and experiments can be found or referred
//to later though is still kind of a bunch of experimental pieces of code and sometimes copy/pasted inputs and outputs etc, but I mean mostly
//the occamsfuncer test cases which pass, especially those which demonstrate it is both a "universal lambda function" and a "pattern calculus function"
//such as it can always in finite time detect equality of any 2 functions in the system for amortized constant time but its a large constant so is lazy.\
//In javascript for practical reasons I want it to at least in some small way compete with megapixel(s) games at low lag, so some design changes were needed.
///
//so it can produce at least an int[500][500] of 32 bit color graphics in realtime, but what to display in them is likely much simpler than recent games,
//except there are many other interesting kinds of turingComplete patterns that are at least as interesting as current games, in theory, so a different niche.
//Similar to occamsfuncer but less strict, less repeatable, but in theory still defends a browser tab from infiniteloops etc recursively
//which as far as I know currently (2020-12+) goes completely undefended (ending at the borders of the browser tab, even if iframes inside them attack them by infiniteloop)
//other than trying to accumulate trust among those who might attack that which is undefended (or nearly undefended as 10 seconds after a browser tab crashes you can close it).
//While occamsfuncer etc may be far more expensive a calculation than its worth for most things (but not all, it will have niche(s)),
//this is something near any website might be improved by, at least small parts of that website, or some websites could be made completely of this...

//The model of computing is... Imagine a stack as a vertical line from below, and at the top theres a horizontal line representing the heap,
//and on the heap are Float64Array objects used as immutable, and downward from that are immutable pairs of [pair or Float64Array],
//(occamsfuncer strictly enforces an iota-like or ski-calculus-like tiny set of opcodes of such pairs and constant depth of them recursively,
//but this js software should be far looser and focus mostly on "in theory still defends a browser tab from infiniteloops etc recursively
//which as far as I know currently (2020-12+) goes completely undefended". Cuz thats something maybe nobody has and nearly everyone could have soon???
//
//which treemaps and other datastructs can be formed of. Each point on the stack points at 1 of these pairsOrArrays.
//Each point on the stack as a maxCost (see Gas.top or NondetNode in occamsfuncer, of many forks of occamsfuncer its very incomplete and experimental),
//and if the amount (of compute cycles, memory, allocation of GPU.js resources, etc...) of resources at some part of stack cant be PREPAID
//then it instantly pops with a bit that says it ran out of resources so to branch to the ifCantPayAkaElseOrCatch case, instead of the normal path,
//and recursively in the stack code can react to whether things happened normally vs ran out of resources,
//BUT like in occamsfuncer, its only deterministic if nothing ever runs out of resources, so this is still very experimental.
//In future versions of this, maybe other array types (than Float64Array) would be included.
//TODO what kinds of dedup and <func,param,return> caching are needed?


//Make sure no code can directly access these vars. Code will be in a sandbox inside the browser, not able to eval or load things from urls etc,
//other than what can be proven to be in sandbox, such as matrix multiply using GPU.js, bit blits to view in canvas, digital signatures, etc.
//For example, to display a Float64Array on a canvas using each float64/double as its "cast to int" value.
var gas = 1e9;
//gas >= minGas, else give up and instantly pop in stack, signaling not enough compute resources, and branch the other way.
//Anywhere on stack can choose to raise minGas up to the current gas, but it cant lower it. Thats why it starts at 0,
//and when pop back to that part of stack, minGas is whatever it was before that call deeper in stack raised it (so it auto lowers to where it was).
var minGas = 0;
//In theory you only need 1 kind of gas for any number of compute resources,
//using a "free market trading ratio" between memory and compute cycles etc, like at some time it might cost 5.3 units of gas per bit of memory
//and 1 unit of gas per compute cycle, by varying that ratio by how much is available at the time. Similar for the resource of compiling GPU.js code etc.

//bits of memory allowed to be allocated by Eval, excluding any memory that COULD be garbcoled (garbage collected) by javascript even if it hasnt yet.
//This number is chosen arbitrarily to start to allocate some constant amount of memory to the system, regardless of how much memory is actually available to javascript.
var memAvail = 100*1000*1000*8;
var memUsed = 0;
//memUsed+memAvail is total allowed by this system.

const randInt = (maxExcl)=>{
	return Math.floor(Math.random()*maxExcl);
};

//thrown when try to spend gas that you dont have. You only have gas-minGas.
const cantPay = 'cantPay';

const readGas = ()=>(gas-minGas);

//consumes gas
const useGas = (amount)=>{
	if(amount < 0) throw 'negative amount '+amount;
	let newGas = gas-amount;
	if(minGas <= newGas) throw cantPay;
	gas -= amount;
};

//Maybe use this about 30 times per second, once per video frame, to give more compute resources,
//so whatever happens takes less time than 1 video frame.
const resetGas = (amount)=>{
	gas = amount;
	//TODO? minGas = 0; memAvail memUsed etc
};

//TODO use λ for occamsfuncer-like things? like λ3245q345qwe4r3dsfgsd345re4rtsdfgvsedrfgsd4rs5tw34tr might be a function id, or something like that.

/*
///
///
///TODO just generate 2 random int32s and grab whatever bits I want from them to make the correct lastFuncId by some random input. (ignore whats below)
///
///
//double exactly represents all integers in range plus/minus 2 exponent 53 (inclusive), and leave at least a range of 2 exponent 52 (1 of the bits) for lastFuncId++.
//This in theory causes different computers who together share these ids, for short times, to probably not overlap eachother. (can do it exactly but costs alot more).
//Ids are nonnegative, just so they're 1 char shorter to write than negatives, but could have 1 more bit.
//Could also have (todo exactly how much? around 10? Its a 64 bit number)
//more bits from the exponent area, but not worth the complexity in js. May be worth the complexity in native systems. Possible improvements for later.
//
//Lets just grab 9 more bits anyways (at the cost of cpu registers often needing a copy of lastFuncIdIncrement instead of 1)
//by lastFuncIdIncrement... so (TODO verify its not half or twice that, etc...) 2^61 possible ids, somewhere around that.
//FIXME verify no overlaps between the different values of grabNMoreBitsFromExponentOfDouble which each choose a range of 2 exponent 52 bits,
//and select randomly a lastFuncId within it, jumping forward by a powOf2 each time.
//It might help in understanding this that for all positive doubles, viewed as int64 bits (no casting, just the raw bits),
//the double and int64 sort exactly the same way, 1 to 1 mapping.
const grabNMoreBitsFromExponentOfDouble = 9;
const lastFuncIdIncrement = Math.pow(2,randInt(Math.pow(2,grabNMoreBitsFromExponentOfDouble))); //FIXME is this the right number?
//FIXME is this multiply losing bits of precision? Cuz I could just start with 1.0 and add it to itself until reaching the exact number I wanted, once at boot.
var lastFuncId = Math.floor(Math.random()*Math.pow(2,52+fixmethisshouldbelog2of???...grabNMoreBitsFromExponentOfDouble));
console.log('Starting as lastFuncId='+lastFuncId+", lastFuncIdIncrement="+lastFuncIdIncrement+" grabNMoreBitsFromExponentOfDouble="+grabNMoreBitsFromExponentOfDouble);
//could have been lastFuncId++ but wanted more ids.
const nextFuncId = ()=>(lastFuncId+=lastFuncIdIncrement);
//FIXME need testcases of nextFuncId for various grabNMoreBitsFromExponentOfDouble and return values of Math.random() in the code abov-. (ign--o-= wh-ts ==ab-ove)
*/
/*
var lastFuncId = 0;
{
	let aPowerOf2 = 1; //2 exponent 0
	let shift = 52;
	while(shift > 0){ //avoid roundoff
		aPowerOf2 *= 2;
		shift--;
		if(Math.random() < .5) lastFuncId += aPowerOf2;
	}
}
//TODO maybe i should just use random integer in range 0 to 2^45 etc, before optimizing the number of bits in a double can use for id purposes.
//
*/
///
///
///TODO just generate 2 random int32s and grab whatever bits I want from them to make the correct lastFuncId by some random input.  (ignore whats above)
///
///
var lastFuncId = Math.floor(Math.random()*Math.pow(2,52)); //TODO grab as many of the 64 bits as can for ids.
console.log('Starting as lastFuncId='+lastFuncId);
const nextFuncId = ()=>(lastFuncId++); //grab those grabNMoreBitsFromExponentOfDouble later. get it working with the smaller id space first.
/*
var lastFuncId = 0;
{
	let aPowerOf2 = 1; //2 exponent 0
	let shift = 52;
	while(shift > 0){ //avoid roundoff
		aPowerOf2 *= 2;
		shift--;
		if(Math.random() < .5) lastFuncId += aPowerOf2;
	}
}
*/


FIXME redesign funcallNode to curry its constructor params then take 1 more param so can use it as a js function to call function on function to find/create function,
like this...

x = function(state){ return function(param){ return state+param*param; }; }
function x(state)

x(100)
function x(param)

x(100)(3)
109
x(100)(3)+x(200)(1)
310
x(100)(3)+x(200)(1000)
1000309
m = x(100)
function x(param)

n = x(200)
function x(param)

m.state
undefined
m(0)
100
n(0)
200

BUT also need ability to get all those fields out of it.


TODO if you give a param to such a lambda, and its not another lambda, then automatically call wrap on it. Or maybe just automatically call wrap on everything
since wrap(anyLambda) returns its param if its already a lambda.


An extreme advantage of this system over javascript functions in general is that functions created at runtime from combos of other functions
are always completely made of data so can be copied between computers, saved, etc, and they never infiniteLoop or use more memory than they're offered, recursively.


TODO define the "standard functions" that happen in funcall(funcallNode,funcallNode) in this mutable plugins map...
const funcallPlugins = {};

//todo include another matmul in its own js file that includes GPU*.js or inlines that and at the end overwrites funcallPlugins.matmul,
//but until thats loaded, there will be a slower implementation of it here (TODO).
funcallPlugins.matmul = (vm, aFuncall)->{ throw 'TODO' };

funcallPlugins.concat = (vm, aFuncall)->{ throw 'TODO' };

//TODO write a function of any byte[] of webasm code to another byte[] of webasm code which decrements a "gas" counter every step and returns early if its 0,'
//especially with the optimization of checking then subtracting n to the counter before n steps or n * m for 2 inner loops of size n and m etc.
//It should be (in theory, todo verify theory and test) guaranteed that no possible 2 lambdas one called on the other, even in plugins,
//can use more gas than allocated. This of course comes at a cost of efficiency, but cpu code takes the biggest loss, while gpu code in some cases runs equally fast.
funcallPlugins.webasm = (vm, aFuncall)->{ throw 'TODO' };

//todo copy my experimental music tools code here which stores opcodes in int (2 uint12 pointers up to double[4096]
//and 1 uint8 opcode choosing multiply, plus, negate, sine, tanh, etc) which each are a (double,double)->double op in a binary forest,
//that reads 2 lower double[] indexs and writes the current index in a linear pass of a double[], and should be much faster than WebAudioAPI ScriptNode
//and can in theory read microphone holes (such as an electric guitar plugged into one, an EEG electrode plugged into the other, etc)
//and write speakers in WebAudioAPI or (if user hooks it in) a local server running JSoundCard
//for lower lag (especially low lag in linux, which is the only way it would be fast enough for a professional musician to want to use it in a live show).
funcallPlugins.acyclicflow = (vm, aFuncall)->{ throw 'TODO' };


//A lambda call.
//Either array is null or [func is null and param is null]. You get 2 childs OR an array, not both.
//Unlike occamsfuncer, array is leaf. Occamsfuncer represents arrays as binary forest of
//pairs of pairs... that lead to true or false which are derived from universalFunc/leaf. But here, array is leaf.
//funcIdOrNull is null to automatically allocate one (without modifying lastFuncId, as its meant to copy funcallNode from external sources).
const funcallNode = function(funcIdOrNull, cur, array, func, param){
	
	this.localFuncId = ((funcIdOrNull!=null) ? funcIdOrNull : nextFuncId()); //immutable
	
	//deterministic. Perfect dedup using sha3_256 merkle hash.
	//mutable cache, lazyEvaled since most never need a global id. Similar to occamsfuncer id (which isnt finished yet).
	//TODO should this be a string like λ3245q345qwe4r3dsfgsd345re4rtsdfgvsedrfgsd4rs5tw34tr ?
	//
	//TODO does this work for getting bits of the Float64Array? What about the other direction, from bits back to Float64Array (or string)?
	//https://stackoverflow.com/questions/2003493/javascript-float-from-to-bits
	//function DoubleToIEEE(f){
	//	var buf = new ArrayBuffer(8);
	//	(new Float64Array(buf))[0] = f;
	//	return [ (new Uint32Array(buf))[0] ,(new Uint32Array(buf))[1] ];
	//}
	this.globalFuncId = null;
	
	//Number of curries left before it executes. Its halted if cur!=0. Cur is always a nonnegative integer.
	//
	//OLD...
	//immutable. True if array is nonnull. May be true or false if func and param are both nonnull (TODO see occamsfuncer quadcall stack,
	//I might be using a linear bigO here when should be constant?).
	//If isHalted, then this.ret==this (and based on that, maybe dont need to store isHalted?).
	//this.isHalted = isHalted; //immutable
	this.cur = cur;
	
	//You get either array or (func,param), but not both.
	
	this.array = array; //immutable. Either null or a Float64Array, and TODO choose a design... allow string here too? cuz string acts like an array.
	
	this.func = func; //immutable
	this.param = param; //immutable
	
	//FIXME did it end cuz of deterministic return without anything in it giving up for lacking enough gas,
	//ELSE gave up early or was affected by something that did, which affects cache.
	//aka <isDeterministic,func,param,return> caching, where isDeterministic is a bit?
	this.ret = null; //mutable. cache of (func param)->return, if it ever returns.

};


//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/instanceof

const isString = (x)=>{
	throw 'TODO';
};

const isDouble = (x)=>{
	throw 'TODO';
};

const isFloat64Array = (x)=>((x instanceof Float64Array)?true:false);

const isFuncallNode = (x)=>((x instanceof funcallNode)?true:false);

const len = (x)=>{
	throw 'TODO: if its a Float64Array or string then its just x.length, but if its a map etc then it could contain the key length'.
};

//wraps some thing in a funcallNode, such as a string or Float64Array or something thats already a funcallNode.
const wrap = (thing)=>{
	if(isFuncallNode(thing)){
		return thing;
	}else if(isFloat64Array(thing)){
		return new funcallNode(null, 1, thing, null, null);
		//TODO a wrapped Float64Array takes 1 param, viewed as an array index.
	}else if(isString(thing)){
		return new funcallNode(null, 1, thing, null, null); //string and Float64Array are both arrays. But does this slow it down?
		//throw 'TODO each of its chars becomes a double in Float64Array, and wrap that. Or is a string already similar enough to an array can just use it directly?';
	}else if(isNumber(thing)){
		return wrap(Float64Array.of(thing));
	}else{
		throw 'TODO should more types be supported? thing='+thing;
	}
};

//callPair, creates a funcallNode
const cp = (func, param)=>{
	//FIXME dedup at least in simple cases, else will create exponential number of nodes.
	return new funcallNode(null, Math.max(0,func.cur-1), null, func, param);
};

//x and y are both halted funcallNodes. Calls one on the other,
//without side-effects other than cache and optimizations and belief that
//certain calls are or are not too expensive to run again (todo salt them to try again if you want),
//and returns what that lambda call returns, unless (TODO) gives up for not enough gas
//(even if lower on stack theres lots of gas, this higher on stack is not allowed to use that).
const funcall = (func, param)=>{
	let callPair = cp(func,param);
	if(callPair.cur > 0) return callPair; //is halted
	
	TODO get which of a few standard functions (like 'matmul' or 'concat' or 'mapPut' etc), then run it recursively, within gas limits, on js stack.
	Use funcallPlugins map, where key is a string but in funcallNode may be Float64Array or string so would need to convert it (todo is that wasteful?).
	such as funcallPlugins.matmul or funcallPlugins.concat.
	
	
	
	//TODO a wrapped Float64Array takes 1 param, viewed as an array index.
	
	
	//TODO recursively eval the lambda call (x y)->z, which has no side-effects other than cache and optimizations,
	//and return z, else not have enough compute resources (gas) and give up early.
	throw 'TODO';
};


/*
FIXME should this use full callquad and NondetNode instead of using the js stack with callpairs?
If so, there would only be a step func



TODO create gaming-low-lag experience of playing with julia/mandelbrot fractal like
currently on http://benrayfield.net (except reactjs makes it slow on all but the newest computers, especially phone browsers) in this system,
and matrix multiply, and LSTM neuralnet, and simple games, etc, especially the attempted Eval of infiniteloops that ALWAYS HALTS cuz gives up early (gas, minGas, etc).
Use GPU.js (which seems to do 4gflops for matrixmultiply and 1000gflops (1tflop) when theres no memory to read (on a 9tflop gpu in both chrome and firefox 2020-12-5).
Also copy some of my music tools code in ported to WebAudioAPI's ScriptNode despite it being laggy (at least on an older computer, havent tried it recently 2020-12)
(delay of user input to cpu then computed about then to the sound heard, even though its high quality sound when it gets to your ears)...
could at least give people a taste of that kind of thing then could sometimes hook into sever for native access to sound hardware and while still
using mouse and keyboard and game controllers in webcam etc... could have low lag sound if that server is on your computer which has those speakers microphones etc.
Can make the sound low lag even in browser if also running local server, but want it to work for first few minutes at least in only browser, to show people what it can do.
From there can get more gpu efficiency etc if user chooses to upgrade to various opensource native plugins.


TODO the library will only be useful if it has sandboxed ops that do atomic pieces of efficient computing, like matrix multiply, concat, treemap get, treemap put, etc.
Those things can of course be done in pure interpreted mode of occamsfuncer, but its so slow it wouldnt be useful.
The plan for occamsfuncer is to optimize combos of the universal func to do that,
but the plan for this js library (which is only loosely based on occamsfuncer) is for those ops to see Float64Array directly
and create more Float64Arrays in simple combos of call pairs.
The choice of which of these standard calculations to do will be by Float64Array viewed as a string, like "matmul",
and maybe, like lazycl, it should take a small map as param instead of a list of params, where the map can be specified as a list alternating key val key val.

Lets define the empty Float64Array as a universalFunction, whose next param is a Float64Array like "matmul" or "concat"...
Or should the Float64Array of "matmul" and "concat" etc be used directly that way?

I want the vararg number of alternating key val key val way of specifying small map, but how?
Should there be, like in occamsfuncer2's curry op, a "number of curries" param so it can do vararg in general?
Each node could have an extra var (cur) which is the number of curries left before it runs.
*/


