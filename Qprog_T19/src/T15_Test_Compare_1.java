import math.Glob;
import quant.state.State;

public class T15_Test_Compare_1 {

	public static void main(String args[]) {
		// Create an state of 9 qubits 0 11 0 11 00 0
		State st9 = new State();
		st9.add_qubits(9);
		
		int[] groups = { 1, 2, 1, 2, 2, 1 };
        st9.setGroups( groups);
        
        
		int[] qNot = { 1, 2, 4, 5};
		st9.not(qNot);
  
        Glob.print(st9.measure(1000));
        
        int[] qCmp = { 1, 2, 4, 5, 8};
        st9.Cmp(qCmp);
		Glob.print(st9.measure(1000));

	}
}
