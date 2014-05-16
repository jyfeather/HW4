package shake_n_bacon;

import java.io.IOException;

import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.*;
public class TestHashTables {

	/**
	 * @author Ian Bruce
	 * @UWNetID brucei
	 * @studentID 1168370
	 * @email brucei@uw.edu
	 * 
	 * Tests methods in both implementations of hash tables for their functionality
	 */
	public static void main(String[] args) {
		Hasher h =  new StringHasher();
		StringComparator comp = new StringComparator();
		DataCounter counter = new HashTable_OA(
				comp, h);
		for(int i = 0; i < 2; i++)
		{
			if(i == 1)
			{
				counter = new HashTable_SC(comp, h);
				System.out.println("Testing SC");
			}
			else
				System.out.println("Testing OA");
			countWords("hamlet.txt", counter);
			System.out.println("Was able to count the words without it crapping out");
			System.out.println("True count of the word 'the': 1162. " +
					"Our counted amount: " + counter.getCount("the"));
			System.out.println("True count of the word 'so': 212. " +
					"Our counted amount: " + counter.getCount("so"));
			System.out.println("True count of the word 'if': 112. " +
					"Our counted amount: " + counter.getCount("if"));
			System.out.println("True count of the word 'blabbityflop': 0. " +
					"Our counted amount: " + counter.getCount("blabbityflop"));
			System.out.println("True amount of distinct words in the file: 5035 " +
					"Our counted amount: " + counter.getSize());
			counter.incCount("the");
			System.out.println("Incrementing the word 'the' by one, new count" +
					"of 'the' is: " + counter.getCount("the"));
			System.out.println("Testing the iterator:");
			SimpleIterator iter = counter.getIterator();
			boolean iteratesOverAllWords = true;
			while(iter.hasNext())
			{
				DataCount data = iter.next();
				iteratesOverAllWords = iteratesOverAllWords && counter.getCount(data.data) > 0;
			}
			if(iteratesOverAllWords)
				System.out.println("All words iterated over were in the table.");
			else
				System.out.println("Not all words iterated over were in the table.");
		}
	}
	
	public static void countWords(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
	}

}
