package shake_n_bacon;

import providedCode.Hasher;

/**
 * @author Roy Gu
 * @UWNetID roygu93
 * @studentID 1125302
 * @email roygu93@uw.edu
 * 
 * Class to hash a given string value
 */
public class StringHasher implements Hasher {

	//post: returns the sum of all characters in the given string value
	public int hash(String str) {
		int hashVal = 0;
		
		for(int i = 0; i < str.length(); i++) 
			hashVal += str.charAt(i);
		
		return hashVal;
	}
}
