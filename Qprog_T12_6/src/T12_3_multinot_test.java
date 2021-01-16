import math.Glob;
import quant.state.State;

public class T12_3_multinot_test {
	static final int nqbits = 8; // number of qbits in operation
	
	public static void main(String args[]) {
		
		// Create an state of 8 qubits
		State st8 = new State();
		st8.add_qubits(nqbits);
		Glob.print("Initial State: |00000000> ");
		st8.print();
		Glob.print(st8.measure(200));
		
	    // we want p.e. |10010111>  so lets apply not to certain qubits
		int qbits[] = { 0, 3, 5, 6, 7 } ; // qubits to apply a not
		st8.not(qbits);
		
		Glob.print("State: |10010111> ");
		st8.print();
		Glob.print(st8.measure(200));

		// restore to initial state. Apply again
		st8.not(qbits);
		Glob.print("Back to initial state: |00000000> ");
		st8.print();
		Glob.print(st8.measure(200));
	}
}
