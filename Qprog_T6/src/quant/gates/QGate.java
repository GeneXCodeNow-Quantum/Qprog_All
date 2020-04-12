package quant.gates;

import math.Cplx;
import math.Glob;
import math.Matrix;

public class QGate extends Matrix {
	static public QGate U;
	static public QGate X;
	static public QGate H;
	static public QGate CNOT;
	
	static {
		// U  Gate
		//   1  0
		//   0  1
		U = new QGate();
		U.add_one(0, 0);
		U.add_one(1, 1);
	}
	static {
		// X  Gate
		//   0  1
		//   1  0
		X = new QGate();
		X.add_one(0, 1);
		X.add_one(1, 0);
	}
	
	static {
		// H  Gate:  1 / sqr(2)
		//   1  1
		//   1 -1
		H = new QGate();
		double _sq2 = (double) (1 / Math.sqrt(2));
		H.add(0,0, new Cplx(  _sq2,0));
		H.add(0,1, new Cplx(  _sq2,0));
		H.add(1,0, new Cplx(  _sq2,0));
		H.add(1,1, new Cplx(- _sq2,0));	
	}
	
	static { 
		// CNOT Gate
		//   1  0  0  0
		//   0  1  0  0
		//   0  0  0  1
		//   0  0  1  0
		CNOT = new QGate();
		CNOT.add_one(0, 0);
		CNOT.add_one(1, 1);
		CNOT.add_one(2, 3);
		CNOT.add_one(3, 2);
	}


	public void getFromMatrix(Matrix m) {
		// change core data
		matrix = m.matrix;
		maxrow = m.maxrow;
		maxcol = m.maxcol;
	}
	public static void main(String[] args) {
		Glob.title("U Gate");
		Glob.print(" 1  0 " );
		Glob.print(" 0  1 " );
		U.print();

		Glob.title("X Gate");
		Glob.print(" 0  1 " );
		Glob.print(" 1  0 " );		
		X.print();
		
		Glob.title("H Gate");
		Glob.print(" 1 / sqr(2) x 1   1 " );
		Glob.print("              1  -1 " );		
		H.print();
		
		Glob.title("CNOT Gate");
		Glob.print(" 1  0  0  0 " );
		Glob.print(" 0  1  0  0 " );		
		Glob.print(" 0  0  0  1 " );		
		Glob.print(" 0  0  1  0 " );		
		CNOT.print();		
	}
}
