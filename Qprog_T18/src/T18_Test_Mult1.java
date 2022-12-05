import math.Glob;
import quant.state.State;

public class T18_Test_Mult1 {
	public static void main(String args[]) {
		// Create an state of 8 qubits
		State st8 = new State();
		st8.add_qubits(8);
		int[] groups = { 2, 2, 4 }; // xx xx xxxx
		st8.setGroups(groups);

		int[] qNOT = { 0, 1, 2, 3 }; // |11 11 0000>
		st8.not(qNOT);
		Glob.print(st8.measure(200));

		int[] qmul = { 0, 1, 2, 3, 4, 5, 6, 7 };
		st8.Mul(qmul);
		Glob.print(st8.measure(200));

	}
}
