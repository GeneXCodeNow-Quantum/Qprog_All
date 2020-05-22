package math;

public class CMatrix {
	// multiplies two matrix
	public static Matrix mul(Matrix m1, Matrix m2) {
		Matrix mat = new Matrix();
		if (m1.maxcol != m2.maxrow)
			return mat;
		m1.matrix.forEach((Integer index1, Cplx c1) -> {
			int[] rowcol1 = m1.getRowCol(index1);
			int row1 = rowcol1[0];
			int col1 = rowcol1[1]; 

			// we have to multiply by all columns in m2
			m2.matrix.forEach((Integer index2, Cplx c2) -> {
				int[] rowcol2 = m1.getRowCol(index2);
				int row2 = rowcol2[0];
				int col2 = rowcol2[1];
				if (col1 == row2) { // must multiply
					mat.add(row1, col2, CCplx.mul(c1, c2));
				}
			});
		});
		mat.maxrow = m1.maxrow;
		mat.maxcol = m2.maxcol;
		return mat;
	}


    // kroneker product 
	public static Matrix kroneker(Matrix m1, Matrix m2) {
		Matrix result = new Matrix();
		m1.matrix.forEach((Integer index1, Cplx d1) -> {
			int[] rowcol1 = m1.getRowCol(index1);
			int row1 = rowcol1[0];
			int col1 = rowcol1[1];

			m2.matrix.forEach((Integer index2, Cplx d2) -> {
				int[] rowcol2 = m2.getRowCol(index2);
				int row2 = rowcol2[0];
				int col2 = rowcol2[1];
				int resrow = row1 * (m2.maxrow + 1) + row2;
				int rescol = col1 * (m2.maxcol + 1) + col2;
				Cplx resdata = CCplx.mul(d1, d2);
				result.add(resrow, rescol, resdata);
			});
		});
		result.maxrow = (m1.maxrow + 1) * (m2.maxrow + 1) - 1;
		result.maxcol = (m1.maxcol + 1) * (m2.maxcol + 1) - 1;
		return result;
	}

	// kroneker product of several matrix
	public static Matrix kroneker(Matrix[] ops) {
		Matrix result = new Matrix();
		if (ops.length == 0)
			return result;

		result = ops[0].clone();
		for (int ind = 1; ind < ops.length; ind++) {
			result = kroneker(result, ops[ind]);
		}

		return result;
	}


	public static void main(String[] args) {
		// product of two matrix
		Glob.title("Product of two 'all-ones' matrix 2x2 and 2x3");
		Matrix m1 = new Matrix();
		for (int i= 0; i<= 1; i++)
			for(int j=0; j<= 1; j++)
				m1.add_one(i,j);
							
		Matrix m2 = new Matrix();
		for (int i= 0; i<= 1; i++)
			for(int j=0; j<= 2; j++)
				m2.add_one(i,j);

		Matrix m3 = CMatrix.mul(m1, m2);
		m3.print();
		
		// kroneker test : two unitary matrix results in new one unitary
		Glob.print("Kroneker of two unitary matrix 2x2 and 2x3");		
		Matrix mk1 = new Matrix();
		Matrix mk2 = new Matrix();

		mk1.add_one(0, 0);
		mk1.add_one(1, 1);
	
		mk2.add_one(0, 0);
		mk2.add_one(1, 1);
		mk2.add_one(2, 2);
	
		Matrix mk3 = CMatrix.kroneker(mk1, mk2);
		Glob.print(mk3.toStr());
		Glob.print("__________________");



	}

}
