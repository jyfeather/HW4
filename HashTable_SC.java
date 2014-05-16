package shake_n_bacon;

import providedCode.*;

/**
 * @author Roy Gu	
 * @UWNetID roygu93
 * @studentID 1125302
 * @email roygu93@uw.edu
 * 
 * Hash table that stores frequencies of words from a given file. This 
 * hash table uses separate chaining to handle collisions
 */
public class HashTable_SC extends DataCounter {
	private int size;	//keeps track of number of unique words in the table
	private HashNode[] table;	//array to store each unique word and its count
	private Comparator<String> comparator;	//comparator to compare two strings
	private Hasher hasher;	//hasher to hash a string
	
	//array of integers for the prime number capacities for the table
	private static final int[] PRIMES = 
		{11, 23, 47, 97, 199, 401, 809, 1667, 3307, 6833, 16993, 34403, 68891, 128591, 199999};
	private int index;	//index for the prime numbers array

	//post: constructs a new hash node table with initial size of 11, index of 0, and size of 0
	public HashTable_SC(Comparator<String> c, Hasher h) {
		table = new HashNode[PRIMES[index]];
		index = 0;
		comparator = c;
		hasher = h;
		size = 0;
	}

	//post: if the word exists in the table, simply increase that word's count by 1, else
	//create a new node with that word as the data, and its count as 1. Increase the size 
	//of the array once the load factor is above 2. The new size of the array is the next 
	//prime number in the prime number array. All nodes in the old array are moved to the 
	//new array
	public void incCount(String data) {
		//if the load factor is above 2, increase the size of the table, keeping its
		//old elements into new indexes
		if((double) size / table.length > 2) {
			index++;
			HashNode[] newTable = new HashNode[PRIMES[index]];
			
			//for each node in the old table, add it to the new table in their new 
			//respective indexes
			for(HashNode node : table) {
				
				//for each linked node in the given bucket, add them to the new table
				while(node != null) {
					int hash = hasher.hash(node.data.data) % newTable.length;
					
					if(newTable[hash] == null)
						newTable[hash] = new HashNode(node.data, null);
					else {
						newTable[hash] = new HashNode(node.data, newTable[hash]);
					}
					
					node = node.next;
				}		
				
			}
			
			table = newTable;
		}
		
		int hashIndex = hasher.hash(data) % table.length; 
		
		//if the node at the given index is null, create a new node with the given data
		//else traverse through the nodes until the given data is found, and increase
		//that data's count by 1
		if(table[hashIndex] == null) {
			table[hashIndex] = new HashNode(new DataCount(data, 1));
			size++;
		} else {
			HashNode traverse = table[hashIndex];
			boolean found = false;
			
			//while the node is not null, find the node with the given data, and increase
			//its count by 1
			while(traverse != null) {
				if(comparator.compare(traverse.data.data, data) == 0) {
					traverse.data.count += 1;
					found = true;
				}
				
				traverse = traverse.next;
			}
			
			//if the node is not found, add it as the first node in the given bucket
			//while setting the old nodes in the bucket as the new node's next value
			if(!found) {
				table[hashIndex] = new HashNode(new DataCount(data, 1), table[hashIndex]);
				size++;
			}
		}
	}

	//post: returns the number of unique elements in the table
	public int getSize() {
		return size;
	}

	//post: returns the frequency of the given string in the table, 0 if its not in the table
	public int getCount(String data) {		
		int count = 0;
		
		HashNode temp = table[hasher.hash(data) % table.length];
		
		//while the temp is not null, traverse through the nodes until the node
		//with the given data is found, and return that node's count
		while(temp != null) {
			if(comparator.compare(temp.data.data, data) == 0) 
				count = temp.data.count;
			
			temp = temp.next;
		}
		
		return count;
	}

	//post: returns an iterator implemented as an inner class
	public SimpleIterator getIterator() {
		return new SCIterator();
	}
	
	//inner class hash node to handle collisions using separate chaining
	private class HashNode {
		public DataCount data;	//datacount for a given string and count of the string
	    public HashNode next;  //next node in the list

	    public HashNode(DataCount data) {
	        this(data, null);
	    }

	    public HashNode(DataCount data, HashNode next) {
	        this.data = data;
	        this.next = next;
	    }
	}
	
	//inner class to create an iterator
	private class SCIterator implements SimpleIterator {
		private int currentNumberOfVals;	//keeps track of the number of values iterated over
		private int itrIndex;	//keeps track of the index of the iterator
		private HashNode current;	//keeps track of the current iterator node
		
		//post: constructs the iterator, setting index as 0, and number of values in the 
		//iterator as 0. It also sets the hash node field as the first node found in the 
		//table
		public SCIterator() {
			currentNumberOfVals = 0;
			itrIndex = 0;
			
			//searches for the first non-null index and sets the current node as that index's node
			while(itrIndex < table.length && table[itrIndex] == null) {
				if(itrIndex != table.length - 1)
					itrIndex++;
			}
			current = table[itrIndex];
		}
		
		//pre: hasNext is true; there is a next value
		//post: returns the next data count value in the iterator
		public DataCount next() {
			HashNode temp = current;
			currentNumberOfVals++;
			
			if(hasNext()) {
				//if current node has no next value, go to the next non-null index in the table
				//and set current to that new index node. Otherwise, set current to the next node
				if(current.next == null) {
					itrIndex++;
					while(itrIndex < table.length && table[itrIndex] == null) {
						itrIndex++;
					}
					current = table[itrIndex];
				} else {
					current = current.next;
				}
			}
			
			return temp.data;
		}

		//post: returns true if there is a next node to iterate through, false otherwise
		public boolean hasNext() {
			return currentNumberOfVals < size;
		}
	}
}
