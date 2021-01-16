package quant.gates;

import math.Glob;

public final class QG_U extends QGate{ // unitary gate
	public QG_U () {
		constructor(1);
	}
	public QG_U (int qbits) {
		constructor(qbits);
	}
    private void constructor (int qbits) {
		for (int i=0; i<= Math.pow( 2, qbits) -1 ; i++) {
			add_one(i,i);
		}
    }
	public static void main(String[] args) {
		// Test Unitary matrix constructor
		QG_U gate_01 = new QG_U(); 
		Glob.print(1,"One qubit Unitary matrix: U");
		gate_01.print(2); // two refers to print level
		
		QG_U gate_02 = new QG_U(2); 
		Glob.print(1,"Two qubit Unitary matrix: U");
		gate_02.print(2); // two refers to print level		
		
		QG_U gate_10 = new QG_U(10); 
		Glob.print(1,"Ten qubit Unitary matrix: U");
		gate_10.print(2); // two refers to print level			
	}
}
