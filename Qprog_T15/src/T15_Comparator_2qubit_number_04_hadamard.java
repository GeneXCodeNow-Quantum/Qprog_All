import math.Glob;
import quant.state.State;

public class T15_Comparator_2qubit_number_04_hadamard {
	
	public static void main(String args[]) {
	    int nmeasures = 10000;
		// Create an state of 5 qubits
		Glob.print("Initial State |HHHH0>");
		State st5 = new State();
		st5.add_qubits(5);
		int [] groups = { 2, 2, 1 };
		st5.setGroups(groups);
		
		int [] qhadamard = {0, 1, 2, 3};
		st5.hadamard(qhadamard);
		
		Glob.print(st5.measure(nmeasures));

		// Comparator Circuit
        int [] qccnot = { 0, 1, 2, 3, 4 };
		st5.cnot(qccnot);
        st5.not(0);
        st5.not(2);
         
		st5.cnot(qccnot);
        st5.not(1);
        st5.not(3);

		st5.cnot(qccnot);
        st5.not(0);
        st5.not(2);
        
		st5.cnot(qccnot);
        st5.not(1);
        st5.not(3);
        		
		Glob.print(st5.measure(nmeasures));
		
	}
}
