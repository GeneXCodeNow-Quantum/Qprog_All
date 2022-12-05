import math.Glob;
import quant.state.State;

public class T18_3qubits_multiplier {

	public static void main(String args[]) {
		// Create an state of 12 qubits 
		State st = new State();
		st.add_qubits(12);

		int[] groups = { 3, 3, 6 }; // xxx xxx xxxxxx 
        st.setGroups( groups);

		int[] qH = { 0, 1, 2, 3, 4, 5  };
		st.hadamard(qH);
  
		Glob.print("Initial State:");
        Glob.print(st.toStr());
        
        int[] qCnot01 = { 2,5,11};
        st.cnot(qCnot01);
        
        int[] qCnot02 = { 1,5,10};
        st.cnot(qCnot02);

        int[] qCnot03 = { 2,4,10,9};
        st.cnot(qCnot03);

        int[] qCnot04 = { 2,4,10 };
        st.cnot(qCnot04);

        int[] qCnot05 = { 0,5,9,8 };
        st.cnot(qCnot05);

        int[] qCnot06 = { 1,4,9,8 };
        st.cnot(qCnot06);

        int[] qCnot07 = { 2,3,9,8 };
        st.cnot(qCnot07);

        int[] qCnot08 = { 1,0,5,4,8 };
        st.cnot(qCnot08);

        int[] qCnot09 = { 2,0,5,3,8 };
        st.cnot(qCnot09);

        int[] qCnot10 = { 2,1,4,3,8 };
        st.cnot(qCnot10);

        int[] qCnot11 = { 0,5,9 };
        st.cnot(qCnot11);

        int[] qCnot12 = { 1,4,9 };
        st.cnot(qCnot12);
      
        int[] qCnot13 = { 2,3,9 };
        st.cnot(qCnot13);

        int[] qCnot14 = { 0,4,8,7 };
        st.cnot(qCnot14);

        int[] qCnot15 = { 1,3,8,7 };
        st.cnot(qCnot15);

        int[] qCnot16 = { 1,0,4,3,7 };
        st.cnot(qCnot16);

        int[] qCnot17 = { 0,3,7,6 };
        st.cnot(qCnot17);     

        int[] qCnot18 = { 2,1,0,5,4,3,7 };
        st.cnot(qCnot18); 

        int[] qCnot19 = { 0,3,7  };
        st.cnot(qCnot19); 
    
        int[] qCnot20 = { 0,4,8  };
        st.cnot(qCnot20); 
 
        int[] qCnot21 = { 1,3,8 };
        st.cnot(qCnot21); 

		Glob.print("Final State:");
        Glob.print(st.toStr());
        
		Glob.print("10000 Measures:");
	    Glob.print(st.measure(10000));

	}
}
