package sg.edu.nus.comp.sseriation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Utilities {

	public static int countRowsInFile(String filename) throws IOException {
		int result = 0;
		Scanner s = new Scanner(new BufferedReader(new FileReader(new File(
				filename)))).useDelimiter("[\n\r]");
		while (s.hasNext()) {
			s.next();
			result++;
		}
		return result;
	}

	public static Object[][] insertColumn(Object[][] mx, Object[] column,
			int index) {
		Object[][] result = new Object[mx.length][];
		for (int i = 0; i < mx.length; i++) {
			if (mx[i] != null) {
				result[i] = new Object[mx[i].length + 1];
				int k = 0;
				for (int j = 0; j < mx[i].length; j++) {
					if (j != index) {
						result[i][k++] = mx[i][j];
					} else {
						result[i][k++] = column[i];
						result[i][k++] = mx[i][j];
					}
				}
			} else if (index == 0) {
				result[i] = new Object[1];
				result[i][0] = column[i];
			} else {
				result[i] = null;
			}
		}
		return result;
	}

	public static double mean(double[] x) {
		return sum(x) / x.length;
	}

	private static int[] convertIntVectorToArray(Vector<Integer> v) {
		int[] result = new int[v.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = v.get(i);

		return result;
	}

	/**
	 * Reads an integer array
	 * 
	 * @param filename
	 *            the filename
	 * @return the integer list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	public static int[] readIntArray(String filename) throws IOException {
		Vector<Integer> tmp = new Vector<Integer>();
		Scanner s = new Scanner(new BufferedReader(new FileReader(filename)))
				.useDelimiter("[\r\n, ]");
		;
		while (s.hasNext()) {
			tmp.add(Integer.valueOf(s.next()));
		}
		s.close();
		return convertIntVectorToArray(tmp);
	}

	/**
	 * Reads a term list.
	 * 
	 * @param filename
	 *            the filename
	 * @return the sorted term list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String[] readWordList(String filename) throws IOException {
		if (filename == null) {
			return null;
		}
		ArrayList<String> words = new ArrayList<String>();
		Scanner s = new Scanner(new BufferedReader(new FileReader(filename)))
				.useDelimiter("[\n\r]");
		while (s.hasNext()) {
			String tmp = s.next();
			tmp = tmp.toLowerCase();
			if (!tmp.equals("")) {
				words.add(tmp);
			}
		}
		s.close();

		String[] result = new String[words.size()];
		for (int i = 0; i < words.size(); i++) {
			result[i] = words.get(i);
		}
		java.util.Arrays.sort(result);
		return result;
	}

	public static void resetFile(String filename) throws IOException {
		FileWriter out = new FileWriter(new File(filename), false);
		out.close();
	}

	public static double sum(double[] x) {
		double result = 0;
		for (int i = 0; i < x.length; i++) {
			result += x[i];
		}
		return result;
	}

	public static void write(Object[] objectList, FileWriter out,
			String separator) throws IOException {
		for (int i = 0; i < objectList.length; i++) {
			if (objectList[i] == null) {
				continue;
			}
			out.write(objectList[i].toString());
			if (i != objectList.length - 1) {
				out.write(separator);
			}
		}
	}

	public static void writeDoubleList(double[] objectList, String filename)
			throws IOException {
		FileWriter out = new FileWriter(new File(filename));
		for (int i = 0; i < objectList.length; i++) {
			out.write(objectList[i] + "\n");
		}
		out.close();
	}

	public static void writeIntList(int[] objectList, String filename)
			throws IOException {
		FileWriter out = new FileWriter(new File(filename));
		for (int i = 0; i < objectList.length; i++) {
			out.write(objectList[i] + "\n");
		}
		out.close();
	}

	public static void writeTable(Object[][] objectTable, String filename)
			throws IOException {
		writeTable(objectTable, filename, " ");
	}

	public static void writeTable(Object[][] objectTable, String filename,
			String separator) throws IOException {
		FileWriter out = new FileWriter(new File(filename));
		for (int i = 0; i < objectTable.length; i++) {
			if (objectTable[i] != null) {
				write(objectTable[i], out, separator);
			}
			out.write("\n");
		}
		out.close();
	}

}
