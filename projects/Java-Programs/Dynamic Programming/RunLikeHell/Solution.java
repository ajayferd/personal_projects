// Sean Szumlanski
// COP 3503, Spring 2021

// ================
// RunLikeHell.java
// ================
// Solution to Program #7. Includes both recursive and DP solutions.


public class Solution
{
	public static int knowledge(int [] blocks)
	{
		return knowledge(blocks, blocks.length);
	}

	// Recursive approach, just for reference.
	private static int knowledge (int [] blocks, int k)
	{
		if (k <= 0)
			return 0;

		// We either take the last knowledge block (meaning we gain the knowledge
		// stored in blocks[k-1], but have to go back *two* blocks before we can
		// take another one), or we skip this block (with no knowledge gain),
		// allowing us to go back *one* block and make the choice of whether or
		// not to take that one. Those are the two recursive calls below. Of
		// course, we take the max of those two results.
		return Math.max(knowledge(blocks, k-2) + blocks[k-1], knowledge(blocks, k-1));
	}


	// DP version.
	public static int maxGain(int [] blocks)
	{
		int n = blocks.length;
		int [] matrix = new int[n+1];

		// This is only here because I need to ensure below that I can initialize
		// two initial conditions instead of just one. If there were zero blocks,
		// then 'matrix' would be an array of length 1, and line 48 would crash.
		if (blocks == null || blocks.length == 0)
			return 0;

		// Initial condition. I actually initialize two cells here, since
		// matrix[i-2] needs to look back two spaces.
		matrix[0] = 0;
		matrix[1] = Math.max(0, blocks[0]);  // avoid knowledge loss if blocks[0] < 0

		for (int i = 2; i <= n; i++)
			matrix[i] = Math.max(matrix[i-2] + blocks[i-1], matrix[i-1]);

		return matrix[n];
	}

	public static double difficultyRating()
	{
		return 3.0;  // dummy value
	}

	public static double hoursSpent()
	{
		return 3.0;  // dummy value
	}
}
