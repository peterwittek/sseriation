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
package sg.edu.nus.comp.sseriation.sdistance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import sg.edu.nus.comp.sseriation.util.Utilities;

import edu.mit.jwi.item.*;

public class Jcn extends EdgeCounter {

	private String[] sids;
	private double[] ics;
	private final String INFORMATION_CONTENT_FILE="";
	
	public Jcn() throws IOException {
		super();
		readIC(INFORMATION_CONTENT_FILE);
	}

	protected void readIC(String icFile) throws IOException {
		int nLines = Utilities.countRowsInFile(icFile);
		sids = new String[nLines];
		Scanner scn = new Scanner(new BufferedReader(new FileReader(icFile)))
				.useDelimiter("[\n]");
		nLines = 0;
		while (scn.hasNext()) {
			StringTokenizer st = new StringTokenizer(scn.next(), " ");
			sids[nLines++] = st.nextToken();
		}
		scn.close();
		java.util.Arrays.sort(sids);
		scn = new Scanner(new BufferedReader(new FileReader(icFile)))
				.useDelimiter("[\n]");
		ics = new double[nLines];
		while (scn.hasNext()) {
			StringTokenizer st = new StringTokenizer(scn.next(), " ");
			int i = Arrays.binarySearch(sids, st.nextToken());
			ics[i] = (Double.parseDouble(st.nextToken()));
		}
		scn.close();
	}

	public double d_min(String term1, String term2) {
		LinkedList<Double> distances = new LinkedList<Double>();
		LinkedList<SidPair> sidPairs = new LinkedList<SidPair>();
		double min = Double.MAX_VALUE;

		for (int i = 1; i <= 2; i++) {
			IIndexWord iword1 = dict
					.getIndexWord(term1, POS.getPartOfSpeech(i));
			if (iword1 == null)
				continue;
			IIndexWord iword2 = dict
					.getIndexWord(term2, POS.getPartOfSpeech(i));
			if (iword2 == null)
				continue;
			List<IWordID> iWordIDs1 = iword1.getWordIDs();
			List<IWordID> iWordIDs2 = iword2.getWordIDs();
			for (Iterator<IWordID> iWordID1 = iWordIDs1.iterator(); iWordID1
					.hasNext();) {
				ISynsetID tmpsid1 = dict.getWord(iWordID1.next()).getSynset()
						.getID();
				for (Iterator<IWordID> iWordID2 = iWordIDs2.iterator(); iWordID2
						.hasNext();) {
					SidPair tmp = new SidPair();
					tmp.sid1 = tmpsid1;
					tmp.sid2 = dict.getWord(iWordID2.next()).getSynset()
							.getID();
					sidPairs.add(tmp);
				}
			}
		}
		for (int i = 0; i < sidPairs.size(); i++) {
			LinkedList<ISynsetID> sids1 = new LinkedList<ISynsetID>();
			LinkedList<ISynsetID> sids2 = new LinkedList<ISynsetID>();
			sids1.addFirst(sidPairs.get(i).sid1);
			sids2.addFirst(sidPairs.get(i).sid2);
			PathInfo lsoPath = lso(sids1, sids2);
			distances.add(2 * Math.log(findIC(lsoPath.getSid())
					- (Math.log(findIC(sidPairs.get(i).sid1)) + Math
							.log(findIC(sidPairs.get(i).sid2)))));
		}
		for (int i = 0; i < distances.size(); i++) {
			if (distances.get(i) < min) {
				min = distances.get(i);
			}
		}
		return min;
	}

	public double d_max(String term1, String term2) {
		LinkedList<Double> distances = new LinkedList<Double>();
		LinkedList<SidPair> sidPairs = new LinkedList<SidPair>();
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 1; i <= 4; i++) {
			IIndexWord iword1 = dict
					.getIndexWord(term1, POS.getPartOfSpeech(i));
			if (iword1 == null)
				continue;
			IIndexWord iword2 = dict
					.getIndexWord(term2, POS.getPartOfSpeech(i));
			if (iword2 == null)
				continue;
			List<IWordID> iWordIDs1 = iword1.getWordIDs();
			List<IWordID> iWordIDs2 = iword2.getWordIDs();
			for (Iterator<IWordID> iWordID1 = iWordIDs1.iterator(); iWordID1
					.hasNext();) {
				ISynsetID tmpsid1 = dict.getWord(iWordID1.next()).getSynset()
						.getID();
				for (Iterator<IWordID> iWordID2 = iWordIDs2.iterator(); iWordID2
						.hasNext();) {
					SidPair tmp = new SidPair();
					tmp.sid1 = tmpsid1;
					tmp.sid2 = dict.getWord(iWordID2.next()).getSynset()
							.getID();
					sidPairs.add(tmp);
				}
			}
		}
		for (int i = 0; i < sidPairs.size(); i++) {
			LinkedList<ISynsetID> sids1 = new LinkedList<ISynsetID>();
			LinkedList<ISynsetID> sids2 = new LinkedList<ISynsetID>();
			sids1.addFirst(sidPairs.get(i).sid1);
			sids2.addFirst(sidPairs.get(i).sid2);
			PathInfo lsoPath = lso(sids1, sids2);
			distances.add(2 * Math.log(findIC(lsoPath.getSid())
					- (Math.log(findIC(sidPairs.get(i).sid1)) + Math
							.log(findIC(sidPairs.get(i).sid2)))));
		}
		for (int i = 0; i < distances.size(); i++) {
			if (distances.get(i) > max) {
				max = distances.get(i);
			}
		}
		if (max == Double.NEGATIVE_INFINITY) {
			max = Double.MAX_VALUE;
		}
		return max;
	}

	protected double findIC(ISynsetID sid) {
		int i = Arrays.binarySearch(sids, sid.toString());
		if (i < 0)
			return 1.0;
		return ics[i];
	}

}
