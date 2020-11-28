package math;

public class Vector extends Matrix {

	// parameter 'm' must be a real vector, but in form of matrix type (p.e. as a
	// result of product of matrix )
	public void getFromMatrix(Matrix m) {
		// change core data
		matrix = m.matrix;
		maxcol = 0;
		maxrow = m.maxrow;
	}

	// new vector functions always with col = 0
	public void add(int row, Cplx value) {
		add(row, 0, value);
	}

	public void put(int row, Cplx value) {
		put(row, 0, value);
	}

	public void put_one(int row) {
		put_one(row, 0);
	}

	public void add_one(int row) {
		add_one(row, 0);
	}

	public void del(int row) {
		del(row, 0);
	}

	public Cplx get(int row) {
		return super.get(row, 0);
	}

	public static void main(String[] args) {
		// test vector with four elements
		Glob.title("( 1,0,-1,i, 2+3i) vector "); 
		Vector vec = new Vector();
		vec.put(0, Cplx.one);
		vec.put(1, Cplx.zero); // it is not shown because only non zero values are stored
		vec.put(2, Cplx.minus_one);
		vec.put(3, new Cplx(0,1)); 
		vec.put(4, new Cplx(2,3)); 
		
		vec.print();
	}
}
