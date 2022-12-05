package quant.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map; // T12_1

import math.Bin;
import math.CCplx;
import math.CMatrix;
import math.Cplx;
import math.Glob;
import math.Matrix;
import math.Vector;
import quant.gates.QGate;

enum Operation {
	NOT, SWAP, CNOT, H, CMP, MUL
}

public class State extends Vector {
	public int nqbits; // how many qubits in the state
	public HashMap<Long, Integer> measures = new HashMap<>(); // the results of the measurements
	// first integer is the key of matrix state and second will be the number of '1'

	public int[] groups; // T15

	// by default its like an scalar. Just one element (0,0) = 1.
	public State() {
		add(0, Cplx.one);
	}

	public static State qb_zero; // |0>
	public static State qb_one; // |1>

	static {
		qb_zero = new State(); // base state with just one element: (0,0) = Cplx.one
		qb_zero.maxrow = 1; // must redimensioning
		qb_zero.nqbits = 1; // T12_1 found refinement

		qb_one = new State(); // base state with just one element: (0,0) = Cplx.one
		qb_one.del(0); // must delete. created on constructor
		qb_one.put(1, Cplx.one); // implicit redimensioning
		qb_one.nqbits = 1; // T12_1 found refinement

	}

	// T15
	public void setGroups(int[] _groups) {
		this.groups = _groups;
	}
	// T15

	// applies a matrix to state (multiplies the matrix by self vector and updates
	// itself with new state
	public void apply(Matrix m) {
		getFromMatrix(CMatrix.mul(m, this)); // assigns result of product (it's a matrix) to self vector.
	}

	// adds a qubit |0> to state (with kroneker operation). Updates itself to new
	// state.
	public void add_qubit() {
		getFromMatrix(CMatrix.kroneker(this, qb_zero));
		nqbits = this.nqbits + 1;
	}

	// adds 'n' qubits |0> to the state (with kroneker operation). Updates itself to
	// new state.
	public void add_qubits(int q) {
		for (int i = 1; i <= q; i++) {
			add_qubit();
		}
	}

	// make 'n' measurements to the state. In a real Q computer, a measurement
	// destroys the qubit
	public String measure(int quant) {
		this.measures.clear();
		String s = "";
		List<Long> keys = new ArrayList<>(this.matrix.keySet());
		Collections.sort(keys);

		for (int lap = 1; lap <= quant; lap++) {
			double current_prob = Math.random(); // current probability measured: 0<= p < 1
			double accum_prob = 0; // accumulated probability
			for (long index : keys) {
				accum_prob += this.matrix.get(index).mod2();
				if (accum_prob > current_prob) { // this is the state measured
					int measure = 0;
					try {
						measure = measures.get(index);
						measure += 1;
					} catch (Exception e) {
						measure = 1;
					}
					this.measures.put(index, measure);
					break;
				}
			}
		}
		// out to console
		s = " --- Measures ----------------- q = " + quant + " \n";

		List<Long> measures_keys = new ArrayList<>(this.measures.keySet());
		Collections.sort(measures_keys);

		for (long measures_index : measures_keys) {
			int q = this.measures.get(measures_index);
			int row = (int) Math.floorDiv(measures_index, (Glob.MAXSTATE + 1));
			Bin bin = new Bin(row, nqbits);
			if (groups != null) { // T 15
				bin.setGroups(groups);
			}

			s = s + " " + bin.toStr() + " -> " + q + " - "
					+ (double) Math.round(((double) q) / ((double) quant) * 10000) / 100 + " %" + " \n";
		}
		s = s + " ------------------------------ \n";
		return s;
	}

	// redefinition of toStr. We want to show probability of each element of state
	public String toStr() {
		double[] prob = { 0 };
		String[] st = { "" };
		matrix.forEach((i, data) -> {
			double mod2 = data.mod2();
			prob[0] += mod2;
			int[] rowCol = getRowCol(i);
			Bin bin = new Bin(rowCol[0], nqbits);
			if (groups != null) { // T 15
				bin.setGroups(groups);
			}
			st[0] = st[0] + " Row: " + rowCol[0] + ":" + bin.toStr() + " -> " + data.toStr() + " prob: " + mod2 + "\n";
		});
		st[0] = st[0] + " Total prob = " + prob[0] + "\n";

		return st[0];
	}

