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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import sg.edu.nus.comp.sseriation.sdistance.Jcn;
import sg.edu.nus.comp.sseriation.util.Utilities;

public class JcnConservative extends LinearOrder {

	private String[] terms;
	private Jcn jcdObject;

	JcnConservative(String filename) throws IOException {
		super(filename, "jcn");
	}

	public JcnConservative(String filename, boolean reset) throws IOException {
		super(filename, "jcn");
		System.out.println("Creating similarity object...");
		jcdObject = new Jcn();
		terms = Utilities.readWordList(filename);
		nInstances = terms.length;
	}

	@Override
	protected double getDistance(int x, int y) {
		return jcdObject.d_max(terms[x], terms[y]);
	}

	@Override
	protected int findSeed() {
		double min = Integer.MAX_VALUE;
		int argmin = -1;
		for (Iterator<Integer> iter = remainingElements.iterator(); iter.hasNext();) {
			int tmpi = iter.next();
			double tmp = getDistance(Arrays.binarySearch(terms, "entity"),
					tmpi);
			if (tmp < min) {
				argmin = tmpi;
				min = tmp;
			}
		}
		return argmin;
	}

	protected void printInstance(int x){
		System.out.println(terms[x]);
	}
	
	@Override
	public void writeOrder() throws IOException {
		System.out.println("Writing order...");
		FileWriter out = new FileWriter(new File(filename.substring(0,
				filename.length() - 4)
				+ "_" + model + "_order.txt"), false);
		for (int i =0; i< order.size(); i++) {
			out.write(terms[order.get(i)] + "\n");
		}
		out.close();
	}

}
