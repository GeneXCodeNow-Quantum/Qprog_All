import math.Glob;
import quant.state.State;

public class T15_Test_Compare_3 {

	public static void main(String args[]) {
		// Create an state of 9 qubits 
		State st9 = new State();
		st9.add_qubits(9);
		
		int[] groups = { 1, 2, 1, 2, 2, 1 }; // x xx x xx xx x
        st9.setGroups( groups);
        
		int[] qH = { 1, 2 };
		st9.hadamard(qH);
		
		st9.not(4);
        Glob.print(st9.measure(1000));
        
        int[] qCmp = { 1, 2, 4, 5, 8};
        st9.Cmp(qCmp);
        
		Glob.print(st9.measure(1000));

	}
}
