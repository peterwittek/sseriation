package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;
import sg.edu.nus.comp.sseriation.util.Utilities;
import sg.edu.nus.comp.sseriation.util.VectorNode;

public abstract class Distributional extends LinearOrder {

	protected VectorNode[][] mx;
	protected int n;
	protected boolean classes;

	Distributional(String filename, String model) throws IOException {
		this(filename, model, true);
	}

	Distributional(String filename, String model, boolean reset)
			throws IOException {
		this(filename, model, reset, true);
	}

	Distributional(String filename, String model, boolean reset, boolean classes)
			throws IOException {
		super(filename, model, reset);
		// Read data and transpose
		this.classes = classes;
		if (classes) {
			mx = SparseVector.readSparseMatrix(filename);
		} else {
			mx = SparseVector.readSparseMatrix(filename);
		}
		n = mx.length;
		mx = SparseVector.transpose(SparseVector.shiftColumns(mx, -1));
		m = mx.length;
	}

	@Override
	protected int findSeed() {
		return m / 2;
	}

	private VectorNode[][] rearrangeMatrix(VectorNode[][] mx) {
		return SparseVector.rearrangeRowVectors(mx, getNewOrder());
	}

	public void writeNewOrder() throws IOException {
		writeNewOrder(filename.substring(0, filename.length() - 4) + "_"
				+ model + ".dat");
	}

	public void writeNewOrder(String filename) throws IOException {
		if (classes) {
			writeNewOrder(this.mx, SparseVector.readClasses(this.filename),
					filename);
		} else {
			writeNewOrder(this.mx, null, filename);
		}
	}

	private void writeNewOrder(VectorNode[][] mx, String[] classes,
			String filename) {
		// Transpose data and write
		try {
			if (this.classes) {
				Utilities.writeTable(Utilities.insertColumn(SparseVector
						.shiftColumns(SparseVector
								.transpose(rearrangeMatrix(mx))), classes, 0),
						filename);
			} else {
				Utilities
						.writeTable(
								SparseVector.shiftColumns(SparseVector
										.transpose(rearrangeMatrix(mx)), 1),
								filename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeNewOrderWithClasses(String filename) throws IOException {
		String type = "";
		if (filename.contains("train")) {
			type = "train";
		} else if (filename.contains("test")) {
			type = "test";
		}
		int position = filename.indexOf(type);
		if (position == -1) {
			position = filename.length() - 4;
		}
		writeNewOrder(
				SparseVector.transpose(SparseVector.shiftColumns(
						SparseVector.readSparseMatrix(filename), -1)),
				SparseVector.readClasses(filename),
				filename.substring(0, position) + model + "_" + type + ".dat");

	}

}
