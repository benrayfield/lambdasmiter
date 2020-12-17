"use strict";

//A node is a Float64Array or a float64 or a pair of nodes. Every function and data is a node. Every node is a lambda function.
//Controlflow is by calling node on node to create or find node, or by stackFrames in Float64Array.
//You can push as much data as you want onto the stack and modify it while in that same stackFrame but after its
//copied to node (wrapping another Float64Array or maybe a range in a shared one) its used as immutable.
//For example, push 512*512 doubles (start as 0, or copy from some node) on the stack, modify them in a loop
//defined by directly writing sp (stackPointer) and ip (instructionPointer) to define loops inside loops and if/else etc,
//then copy it to a node (becomes immutable) then display it in a htmlCanvas at gamingLowLag,
//and similar for browser gamepad api (like wireless xbox controller with 6 analog dimensions/dims),
//multitouchscreeip api for phone browsers, mouse position, WebAudioAPI ScriptNode, or various other IO plugins.

/* Syntax will be something like...

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
	
TODO should if/else be implemented as "ip = x*theCondition+(1-theCondition)*y", where theCondition is a var name whose value is 0 or 1? Could also make a func for
ip = if(theCondition)(x)(y), which returns x*theCondition+(1-theCondition)*y,
or better, make an opcode for (theCondition==0)?x:y, cuz that would be efficient.
	
see Eval function in javascript. Thats where can put in a code string similar to that.
*/

//in units of gas. Unlike compute cycles, get this back (or a fraction of it?) when memory is freed.
//TODO pricePerBit (and similar vars for other kinds of resources, like GPU.js compiling) should automatically vary like a free market price
//depending how much memory and compute cycles are available at the time, within the local computer.
var pricePerBit = 1;

//1 compute cycle is the unit of gas. 1 bit costs pricePerBit amount of gas, and get that back (or a fraction of it?) when the memory is freed.
//TODO does it also need minGas, or should that be in each stackFrame to add back to gas?
//FIXME make sure not to allow counterfeit gas by roundoff, so use integers only.
var gas = 1e9;

//stackPointer, into stack.
var sp = 0;

//instructionPointer, into stack, never into heap except indirectly by stack containing pointers to heap.
var ip = 0;

const log2OfStackSize = 24;
const stackSize = 1<<log2OfStackSize;
const stackMask = stackSize-1; //stackNum[Math.floor(x)&stackMask] is always a valid index
const stackNum = new Float64Array[1<<24]; //most work gets done here, for efficiency.
const stackNode = []; //pointers on stack to nodes on heap. Put the nodes in this list. Its either a float64 or node, so use stackNum for the float64s.
const stackName = []; //var names on stack. Most are null.//same size as stack. Most of these are null.
while(varnames.length < stack.length){
	stackNode.push(null);
	stackName.push(null);
}


//Either has [left and right] or array, and the other is null. Every leaf is a Float64Array
//or a float64 (TODO does that go in array too? Or is it a Float64Array.of(float64) aka size 1?
//TODO dedup
const NodeClass = function(left, right, array){
	this.left = left;
	this.right = right;
	this.array = array;
};

//returns a NodeClass wrapping a Float64Array, which may be deduped or not.
const leaf = (val)->{
	let node = new NodeClass(null, null, val);
	throw 'TODO that partial dedup';
};

//todo copyMemRangeToFindDuplicateOrCreateNode
const subarray = (aFloat64Array, fromIncl, toExcl)->{
	let size = toExcl-fromIncl;
	pay(size); //FIXME should this be paid here or when wrap it in node?
	return aFloat64Array.slice(fromIncl,toExcl);
};

//Returns a NodeClass with those 2 child nodes, and is deduped down to at least the array leafs which may each be deduped or not.
//That means that even if x and y are 2 arrays of equal content, that pair(x,y) called twice returns the same object by ===.
const pair = (left, right)->{
	let node = new NodeClass(left, right, null);
	throw 'TODO that partial dedup';
};

//returns node or one of equal forest shape and leafs content, the normed form.
const perfectDedup = (node)->{
	throw 'TODO';
};

//Do next step(s) in stack, modify sp, ip, etc. Return true if theres more work to do so call again, false if done.
var nextState = ()=>{
	let opcode = stack[ip];
	
	
	
	throw 'TODO';
};

//TODO compile some parts to js eval(generatedCode), GPU.js, webasm, acyclicFlow, etc. Also if theres a server (especially at localhost),
//its likely to have LWJGL OpenCL, Javassist, beanshell, jython, etc, which formally-verified code could be run on (so dont need to trust the code),
//and lower lag access to speakers and microphone thru JSoundCard (todo check lag of WebAudioAPI ScriptNode, havent checked that on my newer computer 2020-12-8).


//call a node on a node. For example, if func and param are both (S I I) then it would infinite loop until
//it runs out of gas (gas-minGas) available at this stackFrame.
//TODO uses nextState() until the calculation finishes or runs out of gas.
//
//TODO formalVerify the combo of opcodes a func may try to put on the stack (using stackFrame lambda, one of the opcodes)
//will obey the gas and other designs, or enforce that at every step (pay(1) at the start of each such call,
//memory allocation, etc, and get the memory part of it back (or a fraction of it?) when memory is freed).
var funcall = (func, param)=>{
	pay(1);
	throw 'TODO';
};

