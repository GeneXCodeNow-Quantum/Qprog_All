import math.Glob;
import quant.state.State;

public class T13_Comparator_1qubit_number_03_hadamard {
	
	public static void main(String args[]) {
		// Create an state of 3 qubits
		Glob.print("Initial State |HH0>");
		State st3 = new State();
		st3.add_qubits(3);
		int [] qhadamard = {0, 1};
		st3.hadamard(qhadamard);
		Glob.print(st3.measure(1000));

		// Comparator Circuit
        int [] qccnot1 = { 0, 2 };
		st3.cnot(qccnot1);
        int [] qccnot2 = { 1, 2 };
		st3.cnot(qccnot2);
		st3.not(2);
		
		Glob.print(st3.measure(1000));
		
	}
}
