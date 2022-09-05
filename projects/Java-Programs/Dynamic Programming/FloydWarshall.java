// Sean Szumlanski
// COP 3503, Spring 2021

// FloydWarshall.java
// ==================
// DP implementations of the Floyd-Warshall algorithm. Finds the shortest path
// (lowest-cost path) between each pair of vertices in a graph.


import java.io.*;
import java.util.*;

public class FloydWarshall
{
	private int [][] matrix;
	private int n;
	private final int oo = (int)1e9;

	FloydWarshall(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		n = in.nextInt();
		matrix = new int[n+1][n+1];

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if ((matrix[i][j] = in.nextInt()) == 0)
					matrix[i][j] = oo;
	}

	private void panic(String errorMessage)
	{
		System.err.println(errorMessage);
		System.exit(0);
	}

	public void fw()
	{
		int [][][] sp = new int[n+1][n+1][n+1];

		// Initialize base cases.
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				sp[i][j][0] = (i == j) ? 0 : matrix[i][j];

		// Floyd-Warshall. Note that I am slightly worried about the cumulative
		// effect that negative edge weights in really large graphs would have
		// here. We could code more defensively by explicitly refusing to add
		// sp[x][y][z] values that are equal to infinity.
		//
		// By the way, notice that if we can use the first k vertices as
		// intermediary vertices along our path, they are numbered 1 through k.
		// If we wanted to number them 0 through k-1, we would have to change
		// sp[i][k][k-1] + sp[k][j][k-1] to sp[i][k-1][k-1] + sp[k-1][j][k-1]
		// in the code below. (Super wonky.)
		for (int k = 1; k <= n; k++)
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++)
					sp[i][j][k] = Math.min(sp[i][j][k-1], sp[i][k][k-1] + sp[k][j][k-1]);

		// Negative cycle detection. This is quite cheeky. You probably shouldn't
		// kill the whole program just because you see a negative cycle.
		for (int i = 1; i <= n; i++)
			if (sp[i][i][n] < 0)
				panic("Negative cycle detected! The results above are invalid!\n");

		// Print matrix.
		for (int i = 1; i <= n; i++)
		{
			System.out.print(i + ":");
			for (int j = 1; j <= n; j++)
				System.out.printf("%5d%s", sp[i][j][n], (j == n) ? "\n" : "");
		}
	}

	public void fw_optimized_space()
	{
		int [][][] sp = new int[n+1][n+1][2];

		// Initialize base cases.
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				sp[i][j][0] = (i == j) ? 0 : matrix[i][j];

		// Floyd-Warshall.
		for (int k = 1; k <= n; k++)
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++)
					sp[i][j][k%2] = Math.min(sp[i][j][(k-1)%2], sp[i][k][(k-1)%2] + sp[k][j][(k-1)%2]);

		// Negative cycle detection. This is quite cheeky. You probably shouldn't
		// kill the whole program just because you see a negative cycle.
		for (int i = 1; i <= n; i++)
			if (sp[i][i][n%2] < 0)
				panic("Negative cycle detected! The results above are invalid!\n");

		// Print matrix.
		for (int i = 1; i <= n; i++)
		{
			System.out.print(i + ":");
			for (int j = 1; j <= n; j++)
				System.out.printf("%5d%s", sp[i][j][n%2], (j == n) ? "\n" : "");
		}
	}

	// Can you see how and why this works? We can essentially get away with
	// storing only one layer of the original 3D array because if we perform
	// all our shortest path updates in place, we might every now and then
	// refer to sp(i,j,k) instead of sp(i,j,k-1), which, if anything, only
	// gets us closer to the final result of sp(i,j,n) prematurely. (This is
	// similar to how the Bellman-Ford might converge on the shortest paths
	// a bit early if we perform in-place updates of the values in the
	// Bellman-Ford array.)
	public void fw_super_optimized_space()
	{
		int [][] sp = new int[n+1][n+1];

		// Initialize base cases.
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				sp[i][j] = (i == j) ? 0 : matrix[i][j];

		// Floyd-Warshall.
		for (int k = 1; k <= n; k++)
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++)
					sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);

		// Negative cycle detection. This is quite cheeky. You probably shouldn't
		// kill the whole program just because you see a negative cycle.
		for (int i = 1; i <= n; i++)
			if (sp[i][i] < 0)
				panic("Negative cycle detected! The results above are invalid!\n");

		// Print matrix.
		for (int i = 1; i <= n; i++)
		{
			System.out.print(i + ":");
			for (int j = 1; j <= n; j++)
				System.out.printf("%5d%s", sp[i][j], (j == n) ? "\n" : "");
		}
	}


	public static void main(String [] args) throws IOException
	{
		FloydWarshall graph = new FloydWarshall("floyd-warshall-input.txt");

		System.out.println("Floyd-Warshall (DP):\n");
		graph.fw();

		System.out.println("\nFloyd-Warshall (DP w/ Space Optimization):\n");
		graph.fw_optimized_space();

		System.out.println("\nFloyd-Warshall (DP w/ Ultra-Condensed Space):\n");
		graph.fw_super_optimized_space();

		// Expected output for this graph:
		//
		//    1:    0   -1   -2    0
		//    2:    4    0    2    4
		//    3:    5    1    0    2
		//    4:    3   -1    1    0
	}
}
