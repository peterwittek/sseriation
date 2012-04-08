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
