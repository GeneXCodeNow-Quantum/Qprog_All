package math;

import java.util.Arrays;

public class Bin { // auxiliar for binary output

	public int[] groups; // groups of bits. p.e. 2, 3, 8 -->
							// --> data: 1011101101010 --> 10 111 01101010
							// (2, 3 and 8 bits)

	private int[] bindata = new int[Glob.QBITS]; // Less weight bit is bindata[0]
	private int indMax = -1; // how many

	// T12_6
	private boolean tagged; // it's a mark to know if haddamard operation made a switch on '1'

	// constructors
	public Bin(int n) {
		constructor(n, 0);
	}

	public Bin(int n, int max_bits) {
		constructor(n, max_bits);
	}

	public void constructor(int base10_number, int how_many_bits) { // 0 to 2 ^ how_many_bits - 1
		groups = new int[0];
		int num = base10_number;
		for (int ind = 0; num > 0; ind++) {
			int mod = num % 2;
			num = num / 2;
			bindata[ind] = mod;
			indMax++;
		}
		if (how_many_bits - 1 > indMax) {
			indMax = how_many_bits - 1;
		}
	}

	public int getBit(int bit) {
		if (bit > indMax)
			return 0;
		return bindata[bit];
	}

	public void delBit(int bit) { // 0 to n - 1
		// change p.e. 1000101 delBit(1) --> 100011 (deleted the second bit)
		for (int i = bit; i < indMax; i++) {
			bindata[i] = bindata[i + 1];
		}
		bindata[indMax] = 0;
		indMax -= 1;
	}

	public Bin clone() {
		Bin ret = new Bin(0);

		ret.bindata = Arrays.copyOf(this.bindata, this.bindata.length);
		ret.groups = Arrays.copyOf(this.groups, this.groups.length);
		ret.indMax = this.indMax;
		return ret;
	}

	public void setGroups(int[] groups) {
		int sumbits = 0;
		for (int i = 0; i < groups.length; i++) {
			sumbits += groups[i];
		}
		if (sumbits == indMax + 1) {
			this.groups = groups;
		}
	}

	public void swap(int bit1, int bit2) {
		int data = bindata[bit1];
		bindata[bit1] = bindata[bit2];
		bindata[bit2] = data;
		if (bit1 > indMax)
			indMax = bit1;
		if (bit2 > indMax)
			indMax = bit2;
	}

	public String toStr() {
		String ret = "";
		boolean withGroups = this.groups.length > 0;

		int indexGroup = 0;
		int maxGroup = 0;
		if (withGroups) {
			maxGroup = this.groups[indexGroup];
			indexGroup++;
		}
		for (int i = indMax; i >= 0; i--) {
			ret = ret + bindata[i];
			if (withGroups) {
				maxGroup--;
				if (maxGroup == 0 && i > 0) {
					ret = ret + " ";
					maxGroup = this.groups[indexGroup];
					indexGroup++;
				}
			}
		}
		return ret;
	}

	public int toInt() {
		int n = 0;

		for (int index = indMax; index >= 0; index--) {
			n = n + (int) Math.pow(2, index) * bindata[index];
		}
		return n;
	}

	// T12_2 begin
	public void not(int q) {
		if (q > indMax)
			return;

		if (bindata[q] == 0) {
			bindata[q] = 1;
		} else {
			bindata[q] = 0;
		}
	}
	// T12_2 end

	// T12_5 Begin
	public void cnot(int q1, int q2) {
		if (q1 > indMax || q2 > indMax)
			return;
		if (bindata[q1] == 1) {
			not(q2);
		}
	}

	public void cnot(int[] qarr) { // last one is controlled
		for (int i = 0; i < qarr.length - 1; i++) {
			if (qarr[i] > indMax)
				return;
			if (bindata[qarr[i]] == 0) {
				return;
			}
		}
		not(qarr[qarr.length - 1]);
	}
	// T12_5 End

	// T12_6 Begin
	public boolean isTagged() {
		return tagged;
	}

