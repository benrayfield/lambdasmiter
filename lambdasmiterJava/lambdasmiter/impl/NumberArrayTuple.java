/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

import javax.tools.Tool;

import lambdasmiter.Tuple;

/** immutable, except the 2 booleans that say its verified bytecode or not or unknown either way.
Every node is a double or a tuple/list of nodes. As bytecode, its unusual in that it ALWAYS HALTS
but is not always turing complete as that means it would allow infinite compute cycles and memory.
Its timing precision will be around a microsecond for greenthreads, around .01 second for OS threads.
<br><br>
How to use Tuple as UnaryOperator<Tuple>, a pure stateless lambda, is explained in comment of Op.tighten.
Basically the input and output tuples are stack[lsp..hsp] or stack[x..hsp]
where x>lsp and hsp-x>=minTupleSizeOfParamAndReturn(thisTupleAsBytecode).
An example with complexNumMultiply (a theoretical Tuple of bytecode) is explained there.
But the most efficient way to use Tuple bytecode is with the mutable stack optimization
and only start the outermost (lowest on stack) call using UnaryOperator<Tuple>.
<br><br>
As UnaryOperator<Tuple>, the param is stack[lsp..hsp],
and return is stack[lsp..hsp] of equal size after it finishes,
so use of a stack is only an optimization of this pure functional lambda system,
especially if you consider opForkNM such as could fork millions of threads or greenthreads for GPU matmul
and return within .01 second (video frame time compatible) at least from 64k greenthreads
(or 8 OS threads of 8k greenthreads each, for a little extra lag,
or 64 OS threads if you have a threadripper cpu for example, i havent used one,
or a few thousand GPU threads cuz they do alot of things in parallel,
or my other code seems to have no problem with a million GPU threads returning before the next video frame
as long as the opencl kernel is already compiled and lwjgl CLMem objects allocated from a pool, for example)
(and each of those can fork more threads recursively,
such as a way to recurse minimax in chess in parallel to depth 10 in a single lambda call)
but millions of threads might be closer to .1 or 1 or 10 seconds depending what they're doing. 
For example, a Tuple thats the bytecode for multiplying 2 complexnums
gets a stack whose top 5 things (or 4 things if exclude itself? but itself is probably on stack top)
are [x.real x.imaginary y.real y.imaginary complexNumMultiply]
and it leaves on the stack [0 0 0 return.real return.imaginary],
or something like that. The 3 padded 0s are cuz a function must leave stack at same height before and after the call,
and can only read and write whats in stack[lsp..hsp] and lsp will often be increased
(but as a constraint, cant be decreased until function return, it goes back to what it was before the call)
to limit what a function call can read/write on the stack, for example if you have megabytes available
to you (and your caller has even more below that) then you can limit a call of complexNumMultiply (a derived func)
to only read/write the top 5 things within that, or however big or small a range you choose within
the top of whats available to you... (or if you want to let functions each read and write in
your megabytes of data, and share data with eachother thru that,
then all those function calls are viewed as UnaryOperator<megabytes of tuple, megabytes of tuple>
but dont actually copy it to tuples cuz tuple is immutable on heap and only stack is mutable,
such as it can in theory do efficient screen blits that way (blit from part of stack to other part of stack,
and after all those blits are done, .01 second later its copied to a html canvas or java BufferedImage
or to opengl triangles and textures or to/from whatever system you choose to hook it into)...
and if caller wants to get rid of those 0s (and maybe they should be above the return value instead of below)
can move that on stack and pop (or simpler, just 3 pops, or a pop3 op)... todo details of design to work out.
It does not create any java objects to call complexNumMultiply, just autoboxed doubles,
and similar for a lambdasmiterVM in javascript or other languages,
BUT if you use it as UnaryOperator<Tuple> then it does use objects (have garbcol costs etc)
cuz Tuple is an object, but if this happens on stack by pushing complexNumMultiply
above those 4 doubles (2 complexnums) to multiply, then it will not create Tuple objects,
just use the existing complexNumMultiply where complexNumMultiply.cache_isCertainlyVerified is true.
That makes this a JIT (Just In Time Compiler), even in interpreted mode,
and can optionally (TODO) be further optimized by
various other JIT compilers such as LWJGL OpenCL, Javassist, GPU.js, etc.
To emulate occamsfuncer (a 15 param universal lambda function / pattern calculus function)
its a UnaryOperator of tuple size 1 to tuple size 1 as it always uses currying of those 15 params.
This system should be able to JIT compile and bytecode verify fast enough to blit bytecode to the screen
like its the pixel colors of video, and TODO that should be one of the testcases,
in the lambdasmiterVM in java and separately in the lambdasmiterVM in javascript
(and maybe make one in python and android too, etc). All these lambdasmiterVMs will work together
seamlessly at gaming low lag across the internet inside massively multiplayer games in peer to peer
networks central networks and wherever and whatever form it happens to be.
A bytecode for a 512x512 32 bit color image is
a double[someSmallConstantNumberOfOpsToPopTheStackThatManyTimes+512*512] of those same uint32 values
or might be optimized into ints (cuz any function call must leave stack at same height before and after the call),
specificly that any nonnegative double value, used as an opcode, pushes itself onto stack,
and the interesting opcodes are all negative, but I meant it as to bytecode verify interesting opcodes
such as relevant to the halting problem, and as the name says, it will smite lag (which may be caused by
an infinite loop or other halting problem related thing) if it
(recursively tightenable constraints at each stack height) delays past
the time of the next video frame, still at gaming low lag and video streaming speeds,
but dont expect the highest resolution like in other games, as it is primarily a system of pure lambdas.
Do expect, instead, that the extreme flexibility and turing complete control of the system
is useful and fun for those kinds of experiments and games and AI research millions of people do together.
*/
public final class NumberArrayTuple extends Tuple{
	
