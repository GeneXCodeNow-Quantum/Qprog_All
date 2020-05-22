
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QGate;
import quant.state.State;

public class T7 {

	public static void main(String args[]) {
		
		// sum of 2 qubits:
		// there are two operations:
		//   - CCNOT between qubit 0, 1 and 2
		//   - CNOT  between qubit 0 and 1 and U on qubit 2
		
		// operation CCNOT:
		Matrix op1 = QGate.CCNOT;
		Matrix op2 = CMatrix.kroneker(QGate.CNOT, QGate.U);
    	Matrix program = CMatrix.mul(op2, op1);
	
		
		// Create an state of 3 qubits
		State st = new State();
		st.add_qubits(3);
		Glob.print(" |000> State  = (1,0,0,0,0,0,0,0) "); 		
		st.print(1);  // 1 is for text indentation two chars to the right 
		Glob.print(st.measure(1000));
		
	    // we want p.e. |110>  so, must apply a XXU on first state
		Matrix[] ops_init = { QGate.U, QGate.U, QGate.U };  // |000>  --> 0 + 0  -->  000
		Matrix op_init = CMatrix.kroneker(ops_init);
		st.apply(op_init);
		Glob.print("Initial State: |110> ");
		st.print();
		Glob.print("Measurements on Initial State:");
		Glob.print(st.measure(1000));
		
		// Apply the program to initial state 
		st.apply(program);
		Glob.print("Final State:");
		st.print();
		
		Glob.print("Measurements on Final State: (must be |101> ");
		Glob.print(st.measure(1000));
		
	
	}
}
