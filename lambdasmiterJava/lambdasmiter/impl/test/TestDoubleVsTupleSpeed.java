/** Ben F Rayfield offers lambdasmiter opensource MIT license */
package lambdasmiter.impl.test;

import java.util.Arrays;
import java.util.Random;

import lambdasmiter.impl.NumberArrayTuple;

public class TestDoubleVsTupleSpeed{
	
	public static void main(String[] args){
		
		for(int repeat=0; repeat<2; repeat++){
			for(int ss=1; ss<=(1<<24); ss*=2){
				System.out.println();
				System.out.println();
				final Number[] stack = new Number[ss];
				System.out.println("rep="+repeat+" Stack size: "+stack.length);
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
						stack[i] = new NumberArrayTuple(childs);
					}
				}
				
				
				System.out.println("starting doubles");
				long timeStart = System.nanoTime();
				double sum = 0;
				for(Number n : stack) sum += n.doubleValue();
				double opsEach = 1;
				//double opsEach = 2;
				double duration = (System.nanoTime()-timeStart)*1e-9;
				double opsPerSec = (stack.length*opsEach)/duration;
				System.out.println("doubles: duration="+duration+" opsPerSec="+opsPerSec);
				
				System.out.println("starting tuples");
				timeStart = System.nanoTime();
				sum = 0;
				//for(Number n : stack) sum += n.doubleValue()+child(n,0).doubleValue();
				for(Number n : stack) sum += child(n,0).doubleValue();
				duration = (System.nanoTime()-timeStart)*1e-9;
				opsEach = 1;
				//double opsEach = 2;
				opsPerSec = (stack.length*opsEach)/duration;
				System.out.println("tuples: duration="+duration+" opsPerSec="+opsPerSec);
				
				/* for stack size of 1<<24, before I put in the loop to try multiple stack sizes.
				starting doubles
				doubles: duration=0.07066120000000001 opsPerSec=2.3743180132802725E8
				starting tuples
				tuples: duration=0.3936137 opsPerSec=4.2623557056067914E7
				*/
			}
		}
	}
	
	public static Number child(Number parent, int index){
		return (parent instanceof NumberArrayTuple) ? ((NumberArrayTuple)parent).get(index) : 0.;
	}

}


