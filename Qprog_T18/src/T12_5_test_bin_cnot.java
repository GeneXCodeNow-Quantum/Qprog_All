import math.Bin;
import math.Glob;

public class T12_5_test_bin_cnot {
	public static void main(String args[]) {
		Bin bin = new Bin(241, 8); // 11110001
		Glob.print(bin.toStr());
		int [] qbits1 = { 7, 6, 5, 3, 0 };
		bin.cnot(qbits1);
		Glob.print(bin.toStr());
		int [] qbits2 = { 7, 6, 5, 4, 0 };
		bin.cnot(qbits2);
		Glob.print(bin.toStr());
	}
}
