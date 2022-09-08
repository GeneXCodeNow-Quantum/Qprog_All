import math.Glob;
import quant.state.State;

public class T15_Comparator_2qubit_number_03_case_00000 {
	
	public static void main(String args[]) {
		// Create an state of 5 qubits
		Glob.print("Initial State |00000>");
		State st5 = new State();
		st5.add_qubits(5);
		int [] groups = { 2, 2, 1 };
		st5.setGroups(groups);
		
		Glob.print(st5.measure(1000));

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
        		
		Glob.print(st5.measure(1000));
		
	}
}
