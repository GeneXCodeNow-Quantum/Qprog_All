import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QGate;
import quant.state.State;
import quant.adder.FullAdder1_1;


public class T11_FullAdder_test01 {

	public static void main(String args[]) {
		// Create an state of 4 qubits
		State st4 = new State();
		st4.add_qubits(4);

		// Full Adder one by one qubits
		Matrix fullAdder = FullAdder1_1.get();
		
		// Create an |1000> add two first qubits (1+0) and apply result on seconds: must be |1001>
		Matrix[] ops_init = { QGate.X, QGate.U, QGate.U, QGate.U }; 
		Matrix op_init = CMatrix.kroneker(ops_init);	
		
		st4.apply(op_init);
		
		st4.apply(fullAdder);
		
		Glob.print("Measurements on adding 1 plus 0 -> result 01 that must be |1001> at 100% .");
		Glob.print(st4.measure(200));
	}
}
