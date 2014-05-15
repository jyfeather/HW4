
package shake_n_bacon;

import providedCode.*;

/**
 * @author Ian Bruce
 * @UWNetID brucei
 * @studentID 1168370
 * @email brucei@uw.edu
 * 
 * A hash table that is made to store word counts taken from text files.
 */
public class HashTable_OA extends DataCounter {

	// the array that stores words and their respective counts 
	private DataCount[] dataArray;
	
	// the amount of distinct words in the table
	private int size;
	
	// used to compare two strings
	private Comparator<String> c;
	
	// the the class for the hash function of this table. Takes in a 
	// word and returns the index of where the corresponding word would appear in the table
	private Hasher h;
	
	// the possible hash table sizes that the table can resize to
	private static final int[] PRIMES = 
		{11, 23, 47, 97, 199, 401, 809, 1667, 3307, 6833, 16993, 34403, 68891, 128591, 199999};
	
	// the index of the number in the array PRIMES used for the current table size
	private int sizeIndex;
	
	// initializes the comparator and hasher to the passed values.
	// Sets the size to initially be 0
	public HashTable_OA(Comparator<String> c, Hasher h) {
		sizeIndex = 0;
		dataArray = new DataCount[PRIMES[sizeIndex]];
		this.c = c;
		this.h = h;
		size = 0;
	}

	// adds one to the count of the specified word
	public void incCount(String data) 
	{
		if((double)size / PRIMES[sizeIndex] >= .75)
		{
			SimpleIterator iter = getIterator();
			sizeIndex++;
			int newSize = PRIMES[sizeIndex];
			DataCount[] newDataArray = new DataCount[newSize];
			while(iter.hasNext())
			{
				DataCount dataVal = iter.next();
				int index = h.hash(dataVal.data) % newSize;
				while(newDataArray[index] != null)
				{
					index++;
					if(index == newSize)
						index = 0;
				}
				newDataArray[index] = dataVal;
			}
			dataArray = newDataArray;
		}
		DataCount corresponding = getData(data);
		corresponding.count++;
	}

	// returns the amount of distinct words in the table
	public int getSize() {
		return size;
	}

	// returns the count of the specified word
	public int getCount(String data) {
		return getData(data).count;
	}

	// returns a SimpleIterator that can iterate over the DataCounts in this table
	public SimpleIterator getIterator() {
		return new OAIterator();
	}
	
	// returns the DataCount that corresponds with the passed word
	private DataCount getData(String word)
	{
		int index = h.hash(word) % PRIMES[sizeIndex];
		while(dataArray[index] != null && 
			c.compare(word, dataArray[index].data) != 0)
		{
			index++;
			if(index == dataArray.length)
				index = 0;
		}
		if(dataArray[index] == null)
		{
			dataArray[index] = new DataCount(word, 0);
			size++;
		}
		return dataArray[index];
	}
	
	// the iterator that iterates over elements in this table
	private class OAIterator implements SimpleIterator
	{
		// the current number of values that the iterator has iterated over
		private int currentNumberOfVals;
		
		// the current index of the hash table that the iterator is looking at
		private int index;
		
		// sets the current number of values to be zero and has the iterator look
		// at index 0 on the first call of next()
		public OAIterator()
		{
			currentNumberOfVals = 0;
			index = -1;
		}
		
		// returns true if the iterator hasn't iterated over the last element in the table
		public boolean hasNext()
		{
			return currentNumberOfVals < size;
		}
		
		// returns the next non-null DataCount in the table
		public DataCount next()
		{
			index++;
			while(dataArray[index] == null)
				index++;
			currentNumberOfVals++;
			return dataArray[index];
		}
	}
}
