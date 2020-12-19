/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class Tuple extends Number implements List<Number>, UnaryOperator<Tuple>{
	
	public final int intValue(){
		return (int)doubleValue();
	}

	public final long longValue(){
		return (int)doubleValue();
	}

	public final float floatValue(){
		return (float)doubleValue();
	}

	public final double doubleValue(){
		//FIXME choose for this to be 1 or size(), preferably size() but only if does not interfere with java autoboxing optimization
		//combined with Op.jump doing the jump if numstack[hsp].doubleValue()==0 (ip += 23 bits in the double opcode)
		//else ip++;
		//Since The Number[] stack only contains Tuple and double, intValue() and longValue() and floatValue()
		//return a casted doubleValue() to make it easier for autoboxing optimization to use Number[] as only doubles, in theory.
		//If its slower than expected, redesign this to always return 1, and see if that makes it faster,
		//else keep it as size(). All lambdasmiterVMs have to return the same double value for the same tuple.
		//Tuples in theory will be allowed up to size 2 exponent 53 (or maybe 2 exponent 52 or 48?)
		//but in this implementation that would just run out of gas since doesnt have that much memory address space
		//or actual memory since a billion doubles are 8gB. Theres nothing stopping you from having
		//terabytes or more of memory, in theory, on the heap, but you'd have to code a different subclass of Tuple
		//if you want that much in a single tuple to not run out of gas for trying.
		//For bigdata its recommended to use trees of smaller tuples such as at most a few megabytes each or 64k doubles
		//or very tiny tuples like pairs or a tuple of size 100 holding 100 unicode chars or utf32 etc or doubleARGB pixels
		//or using double as a 2d voxel having an x y red green blue range in the integer value of the double
		//like an int32 can be a voxel in a 1024x1024 video with 16 possible brightnesses of each of red green and blue
		//which I've tested in other code can using java BufferedImage reach 50-200 fps,
		//or 4096x2048 video with 8 possible brightnesses of each of red green and blue fits in int32,
		//or you could use more than the low 32 bits of double but in javascript for example
		//you might have a tuple in a Uint32Array or Uint8ClampedArray that in abstract and reflection math fits in doubles,
		//or you might actually store a string in a string as long as you can translate it to/from doubles as needed,
		//or a byte array in a byte array, or view a html canvas itself as a tuple as long as you
		//make sure that tuple is garbcoled (garbage collected) before modifying the canvas
		//since, without exception, tuples are recursively immutable.
		//Every number is immutable and is [a float64 or list of number].
		//Also, Tuple being an abstract superclass might interfere with autoboxing optimization?
		return size();
	}
	
	public abstract boolean isValidBytecode();
	
}