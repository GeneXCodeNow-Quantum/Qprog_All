package math;

public class Glob {
	static public int MAXQBITS = 100;
	
	public static double round(double n) {
		return Math.round(n * ROUND_BASE) / (double) ROUND_BASE;
	}

	public static double visual_round(double n) {
		return Math.round(n * ROUND_BASE_VISUAL) / (double) ROUND_BASE_VISUAL;
	}
	// aux for out information to console
	public static void print(String lin) {
		print(0,lin);
	}
	public static void print(int level, String lin) {
		String l = " >" + lin;
		l = l.replaceAll("\n", "\n  ");
		for (int i = 0; i < level; i++) {
			l = l.replaceAll("\n", "\n  ");
			l = "  " + l;	
		}
		System.out.println(l);
	}
	
	public static void line() { // writes a underline
		line(true);
	}

	public static void line(boolean jump) {
		if (jump)
			System.out.println("\n" + completeLine("") + "\n");
		else
			System.out.println(completeLine("")); 
	}
	
	public static void title(String title) {
		String tit = "" + CHARL + CHARL + " " + title + " ";
		tit = completeLine(tit);
		System.out.println("");
		line(false);
		System.out.println(tit);
		line(false);
	}

	private static String completeLine(String line) {
		String lineRet = line;
		for (; lineRet.length() < MCHAR;) {
			lineRet = lineRet + CHARL;
		}
		return lineRet;
	}

	static private int NDECIMALS = 6; // number of decimals to round
	static private double ROUND_BASE = Math.pow(10, NDECIMALS);
	static private int NDECIMALS_VISUAL = 3; // number of decimals to round
	static private double ROUND_BASE_VISUAL = Math.pow(10, NDECIMALS_VISUAL);
	// aux for out data in console
	static private int MCHAR = 120; // max chars in a line to output console information
	static private char CHARL = '*';
}
