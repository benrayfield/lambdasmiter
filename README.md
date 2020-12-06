# lambdasmiter
A universal function for browser javascript loosely based on occamsfuncer (which is already well tested but too slow). Like hitting the halting-problem with Thor's hammer, it smites infinite loops etc 1 million times faster than the competition (such as browser tab running an infinite loop will after ~10 seconds say its not responding do you want to close the tab, or like Ethereum syncs every 15 seconds, or like a realtime-operating-system for critical machines except its compiler is many times slower than it reacts at runtime). Get creative. Have fun. Build things together, defended by lambdasmiter so your browser tab cant crash even if you try to run an infinite loop etc. Can in theory smite through 100,000 infinite loops per second (compared to the usual 0 to 0.1 per second) then get back to your cat memes and multiplayer gaming experiments without missing a step, without delaying the next video frame of the game, without your mouse or text cursor feeling jumpy, and especially for AI experiments where AIs write experimental code to create new AIs and people and AIs play and build together and share lambdas across the internet similar to how most apps share byte streams, except the lambda, not the byte, is the unit of computing here. Its a workaround for the halting problem which smites any lambda call reliably when its about to, or sometimes can be known that it would later, use more memory or compute cycles etc than its allocated thousands per second of such recursive limits within limits per stack height, in a subset of pure functional browser javascript with GPU optimizations and gaming low lag canvas pixels and game controllers and textareas and p2p etc, and whole or sparse system state (stateless lambdas, including Float64Array, in lazy merkle forest) is saveable and loadable in byte arrays, and random or malicious or just fun experimental code shared across the internet can be run safely together in a tiny fraction of a second. To measure how many infinite loops per second it can "smite / give up on / back out of", you could read how much "gas" is available, and fork nearly half of it (or any chosen amount) to each of 2 recursions, and return from that (2 exponent) how many recursions deep it goes summing the left child's recursions plus the right child's recursions and maybe plus one for here. Similarly, you could run some code with at most 80% of the available gas (excluding whatever the calls lower on stack do not allow here to use) and if that code returns and has only spent 5% of that gas, then whatever is left is still available here for further calls.

TODO define the "standard functions" that happen in funcall(funcallNode,funcallNode) in this mutable plugins map...
const funcallPlugins = {};

//todo include another matmul in its own js file that includes GPU*.js or inlines that and at the end overwrites funcallPlugins.matmul,
//but until thats loaded, there will be a slower implementation of it here (TODO).
funcallPlugins.matmul = (vm, aFuncall)->{ throw 'TODO' };

funcallPlugins.concat = (vm, aFuncall)->{ throw 'TODO' };

funcallPlugins.webasm = (vm, aFuncall)->{ throw 'TODO' };

//todo copy my experimental music tools code here which stores opcodes in int (2 uint12 pointers up to double[4096]
//and 1 uint8 opcode choosing multiply, plus, negate, sine, tanh, etc) which each are a (double,double)->double op in a binary forest,
//that reads 2 lower double[] indexs and writes the current index in a linear pass of a double[], and should be much faster than WebAudioAPI ScriptNode
//and can in theory read microphone holes (such as an electric guitar plugged into one, an EEG electrode plugged into the other, etc)
//and write speakers in WebAudioAPI or (if user hooks it in) a local server running JSoundCard
//for lower lag (especially low lag in linux, which is the only way it would be fast enough for a professional musician to want to use it in a live show).
funcallPlugins.acyclicflow = (vm, aFuncall)->{ throw 'TODO' };
