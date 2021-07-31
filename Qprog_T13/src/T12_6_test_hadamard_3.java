import math.Glob;
import quant.state.State;

public class T12_6_test_hadamard_3 {
	
	public static void main(String args[]) {
		// Create an state of 4 qubits
		Glob.print("Initial state : |0000> ");
		State st4 = new State();
		st4.add_qubits(4);
		Glob.print("Apply Hadamard on last two: UUHH : |00HH> ");
		int [] qH = { 2, 3 };
		st4.hadamard(qH);
		st4.print();
		Glob.print(st4.measure(200));
		Glob.print("Apply Hadamard again, so we'll obtain initial state ");		
		st4.hadamard(qH);
		Glob.print(st4.measure(200));
		
	}
}
