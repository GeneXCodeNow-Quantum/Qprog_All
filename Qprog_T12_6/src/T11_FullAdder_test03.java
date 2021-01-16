import math.CMatrix;
import math.Glob;
import math.Matrix;
import quant.gates.QGate;
import quant.state.State;
import quant.adder.FullAdder1_1;


public class T11_FullAdder_test03 {

	public static void main(String args[]) {
		// Create an state of 4 qubits
		State st4 = new State();
		st4.add_qubits(4);

		// Full Adder one by one qubits
		Matrix fullAdder = FullAdder1_1.get();
		
		// Create an hadamard state |HH00> and add two first qubits and apply result on seconds: must be all possible combinations
		Matrix[] ops_init = { QGate.H, QGate.H, QGate.U, QGate.U }; 
		Matrix op_init = CMatrix.kroneker(ops_init);	
		
		st4.apply(op_init);
		
		st4.apply(fullAdder);
		
		Glob.print("Measurements must be all possible combinations with equal probability ");
		Glob.print(st4.measure(1000));
		
		Glob.print("Let's see the amplitudes (probabilities) of the state");
		st4.print(1);
	}
}
