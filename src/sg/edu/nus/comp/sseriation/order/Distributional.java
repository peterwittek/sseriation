/**
 * Semantic Seriation based on Hamiltonian Path
 *  Copyright (C) 2012 Peter Wittek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package sg.edu.nus.comp.sseriation.order;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.util.SparseVector;
import sg.edu.nus.comp.sseriation.util.Utilities;
import sg.edu.nus.comp.sseriation.util.VectorNode;

public abstract class Distributional extends LinearOrder {

	protected VectorNode[][] mx;
	protected int nDimensions;
	protected static final String MODEL_NAME = "";
	private boolean isClasses;
	private boolean isTransposed;

	Distributional(String filename, String model) throws IOException {
		this(filename, model, true);
	}

	Distributional(String filename, String model, boolean reset)
			throws IOException {
		this(filename, model, reset, true);
	}

	Distributional(String filename, String model, boolean reset,
			boolean isClasses) throws IOException {
		this(filename, model, reset, isClasses, false);
	}

	Distributional(String filename, String model, boolean reset,
			boolean isClasses, boolean isTransposed) throws IOException {
		super(filename, model, reset);
		// Read data and isTransposed
		this.isClasses = isClasses;
		this.isTransposed = isTransposed;
		mx = SparseVector.readSparseMatrix(filename);
		if (isTransposed) {
			mx = SparseVector.transpose(SparseVector.shiftColumns(mx, -1));
		}
		nInstances = mx.length;
		nDimensions = SparseVector.findMaxColumnIndex(mx);
		initialize();
	}

	@Override
	protected int findSeed() {
		return nInstances / 2;
	}

	private VectorNode[][] rearrangeMatrix(VectorNode[][] mx) {
		return SparseVector.rearrangeRowVectors(mx, getNewOrder());
	}

	public void writeNewOrder() throws IOException {
		writeNewOrder(filename.substring(0, filename.length() - 4) + "_"
				+ model + ".dat");
	}

	public void writeNewOrder(String filename) throws IOException {
		if (isClasses) {
			writeNewOrder(this.mx, SparseVector.readClasses(this.filename),
					filename);
		} else {
			writeNewOrder(this.mx, null, filename);
		}
	}

	private void writeNewOrder(VectorNode[][] mx, String[] classes,
			String filename) {
		if (isTransposed) {
			try {
				mx = SparseVector.shiftColumns(SparseVector.transpose(rearrangeMatrix(mx)));
				if (isClasses) {
					Utilities.writeTable(
							Utilities.insertColumn(mx, classes, 0), filename);
				} else {
					Utilities.writeTable(mx, filename);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				if (isClasses) {
						Utilities.writeTable(Utilities.rearrangeRows(
							Utilities.insertColumn(mx, classes, 0),
							getNewOrder()), filename);
				} else {
					Utilities.writeTable(rearrangeMatrix(mx), filename);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeNewOrderWithClasses(String filename) throws IOException {
		int position = filename.length() - 4;
		writeNewOrder(
				SparseVector.transpose(SparseVector.shiftColumns(
						SparseVector.readSparseMatrix(filename), -1)),
				SparseVector.readClasses(filename),
				filename.substring(0, position) + "_" + model + ".dat");
	}

}
