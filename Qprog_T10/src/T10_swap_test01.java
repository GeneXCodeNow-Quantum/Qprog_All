
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QGate;
import quant.state.State;

public class T10_swap_test01 {

	public static void main(String args[]) {
		
		// swap two qubits
	
		// Create an state of 2 qubits
		State st2 = new State();
		st2.add_qubits(2);
		Glob.print(" |00> State  = (1,0,0,0) "); 		
		st2.print(1);  // 1 is for text indentation two chars to the right 
		Glob.print(st2.measure(1000));
		
	    // we want p.e. |10>  so, must apply a XU on first state
		Matrix[] ops_init = { QGate.X, QGate.U }; 
		Matrix op_init = CMatrix.kroneker(ops_init);
		st2.apply(op_init);
		Glob.print("Initial State: |10> ");
		st2.print(1);
		Glob.print("Measurements on Initial State:");
		Glob.print(st2.measure(1000));
		
		Glob.title("Apply swap operation ");
		
		st2.apply(QGate.Swap);
		Glob.print("Final State:");
		st2.print();
		
		Glob.print("Measurements on Final State: (must be |01> ");
		Glob.print(st2.measure(1000));
		
		Glob.print("Reverse (apply again): (must be |10> ");
		st2.apply(QGate.Swap);
		Glob.print("Reverse State (must be initial state):");
		st2.print(1);		
		Glob.print(st2.measure(1000));
		
	}
}
