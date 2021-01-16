package math;

public class CCplx {
    
	public static Cplx add(Cplx c1, Cplx c2) {
		Cplx res = new Cplx(0, 0);
		res.rv = c1.rv + c2.rv;
		res.iv = c1.iv + c2.iv;
		res.round();
		return res;
	}

	public static Cplx mul(Cplx c1, Cplx c2) {
		Cplx res = new Cplx(0, 0);
		res.rv = c1.rv * c2.rv - c1.iv * c2.iv;
		res.iv = c1.rv * c2.iv + c1.iv * c2.rv;
		res.round();
		return res;
	}

	public static boolean eq(Cplx c1, Cplx c2) {
       return c1.rv == c2.rv && c1.iv == c2.iv;
	}
	
	public static void main(String[] str) {
	  Glob.title("Add (1 + 2i) + (3 - 5i) . Result = (4 - 3i) ");
	  Cplx c1 = new Cplx(1,2);
	  Cplx c2 = new Cplx(3,-5);
	  Cplx c3 = CCplx.add(c1, c2);
	  Glob.print(c1.toStr() + " + " + c2.toStr() + " = " + c3.toStr());
	  
	  Glob.title("Product (1 + 2i) x (3 - 5i) . Result = (13 + i) ");
	  Glob.print(1, "--> real: 1x3 + 2x(-5) x i^2 = 3 + 10 = 13  ");
	  Glob.print(1, "--> imag: 1x(-5)i + 2ix3 = -5i + 6i = i  ");
	  Cplx c4 = CCplx.mul(c1, c2);
	  Glob.print(c1.toStr() + " x " + c2.toStr() + " = " + c4.toStr());
	  
	}
	
}
