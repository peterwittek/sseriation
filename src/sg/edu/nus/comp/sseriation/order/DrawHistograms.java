package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.Utilities;

public class DrawHistograms {

	public static void main(String[] args) throws IOException {
		String database = args[0];
		String trainingFile = database + "_train.dat";
		LinearOrder lo = new Euclidean(trainingFile);
		lo.setOrder(original(lo.m));
		double data[][] = new double[2][];
		data[0] = lo.calculateConsecutiveDistances();
		Utilities.writeDoubleList(data[0], database + "_train_histogram.dat");
		lo.setOrder(Utilities.readIntArray(database + "_train_" + lo.model
				+ "_order.txt"));
		data[1] = lo.calculateConsecutiveDistances();
		Utilities.writeDoubleList(data[1], database + "_train_" + lo.model
				+ "_histogram.dat");
	}

	public static int[] original(int m) throws IOException {
		int[] order = new int[m];
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		return order;
	}

}
