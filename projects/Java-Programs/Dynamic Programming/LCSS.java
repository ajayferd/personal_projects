// Sean Szumlanski
// COP 3503, Spring 2021

// LCSS.java
// =========
// Recursive and dynamic programming solutions to the LCSS (longest common
// subsequence) problem. These functions return the length of the LCSS between
// two strings. For example:
//
//     lcss("GOODMORNING", "HODOR") = 4 (for "ODOR")
//     lcss("RACECAR", "CREAM") = 3 (for "REA" and "CEA")
//     lcss("X", "ENOHPOLYX") = 1 (for "X")
//     lcss("CDD", "DD") = 2 (for "DD")
//
// I've included two recursive versions: one that assumes neither string is
// empty, and one that deals with empty strings. I think it's helpful to see
// both approaches, as they both speak to different base cases, which in turn
// makes it a bit easier to develop a DP solution.
//
// Some of the DP approaches include space optimizations that make it impossible
// to recover the actual subsequence of letters that constitute the LCSS between
// two strings. However, these methods still return the correct integer result.


import java.util.HashMap;

public class LCSS
{
	// Recursive LCSS. This method handles empty strings.
	public static int lcss_recursive(String a, String b)
	{
		// If either string is zero, it's game over: the longest common
		// subsequence necessarily has length zero in those cases.
		if (a.length() == 0 || b.length() == 0)
			return 0;

		// We'll reuse these for our recursive calls.
		String asub = a.substring(0, a.length() - 1);
		String bsub = b.substring(0, b.length() - 1);

		// If we have a character match, then it's part of the LCSS for
		// this subproblem. Remove that character and solve the new
		// new subproblem.
		if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1))
			return 1 + lcss_recursive(asub, bsub);

		// Otherwise, we might have to shave a character off a, or
		// shave a character off b. We solve both subproblems here
		// and return the max of the two results.
		return Math.max(
			lcss_recursive(asub, b),
			lcss_recursive(a, bsub)
		);
	}

	// This version avoids creating entirely new strings for each substring of
	// interest, instead using length parameters to indicate the lengths of the
	// substrings we're considering with each recursive call.
	public static int lcss_recursive_without_substrings(String a, String b)
	{
		return lcss_recursive_without_substrings(a, b, a.length(), b.length());
	}

	public static int lcss_recursive_without_substrings(String a, String b, int alen, int blen)
	{
		if (alen == 0 || blen == 0)
			return 0;

		if (a.charAt(alen - 1) == b.charAt(blen - 1))
			return 1 + lcss_recursive_without_substrings(a, b, alen - 1, blen - 1);

		return Math.max(
			lcss_recursive_without_substrings(a, b, alen - 1, blen),
			lcss_recursive_without_substrings(a, b, alen, blen - 1)
		);
	}

	// Memoization version.
	public static int lcss_memo(String a, String b)
	{
		int [][] memo = new int[a.length()+1][b.length()+1];

		for (int i = 0; i <= a.length(); i++)
			for (int j = 0; j <= b.length(); j++)
				memo[i][j] = -1;

		return lcss_memo(a, b, memo);
	}

	private static int lcss_memo(String a, String b, int [][] memo)
	{
		if (a.length() == 0 || b.length() == 0)
			return 0;

		if (memo[a.length()][b.length()] != -1)
			return memo[a.length()][b.length()];

		String asub = a.substring(0, a.length() - 1);
		String bsub = b.substring(0, b.length() - 1);

		if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1))
			memo[a.length()][b.length()] = 1 + lcss_memo(asub, bsub, memo);
		else
			memo[a.length()][b.length()] = Math.max(
			                                  lcss_memo(asub, b, memo),
			                                  lcss_memo(a, bsub, memo)
			                               );

		return memo[a.length()][b.length()];
	}

	// Memoization with a HashMap of HashMaps.
	public static int lcss_hashy_memo(String a, String b)
	{
		HashMap<String, HashMap<String, Integer>> memo = new HashMap<>();
		return lcss_hashy_memo(a, b, memo);
	}

	private static int lcss_hashy_memo(String a, String b, HashMap<String, HashMap<String, Integer>> memo)
	{
		if (a.length() == 0 || b.length() == 0)
			return 0;

		Integer result = memoLookup(a, b, memo);
		if (result != null)
			return result;

		String asub = a.substring(0, a.length() - 1);
		String bsub = b.substring(0, b.length() - 1);

		if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1))
			result = 1 + lcss_hashy_memo(asub, bsub, memo);
		else
			result = Math.max(
			            lcss_hashy_memo(asub, b, memo),
			            lcss_hashy_memo(a, bsub, memo)
			         );

		memoize(a, b, result, memo);
		return result;
	}

	// Look up result in memo. Return null if not found.
	private static Integer memoLookup(String a, String b, HashMap<String, HashMap<String, Integer>> memo)
	{
		HashMap<String, Integer> thisMap = memo.get(a);

		if (thisMap == null)
			return null;

		return thisMap.get(b);
	}

	// Store result in memo.
	private static void memoize(String a, String b, int result, HashMap<String, HashMap<String, Integer>> memo)
	{
		HashMap<String, Integer> thisMap = memo.get(a);

		if (thisMap == null)
		{
			thisMap = new HashMap<>();
			memo.put(a, thisMap);
		}

		thisMap.put(b, result);
	}

	// DP version of lcss_recursive.
	public static int lcss_dp(String a, String b)
	{
		int [][] matrix = new int[a.length()+1][b.length()+1];

		// Assumes first row and column are already zeroed out.

		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++)
				if (a.charAt(i-1) == b.charAt(j-1))
					matrix[i][j] = 1 + matrix[i-1][j-1];
				else
					matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);

		return matrix[a.length()][b.length()];
	}

	// This method assumes neither string is empty. This affects our base cases,
	// and also reveals how we can code a DP solution whose matrix is slightly
	// smaller (a.length() x b.length() instead of a.length()+1 x b.length()+1).
	public static int lcss_recursive_no_empty_strings(String a, String b)
	{
		// If one of the strings has a single character, we search the other
		// string to see whether that character occurs there.
		if (a.length() == 1)
			return (b.indexOf(a.charAt(0)) > -1) ? 1 : 0;

		if (b.length() == 1)
			return (a.indexOf(b.charAt(0)) > -1) ? 1 : 0;
		
		String asub = a.substring(0, a.length() - 1);
		String bsub = b.substring(0, b.length() - 1);
		
		if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1))
			return 1 + lcss_recursive_no_empty_strings(asub, bsub);
		
		return Math.max(
			lcss_recursive_no_empty_strings(asub, b),
			lcss_recursive_no_empty_strings(a, bsub)
		);
	}

	// DP version of lcss_no_empty_strings_recursive().
	public static int lcss_dp_no_empty_strings(String a, String b)
	{
		int [][] matrix = new int[a.length()][b.length()];

		int i, j;

		for (i = 0; i < b.length() && b.charAt(i) != a.charAt(0); i++)
			matrix[0][i] = 0;
		for (i = i; i < b.length(); i++)
			matrix[0][i] = 1;

		for (i = 0; i < a.length() && a.charAt(i) != b.charAt(0); i++)
			matrix[i][0] = 0;
		for (i = i; i < a.length(); i++)
			matrix[i][0] = 1;

		for (i = 1; i < a.length(); i++)
			for (j = 1; j < b.length(); j++)
				if (a.charAt(i) == b.charAt(j))
					matrix[i][j] = 1 + matrix[i-1][j-1];
				else
					matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);

		return matrix[a.length()-1][b.length()-1];
	}

	// This is the version of lcss_dp() with space optimization! We went from
	// an O(mn) matrix (where m = a.length() and n = b.length() to an O(n)
	// matrix! The runtime is still O(mn), though.
	public static int lcss_dp_less_space(String a, String b)
	{
		int [][] matrix = new int[2][b.length()+1];

		// Assumes first row and column are already zeroed out.

		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++)
				if (a.charAt(i-1) == b.charAt(j-1))
					matrix[i%2][j] = 1 + matrix[(i-1)%2][j-1];
				else
					matrix[i%2][j] = Math.max(matrix[(i-1)%2][j], matrix[i%2][j-1]);

		return matrix[a.length()%2][b.length()];
	}

	// This version's space complexity is O(MIN{m,n}) because it swaps strings
	// a and b if a's length is less than b's length.
	public static int lcss_dp_even_less_space(String a, String b)
	{
		if (a.length() < b.length())
		{
			String temp = a; a = b; b = temp;
		}

		int [][] matrix = new int[2][b.length()+1];

		// Assumes first row and column are already zeroed out.

		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++)
				if (a.charAt(i-1) == b.charAt(j-1))
					matrix[i%2][j] = 1 + matrix[(i-1)%2][j-1];
				else
					matrix[i%2][j] = Math.max(matrix[(i-1)%2][j], matrix[i%2][j-1]);

		return matrix[a.length()%2][b.length()];
	}

	// This is just insane.
	public static int lcss_dp_one_row(String a, String b)
	{
		if (a.length() < b.length())
		{
			String temp = a; a = b; b = temp;
		}

		// Note: This is counting on Java to initialize matrix[0] to 0. In another
		// language, we would have to do that manually in order for this solution
		// to work.

		int [] matrix = new int[b.length()+1];
		int diagonal;

		for (int i = 1; i <= a.length(); i++)
		{
			diagonal = 0;

			for (int j = 1; j <= b.length(); j++)
			{
				int weAreAboutToLoseThisValue = matrix[j];

				if (a.charAt(i-1) == b.charAt(j-1))
					matrix[j] = 1 + diagonal;
				else
					matrix[j] = Math.max(matrix[j-1], matrix[j]);

				diagonal = weAreAboutToLoseThisValue;
			}
		}

		return matrix[b.length()];
	}

	public static void main(String [] args)
	{
		System.out.println("Recursive approach:");
		System.out.println("===================");
		System.out.println(lcss_recursive("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_recursive("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_recursive("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_recursive("racecar", "cream"));        // rea => 3
		System.out.println(lcss_recursive("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_recursive("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_recursive("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_recursive("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("Recursive approach without generating new copies of (sub)strings:");
		System.out.println("=================================================================");
		System.out.println(lcss_recursive_without_substrings("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_recursive_without_substrings("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_recursive_without_substrings("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_recursive_without_substrings("racecar", "cream"));        // rea => 3
		System.out.println(lcss_recursive_without_substrings("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_recursive_without_substrings("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_recursive_without_substrings("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_recursive_without_substrings("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("Recursive approach with alternate base cases (no empty strings):");
		System.out.println("================================================================");
		System.out.println(lcss_recursive_no_empty_strings("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_recursive_no_empty_strings("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_recursive_no_empty_strings("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_recursive_no_empty_strings("racecar", "cream"));        // rea => 3
		System.out.println(lcss_recursive_no_empty_strings("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_recursive_no_empty_strings("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_recursive_no_empty_strings("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_recursive_no_empty_strings("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("Memoization approach with 2D array:");
		System.out.println("===================================");
		System.out.println(lcss_memo("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_memo("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_memo("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_memo("racecar", "cream"));        // rea => 3
		System.out.println(lcss_memo("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_memo("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_memo("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_memo("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("Memoization approach with HashMap:");
		System.out.println("==================================");
		System.out.println(lcss_hashy_memo("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_hashy_memo("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_hashy_memo("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_hashy_memo("racecar", "cream"));        // rea => 3
		System.out.println(lcss_hashy_memo("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_hashy_memo("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_hashy_memo("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_hashy_memo("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("DP approach with 2D array:");
		System.out.println("==========================");
		System.out.println(lcss_dp("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_dp("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_dp("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_dp("racecar", "cream"));        // rea => 3
		System.out.println(lcss_dp("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_dp("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_dp("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_dp("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("DP approach with slightly smaller 2D array:");
		System.out.println("===========================================");
		System.out.println(lcss_dp_no_empty_strings("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_dp_no_empty_strings("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_dp_no_empty_strings("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_dp_no_empty_strings("racecar", "cream"));        // rea => 3
		System.out.println(lcss_dp_no_empty_strings("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_dp_no_empty_strings("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_dp_no_empty_strings("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_dp_no_empty_strings("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("DP approach with only two rows:");
		System.out.println("===============================");
		System.out.println(lcss_dp_less_space("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_dp_less_space("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_dp_less_space("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_dp_less_space("racecar", "cream"));        // rea => 3
		System.out.println(lcss_dp_less_space("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_dp_less_space("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_dp_less_space("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_dp_less_space("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("DP approach with only two rows and another space optimization:");
		System.out.println("==============================================================");
		System.out.println(lcss_dp_even_less_space("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_dp_even_less_space("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_dp_even_less_space("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_dp_even_less_space("racecar", "cream"));        // rea => 3
		System.out.println(lcss_dp_even_less_space("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_dp_even_less_space("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_dp_even_less_space("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_dp_even_less_space("cdd", "dd"));               // dd => 2

		System.out.println();

		System.out.println("DP approach with only one row (*SUPER FANCY*):");
		System.out.println("==============================================");
		System.out.println(lcss_dp_one_row("goodmorning", "hodor"));    // odor => 4
		System.out.println(lcss_dp_one_row("hodor", "goodmorning"));    // odor => 4
		System.out.println(lcss_dp_one_row("hodorx", "goodmorningx"));  // odorx => 5
		System.out.println(lcss_dp_one_row("racecar", "cream"));        // rea => 3
		System.out.println(lcss_dp_one_row("modohodo", "hodo"));        // hodo => 4
		System.out.println(lcss_dp_one_row("kitten", "smithing"));      // itn => 3
		System.out.println(lcss_dp_one_row("x", "enohpolyx"));          // x => 1
		System.out.println(lcss_dp_one_row("cdd", "dd"));               // dd => 2
	}
}
