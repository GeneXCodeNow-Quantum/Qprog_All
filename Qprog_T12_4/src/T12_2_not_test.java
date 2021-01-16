import math.Glob;
import quant.state.State;

public class T12_2_not_test {
	static final int nqbits = 3; // number of qbits in operation
	
	public static void main(String args[]) {
		
		// Create an state of 3 qubits
		State st3 = new State();
		st3.add_qubits(nqbits);
		Glob.print("Initial State: |000> ");
		st3.print();
		Glob.print(st3.measure(200));
		
	    // we want p.e. |010>  so lets apply not to qubit phi one
		st3.not(1);
		
		Glob.print("State: |010> ");
		st3.print();
		Glob.print(st3.measure(200));

		// restore to initial state. Apply again
		st3.not(1);
		Glob.print("Back to initial state: |000> ");
		st3.print();
		Glob.print(st3.measure(200));
	}
}
