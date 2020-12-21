package lambdasmiterJavaNano;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

/** immutable number. Every number is either a double or a list of number, so can form trees whose leafs are doubles. */
public final class Tuple extends Number implements List<Number>, UnaryOperator<Tuple>{
	
	lambdasmiterJavaNano, unlike the normal lambdasmiterJava, is going to do the whole system in 1 small java file.
	
	TODO way to hook in plugins (such as lazycl, acyclicFlow, etc) each as UnaryOperator<Tuple> or something like that?
	Dont actually need them for things that only need as much compute cycles as music tools, low resolution video, etc,
	but to reach teraflop, do need that. So get the basics working first, and start using it for dragAndDrop
	of function onto function to find/create function, and use it to create its own per pixel graphics
	as an implementation of ocuirect as thats the UI I want to start with.
	
	public static final Number[] numstack = new Number[1<<24];
	
	/** if 0, it means it hasnt been computed yet. This can say verify passes, verify fails, and has some bits of hsp-lsp. */
	private int verifyResult;
	
	//must be exactly 128 of these. the nonnegative 128 of them are push opcode onto stack as literal.
	public static final byte o=(byte)-128,
		opJumpIf0=o++, opJumpSwitch=o++, opCallWithoutTighten=o++, opCallAndTighten=o++,
		opCallAndTightenToUnary=o++, opCallForkNM=o++, opMin=o++, opMul=o++, opAdd=o++, opNeg=o++;
	
	
	public static Tuple tuple(Number... childs){
		throw new RuntimeException("TODO");
	}
	TODO how to charge for gas?
	
	public static Tuple dedup(Tuple t){
		throw new RuntimeException("TODO");
	}
	TODO how to charge for gas?
	
	private final Number[] childs;
	
	public Tuple(Number... childs){
		this.childs = childs;
	}
	
	/** call this lambda on param lambda to find/create lambda returned */
	public Number e(Number param){
		throw new RuntimeException("TODO");
	}
	TODO implement this using evalSelfOn (could do it with this as UnaryOperator<Tuple> . apply(Tuple) but numstack is lower bigO).
	TODO how to charge for gas?
	
	public Tuple apply(Tuple t){
		throw new RuntimeException("TODO");
	}

	
	public int intValue(){
		return (int)doubleValue();
	}

	public long longValue(){
		return (int)doubleValue();
	}

	public float floatValue(){
		return (float)doubleValue();
	}

	public double doubleValue(){
		return size();
	}

	public int size(){
		return childs.length;
	}

	public boolean isEmpty(){
		throw new RuntimeException("TODO");
	}

	public boolean contains(Object o){
		throw new RuntimeException("TODO");
	}

	public Iterator<Number> iterator(){
		throw new RuntimeException("TODO dont allow iterator to remove");
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

	public Number get(int index){
		return childs[index];
	}

	public Number set(int index, Number element){
		throw new RuntimeException("TODO");
	}

	public void add(int index, Number element){
		throw new RuntimeException("TODO");
	}

	public Number remove(int index){
		throw new RuntimeException("TODO");
	}

	public int indexOf(Object o){
		throw new RuntimeException("TODO");
	}

	public int lastIndexOf(Object o){
		throw new RuntimeException("TODO");
	}

	public ListIterator<Number> listIterator(){
		throw new RuntimeException("TODO dont allow iterator to remove");
	}

	public ListIterator<Number> listIterator(int index){
		throw new RuntimeException("TODO dont allow iterator to remove");
	}

	public List<Number> subList(int fromIndex, int toIndex){
		throw new RuntimeException("TODO");
	}
	
	public long evalSelfOn(int Lsp, int Hsp, long GasMinusLgas, boolean AllowDirty, boolean isDirty){
		if(!isValidBytecode()) throw new RuntimeException("First, every Number can be called on every Number and is a function. TODO whats that default, non-throwing behavior to do, such as return 0.0 or smite or something... when a function of invalid bytecode (which I am not being used as such a function but I am its [0]) is called?");
		int ip = 0;
		int hsp = Hsp;
		while(--GasMinusLgas > 0){ //FIXME return 0L if run out of gas. Use 0L to mean SMITE, and if at least 1L is left then it worked.
			final double opcode = (double)tuple[ip];
			switch((byte)opcode){
				case callWithoutTighten:
					check isValid*functioncontaining*bytecode... then...
					put it on stack
					then subtract from GasMinusLgas however much allow it to use (since its "withoutTighten" its
						all of it, minus maybe just enough to SMITE self in case it runs out)
					then call Tuple.evalSelfOn recursively.
					Then whatever it returns, check if its 0L, and if so it was SMITED,
					then either way add what it returns into GasMinusLgas which is how much gas it didnt use of what was given.
					throw new RuntimeException("TODO");
					TODO since Numstack is now a static var, can create push, pushD, pop, popD, peek, peekD etc funcs, instead of duplicating that here.
				break;case jumpIf0:
					throw new RuntimeException("TODO");
					TODO since Numstack is now a static var, can create push, pushD, pop, popD, peek, peekD etc funcs, instead of duplicating that here.
				break;case max:
					Numstack[hsp-1] = Math.max(Numstack[hsp-1].doubleValue(),Numstack[hsp].doubleValue());
					Numstack[hsp--] = 0.; //garbcol in case its a tuple
					ip++;
					TODO since Numstack is now a static var, can create push, pushD, pop, popD, peek, peekD etc funcs, instead of duplicating that here.
				break;case mul:
					Numstack[hsp-1] = Numstack[hsp-1].doubleValue()*Numstack[hsp].doubleValue();
					Numstack[hsp--] = 0.; //garbcol in case its a tuple
					ip++;
					TODO since Numstack is now a static var, can create push, pushD, pop, popD, peek, peekD etc funcs, instead of duplicating that here.
				default:
					//opcodes 0..127 are nonnegative opcode. push that literal. To push negative literal nl, 2 opcodes: -nl then opNeg.
					//FIXME make sure exactly the other 128 of them are in the switch, and any not defined in the spec yet, do SMITE.
					Numstack[hsp++] = opcode;
					ip++;
					TODO since Numstack is now a static var, can create push, pushD, pop, popD, peek, peekD etc funcs, instead of duplicating that here.
			}
		}
		return GasMinusLgas;
	}
	/*TODO consider splitting numstack and every tuple, both into a Tuple[] and double[]???? for efficiency.
	Makes things harder to think about, harder to organize, but would be faster.
	
	TODO use java ForkJoinPool, java.util.Stream, DoubleStream, etc?
		How can i call this in parallel to implement Op.callForkNM?
		thats os threads andor greenthreads. of course OpenclUtil.callOpencl and Opencl.callOpenclDependnet are optimizations.
	*/ 
	
	public static void main(){
		TODO inline a JPanel that displays a BufferedImage, observes mouse movements, mouse wheel, etc,
		whose state is a Number, and which gives back a Number for UI events, etc. Put it on screen in a JFrame,
		and have a basic File and dir storage of RandomAccessFile or something like that,
		for longterm storage and turning the system on and off, to start actually using it,
		and use the system to build the system asap.
	}

}
