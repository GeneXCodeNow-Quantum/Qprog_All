
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QG_Swap;
import quant.gates.QGate;
import quant.state.State;

public class T10_swap_test02 {
	static final int nqbits = 3; // number of qbits in operation
	static final int swap_qb1 = 0; // first qbit to swap (0 to n-1 )
	static final int swap_qb2 = 2; // third qbit to swap (0 to n-1 )

	public static void main(String args[]) {
		// swap two external qubits from a set of 3.


		// Create an state of 3 qubits
		State st3 = new State();
		st3.add_qubits(nqbits);
		Glob.print(" |000> State  = (1,0,0,0,0,0,0,0) ");
		st3.print(1); // 1 is for text indentation two chars to the right
		Glob.print(st3.measure(1000));

		// we want p.e. |110> so, must apply a XXU on first state
		Matrix[] ops_init = { QGate.X, QGate.X, QGate.U };
		Matrix op_init = CMatrix.kroneker(ops_init);
		
		st3.apply(op_init);
		Glob.print("Initial State: |110> ");
		st3.print(1);
		Glob.print("Measurements on Initial State:");
		Glob.print(st3.measure(1000));

		Glob.title("Apply swap operation ");
		Matrix swap_operation = new QG_Swap(nqbits, swap_qb1, swap_qb2);
		st3.apply(swap_operation);
		Glob.print("Final State:");
		st3.print();

		Glob.print("Measurements on Final State: (must be |011> ");
		Glob.print(st3.measure(1000));

		Glob.print("Reverse (apply again): (must be |110> ");
		st3.apply(swap_operation);
		Glob.print("Reverse State (must be initial state):");
		st3.print(1);		
		Glob.print(st3.measure(1000));
	}
}
