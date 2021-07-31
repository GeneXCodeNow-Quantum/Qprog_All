import math.Glob;
import quant.state.State;

public class T12_6_test_hadamard_1 {
	
	public static void main(String args[]) {
		// Create an state of 1 qubits
		Glob.print("Initial state : |0> ");
		State st1 = new State();
		st1.add_qubits(1);
		Glob.print("Apply Hadamard H ");
		int [] qH = { 0 };
		st1.hadamard(qH);
		st1.print();
		Glob.print(st1.measure(200));
		Glob.print("Apply Hadamard again, so we'll obtain initial state ");		
		st1.hadamard(qH);
		Glob.print(st1.measure(200));
		
	}
}
