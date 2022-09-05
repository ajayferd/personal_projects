// Sean Szumlanski
// COP 3503, Spring 2021

// Fibonacci.java
// ==============
// Five versions of Fibonacci:
//
// 1. fib()          - Traditional, exponential version.
// 2. fibMemo()      - Memoization version. Top-down and recursive.
// 3. fibUltraMemo() - Memoization with HashMap instead of array.
// 4. fibDP()        - Dynamic programming version. Bottom-up and iterative.
// 5. fibDPFancy()   - Dynamic programming version with space optimization.


import java.io.*;
import java.util.*;

public class Fibonacci
{
	private static final int UNINITIALIZED = -1;

	// Traditional recursive implementation. Runtime: O(1.618^n); Space: O(n)
	public static int fib(int n)
	{
		if (n < 2) return n;
		return fib(n-1) + fib(n-2);
	}

	// Memoization version. Runtime: O(n); Space: O(n)
	//
	// In class, I used an array to do this, but I wanted you to see here that
	// it's possible to do with a HashMap, too.
	public static int fibMemo(int n)
	{
		int [] memo = new int[n+1];
		Arrays.fill(memo, UNINITIALIZED);

		return fibMemo(n, memo);
	}

	private static int fibMemo(int n, int [] memo)
	{
		if (n < 2)
			return n;

		if (memo[n] != UNINITIALIZED)
			return memo[n];

		memo[n] = fibMemo(n-1, memo) + fibMemo(n-2, memo);
		return memo[n];
	}

	// Memoization version. Runtime: O(n); Space: O(n)
	//
	// In class, I used an array to do this, but I wanted you to see here that
	// it's possible to do with a HashMap, too.
	public static int fibUltraMemo(int n)
	{
		HashMap<Integer, Integer> memo = new HashMap<Integer, Integer>();
		return fibUltraMemo(n, memo);
	}

	private static int fibUltraMemo(int n, HashMap<Integer, Integer> memo)
	{
		// Traditional base cases still apply.
		if (n < 2)
			return n;

		// This is an additional "flavor" of base case, where we check whether
		// F(n) has already been computed and stored in our memo.
		Integer memoized = memo.get(n);
		if (memoized != null)
			return memoized;

		// If this result is not already in our memo, then capture it, put it in
		// the memo, and then return that result from this method call.
		int result = fibUltraMemo(n-1, memo) + fibUltraMemo(n-2, memo);
		memo.put(n, result);
		return result;
	}

	// Dynamic programming version. Runtime: O(n); Space: O(n)
	public static int fibDP(int n)
	{
		// We can use this to safe guard against integer overflow and undefined
		// values of F(n).
		if (n < 0 || n > 45)
			return -1;

		// This ensures we have enough space for F[0] and F[1], even if n == 0.
		int [] F = new int[Math.max(2, n+1)];

		// Initalize base cases to jump start the for-loop.
		F[0] = 0;
		F[1] = 1;

		// Notice that we build our solutions from the bottom up.
		for (int i = 2; i <= n; i++)
			F[i] = F[i-1] + F[i-2];

		return F[n];
	}

	// DP version with space optimzation. Runtime: O(n); Space: O(1)
	public static int fibDPFancy(int n)
	{
		// We can use this to safe guard against integer overflow and undefined
		// values of F(n).
		if (n < 0 || n > 45)
			return -1;

		// We will only use an array of length two, because we only ever need
		// the two previous Fibonacci numbers in order to calculate the next
		// one. Our for-loop will use a toggle to alternate between overwriting
		// index 0 and index 1 in the array, so we always overwrite the oldest
		// Fibonacci number that we're storing.
		int [] F = new int[2];

		// Initalize base cases to jump start the for-loop.
		F[0] = 0;
		F[1] = 1;

		// Notice that we build our solutions from the bottom up.
		for (int i = 2; i <= n; i++)
			F[i%2] = F[0] + F[1];

		return F[n%2];
	}

	public static void main(String [] args)
	{
		if (args.length < 1)
		{
			System.err.println("Proper syntax: java Fibonacci <n>");
			return;
		}

		int n = Integer.parseInt(args[0]);
		System.out.println("F(" + n + ") = " + fibMemo(n));
		System.out.println("F(" + n + ") = " + fibUltraMemo(n));
		System.out.println("F(" + n + ") = " + fibDP(n));
		System.out.println("F(" + n + ") = " + fibDPFancy(n));
		System.out.println("F(" + n + ") = " + fib(n));
	}
}
