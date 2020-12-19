package lambdasmiter.impl;

/**
<br><br>
every recursion into next deeper bytecode, every callWithoutTighten[
	private final int[] ipStack;
	private final Tuple[] bytecodeStack;
]
only every callAndTighten[
	(*callWithoutTighten stuff and...)
	private final double[] lowgasStack;
	private final int[] lspStack;
	private final int[] hspStack;
]
<br><br>
private int metastackIndexWhereTightenedToNotAllowDirty; //private final boolean[] allowdirtyornotStack;
Dont forget about Op.forkNM, which divides gas-lowGas into floor((gas-lowGas)/N) parts,
each of which have their own "multiverse branch" of the stack they can in turing complete ways continue differently,
which I could easily represent as a java class of those few vars and a pointer to lower metastack (instance of same class)
so N linkedlists whose cdr is all that same lower on the stack. But I dont want to alloc java object
for many small calls.
I want metastack to be lightweight.
What if Op.tighten does 2 things, 1 before a call and 1 after, to loosen back to where it was,
as some kind of metastackopcodes like lsp+=632 doACall then lsp-=632.
A problem is, if gas runs out in the middle of doACall, it might have already further increased lsp,
so in that case need to make sure whatever it did also gets loosened,
and same for few other registers/eachtheirownpartofmetastack.
Consider the optimization that I can do n dimensional vector arithmetic in a single integer,
such as using long as 6 int10s, but need more bits than int10. could use long as 2 int32s.
but requires not overflow those inner number types.
A problem is if emulating forkNM in cpu, instead of GPU where it would most often be used (GPU later, prototype now),
that they all may need to read parts of their shared stack below (lsp up something,at a fork index in stack,
above which they can mutably differ, then join them in a M*N size range in stack to write the results)
Maybe it should fork N VMs which check each action of read or write stack (single index or range)
and send whats lower to a VM they have a ptr to else do it in themself,
so dont use Number[] directly but use a function.
Allocating VMs is very very lightweight, but not so lightweight Id want to do it for complexNumMultiply
which is only a few basic math ops. It would make that a few times slower,
mostly due to java objects or javascript objects etc having garbcol overhead.
Also... no writes would be allowed to the VM seen thru ptr, only reads,
cuz other parallel VMs could be accessing it. You can readwrite higher but only read lower.
So maybe it needs another register, similar to lsp and hsp, that goes between them (inclusive),
which above it can be readwrite but below it is read only.
That fits well with forkNM.
<br><br>
^^^
problem SOLVED of that being too many vars to efficiently save every funcall by...
Have 2 CALL opcodes, 1 thats callAndTighten, and 1 thats callWithoutTighten,
and only callAndTighten adds another such entry to metastack,
and callWithoutTighten adds some smaller subset of that stuff. so its fast.
test it on complexnummultiply asap, and other testcases.
<br><br>
*
and (todo choose a design, or only for use in forkMN) might also be these 2 more vars:
lspRead <= lspReadwrite <= hsp <= ksp (fixme might be offby1 etc).
<br><br>
lspRead <= lspReadwrite <= hsp (fixme might be offby1 etc).
This register was added as an optimization for forkNM but may be more generally useful.
For example, an opencl ndrange kernel param
can be a shared constant block of memory somewhere between lspRead and lspReadwrite.
Any number of threads can differ above lspReadwrite while sharing whats below it.
Imagine that instead of stack index being an int, its a long, where the high 32 bits are threadId,
and the low 32 bits are stack index as usual, in a small piece of multiverse of branches of stack,
but of course you'd need alot more than 2^32 branches if they expand recursively as forks of forks of forks,
but its an optimization usable for the number of forks expected to happen within a fraction of a second
and a VM is normally meant to be reset between each 2 interactions with the outside world
like every 1./60 second between 2 video frames of a game (or industrial sensors etc, even faster and fewer).
Some languages dont have long. Double could hold 2 uint26, and the default stack size is 2^24,
so that could work to use double as a multiverse stack index of up to 2^26 threads
(accumulate threads in multiple calls of forkNM), but optimizing it is the main issue.
In languages that do have long, could just use long for efficiency as thats an internal VM calculation.
What if, aboveOrEqualTo hsp, theres a ksp (kernel stack pointer) that Op.tighten can reduce but never increase,
like in an opencl ndrange kernel it needs to limit the amount of private memory
that can see a get_global_id(0)->oneOf(0..(N-1)) etc, cuz theres N of them that need to align.
<br><br>
low stack pointer, below highSp *
private int lspRead;
*
private int lspReadwrite;
<br><br>
** normal/high stack pointer *
<br><br>
private int hsp;
//kernel stack pointer, tightens downward. TODO? see comment in lspReadwrite: private int maxSp/ksp;
//lspRead <= lspReadwrite <= hsp <= ksp. (fixme offby1?).
//used for limiting max stack it can use, especially
//in things like opencl ndrange kernels (lambdasmiter is intentionally designed to
//not depend on opencl, cuda, java, javascript, python, android, or any other specific system.
//its a kind of computing, not a specific implementation),
//though could be used for any kind of parallel computing or single threaded to describe size of stack nomatter how huge
//thats available to push toward in many calls of calls of calls..
private int ksp;
<br><br>
FIXME in opForkMN, gas-lowGas must be split N ways equally between N parallel calls *
private double gas, lowGas;
*/
public final class Constraint{
	
	/** null if stack is empty. This is next lower Constraint on stack,
	and multiple Constraint can have the same prev, which happens in Op.forkNM,
	at least in abstract math but may be optimized to represent those constraints
	in a more efficient form such as in compiled opencl ndrange kernel code etc,
	instead of creating that many java object.
	Or TODO maybe there should be a kind of ConstraintN so only create 1
	that represents all parallel forks if those forks are all doing some very
	similar calculation aligned on a regular grid of memory.
	*/
	public final Constraint prev;
	
	/** starts true at bottom of stack, and at any point lower on stack can choose to become false (pure determinism)
	there and everywhere deeper on stack, until return from that, and when cross that border on stack,
	allowDirty is true again. Like recursive gas limits, this can be done in many recursions in and out differently.
	When !allowDirty and its about to set dirty to true, it instead evals to (S I I (S I I)) aka a very simple infinite loop,
	aka smites itself, which causes it to give up on that calculation and back out to
	the innermost spend (gas limit/catchHaltingProblem) call.
	Thats the same way it works in occamsfuncer, or at least in some forks of occamsfuncer which are not all
	merged or complete yet, but the universal function of occamsfuncer is working in the newest for as of 2020-12.
	*/
	public final boolean allowDirty;
	
	/** low/min gas. lgas<=gas.
	Range 0 to 2 exponent 53, for efficient compatibility with systems that only have float64/double hardware ops,
	but for efficiency in java long is faster than double, and gas is always an integer even if its stored in a double
	to avoid counterfeiting it by intentional roundoff.
	*/
	private final long lgas;
	
	/** lsp <= hsp.
	and (todo choose a design, or only for use in forkMN) might also be these 2 more vars:
	lspRead <= lspReadwrite <= hsp <= ksp (fixme might be offby1 etc).
	*/
	private final int lsp, hsp;
	
	public Constraint(Constraint prev, boolean allowDirty, long lgas, int lsp, int hsp){
		this.prev = prev;
		this.allowDirty = allowDirty;
		this.lgas = lgas;
		this.lsp = lsp;
		this.hsp = hsp;
	}

}