	public void print() {
		System.out.println(this.toStr());
	}

	// renumerate qubit number to bit internal weight representation
	private int qubit_to_bit_number(int q) { // T14 we need just one
		return nqbits - 1 - q;
	}

	// renumerate qubit number to bit internal weight representation
	private int[] qubit_to_bit_number(int[] qarr) {
		int[] rev = new int[qarr.length];
		for (int i = 0; i < qarr.length; i++) {
			rev[i] = qubit_to_bit_number(qarr[i]); // T14 rewritten
		}
		return rev;
	}

	public void not(int qbit) {
		int[] qarr = { qbit };
		apply(qarr, Operation.NOT);
	}

	public void not(int[] qarr) {
		apply(qarr, Operation.NOT);
	}

	public void swap(int q1, int q2) {
		int[] qswap = { q1, q2 };
		apply(qswap, Operation.SWAP);
	}

	public void cnot(int[] qarr) {
		apply(qarr, Operation.CNOT);
	}

	// T12_6 begin
	public void hadamard(int[] qarr) {
		// must be processed one by one because each one generates a split
		int[] _qarr = { 0 };

		for (int i = 0; i < qarr.length; i++) {
			_qarr[0] = qarr[i];
			apply(_qarr, Operation.H);
		}
	}

	// T12_6 end

	// T15 Comparation
	public void Cmp(int[] qarr) {
		apply(qarr, Operation.CMP);
	}
	// T15 Comparation End

	// T18
	public void Mul(int[] qarr) { // a2 a1 a0 b2 b1 b0 r5 r4 r3 r2 r1 r0
		apply(qarr, Operation.MUL);
	}
	// T18

	private void apply(int[] _qarr, Operation oper) {
		int[] qarr = qubit_to_bit_number(_qarr);
		// we must change own matrix
		State res = new State(); // but it has a non empty matrix
		res.del(0); // so delete its unique element
		res.maxcol = this.maxcol;
		res.maxrow = this.maxrow;

		// own matrix (vector) loop
		for (Map.Entry<Long, Cplx> entry : matrix.entrySet()) {
			Long index = entry.getKey();
			Cplx value = entry.getValue();

			// 'index' is the combination of row and col where col always is = 0
			int[] rowCol = getRowCol(index);
			int row = rowCol[0];

			// let's apply 'not' on row, but first must be in binary form
			Bin bin = new Bin(row, nqbits);

			// operation dependent code
			switch (oper) {
			case NOT:
				for (int i = 0; i < qarr.length; i++) {
					bin.not(qarr[i]);
				}
				break;
			case SWAP: // two first qarr elements are the two qubits number to swap
				bin.swap(qarr[0], qarr[1]);
				break;
			case CNOT:
				bin.cnot(qarr);
				break;
			case H: // there is only one qubit to apply : qarr[0]
				value = CCplx.div_sqr2(value);

				Bin bin_1 = bin.hadamard(qarr[0]); // set bin the '0' value (to add latter)
				if (bin.isTagged()) { // it was originally a one
					res.add(bin_1.toInt(), CCplx.opposite(value)); // actually performs a subtraction
				} else { // it was originally a zero
					res.add(bin_1.toInt(), value);
				}
				break;
			case CMP: // T15

				int imax = (qarr.length - 1) / 2;
				boolean equals = true;
				for (int i = 0; i < imax && equals; i++) {
					if (!bin.equals(qarr[i], qarr[i + imax])) {
						equals = false;
					}
				}
				if (equals) {
					int iBitResult = qarr[qarr.length - 1];
					bin.not(iBitResult);
				}
				break;
			case MUL: // T18
				// in qarr there are the positions of operand 1, operand 2 and result
				// p.e. { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }
				// that means operand 1 on qubits 0, 1, 2 more weight bit 0, less 2
				//            operand 2 on qubits 3, 4, 5 more weight bit 3, less 5
				//            result    on qubits 6, 7, 8, 9, 10 , 11 more weight bit 6, less 11

				int length_operands = qarr.length / 4;   // p.e. 12 / 4 = 3
				int length_result = length_operands * 2; // p.e. 3 x 2 = 6

				int init_operand1 = 0;
				int init_operand2 = init_operand1 + length_operands;
				int init_result = init_operand2 + length_operands;

				int[] operand1_positions = new int[length_operands];
				int[] operand2_positions = new int[length_operands];
				int[] result_positions = new int[length_result];

				for (int i = init_operand1, count = 0; i < init_operand2; i++, count++) {
					operand1_positions[count] = qarr[i];
				}
				for (int i = init_operand2, count = 0; i < init_result; i++, count++) {
					operand2_positions[count] = qarr[i];
				}
				for (int i = init_result, count = 0; i < qarr.length; i++, count++) {
					result_positions[count] = qarr[i];
				}

				Bin operand1_binary = bin.subBin(operand1_positions);
				Bin operand2_binary = bin.subBin(operand2_positions);

				int operand1_decimal = operand1_binary.toInt();
				int operand2_decimal = operand2_binary.toInt();

				int result_decimal = operand1_decimal * operand2_decimal;
				Bin result_binary = new Bin(result_decimal, length_result);

				bin.setBin(result_positions, result_binary);

				break;
			}

			res.add(bin.toInt(), value); // let's put new data in new state
		}
		matrix = res.matrix;
	}

