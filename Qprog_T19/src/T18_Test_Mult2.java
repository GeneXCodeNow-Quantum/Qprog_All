import math.Glob;
import quant.state.State;

public class T18_Test_Mult2 {
	public static void main(String args[]) {
		// Create an state of 12 qubits
		State st12 = new State();
		st12.add_qubits(12);
		int[] groups = { 3, 3, 6 }; // xxx xxx xxxxxx 
		st12.setGroups(groups);

		int[] qNOT = { 0, 1, 2, 3, 5,  }; // |111 101 000000>
		st12.not(qNOT);
		Glob.print(st12.measure(200));

		int[] qmul = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		st12.Mul(qmul);
		Glob.print(st12.measure(200));
	}
}
