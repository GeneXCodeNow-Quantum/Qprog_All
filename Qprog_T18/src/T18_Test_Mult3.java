import math.Glob;
import quant.state.State;

public class T18_Test_Mult3 {
	public static void main(String args[]) {
		// Create an state of 8 qubits
		State st = new State();
		st.add_qubits(8);
		int[] groups = { 2, 2, 4 };
		st.setGroups(groups);

		int[] qH = { 0, 1, 2, 3 };
		st.hadamard(qH);
		Glob.print(st.measure(10000));

		int[] qmul = { 0, 1, 2, 3, 4, 5, 6, 7 };
		st.Mul(qmul);
		Glob.print(st.measure(10000));
    }
}