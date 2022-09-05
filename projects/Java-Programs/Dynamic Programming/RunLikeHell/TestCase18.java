// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase18.java
// ============================
// Test cases #12 through #20 generate random inputs and check your method's
// return value against the result produced by my solution code.


public class TestCase18
{
	public static void main(String [] args) throws Exception
	{
		// Select a random number of knowledge blocks.
		int r = (int)(Math.random() * 10000) + 1;
		int [] blocks = new int[r];

		// Randomly generate the values in the knowledge blocks.
		for (int j = 0; j < r; j++)
			blocks[j] = (int)(Math.random() * 10000) + 1;

		// This is the result from my solution code.
		int ans = Solution.maxGain(blocks);

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
