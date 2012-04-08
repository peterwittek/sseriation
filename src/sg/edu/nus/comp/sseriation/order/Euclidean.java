package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;

public class Euclidean extends Distributional {

	public Euclidean(String filename) throws IOException {
		super(filename, "eucl");
	}

	public Euclidean(String filename, boolean classes) throws IOException {
		super(filename, "eucl", true, classes);
	}

	public static void main(String[] args) throws IOException {
		String database;
		database = args[0];
		String trainingFile = database + "_train.dat";
		Euclidean eu = new Euclidean(trainingFile);
		eu.generateOrder();
		eu.writeOrder();
		eu.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		eu.writeNewOrderWithClasses(testFile);
	}

	@Override
	protected double distanceFunction(int x, int y) {
		return SparseVector.euclidean(mx[x], mx[y]);
	}

}