/*



rep=0 Stack size: 1
starting doubles
doubles: duration=1.5E-6 opsPerSec=666666.6666666666
starting tuples
tuples: duration=3.57E-4 opsPerSec=2801.1204481792715


rep=0 Stack size: 2
starting doubles
doubles: duration=4.0000000000000003E-7 opsPerSec=5000000.0
starting tuples
tuples: duration=9.000000000000001E-7 opsPerSec=2222222.222222222


rep=0 Stack size: 4
starting doubles
doubles: duration=9.000000000000001E-7 opsPerSec=4444444.444444444
starting tuples
tuples: duration=2.7E-6 opsPerSec=1481481.4814814816


rep=0 Stack size: 8
starting doubles
doubles: duration=6.000000000000001E-7 opsPerSec=1.3333333333333332E7
starting tuples
tuples: duration=1.7E-6 opsPerSec=4705882.352941177


rep=0 Stack size: 16
starting doubles
doubles: duration=9.000000000000001E-7 opsPerSec=1.7777777777777776E7
starting tuples
tuples: duration=2.3E-6 opsPerSec=6956521.739130435


rep=0 Stack size: 32
starting doubles
doubles: duration=1.3E-6 opsPerSec=2.4615384615384616E7
starting tuples
tuples: duration=5.2E-6 opsPerSec=6153846.153846154


rep=0 Stack size: 64
starting doubles
doubles: duration=2.1000000000000002E-6 opsPerSec=3.0476190476190474E7
starting tuples
tuples: duration=7.3E-6 opsPerSec=8767123.287671233


rep=0 Stack size: 128
starting doubles
doubles: duration=5.84E-5 opsPerSec=2191780.821917808
starting tuples
tuples: duration=1.01E-5 opsPerSec=1.2673267326732673E7


rep=0 Stack size: 256
starting doubles
doubles: duration=7.2000000000000005E-6 opsPerSec=3.555555555555555E7
starting tuples
tuples: duration=1.86E-5 opsPerSec=1.3763440860215053E7


rep=0 Stack size: 512
starting doubles
doubles: duration=1.3300000000000001E-5 opsPerSec=3.849624060150375E7
starting tuples
tuples: duration=2.33E-5 opsPerSec=2.1974248927038625E7


rep=0 Stack size: 1024
starting doubles
doubles: duration=2.5200000000000003E-5 opsPerSec=4.0634920634920634E7
starting tuples
tuples: duration=4.6500000000000005E-5 opsPerSec=2.2021505376344085E7


rep=0 Stack size: 2048
starting doubles
doubles: duration=5.03E-5 opsPerSec=4.0715705765407555E7
starting tuples
tuples: duration=8.87E-5 opsPerSec=2.3089064261555806E7


rep=0 Stack size: 4096
starting doubles
doubles: duration=9.920000000000001E-5 opsPerSec=4.129032258064516E7
starting tuples
tuples: duration=1.809E-4 opsPerSec=2.2642343836373687E7


rep=0 Stack size: 8192
starting doubles
doubles: duration=2.0130000000000001E-4 opsPerSec=4.0695479384003974E7
starting tuples
tuples: duration=3.6080000000000004E-4 opsPerSec=2.270509977827051E7


rep=0 Stack size: 16384
starting doubles
doubles: duration=1.317E-4 opsPerSec=1.244039483675019E8
starting tuples
tuples: duration=4.926E-4 opsPerSec=3.326025172553796E7


rep=0 Stack size: 32768
starting doubles
doubles: duration=1.451E-4 opsPerSec=2.258304617505169E8
starting tuples
tuples: duration=5.684E-4 opsPerSec=5.7649542575650945E7


rep=0 Stack size: 65536
starting doubles
doubles: duration=3.064E-4 opsPerSec=2.1389033942558745E8
starting tuples
tuples: duration=0.0011029 opsPerSec=5.9421525070269294E7


rep=0 Stack size: 131072
starting doubles
doubles: duration=0.0030961 opsPerSec=4.233454991763832E7
starting tuples
tuples: duration=0.0059128 opsPerSec=2.2167501014747664E7


rep=0 Stack size: 262144
starting doubles
doubles: duration=7.519E-4 opsPerSec=3.486421066631201E8
starting tuples
tuples: duration=0.0018224 opsPerSec=1.4384547848990342E8


rep=0 Stack size: 524288
starting doubles
doubles: duration=0.0011367 opsPerSec=4.6123691387349343E8
starting tuples
tuples: duration=0.0058715 opsPerSec=8.92937068892106E7


rep=0 Stack size: 1048576
starting doubles
doubles: duration=0.0043206 opsPerSec=2.4269221867333242E8
starting tuples
tuples: duration=0.0308147 opsPerSec=3.402843448094578E7


rep=0 Stack size: 2097152
starting doubles
doubles: duration=0.0074501 opsPerSec=2.814931343203447E8
starting tuples
tuples: duration=0.0543027 opsPerSec=3.861966347897986E7


rep=0 Stack size: 4194304
starting doubles
doubles: duration=0.0166274 opsPerSec=2.5225254700073373E8
starting tuples
tuples: duration=0.07866110000000001 opsPerSec=5.3321196881304726E7


rep=0 Stack size: 8388608
starting doubles
doubles: duration=0.039108000000000004 opsPerSec=2.1449851692748284E8
starting tuples
tuples: duration=0.17497220000000002 opsPerSec=4.794251886871171E7


rep=0 Stack size: 16777216
starting doubles
doubles: duration=0.06446790000000001 opsPerSec=2.6024139145218003E8
starting tuples
tuples: duration=0.5078889 opsPerSec=3.3033240143661343E7


rep=1 Stack size: 1
starting doubles
doubles: duration=6.000000000000001E-7 opsPerSec=1666666.6666666665
starting tuples
tuples: duration=5.000000000000001E-7 opsPerSec=1999999.9999999998


rep=1 Stack size: 2
starting doubles
doubles: duration=3.0000000000000004E-7 opsPerSec=6666666.666666666
starting tuples
tuples: duration=5.000000000000001E-7 opsPerSec=3999999.9999999995


rep=1 Stack size: 4
starting doubles
doubles: duration=7.000000000000001E-7 opsPerSec=5714285.714285714
starting tuples
tuples: duration=5.000000000000001E-7 opsPerSec=7999999.999999999


rep=1 Stack size: 8
starting doubles
doubles: duration=6.000000000000001E-7 opsPerSec=1.3333333333333332E7
starting tuples
tuples: duration=7.000000000000001E-7 opsPerSec=1.1428571428571427E7


rep=1 Stack size: 16
starting doubles
doubles: duration=8.000000000000001E-7 opsPerSec=2.0E7
starting tuples
tuples: duration=1.1E-6 opsPerSec=1.4545454545454545E7


rep=1 Stack size: 32
starting doubles
doubles: duration=1.2000000000000002E-6 opsPerSec=2.6666666666666664E7
starting tuples
tuples: duration=1.6000000000000001E-6 opsPerSec=2.0E7


rep=1 Stack size: 64
starting doubles
doubles: duration=2.0000000000000003E-6 opsPerSec=3.1999999999999996E7
starting tuples
tuples: duration=2.9E-6 opsPerSec=2.2068965517241377E7


rep=1 Stack size: 128
starting doubles
doubles: duration=1.0000000000000002E-6 opsPerSec=1.2799999999999999E8
starting tuples
tuples: duration=1.9E-6 opsPerSec=6.736842105263157E7


rep=1 Stack size: 256
starting doubles
doubles: duration=1.3E-6 opsPerSec=1.9692307692307693E8
starting tuples
tuples: duration=3.0E-6 opsPerSec=8.533333333333333E7


rep=1 Stack size: 512
starting doubles
doubles: duration=2.3E-6 opsPerSec=2.226086956521739E8
starting tuples
tuples: duration=2.7600000000000003E-5 opsPerSec=1.8550724637681156E7


rep=1 Stack size: 1024
starting doubles
doubles: duration=4.5E-6 opsPerSec=2.2755555555555555E8
starting tuples
tuples: duration=1.2E-5 opsPerSec=8.533333333333333E7


rep=1 Stack size: 2048
starting doubles
doubles: duration=8.6E-6 opsPerSec=2.381395348837209E8
starting tuples
tuples: duration=2.32E-5 opsPerSec=8.827586206896551E7


rep=1 Stack size: 4096
starting doubles
doubles: duration=1.7E-5 opsPerSec=2.4094117647058824E8
starting tuples
tuples: duration=8.71E-5 opsPerSec=4.70264064293915E7


rep=1 Stack size: 8192
starting doubles
doubles: duration=1.0300000000000001E-5 opsPerSec=7.953398058252426E8
starting tuples
tuples: duration=4.1200000000000005E-5 opsPerSec=1.9883495145631066E8


rep=1 Stack size: 16384
starting doubles
doubles: duration=2.71E-5 opsPerSec=6.045756457564576E8
starting tuples
tuples: duration=8.680000000000001E-5 opsPerSec=1.8875576036866358E8


rep=1 Stack size: 32768
starting doubles
doubles: duration=6.53E-5 opsPerSec=5.0180704441041344E8
starting tuples
tuples: duration=1.933E-4 opsPerSec=1.6951888256595963E8


rep=1 Stack size: 65536
starting doubles
doubles: duration=1.54E-4 opsPerSec=4.255584415584416E8
starting tuples
tuples: duration=4.1220000000000004E-4 opsPerSec=1.5899078117418727E8


rep=1 Stack size: 131072
starting doubles
doubles: duration=3.142E-4 opsPerSec=4.171610439210694E8
starting tuples
tuples: duration=9.338E-4 opsPerSec=1.403641036624545E8


rep=1 Stack size: 262144
starting doubles
doubles: duration=6.384E-4 opsPerSec=4.1062656641604006E8
starting tuples
tuples: duration=0.0020334000000000003 opsPerSec=1.2891905183436607E8


rep=1 Stack size: 524288
starting doubles
doubles: duration=0.0012796 opsPerSec=4.0972804001250386E8
starting tuples
tuples: duration=0.0051913 opsPerSec=1.0099358542176333E8


rep=1 Stack size: 1048576
starting doubles
doubles: duration=0.0023461000000000003 opsPerSec=4.469442905247005E8
starting tuples
tuples: duration=0.0120126 opsPerSec=8.728967917020462E7


rep=1 Stack size: 2097152
starting doubles
doubles: duration=0.004895 opsPerSec=4.284273748723187E8
starting tuples
tuples: duration=0.027547000000000002 opsPerSec=7.612995970523106E7


rep=1 Stack size: 4194304
starting doubles
doubles: duration=0.0099141 opsPerSec=4.230645242634228E8
starting tuples
tuples: duration=0.07092770000000001 opsPerSec=5.913492189934256E7


rep=1 Stack size: 8388608
starting doubles
doubles: duration=0.037288100000000005 opsPerSec=2.2496742928709155E8
starting tuples
tuples: duration=0.15892320000000001 opsPerSec=5.278403656608978E7


rep=1 Stack size: 16777216
starting doubles
doubles: duration=0.059671300000000004 opsPerSec=2.8116055792315567E8
starting tuples
tuples: duration=0.5920615 opsPerSec=2.8336948104208767E7

*/