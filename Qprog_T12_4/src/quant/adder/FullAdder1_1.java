package quant.adder;

import math.CMatrix;
import math.Matrix;
import quant.gates.QG_Swap;
import quant.gates.QGate;

public class FullAdder1_1 {
	public static Matrix get() {

		// first operation: CCNOT x U
		Matrix[] ops_01 = { QGate.CCNOT, QGate.U };
		Matrix op_01 = CMatrix.kroneker(ops_01);

		// second operation: U x Swap (q1, q3)
		Matrix[] ops_02 = { QGate.U, new QG_Swap(3, 0, 2) };
		Matrix op_02 = CMatrix.kroneker(ops_02);

		// third operation: CNOT x U x U
		Matrix[] ops_03 = { QGate.CNOT, QGate.U, QGate.U  };
		Matrix op_03 = CMatrix.kroneker(ops_03);

		// fourth operation: CNOT x U x U // same as second
		Matrix[] ops_04 = { QGate.U, new QG_Swap(3, 0, 2) };
		Matrix op_04 = CMatrix.kroneker(ops_04);

		// fifth operation: U x U x swap (q2, q3)
		Matrix[] ops_05 = { QGate.U, QGate.U, new QG_Swap(2, 0, 1) };
		Matrix op_05 = CMatrix.kroneker(ops_05);

		// sixth operation: U x CNOT x U
		Matrix[] ops_06 = { QGate.U, QGate.CNOT, QGate.U };
		Matrix op_06 = CMatrix.kroneker(ops_06);

		// seventh operation: U x U x swap (q2, q3) // same as fith
		Matrix[] ops_07 = { QGate.U, QGate.U, new QG_Swap(2, 0, 1) };
		Matrix op_07 = CMatrix.kroneker(ops_07);

		// to multiply all matrix we must begin from de seventh backwards to first.
		Matrix fullAdder;

		fullAdder = CMatrix.mul(op_07, op_06);
		fullAdder = CMatrix.mul(fullAdder, op_05);
		fullAdder = CMatrix.mul(fullAdder, op_04);
		fullAdder = CMatrix.mul(fullAdder, op_03);
		fullAdder = CMatrix.mul(fullAdder, op_02);
		fullAdder = CMatrix.mul(fullAdder, op_01);

		return fullAdder;

	}

	public static void main(String[] args) {
		Matrix fullAdder = FullAdder1_1.get();
		fullAdder.print();
	}

}
