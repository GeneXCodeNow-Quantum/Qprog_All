
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QG_Swap;
import quant.gates.QGate;
import quant.state.State;

public class T10_swap_test04 {
	static final int nqbits = 8; // number of qbits in operation
	static final int swap_qb1 = 1; // first qbit to swap (0 to n-1 )
	static final int swap_qb2 = 6; // second qbit to swap (0 to n-1 )

	public static void main(String args[]) {
		
		// swap qubit 1 and 6 from a set of 8.  .q....p. <-> .p....q. 
		
		// Create an state of 8 qubits
		State st8 = new State();
		st8.add_qubits(nqbits);
		Glob.print(" |00000000> State  = (1,0,0,0,0,0,0,0...(256 elements)... 0) "); 		
		st8.print(1);  // 1 is for text indentation two chars to the right 000
		Glob.print(st8.measure(1000));
		
	    // we want p.e. |11111101>  so, must apply a XXXXXXUX on first state
		Matrix[] ops_init = { QGate.X, QGate.X, QGate.X, QGate.X, QGate.X, QGate.X, QGate.U, QGate.X }; 
		Matrix op_init = CMatrix.kroneker(ops_init);	
		
		st8.apply(op_init);
		Glob.print("Initial State: |11111101> ");
		st8.print(1);
		Glob.print("Measurements on Initial State:");
		Glob.print(st8.measure(1000));

		Glob.title("Apply swap operation ");
		Matrix swap_operation = new QG_Swap(nqbits, swap_qb1, swap_qb2);
     	st8.apply(swap_operation);
		Glob.print("Final State:");
		st8.print(1);
		
		Glob.print("Measurements on Final State: (must be |10111111> ");
		Glob.print(st8.measure(1000));
		
		Glob.print("Reverse (apply again): (must be |11111101> ");
		st8.apply(swap_operation);
		Glob.print("Reverse State (must be initial state):");
		st8.print(1);
		Glob.print(st8.measure(1000));
	}
}
