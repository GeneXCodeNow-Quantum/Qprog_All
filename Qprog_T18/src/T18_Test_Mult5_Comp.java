import math.Glob;
import quant.state.State;

public class T18_Test_Mult5_Comp {

	public static void main(String args[]) {
		// Create an state of 19 qubits
		State st = new State();
		st.add_qubits(19);
		int[] groups = { 3, 3, 6, 6, 1 }; // xxx xxx xxxxxx xxxxxx x
		st.setGroups(groups);

		int[] qH = { 0, 1, 2, 3, 4, 5 };
		st.hadamard(qH);

		int[] qmul = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		st.Mul(qmul);
		
		int[] qNot_for_cmp = { 13, 15, 17 }; // 010101 <<-- in binary. in decimal -->> 21
		st.not(qNot_for_cmp);

		int[] qCmp = { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		st.Cmp(qCmp);

		Glob.print(st.measure(16000));

	}
}
