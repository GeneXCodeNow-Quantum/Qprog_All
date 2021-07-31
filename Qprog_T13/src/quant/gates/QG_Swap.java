package quant.gates;

import math.Bin;
import math.Glob;
public class QG_Swap extends QGate {
	// qBits: number of qbits in the set.
	// bit1:  first  qubit to swap (0 to qbits-1)
	// bit2:  second qubit to swap (0 to qbits-1)
	public QG_Swap(int qBits, int bit1, int bit2) {  
    // for example:  8 qubits, swap third and sixth --> QG_Swap(8, 2, 5) --> qBits = 8, bit1 = 2, bit2 = 5
		QG_U um = new QG_U(qBits); // creates an unitary matrix of qBits
		um.matrix.forEach((index, data) -> {
			int[] rowCol = getRowCol(index); // rowCol structure read 
			int row = rowCol[0]; 
			int col = rowCol[1];
			Bin bin = new Bin(col);
			bin.swap(bit1, bit2);
			col = bin.toInt(); 
			add(row, col, data);
		});
	}

	public static void main( String[] args) {
		Glob.title("Swap 2 qubits");
		QG_Swap sw = new QG_Swap(2,0,1);
		sw.print(); 
		
		Glob.title("Swap qubit 0 and qubit 2 of a 3 qubit set");
		QG_Swap sw3 = new QG_Swap(3,0,2);
		sw3.print();
	}
	
}
