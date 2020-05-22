package quant.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import math.Bin;
import math.CMatrix;
import math.Cplx;
import math.Glob;
import math.Matrix;
import math.Vector;
import quant.gates.QGate;


public class State extends Vector {
	public int nqbits; // how many qubits in the state
	public HashMap<Integer, Integer> measures = new HashMap<>(); // the results of the measurements
	// first integer is the key of matrix state and second will be the number of '1'

	// by default its like an scalar. Just one element (0,0) = 1.
	public State() {
		add(0, Cplx.one);
	}

	public static State qb_zero;  // |0>
	public static State qb_one;   // |1>

	static{
		qb_zero = new State();    // base state with just one element: (0,0) = Cplx.one
		qb_zero.maxrow = 1;       // must redimensioning 

		qb_one = new State();     // base state with just one element: (0,0) = Cplx.one
		qb_one.del(0);            // must delete. created on constructor 
		qb_one.put(1, Cplx.one);  // implicit redimensioning

	}

	

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
		List<Integer> keys = new ArrayList<>(this.matrix.keySet());
		Collections.sort(keys);

		for (int lap = 1; lap <= quant; lap++) {
			double current_prob = Math.random(); // current probability measured: 0<= p < 1
			double accum_prob = 0; // accumulated probability
			for (int index : keys) {
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

		List<Integer> measures_keys = new ArrayList<>(this.measures.keySet());
		Collections.sort(measures_keys);

		for (int measures_index : measures_keys) {
			int q = this.measures.get(measures_index);
			int row = measures_index / Glob.MAXQBITS;
			Bin bin = new Bin(row, nqbits);
			s = s + " " + bin.toStr() + " -> " + q + " - " + Math.round(((double) q) / ((double) quant) * 1000) / 10
					+ " %" + " \n";
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
			st[0] = st[0] + " Row: " + rowCol[0] + " -> " + data.toStr() + " prob: " + mod2 + "\n";
		});
		st[0] = st[0] + " Total prob = " + prob[0] + "\n";

		return st[0];
	}

	public void print() {
		System.out.println(this.toStr());
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
		Glob.print(1,"Entanglement !");

	}
}
