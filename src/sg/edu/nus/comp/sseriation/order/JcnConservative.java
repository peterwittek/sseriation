package sg.edu.nus.comp.sseriation.order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import sg.edu.nus.comp.sseriation.sdistance.Jcn;
import sg.edu.nus.comp.sseriation.util.Utilities;

public class JcnConservative extends LinearOrder {

	private String[] terms;
	private Jcn jcdObject;

	public static void main(String[] args) throws IOException {
		String database;
		database = args[0];
		JcnConservative oJC = new JcnConservative(database + ".txt", false);
		oJC.generateOrder();
		oJC.writeOrder();
	}

	JcnConservative(String filename) throws IOException {
		super(filename, "jcn", true);
	}

	public JcnConservative(String filename, boolean reset) throws IOException {
		super(filename, "jcn", reset);
		System.out.println("Creating similarity object...");
		jcdObject = new Jcn();
		terms = Utilities.readWordList(filename);
		m = terms.length;
	}

	@Override
	protected double distanceFunction(int x, int y) {
		return jcdObject.d_max(terms[x], terms[y]);
	}

	@Override
	protected int findSeed() {
		double min = Integer.MAX_VALUE;
		int argmin = -1;
		for (Iterator<Integer> iter = V.iterator(); iter.hasNext();) {
			int tmpi = iter.next();
			double tmp = distanceFunction(Arrays.binarySearch(terms, "entity"),
					tmpi);
			if (tmp < min) {
				argmin = tmpi;
				min = tmp;
			}
		}
		return argmin;
	}

	@Override
	public void writeOrder() throws IOException {
		System.out.println("Writing order...");
		FileWriter out = new FileWriter(new File(filename.substring(0,
				filename.length() - 4)
				+ "_" + model + "_order.txt"), false);
		for (int i = leftSide.size() - 1; i >= 0; i--) {
			out.write(terms[leftSide.elementAt(i)] + "\n");
		}
		out.write(terms[seed] + "\n");
		for (int i = 0; i < rightSide.size(); i++) {
			out.write(terms[rightSide.elementAt(i)] + "\n");
		}
		out.close();
	}

}
