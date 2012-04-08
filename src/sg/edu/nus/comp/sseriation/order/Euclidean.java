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
