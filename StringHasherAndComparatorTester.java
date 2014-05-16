/**
 * @author Roy Gu	
 * @UWNetID roygu93
 * @studentID 1125302
 * @email roygu93@uw.edu
 * 
 * Class to test StringHasher and StringComparator
 */

package shake_n_bacon;

public class StringHasherAndComparatorTester {

	public static void main(String[] args) {
		System.out.println("Testing StringComparator...");
		System.out.println();
		
		String s1 = "hifef";
		String s2 = "hi";
		StringComparator s = new StringComparator();
		
		System.out.println("Testing 'hifef' vs 'hi' - Expect positive number...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) > 0);
		System.out.println();

		s1 = "hi";
		s2 = "hi";
		
		System.out.println("Testing 'hi' vs 'hi' - Expect 0...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) == 0);
		System.out.println();
		
		s1 = "hi";
		s2 = "hifef";
		
		System.out.println("Testing 'hi' vs 'hifef' - Expect negative number...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) < 0);
		System.out.println();
		
		s1 = "aardvark";
		s2 = "ask";
		
		System.out.println("Testing 'aardvark' vs 'ask' - Expect negative number...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) < 0);
		System.out.println();
		
		s1 = "12345";
		s2 = "23456";
		
		System.out.println("Testing '12345' vs '23456' - Expect negative number...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) < 0);
		System.out.println();
		
		s1 = "???";
		s2 = "1234";
		
		System.out.println("Testing '???' vs '1234' - Expect positive number...");
		System.out.println(s.compare(s1, s2));
		System.out.println(s.compare(s1, s2) > 0);
		System.out.println();
		
		System.out.println("Testing StringHasher...");
		System.out.println();
		
		StringHasher h = new StringHasher();
		int count = 0;
		
		s1 = "hi";
		
		for(int i = 0; i < s1.length(); i++) {
			count += s1.charAt(i);
		}
		
		System.out.println("Testing 'hi' - Expected output: " + count);
		System.out.println(h.hash(s1));
		System.out.println(h.hash(s1) == count);
		System.out.println();
		
		count = 0;
		s1 = "1234abcd??";
		
		for(int i = 0; i < s1.length(); i++) {
			count += s1.charAt(i);
		}
		
		System.out.println("Testing '1234abcd??' - Expected output: " + count);
		System.out.println(h.hash(s1));
		System.out.println(h.hash(s1) == count);
		System.out.println();
	}

}
