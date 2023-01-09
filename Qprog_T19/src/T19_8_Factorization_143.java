import math.Glob;
import quant.state.State;

public class T19_8_Factorization_143 {

	public static void main(String args[]) {
		// Create an state of 25 qubits
		State st = new State();
		st.add_qubits(25);
		int[] groups = { 4, 4, 8, 8, 1 }; // xxxx xxxx xxxxxxxx xxxxxxxx x
		st.setGroups(groups);

		int[] qH = { 0, 1, 2, 3, 4, 5, 6, 7 };
		st.hadamard(qH);
		
		// positions: 16-23
		int[] qNot_for_cmp = { 16, 20, 21, 22, 23  }; // 10001111 <<-- in binary. in decimal -->> 143 (11 x 13)
		st.not(qNot_for_cmp);
		
		st.not(24);  // ancilla
		st.hadamard( 24 );
		st.oracle4();
		
		int max_count = 8;
		for( int count = 1; count <= max_count ; count ++ ) {
		  st.diffuser4();
	      st.oracle4();		
		}
		
		st.hadamard( 24 );		
		
        st.print();
		Glob.print(st.measure(1));
	}
}
