package shake_n_bacon;

import providedCode.*;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 * 
 *        TODO: Replace this comment with your own as appropriate.
 * 
 *        1. You may implement HashTable with separate chaining discussed in
 *        class; the only restriction is that it should not restrict the size of
 *        the input domain (i.e., it must accept any key) or the number of
 *        inputs (i.e., it must grow as necessary).
 * 
 *        2. Your HashTable should rehash as appropriate (use load factor as
 *        shown in the class).
 * 
 *        3. To use your HashTable for WordCount, you will need to be able to
 *        hash strings. Implement your own hashing strategy using charAt and
 *        length. Do NOT use Java's hashCode method.
 * 
 *        4. HashTable should be able to grow at least up to 200,000. We are not
 *        going to test input size over 200,000 so you can stop resizing there
 *        (of course, you can make it grow even larger but it is not necessary).
 * 
 *        5. We suggest you to hard code the prime numbers. You can use this
 *        list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make sure you
 *        only hard code the prime numbers that are going to be used. Do NOT
 *        copy the whole list!
 * 
 *        TODO: Develop appropriate tests for your HashTable.
 */

public class HashTable_SC extends DataCounter {
	private int size;
	private HashNode[] table;
	private SimpleIterator itr;
	private Comparator<String> c;
	private Hasher h;
	private static final int[] PRIMES = 
		{11, 23, 47, 97, 199, 401, 809, 1667, 3307, 6833, 16993, 34403, 68891, 128591, 199999};
	private int index = 0;

	public HashTable_SC(Comparator<String> c, Hasher h) {
		table = new HashNode[PRIMES[index]];
		this.c = c;
		this.h = h;
		
	}

	@Override
	public void incCount(String data) {
		HashNode temp = table[data.hashCode() % table.length];
		
		while(temp != null) {
			if(temp.data.data == data) {
				temp.data.count += 1;
			}
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(String data) {
		HashNode temp = table[data.hashCode() % table.length];
		int count = 0;
		
		while(temp != null) {
			if(temp.data.data == data) {
				count = temp.data.count;
			}
		}
		
		return count;
	}

	@Override
	public SimpleIterator getIterator() {
		return itr;
	}
	
	private class HashNode {
		public DataCount data;       
	    public HashNode next;  

	    public HashNode(DataCount data) {
	        this(data,null);
	    }

	    public HashNode(DataCount data, HashNode next) {
	        this.data = data;
	        this.next = next;
	    }
	}

}
