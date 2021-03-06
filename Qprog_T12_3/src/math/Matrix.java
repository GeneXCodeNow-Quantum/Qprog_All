package math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Matrix {
	// At least one value in each row and at least one value in each column
	// Sparse matrix: There are few non-zero elements, so, these non zero values are
	// the only values saved.
	// we use a hashmap. It is an structure like: index - value.
	// 'Index' is an integer that refers to position and 'value' is the data (in
	// this case of type Cplx).
	// The maximum matrix is a SQUARE matrix like this:
	// | a(0,0) a(0,1) a(0,2) ... a(0,MS)  |     where MS = MAXSTATE = 2 ^ MAXQUBITS - 1
	// | a(1,0) a(1,1) a(1,2) ... a(1,MS)  |
	// | ... |
	// | a(MS,0) a(MS,1)      ... a(MS,MS) |
	//
	// Position (index in hashmap structure) is calculated as follows:
	// for a(0,0)      --> index = 0  
	// for a(0,1)      --> index = 1 ... 
	// for a(0, MS)    --> index = MS
	// for a(1,0)      --> index = ( MS + 1 ) 
	// for a(1,1)      --> index = ( MS + 1 ) + 1 
	// for a(1,j)      --> index = ( MS + 1 ) + j ...
	// for a(1,MS)     --> index = ( MS + 1 ) + MS
	// for a(2,0)      --> index = 2 x ( MS + 1 )
	// for a(2,1)      --> index = 2 x ( MS + 1 ) + 1
	// for a(2,j)      --> index = 2 x ( MS + 1 ) + j
    // ---------------------------------------------------------------------------------
	// for a(i,j)      --> index = i x ( MS + 1 ) + j (see 'getKey' function in class)
    // ---------------------------------------------------------------------------------
	
	// for a(MS, MS )  --> index = MS x (MS + 1) + MS = MS ^ 2 + 2 x MS = 2 ^ (2 x MQ ) - 1  where MQ = MAXQUBITS

	// --- core data -----
	public HashMap<Long, Cplx> matrix = new HashMap<>();
	public int maxrow;
	public int maxcol;
	// -------------------

	// converts row-col values into index
	private long getKey(int row, int col) {
		long lrow = (long) row;
		long lcol = (long) col;
		long msta = (long) Glob.MAXSTATE + 1;
		return lrow * msta + lcol;
	}

	// inverse of getKey . returns row and col given 'key'.
	public int[] getRowCol(long key) {
		int[] rowCol = new int[2];
		long lrow = Math.floorDiv( key, Glob.MAXSTATE + 1 ); // row
		long lcol = key % ( Glob.MAXSTATE + 1 );  // col
		rowCol[0] = (int) lrow; //(int) Math.floorDiv( key, Glob.MAXSTATE + 1 ); // row
		rowCol[1] = (int) lcol; // (int) key % ( Glob.MAXSTATE + 1 );  // col
 		return rowCol;
	}

	// if row is greater than maxrow, then assign new value 'row'
	// if col is greater than maxcol, then assign new value 'col'
	protected void recalc_dim(int row, int col) {
		if (row > maxrow) {
			maxrow = row;
		}
		if (col > maxcol) {
			maxcol = col;
		}
	}

	// reads the value in row-col
	public Cplx get(int row, int col) {
		long key = getKey(row, col);
		Cplx v = matrix.get(key);
		return (v != null ? v : Cplx.zero);
	}

	// deletes the value in row-col
	public void del(int row, int col) {
		long key = getKey(row, col);
		matrix.remove(key);
	}

	// adds a new value in row - col position. If already exists, overwrites it
	// adding a zero value means delete the data in that position
	public void put(int row, int col, Cplx value) {
		if (value.isZero()) {
			del(row, col);
			return;
		}

		long key = getKey(row, col);
		matrix.put(key, value);
		recalc_dim(row, col);
	}

	// adds a one (as complex number) in row-col position. If already exists a
	// value, overwrites it
	public void put_one(int row, int col) {
		put(row, col, Cplx.one);
	}

	// adds passed value to existing value
	public void add(int row, int col, Cplx value_to_add) {
		long key = getKey(row, col);

		Cplx initialValue = matrix.get(key);
		Cplx finalValue = (initialValue != null ? CCplx.add(initialValue, value_to_add) : value_to_add);
		put(row, col, finalValue);
	}

	// adds one (as complex number) to exixting value
	public void add_one(int row, int col) {
		add(row, col, Cplx.one);
	}

	// close values to another matrix
	public Matrix clone() {
		Matrix m = new Matrix();
		m.matrix = (HashMap<Long, Cplx>) this.matrix.clone();
		m.maxrow = this.maxrow;
		m.maxcol = this.maxcol;
		return m;
	}

	// convert data to string to be shown on screen console
	public String toStr() { // it can be internally unsorted, so must be red in ascendent order
		String[] st = { "" };
		List<Long> keys = new ArrayList<>(matrix.keySet());
		Collections.sort(keys);
		for (long index : keys) {
			int[] rowCol = getRowCol(index);
			st[0] = st[0] + " Row: " + rowCol[0] + " Col: " + rowCol[1] + " -> " + matrix.get(index).toStr() + "\n";
		}
		return st[0];
	}

	// show matrix data on console

	public void print() {
		print(0);
	}

	public void print(int level) {
		Glob.print(level, toStr());
	}

	// test cases
	public static void main(String[] args) {

		Glob.title("All ones Matrix 3 x 2");
		Matrix mat1 = new Matrix();
		for (int i = 0; i <= 2; i++)
			for (int j = 0; j <= 1; j++)
				mat1.put_one(i, j);
		mat1.print();

		Glob.title("Clone Test");
		Glob.print(1, "Clone matrix and delete (0,0) ");
		Matrix mat2 = mat1.clone();
		mat2.del(0, 0);
		Glob.print(2, "First Matrix remains de same ");
		mat1.print(2);
		Glob.print(2, "Second Matrix without 0-0 element");
		mat2.print(2);

		Glob.print(2, "Second Matrix add (2-i) to the 1-1 element");
		mat2.add(1, 1, new Cplx(2, -1));
		mat2.print(2);
		Glob.print(2, "Second Matrix put (5i) to the 2-0 element  (rewrite)");
		mat2.put(2, 0, new Cplx(0, 5));
		mat2.print(2);

		Glob.title(
				"Empty matrix. 1. add 1 to (3,3) and (5,5) elements. 2.- add -1 to (3,3) element and -1 + i to (5,5) ");
		Matrix mat3 = new Matrix();
		Glob.print(" empty matrix: --> ");
		mat3.print();
		
		Glob.print(" add 1 to (3,3) and (5,5) elements. matrix: --> ");
		mat3.add_one(3, 3);
		mat3.add_one(5, 5);
		mat3.print(2);
		
		Glob.print(" add 1 to (3,3) element. matrix: --> ");
		mat3.add_one(3, 3);
		mat3.print(2);

	}
}
