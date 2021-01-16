package math;

public class Cplx {
	public double rv; // real value
	public double iv; // imaginary value

	public static Cplx zero = new Cplx(0, 0);
	public static Cplx one = new Cplx(1, 0);
	public static Cplx minus_one = new Cplx(-1, 0);

	// constructor
	public Cplx(double _rv, double _iv) {
		rv = _rv;
		iv = _iv;
		round();
	}

	// square modulus
	public double mod2() {
		double result = (double) ((Math.pow(rv, 2) + Math.pow(iv, 2)));
		return Glob.round(result);
	}
	
	// modulus
	public double mod() {
		return (double) Math.sqrt(mod2());
	}

	public boolean isNotZero() {
		return !isZero();
	}

	public boolean isZero() {
		return rv == 0 && iv == 0;
	}
	
	public void round() {
		rv = Glob.round(this.rv);
		iv = Glob.round(this.iv);
	}

	public String toStr() {
		return toStr(false);
	}

	public String toStr(boolean forceLong) {
		double _rv = Glob.visual_round(this.rv);
		double _iv = Glob.visual_round(this.iv);
		
		String s = (_rv >= 0) ? "( " : "("; // negative sign or space
		if (iv >= 0)
			s += _rv + " + " + _iv + "i " + ")";
		else
			s += _rv + " - " + -_iv + "i " + ")";

		if (!forceLong) {
			if (_rv != 0 && _iv == 0) {
				if (_rv >= 0)
					s = " " + _rv + "";
				else
					s = _rv + "";

			} else if (_iv != 0 && _rv == 0) {
				if (_iv >= 0)
					s = " " + _iv + "i";
				else
					s = _iv + "i";

			} else if (_iv == 0 && _rv == 0) {
				s = " 0.0";
			}
		}
		;
		return s;
	}

	public static void main(String[] str) {
		Glob.print(Cplx.zero.toStr());
		Glob.print(Cplx.one.toStr());
		Glob.print(Cplx.minus_one.toStr());
		Glob.print(new Cplx(0, 1).toStr());
		Glob.print(new Cplx(0, -1).toStr());

		Glob.print(Cplx.zero.toStr(true));
		Glob.print(Cplx.one.toStr(true));
		Glob.print(Cplx.minus_one.toStr(true));
		Glob.print(new Cplx(0, 1).toStr(true));
		Glob.print(new Cplx(0, -1).toStr(true));

		Glob.print(new Cplx(2, 3).toStr());
		Glob.print(new Cplx(2.123, -3.87).toStr());

	}
}
