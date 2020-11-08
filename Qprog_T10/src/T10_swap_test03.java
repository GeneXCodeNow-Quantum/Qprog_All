
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QG_Swap;
import quant.gates.QGate;
import quant.state.State;

public class T10_swap_test03 {
	static final int nqbits = 4; // number of qbits in operation
	static final int swap_qb1 = 0; // first qbit to swap (0 to n-1 )
	static final int swap_qb2 = 1; // second qbit to swap (0 to n-1 )

	public static void main(String args[]) {

		// swap two last qubits from a set of 4.

		// Create an state of 4 qubits
		State st4 = new State();
		st4.add_qubits(nqbits);
		Glob.print(" |0000> State  = (1,0,0,0,0,0,0,0...(16 elements)... 0) ");
		st4.print(1); // 1 is for text indentation two chars to the right
		Glob.print(st4.measure(1000));

		// we want p.e. |1010> so, must apply a XUXU on first state
		Matrix[] ops_init = { QGate.X, QGate.U, QGate.X, QGate.U };
		Matrix op_init = CMatrix.kroneker(ops_init);
		
		st4.apply(op_init);
		Glob.print("Initial State: |1010> ");
		st4.print(1);
		Glob.print("Measurements on Initial State:");
		Glob.print(st4.measure(1000));

		Glob.title("Apply swap operation ");
		Matrix swap_operation = new QG_Swap(nqbits, swap_qb1, swap_qb2);
		st4.apply(swap_operation);
		Glob.print("Final State:");
		st4.print();

		Glob.print("Measurements on Final State: (must be |1001> ");
		Glob.print(st4.measure(1000));
		
		Glob.print("Reverse (apply again): (must be |1010> ");
		st4.apply(swap_operation);
		Glob.print("Reverse State (must be initial state):");
		st4.print(1);		
		Glob.print(st4.measure(1000));

	}
}
