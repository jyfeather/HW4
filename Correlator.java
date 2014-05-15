package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

/**
 * @author Ian Bruce
 * @UWNetID brucei
 * @studentID 1168370
 * @email brucei@uw.edu
 * 
 * A class that computes a metric for determining the similarities in word
 * frequencies for two text files. The metric takes the sum of the squares of
 * the differences between the percentage frequencies of words that occur in both
 * files. 
 */
public class Correlator {

	// computes the similarity metric described above. The first element 
	// in the string array passed as a parameter should be -s if a separate
	// chaining hash table is to be used, and -o for a linear probing hash
	// table. The next two elements are the filepaths of the text files that are
	// to be compared
	public static void main(String[] args) {
		if (args.length != 3) {
			usage();
		}
		String firstArg = args[0].toLowerCase();
		StringComparator comp = new StringComparator();
		StringHasher hash = new StringHasher();
		DataCounter counter1 = new HashTable_OA(comp, hash);
		DataCounter counter2 = new HashTable_OA(comp, hash);
		if(firstArg.equals("-s"))
		{
			counter1 = new HashTable_SC(comp, hash);
			counter2 = new HashTable_SC(comp, hash);
		}
		else if(!firstArg.equals("-o"))
			usage();
		int length1 = countWords(args[1], counter1);
		int length2 = countWords(args[2], counter2);
		System.out.println("Got past that part");
		SimpleIterator iter = counter1.getIterator();
		double variance = 0;
		while(iter.hasNext())
		{
			DataCount word = iter.next();
			int count1 = counter1.getCount(word.data);
			int count2 = counter2.getCount(word.data);
			double frequency1 = (double)count1/length1;
			double frequency2 = (double)count2/length2;
			if(frequency1 < .01 && frequency1 > .0001 
					&& frequency2 < .01 && frequency2 > .0001)
				variance = variance + (frequency2-frequency1)*(frequency2-frequency1);
		}
		System.out.println(variance);
	}
	
	// prints an error message regarding invalid syntax in the first argument in main
	private static void usage() {
		System.err
				.println("Usage: [-s | -o] <filename of document to analyze>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}
	
	// reads words from the passed string file and puts them into the counter.
	// returns the total amount of words counted in the file.
	// if an invalid filepath is passed, this program will catch this error
	public static int countWords(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			int totalCounts = 0;
			while (word != null) {
				totalCounts++;
				counter.incCount(word);
				word = reader.nextWord();
			}
			return totalCounts;
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return 0;
	}
}