	public static void main(String[] args) {

		Glob.title(" 1 qbit state |0> ");
		State st_qb = new State();
		st_qb.add_qubit(); // adds a new qubit |0> to the empty state --> result: (1,0) state
		st_qb.print();
		Glob.print(st_qb.measure(1000));

		Glob.title("applies U gate --> must remain the same");
		st_qb.apply(QGate.U);
		st_qb.print();
		Glob.print(st_qb.measure(1000));

		Glob.title("applies X gate --> must change to (0, 1) that is |1>");
		st_qb.apply(QGate.X);
		st_qb.print();
		Glob.print(1, st_qb.measure(1000));

		Glob.title("applies X gate again --> must change to (1, 0)  that is |0> ");
		st_qb.apply(QGate.X);
		st_qb.print(1);
		Glob.print(st_qb.measure(1000));

		Glob.title(" applies H gate --> in superposition ! --> 1 / sqr(2) (1, 1)");
		st_qb.apply(QGate.H);
		st_qb.print(1);
		Glob.print(st_qb.measure(1000));

		Glob.title(" applies H gate again --> so, it's reversible --> |0> again");
		st_qb.apply(QGate.H);
		st_qb.print(1);
		Glob.print(st_qb.measure(1000));

		Glob.title(" 2 qbit on zero state --> (1, 0, 0 ,0 )   |00> state");
		State st_2qb = new State();
		st_2qb.add_qubits(2); // |0> |0> by default
		st_2qb.print();
		Glob.print(st_2qb.measure(1000));

		Glob.title(" 2 qbit on |00> and then hadamard on first");
		Matrix op_HU = CMatrix.kroneker(QGate.H, QGate.U);
		op_HU.print(1);
		st_2qb.apply(op_HU);
		st_2qb.print(1);
		Glob.print(1, st_2qb.measure(1000));

		Glob.title(" 2 qbits |00> step1: H on first step2: CNOT");
		State st_ent = new State();
		st_ent.add_qubits(2);
		Matrix op_ent = CMatrix.mul(QGate.CNOT, op_HU);
		st_ent.apply(op_ent);
		st_ent.print();
		Glob.print(st_ent.measure(1000));
		Glob.print(1, "Entanglement !");

	}
}