//see that example code in the other comment, including "anInnerLoop = sp++" and "ip = anInnerLoop".
//and "someFunctionDefinedHere = someFunctionDefinedHere(someFunctionDefinedHere)" or "(x+2) = *y //x[2] = y".
var Eval = (code)=>{
	throw 'TODO';
};

var safePtr = (num)=>(Math.floor(x)&stackMask);

var isNumber = (thing)=>{
	throw 'TODO';
};

//Write a double or node, into stack (stackNum or stackNode, depending on its type).
//Its caller's responsibility for lvalueEvaledAndNormed to be an integer in range 0 to stackSize-1 (or 0 to sp-1? or 0 to sp?).
var trustedStackWrite = (ptr, val)=>{
	//(isNumber(val) ? stackNum : stackNode)[ptr] = val;
	if(isNumber(val)) stackNum[ptr] = val;
	else stackNode[ptr] = val;
};

//Returns the evaled rvalue (such as 17 if its "x = y(z)+7" and y(z) evals to 10, or 0 if the trimmed line is the token 'fn'.
//In theory, this is slow, compared to Eval or nextState or funcall (js functions) and is mostly here for while I'm building the system.
//In theory, controlflow still works even if only eval 1 line at a time, such as "anInnerLoop = sp++"
//or "ip = anInnerLoop" or "someFunctionDefinedHere = someFunctionDefinedHere(someFunctionDefinedHere)" or "(x+2) = *y //x[2] = y".
//The line must not have multiple statements, whole loops, etc. It can start a loop, end a loop, branch in an if/else,
//set a var to an expression of other vars or literal, etc. The lvalue (whats on the left) of an = is anythning that evaluates to an integer,
//such as 1003 = x+5, which you can then get the value x+5 by *1003 such as in "z = *1003". There doesnt seem to be a need for &, the counterpart of *.
//Every statement seems to be "something = somethingElse" or "fn" (nothing else on the line) or an expression, or a stringLiteral or numberLiteral.
//Every expression appears to be parens and basic math ops, like x+5, or *(x+2), or a funcall x(y), or combos of those like x(z)(y(z))+5.
var EvalLine = (line)=>{
	console.log('EvalLine: '+line);
	let s = line.trim();
	if(s=='fn'){
		//This is the only kind of line thats not "something = somethingElse". For example if/else and the end of a loop both are "ip = something" to jump.
		throw 'TODO';
	}
	let indexOfEquals = line.indexOf('=');
	if(indexOfEquals == -1) throw 'Its not the token fn, so it must have an = in: '+line;
	let lvalue = s.substr(0,indexOfEquals).trim();
	let rvalue = s.substr(indexOfEquals+1,s.length).trim();
	console.log('lvalue = '+lvalue+', rvalue = '+rvalue);
	let lvalueEvaled = EvalExpr(lvalue);
	let rvalueEvaled = EvalExpr(rvalue);
	let lvalueEvaledAndNormed = safePtr(lvalueEvaled); //FIXME what if its a pair or array instead of a double?
	trustedStackWrite(lvalueEvaledAndNormed, rvalueEvaled);
	return rvalueEvaled;
};

//eval an expression, which does not contain '=', and is not when trimmed equal to 'fn'. Expressions like *(x+2) or x or x(z)(y(z))+5 or *1003 or 1003 or 4.56
//Returns a NodeClass or a double.
var EvalExpr = (line)=>{
	//TODO split it into smaller expressions and call recursively,
	//until reach a stringlit, numberlit, varname, etc.
	
	//First, choose a syntax.
	//TODO should math ops use the same syntax as lambdas, like "+(x)(5)" or "``+ x 5" (like ` syntax in unlambda) instead of "x+5"?
	
	throw 'TODO'
};

//A var name evals to the address on stack of that var. If you want the value at that address of var xyz, use *xyz or *(xyz+37) to mean xyz[37].
//
//This isnt needed for most calculations (such as in nextState, which will be called by funcall)
//as they use stack indexs, which may have varNames at them but they dont use the varNames.
//varNames only exist in a certain stackFrame and have no effect in higher or lower stackFrames.
//
//But in early experiments, I'll do the inefficient way and keep a map update of var name to stack of values or something like that.
//Or even less efficient, just linear search stackName[sp down to 0] for the name.
var EvalVarName = (varName)=>{
	//FIXME this allows names to be found lower on stack than current stackFrame, which should NOT be allowed cuz a lambda's return must depend only on its param,
	//and if you want to pass in a namespace, then use an immutable log-cost-forkEditable treemap (a node).
	for(let p=sp; p>=0; p--) if(varName == stackName[p]) return p; 
	return 0; //FIXME make sure nothing is ever stored at index 0
};

var readAtPtr = (ptr)=>{
	ptr = safePtr(ptr);
	return stackNode[ptr] || stackNum[ptr]; //Its a NodeClass if nonnull, else its the double in the Float64Array for efficiency.
};







