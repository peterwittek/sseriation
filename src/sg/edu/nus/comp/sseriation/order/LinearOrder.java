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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import sg.edu.nus.comp.sseriation.util.Utilities;

public abstract class LinearOrder {

	private class minObject {
		public double min;
		public int argmin;

		minObject(double min, int argmin) {
			this.min = min;
			this.argmin = argmin;
		}
	}

	protected int seed;
	protected Vector<Integer> rightSide = new Vector<Integer>();
	protected Vector<Integer> leftSide = new Vector<Integer>();
	protected HashSet<Integer> V = new HashSet<Integer>();
	protected String model;

	public String getModel() {
		return model;
	}

	protected String filename;
	protected int m;// The number of features
	private boolean reset;

	public LinearOrder(String filename, String model) throws IOException {
		this(filename, model, true);
	}

	public LinearOrder(String filename, String model, boolean reset)
			throws IOException {
		this.model = model;
		this.filename = filename;
		this.reset = reset;
		if (reset) {
			Utilities.resetFile(filename.substring(0, filename.length() - 4)
					+ "_" + model + "_seed.txt");
			Utilities.resetFile(filename.substring(0, filename.length() - 4)
					+ "_" + model + "_left.txt");
			Utilities.resetFile(filename.substring(0, filename.length() - 4)
					+ "_" + model + "_right.txt");
		}
	}

	public double[] calculateConsecutiveDistances() {
		int[] order = getNewOrder();
		double[] result = new double[m - 1];
		for (int i = 0; i < m - 1; i++) {
			System.out.println(i);
			result[i] = distanceFunction(order[i], order[i + 1]);
		}
		return result;
	}

	protected abstract double distanceFunction(int x, int y);

	private minObject findMin(int x) {
		double min = Integer.MAX_VALUE;
		int argmin = -1;
		for (Iterator<Integer> iter = V.iterator(); iter.hasNext();) {
			int tmpi = iter.next();
			double tmpd = distanceFunction(x, tmpi);
			if (tmpd < min) {
				argmin = tmpi;
				min = tmpd;
			}
		}
		minObject result = new minObject(min, argmin);
		return result;
	}

	protected abstract int findSeed();

	public void generateOrder() throws IOException {
		for (int i = 0; i < m; i++) {
			V.add(i);
		}
		if (reset) {
			System.out.println("Finding seed...");
			seed = findSeed();
			V.remove(seed);
			writeOne(filename.substring(0, filename.length() - 4) + "_" + model
					+ "_seed.txt", seed);
			System.out.println("Finding left seed...");
			int tl = findMin(seed).argmin;
			V.remove(tl);
			leftSide.add(tl);
			writeOne(filename.substring(0, filename.length() - 4) + "_" + model
					+ "_left.txt", tl);
			System.out.println("Finding right seed...");
			int tr = findMin(seed).argmin;
			V.remove(tr);
			rightSide.add(tr);
			writeOne(filename.substring(0, filename.length() - 4) + "_" + model
					+ "_right.txt", tr);
		} else {
			seed = Utilities.readIntArray(filename.substring(0,
					filename.length() - 4)
					+ "_" + model + "_seed.txt")[0];
			V.remove(seed);
			int[] tmp = Utilities.readIntArray(filename.substring(0,
					filename.length() - 4)
					+ "_" + model + "_left.txt");
			for (int i = 0; i < tmp.length; i++) {
				leftSide.add(tmp[i]);
				V.remove(tmp[i]);
			}
			tmp = Utilities.readIntArray(filename.substring(0,
					filename.length() - 4)
					+ "_" + model + "_right.txt");
			for (int i = 0; i < tmp.length; i++) {
				rightSide.add(tmp[i]);
				V.remove(tmp[i]);
			}
		}
		System.out.println("Generating order...");
		int progress = 0;
		minObject tlMinObject = findMin(leftSide.lastElement());
		minObject trMinObject = findMin(rightSide.lastElement());
		boolean changeLeft = false;
		boolean changeRight = false;
		while (!V.isEmpty()) {
			if (changeLeft) {
				tlMinObject = findMin(leftSide.lastElement());
				changeLeft = false;
			}
			if (changeRight) {
				trMinObject = findMin(rightSide.lastElement());
				changeRight = false;
			}
			if (tlMinObject.min <= trMinObject.min && tlMinObject.argmin != -1) {
				V.remove(tlMinObject.argmin);
				leftSide.add(tlMinObject.argmin);
				writeOne(filename.substring(0, filename.length() - 4) + "_"
						+ model + "_left.txt", tlMinObject.argmin);
				changeLeft = true;
			} else if (trMinObject.argmin != -1) {
				V.remove(trMinObject.argmin);
				rightSide.add(trMinObject.argmin);
				writeOne(filename.substring(0, filename.length() - 4) + "_"
						+ model + "_right.txt", trMinObject.argmin);
				changeRight = true;
			}
			if (trMinObject.argmin == tlMinObject.argmin) {
				changeLeft = true;
				changeRight = true;
			}
			progress++;
			if (progress % 50 == 0) {
				System.out.println(progress);
			}
			if (trMinObject.argmin == -1 && tlMinObject.argmin == -1) {
				/*
				 * rightSide.removeElementAt(rightSide.size() - 1); snapshotV();
				 * V.remove(trMinObject.argmin); changeRight = true; if
				 * (!V.isEmpty()){ V.remove(tlMinObject.argmin); changeLeft =
				 * true; }
				 */
				break;
			}
		}
	}

	protected int[] getNewOrder() {
		int[] result = new int[leftSide.size() + rightSide.size() + 1];
		int j = 0;
		for (int i = leftSide.size() - 1; i >= 0; i--, j++) {
			result[j] = leftSide.elementAt(i);
		}
		result[j++] = seed;
		for (int i = 0; i < rightSide.size(); i++, j++) {
			result[j] = rightSide.elementAt(i);
		}
		return result;
	}

	public double[] scale() throws IOException {
		double[] consecDists = calculateConsecutiveDistances();
		double[] scale = new double[consecDists.length + 1];
		scale[0] = 0;
		for (int i = 0; i < consecDists.length; i++) {
			scale[i + 1] = scale[i] + consecDists[i];
		}
		return scale;
	}

	public void setOrder(int[] order) {
		leftSide.removeAllElements();
		rightSide.removeAllElements();
		leftSide.add(order[0]);
		seed = order[1];
		for (int i = 2; i < order.length; i++) {
			rightSide.add(order[i]);
		}
	}

	public void snapshotV() throws IOException {
		FileWriter out = new FileWriter(new File(filename.substring(0,
				filename.length() - 4)
				+ "_" + model + "_Vsnapshot.txt"), false);
		for (Iterator<Integer> iter = V.iterator(); iter.hasNext();) {
			out.write(iter.next() + "\n");
		}
		out.close();
	}

	private void writeOne(String filename, int x) throws IOException {
		FileWriter out = new FileWriter(new File(filename), true);
		out.write(x + "\n");
		out.close();
	}

	public void writeOrder() throws IOException {
		Utilities.writeIntList(getNewOrder(),
				filename.substring(0, filename.length() - 4) + "_" + model
						+ "_order.txt");
	}

	public void writeScale() throws IOException {
		Utilities.writeDoubleList(scale(),
				filename.substring(0, filename.length() - 4) + "_" + model
						+ "_scale.dat");
	}

}
