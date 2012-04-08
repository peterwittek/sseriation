package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;

public class MutualInformation extends Distributional {

	private int nBins = 100;

	MutualInformation(String filename) throws IOException {
		this(filename, true);
	}

	public MutualInformation(String filename, boolean reset) throws IOException {
		this(filename, reset, true);
	}

	MutualInformation(String filename, boolean reset, boolean classes)
			throws IOException {
		super(filename, "muti", reset, classes);
		mx = SparseVector.binify(mx, nBins);
	}

	public static void main(String[] args) throws IOException {
		String database;
		database = args[0];
		String trainingFile = database + "_train.dat";
		MutualInformation mi = new MutualInformation(trainingFile, false);
		mi.generateOrder();
		mi.writeOrder();
		mi.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		mi.writeNewOrderWithClasses(testFile);
	}

	@Override
	protected double distanceFunction(int x, int y) {
		return SparseVector.mutualInformationMetric(mx[x], mx[y], nBins, n);
	}

}
