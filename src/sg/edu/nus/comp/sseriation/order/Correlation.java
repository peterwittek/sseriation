package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;
import sg.edu.nus.comp.sseriation.util.Utilities;

public class Correlation extends Distributional {

	public Correlation(String filename) throws IOException {
		super(filename, "corr");
	}

	public Correlation(String filename, boolean classes) throws IOException {
		super(filename, "corr", true, classes);
	}

	public static void createNewOrder(String[] args) throws IOException {
		String database;
		database = args[0];
		String trainingFile = database + "_train.dat";
		Correlation co = new Correlation(trainingFile);
		co.generateOrder();
		co.writeOrder();
		co.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		co.writeNewOrderWithClasses(testFile);
		Utilities.writeDoubleList(co.scale(), database + "_" + co.model
				+ "_scale.dat");
	}

	@Override
	protected double distanceFunction(int x, int y) {
		return 1 - Math.abs(SparseVector.correlation(mx[x], mx[y], n));
	}

}