	public Bin hadamard(int q) {
		Bin retbin;
		if (q > indMax)
			return null;

		retbin = clone(); // copy values

		if (bindata[q] == 0) { // itself we want it to be zero and returned one.
			retbin.not(q);
		} else { // so if itself was one, let's change it
			not(q);
			tagged = true; // this is the mark to know if it was originally a '1'
		}
		return retbin;
	}
	// T12_6 End
	
	// T15 compare bits
	public boolean equals(int bit1, int bit2) {
		if (bit1 > indMax || bit2 > indMax)
			return false;
		return bindata[bit1] == bindata[bit2];
	}
	// T15

	// T18
	public Bin subBin(int[] positions) {
		// returns a binary that is a 'substring' of itself. (just the bits passed on 'qb'
		// for example: Bin myBin = '010101100010' --> myBin.subBin ( {5,4,3 } )  is '100'
		//                selected         xxx 
		int len = positions.length;
		Bin bin = new Bin(0, len);
		for (int i = 0; i < len; i++) {
			bin.bindata[len - 1 - i] = this.bindata[positions[i]];
		}
		return bin;
	}

	public void setBin(int[] positions, Bin bin) { 
		for (int i = 0; i < positions.length; i++) {
			bindata[positions[i]] = bin.bindata[bin.indMax - i];
		}
	}
	// T18


	static public void main(String[] args) {
		Glob.title(" 35 in binary with 8 bits");

		Bin bin = new Bin(35, 8);

		Glob.print(1, " bin.toStr() : " + bin.toStr());
		Glob.print(1, " number in decimal: " + bin.toInt());
		Glob.print(1, " indMax = " + bin.indMax);

		// groups: 2 - 6 --> xx xxxxxx
		int[] group = { 2, 6 };
		bin.setGroups(group);
		Glob.print(" group 2 - 6 bin.toStr() : " + bin.toStr());

		bin.swap(1, 4);
		Glob.print(" swap 1 - 4  bin.toStr() : " + bin.toStr());
		Glob.print(" swap 1 - 4  bin.toInt() : " + bin.toInt());

		Glob.title(" 345 in binary with 12 bits");

		Bin bin2 = new Bin(345, 12);
		Glob.print(1, " bin.toStr() :  " + bin2.toStr());
		Glob.print(1, " indMax = " + bin2.indMax);

		int[] group2 = { 2, 4, 6 };
		bin2.setGroups(group2);
		Glob.print(" group 2-4-6 bin.toStr() : " + bin2.toStr() + "\n");

		Glob.title(" Delete bit ");
		Glob.print(1, "2047 in binary with 12 bits");
		Glob.print(1, " grouped: 2-2-4-4");
		Bin bin3 = new Bin(2047, 12);
		int[] group3 = { 2, 2, 4, 4 };
		bin3.setGroups(group3);
		Glob.print(2, " bin.toStr() :  " + bin3.toStr());
		Glob.print(2, " indMax = " + bin3.indMax);
		Glob.print(2, " bin.toInt() :  " + bin3.toInt());
		Glob.print(1, " delete bit 0 (first right) and group 2-2-4-3:");
		bin3.delBit(0);
		int[] group3_2 = { 2, 2, 4, 3 };
		bin3.setGroups(group3_2);
		Glob.print(2, " bin.toStr() :  " + bin3.toStr());
		Glob.print(2, " indMax = " + bin3.indMax);
		Glob.print(2, " bin.toInt() :  " + bin3.toInt());

		// T18
		Glob.title("T18 subBin example");
		Bin bin_T18 = new Bin(1378, 12); //  010101100010  
		Glob.print(1, " bin.toStr() :  " + bin_T18.toStr());
		int [] picked = { 5, 4, 3 };
		Bin newBin_picked = bin_T18.subBin( picked );
		Glob.print(1, " picked 5,4,3:  ������" + newBin_picked.toStr() + "���"  );
		
		Glob.title("T18 setBin example");
		Glob.print(1, " before set : " + bin_T18.toStr());
		int [] set_positions = { 2, 1, 0 };
	    Bin bin_to_set = new Bin(7,3);
		Glob.print(1, " bin to set : ........." + bin_to_set.toStr());
		bin_T18.setBin(set_positions, bin_to_set);
		Glob.print(1, " after set  : " + bin_T18.toStr());
	}
}
