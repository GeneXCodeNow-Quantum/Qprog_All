
import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QGate;
import quant.state.State;

public class T12_1_swap_test {
	static final int nqbits = 5; // number of qbits in operation
	static final int swap_qb1 = 0; // phi zero 
	static final int swap_qb2 = 3; // phi three

	public static void main(String args[]) {
				
		// Create an state of 5 qubits
		State st5 = new State();
		st5.add_qubits(nqbits);
	
	    // we want p.e. |10000>  so, must apply a XUUUU on first state
		Matrix[] ops_init = { QGate.X, QGate.U, QGate.U, QGate.U, QGate.U }; 
		Matrix op_init = CMatrix.kroneker(ops_init);	
		
		st5.apply(op_init);
		Glob.print("Initial State: |10000> ");
		st5.print();
		Glob.print(st5.measure(200));

		Glob.title("Apply swap operation ");
		st5.swap(swap_qb1, swap_qb2);
		Glob.print("Final State: |00010>");
		st5.print();
		
		Glob.print(st5.measure(200));
		
		Glob.print("Reverse (apply again): (must be |10000> ");
		st5.swap(swap_qb1, swap_qb2);
		Glob.print("Reverse State (must be initial state):");
		st5.print();
		Glob.print(st5.measure(200));
	}
}
