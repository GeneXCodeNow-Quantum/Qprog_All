import math.Glob;
import quant.state.State;

public class T12_5_test_cnot_qubit {
	static final int nqbits = 8; // number of qbits in operation

	public static void main(String args[]) {
		// Create an state of 8 qubits
		State st8 = new State();
		st8.add_qubits(nqbits);
		
		int [] qnot = { 0, 1, 2, 3, 7 };
		st8.not(qnot);
		Glob.print(st8.measure(200));
		
		int [] qcnot1 = { 0, 1, 2, 4, 7 };
		st8.cnot(qcnot1);
		Glob.print(st8.measure(200));
		
		int [] qcnot2 = { 0, 1, 2, 3, 7 };
		st8.cnot(qcnot2);
		Glob.print(st8.measure(200));	
	}
}
