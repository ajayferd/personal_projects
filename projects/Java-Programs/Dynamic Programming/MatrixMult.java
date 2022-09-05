// Sean Szumlanski
// COP 3503, Spring 2021

// MatrixMult.java
// ===============
// DP solution to matrix multiplation problem.

public class MatrixMult
{
	public static int mm(int [] d)
	{
		// Note: If we have 5 dimensions in the array, there are 4 matrices,
		// and we number them 0 through 3. So, the number for the last matrix
		// is (n - 1), which is just (d.length - 2).

		int n = d.length - 1;
		return mm(d, 0, n - 1);
	}

	private static int mm(int [] d, int i, int j)
	{
		if (i == j)
			return 0;

		int minResult = Integer.MAX_VALUE;

		for (int k = i; k < j; k++)
		{
			// You could also just use Math.min() here:
			//
			// minResult = Math.min(minResult, M(d, i, k) + M(d, k+1, j) + d[i] * d[k+1] * d[j+1]);

			int temp = mm(d, i, k) + mm(d, k+1, j) + d[i] * d[k+1] * d[j+1];

			if (temp < minResult)
				minResult = temp;
		}

		return minResult;
	}

	public static int mmDP(int [] d)
	{
		int n = d.length - 1;
		int [][] matrix = new int[n][n];

		for (int i = 0; i < n; i++)
			matrix[i][i] = 0;

		for (int i = n - 1; i >= 0; i--)
		{
			for (int j = i + 1; j < n; j++)
			{
				int minResult = Integer.MAX_VALUE;

				for (int k = i; k < j; k++)
				{
					// You could also just use Math.min() here.
					int temp = matrix[i][k] + matrix[k+1][j] + d[i] * d[k+1] * d[j+1];

					if (temp < minResult)
						minResult = temp;
				}

				matrix[i][j] = minResult;
			}
		}

		return matrix[0][n-1];
	}

	public static void main(String [] args)
	{
		int [] d;

		d = new int [] {2, 10, 50, 20};  // answer = 3000
		System.out.println("Recursive result: " + mm(d));
		System.out.println("Iterative result: " + mmDP(d));
		System.out.println();

		d = new int [] {50, 20, 1, 10, 100};  // answer = 7000
		System.out.println("Recursive result: " + mm(d));
		System.out.println("Iterative result: " + mmDP(d));
		System.out.println();

		d = new int [] {2, 4, 2, 3, 1};  // answer = 22
		System.out.println("Recursive result: " + mm(d));
		System.out.println("Iterative result: " + mmDP(d));
		System.out.println();
	}
}
