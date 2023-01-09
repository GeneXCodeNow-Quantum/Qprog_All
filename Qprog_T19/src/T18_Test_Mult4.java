import math.Glob;
import quant.state.State;

public class T18_Test_Mult4 {
	public static void main(String args[]) {
		// Create an state of 12 qubits
		State st = new State();
		st.add_qubits(12);
		int[] groups = { 3, 3, 6 };
		st.setGroups(groups);

		int[] qH = { 0, 1, 2, 3, 4 ,5 };
		st.hadamard(qH);
		Glob.print(st.measure(10000));

		int[] qmul = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		st.Mul(qmul);
		Glob.print(st.measure(10000));
    }
}