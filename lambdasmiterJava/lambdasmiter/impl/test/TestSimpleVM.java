/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter.impl.test;

import lambdasmiter.TestAnyLambdasmiterVM;
import lambdasmiter.VM;
import lambdasmiter.impl.SimpleVM;

public class TestSimpleVM{
	
	public static void main(String[] args){
		VM vm = new SimpleVM();
		System.out.println("Testing "+vm);
		TestAnyLambdasmiterVM.test(vm);
		System.out.println("Tests pass for "+vm);
	}

}
