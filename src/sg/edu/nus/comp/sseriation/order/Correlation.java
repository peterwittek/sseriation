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
		co.generateOrderLeftRight();
		co.writeOrder();
		co.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		co.writeNewOrderWithClasses(testFile);
		Utilities.writeDoubleList(co.getScale(), database + "_" + co.model
				+ "_scale.dat");
	}

	@Override
	protected double getDistance(int x, int y) {
		return 1 - Math.abs(SparseVector.correlation(mx[x], mx[y], nDimensions));
	}

}
