package sg.edu.nus.comp.sseriation.sdistance;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class EdgeCounter {

	public IDictionary dict;
	private final String WORDNET_DICT_DIR="";
	
	public EdgeCounter() throws IOException {
		URL url = new URL("file", null, WORDNET_DICT_DIR);
		dict = new Dictionary(url);
		dict.open();
	}

	public byte shortestPath(String term1, String term2) {
		if (term1.equals(term2)) {
			return 0;
		}
		byte result = Byte.MAX_VALUE;
		LinkedList<SidPair> sidPairs = new LinkedList<SidPair>();

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
			if (lsoPath.getEdgeLength() < result) {
				result = lsoPath.getEdgeLength();
			}
		}
		return result;
	}

	public PathInfo lso(LinkedList<ISynsetID> sids1, LinkedList<ISynsetID> sids2) {
		LinkedList<PathInfo> results = new LinkedList<PathInfo>();
		if (sids1.size() + sids2.size() > 40) {
			PathInfo result = new PathInfo();
			result.setEdgeLength(Byte.MAX_VALUE);
			result.setSid(sids1.getLast());
			return result;
		}
		List<ISynsetID> hypernyms1 = getHypernyms(sids1.getLast());
		List<ISynsetID> hypernyms2 = getHypernyms(sids2.getLast());
		if (hypernyms1.size() > 0 & hypernyms2.size() > 0) {
			for (ISynsetID sid1 : hypernyms1) {
				for (ISynsetID sid2 : hypernyms2) {
					sids1.addLast(sid1);
					sids2.addLast(sid2);
					PathInfo result = findCommonSense(sids1, sids2);
					if (result == null) {
						results.add(lso(sids1, sids2));
					} else {
						results.add(result);
					}
					sids2.removeLast();
				}
				sids1.removeLast();
			}
		} else if (hypernyms1.size() > 0) {
			for (ISynsetID sid1 : hypernyms1) {
				sids1.addLast(sid1);
				PathInfo result = findCommonSense(sids1, sids2);
				if (result == null) {
					results.add(lso(sids1, sids2));
				} else {
					results.add(result);
				}
				sids1.removeLast();
			}
		} else if (hypernyms2.size() > 0) {
			for (ISynsetID sid2 : hypernyms2) {
				sids2.addLast(sid2);
				PathInfo result = findCommonSense(sids1, sids2);
				if (result == null) {
					results.add(lso(sids1, sids2));
				} else {
					results.add(result);
				}
				sids2.removeLast();
			}
		} else {
			PathInfo result = findCommonSense(sids1, sids2);
			if (result == null) {
				result = new PathInfo();
				result.setEdgeLength(Byte.MAX_VALUE);
				result.setSid(sids1.getLast());
			}
			results.add(result);
		}
		int min = Integer.MAX_VALUE;
		PathInfo bestResult = null;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getEdgeLength() < min) {
				min = results.get(i).getEdgeLength();
				bestResult = results.get(i);
			}
		}
		return bestResult;
	}

	private List<ISynsetID> getHypernyms(ISynsetID sid) {
		ISynset synset = dict.getSynset(sid);
		return synset.getRelatedSynsets(Pointer.HYPERNYM);
	}

	private PathInfo findCommonSense(LinkedList<ISynsetID> sids1,
			LinkedList<ISynsetID> sids2) {
		PathInfo result = null;
		for (byte i = 0; i < sids1.size(); i++) {
			for (byte j = 0; j < sids2.size(); j++) {
				if (sids1.get(i).equals(sids2.get(j))) {
					result = new PathInfo();
					result.setSid(sids1.get(i));
					result.setEdgeLength((byte) (i + j));
					return result;
				}
			}
		}
		return result;
	}

	public void displaySynsetID(String term) {
		System.out.println(dict
				.getWord(dict.getIndexWord(term, POS.NOUN).getWordIDs().get(0))
				.getSynset().getID());
	}

	public void displaySense(ISynsetID sid) {
		List<IWord> words = dict.getSynset(sid).getWords();
		// System.out.print(sid + " ");
		for (Iterator<IWord> i = words.iterator(); i.hasNext();) {
			System.out.print(i.next().getLemma());
			if (i.hasNext())
				System.out.print(", ");
		}
		// System.out.println("");
	}

}