	private final Number[] tuple;
	
	/** starts false. becomes true when verify this as bytecode to use as a lambda and verify deterministicly says yes. */
	private boolean cache_isCertainlyVerified;
	
	/** starts false. becomes true when verify this as bytecode to use as a lambda and verify deterministicly says no. */
	private boolean cache_isCertainlyFailedVerify;
	
	/** is always at least 1, so starts as 0 and is calculated once when and if cache_isCertainlyVerified becomes true.
	As UnaryOperator<Tuple>, any param of at least this size will work (though may still infinite loop and run out of gas etc),
	and the returned Tuple is always same size as the param Tuple. Also, any smaller Tuple is technically allowed
	but will not run the bytecode, will instead return some constant or smite itself etc (todo choose a design).
	*/
	private int cache_needMinTupleSize;
	
	//TODO merge isCertainlyVerified and isCertainlyFailedVerify into 1 var?
	
	/** todo lazyDedup of tuple, and have a tuple(Number...) function instead, andOr tuple(VM, int fromIncl, int toExcl), etc */
	public NumberArrayTuple(Number... tuple){
		this.tuple = tuple;
	}
	
	public Number get(int index){
		return tuple[index];
	}

	public int size(){
		return tuple.length;
	}
	
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

	public boolean isEmpty(){
		return tuple.length==0;
	}

	public boolean contains(Object o){
		throw new RuntimeException("TODO");
	}

	public Iterator<Number> iterator(){
		throw new RuntimeException("TODO");
	}

	public Object[] toArray(){
		throw new RuntimeException("TODO");
	}

	public <T> T[] toArray(T[] a){
		throw new RuntimeException("TODO");
	}

	public boolean add(Number e){
		throw new RuntimeException("TODO");
	}

	public boolean remove(Object o){
		throw new RuntimeException("TODO");
	}

	public boolean containsAll(Collection<?> c){
		throw new RuntimeException("TODO");
	}

	public boolean addAll(Collection<? extends Number> c){
		throw new RuntimeException("TODO");
	}

	public boolean addAll(int index, Collection<? extends Number> c){
		throw new RuntimeException("TODO");
	}

	public boolean removeAll(Collection<?> c){
		throw new RuntimeException("TODO");
	}

	public boolean retainAll(Collection<?> c){
		throw new RuntimeException("TODO");
	}

	public void clear(){
		throw new RuntimeException("TODO");
	}

	public Number set(int index, Number element){
		throw new UnsupportedOperationException("immutable");
	}

	public void add(int index, Number element){
		throw new UnsupportedOperationException("immutable");
	}

	public Number remove(int index){
		throw new UnsupportedOperationException("immutable");
	}

	public int indexOf(Object o){
		throw new RuntimeException("TODO");
	}

	public int lastIndexOf(Object o){
		throw new RuntimeException("TODO");
	}

	public ListIterator<Number> listIterator(){
		throw new RuntimeException("TODO");
	}

	public ListIterator<Number> listIterator(int index){
		throw new RuntimeException("TODO");
	}

	public List<Number> subList(int fromIndex, int toIndex){
		throw new RuntimeException("TODO");
	}

	public NumberArrayTuple apply(Tuple t){
		throw new RuntimeException("TODO");
	}
	
	

}



/** immutable *
public final class Node{
	
	/** If this Node is a tuple, it must still be useable as a double, even though its (todo choose a design)
	just something trivial like always be 0.
	*
	public final double d;
	
	/** immutable *
	public final List<Node> tuple;
	
	public Node(double d){
		this.d = d;
		this.tuple = Collections.EMPTY_LIST;
	}
	
	/** param must be immutable *
	public Node(List<Node> tuple){
		this.d = 0;
		this.tuple = tuple;
	}
	
	//TODO equals and hashCode. equals by secureHash.

}


package lambdasmiter;

import java.util.Arrays;
import java.util.Random;

public class TestDoubleVsTupleSpeed{
	
	public static void main(String[] args){
		
		final Number[] stack = new Number[1<<24];
		Random rand = new Random();
		for(int i=0; i<stack.length; i++){
			stack[i] = rand.nextGaussian();
		}
		for(int i=0; i<stack.length; i++){
			if(i>0 && ((i%3==0)||((i&23434563)%7==0))){
				Number[] childs = new Number[rand.nextInt(5)*rand.nextInt(7)+1];
				for(int j=0; j<childs.length; j++){
					childs[j] = stack[rand.nextInt(i)];
				}
				stack[i] = new DoubleTupleNode(childs);
			}
		}
		System.out.println("starting");
		long timeStart = System.nanoTime();
		double sum = 0;
		//for(Number n : stack) sum += n.doubleValue()+child(n,0).doubleValue();
		//for(Number n : stack) sum += n.doubleValue();
		for(Number n : stack) sum += child(n,0).doubleValue();
		double duration = (System.nanoTime()-timeStart)*1e-9;
		double opsEach = 1;
		//double opsEach = 2;
		double opsPerSec = (stack.length*opsEach)/duration;
		System.out.println("duration="+duration+" opsPerSec="+opsPerSec);
	}
	
	public static Number child(Number parent, int index){
		return (parent instanceof DoubleTupleNode) ? ((DoubleTupleNode)parent).get(index) : 0.;
	}

}


*/