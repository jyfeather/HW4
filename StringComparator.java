package shake_n_bacon;

import providedCode.*;

/**
 * @author Roy Gu
 * @UWNetID roygu93
 * @studentID 1125302
 * @email roygu93@uw.edu
 * 
 *        TODO: REPLACE this comment with your own as appropriate.
 * 
 *        1. This comparator is used by the provided code for both data-counters
 *        and sorting. Because of how the output must be sorted in the case of
 *        ties, your implementation should return a negative number when the
 *        first argument to compare comes first alphabetically.
 * 
 *        2. Do NOT use any String comparison provided in Java's standard
 *        library; the only String methods you should use are length and charAt.
 * 
 *        3. You can use ASCII character codes to easily compare characters
 *        http://www.asciitable.com/
 * 
 *        4. When you are unsure about the ordering, you can try
 *        str1.compareTo(str2) to see what happens. Your
 *        stringComparator.compare(str1, str2) should behave the same way as
 *        str1.compareTo(str2). They don't have to return the same value, but
 *        their return values should have the same sign (+,- or 0).
 */
public class StringComparator implements Comparator<String> {

	//post: returns a negative number when the first given argument alphabetically comes
	//before the second given argument, a 0 if the two arguments are exactly the same, and
	//a positive number when the second argument comes alphabetically before the first argument
	public int compare(String s1, String s2) {
		int index = 0;
		
		//while the index is less than the length of both given strings, traverse through
		//the same index character of both strings until unequal characters are found, then 
		//return the comparison of the characters. 
		while(index < s1.length() && index < s2.length()) {
			if(s1.charAt(index) - s2.charAt(index) != 0)
				return s1.charAt(index) - s2.charAt(index);
			else
				index++;
		}
		
		//if the strings are equals in length, return 0, else if s1 is longer, return 1,
		//else return -1
		if(s1.length() == s2.length()) 
			return 0;
		else if (s1.length() > s2.length())
			return 1;
		else 
			return -1;
	}
}
