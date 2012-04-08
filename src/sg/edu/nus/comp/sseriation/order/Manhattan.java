package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;

public class Manhattan extends Distributional {

	public Manhattan(String filename) throws IOException {
		super(filename, "manh");
	}

	public Manhattan(String filename, boolean classes) throws IOException {
		super(filename, "manh", true, classes);
	}

	public static void main(String[] args) throws IOException {
		String database;
		database = args[0];
		String trainingFile = database + "_train.dat";
		Distributional dor = new Manhattan(trainingFile);
		dor.generateOrder();
		dor.writeOrder();
		dor.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		dor.writeNewOrderWithClasses(testFile);
	}

	@Override
	protected double distanceFunction(int x, int y) {
		return SparseVector.manhattan(mx[x], mx[y]);
	}

}
