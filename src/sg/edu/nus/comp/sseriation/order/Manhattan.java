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
		dor.generateOrderLeftRight();
		dor.writeOrder();
		dor.writeNewOrderWithClasses(trainingFile);
		String testFile = database + "_test.dat";
		dor.writeNewOrderWithClasses(testFile);
	}

	@Override
	protected double getDistance(int x, int y) {
		return SparseVector.manhattan(mx[x], mx[y]);
	}

}
