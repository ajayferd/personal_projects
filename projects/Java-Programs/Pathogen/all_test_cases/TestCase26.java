// Sean Szumlanski
// COP 3503, Spring 2021

// =========================
// Pathogen: TestCase26.java
// =========================
// Checks paths through maze23.txt. Also checks whether the maze array is
// unmodified after calling the findPaths() method.
//
// There are five copies of this test case (to increase the number of points
// awarded for ensuring the maze array is not modified upon returning from the
// findPaths() method).


import java.io.*;
import java.util.*;

public class TestCase26
{
	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above.
	private static char [][] readMaze(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		// After reading the integers, there's still a new line character we
		// need to do away with before we can continue.

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}

	// Read solution strings (representing paths through the maze) from input
	// file and return HashSet containing all such strings.
	private static HashSet<String> readSolutions(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));
		HashSet<String> solutions = new HashSet<>();

		while (in.hasNextLine())
			solutions.add(in.nextLine());

		return solutions;
	}

	// Checks whether two 2D char arrays have the same contents. Returns true
	// if so, false otherwise. Assumes both arrays are non-null, non-empty,
	// non-jagged (i.e., they're rectangular), and that they both have the same
	// dimensions.
	private static boolean samesies(char [][] m1, char [][] m2)
	{
		int numRows = m1.length;
		int numCols = m1[0].length;

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
				if (m1[i][j] != m2[i][j])
					return false;

		return true;
	}

	public static void main(String [] args) throws IOException
	{
		// Load maze and solution paths from file.
		char [][] maze = readMaze("input_files/maze23.txt");
		HashSet<String> solutionPaths = readSolutions("input_files/maze23-all-paths.txt");

		// Call backtracking algorithm to find all paths through maze.
		Pathogen.disableAnimation();
		HashSet<String> yourPaths = Pathogen.findPaths(maze);

		// Check for success. Print set contents if sets differ.
		if (yourPaths.equals(solutionPaths))
		{
			System.out.println("Hooray! Found " + yourPaths.size() + " distinct path(s)!");
		}
		else
		{
			System.out.println("Whoops! Those string sets differ!");

			System.out.println();

			System.out.println("======================");
			System.out.println("Expected Path Strings:");
			System.out.println("======================");
			for (String s : solutionPaths)
				System.out.println(s);

			System.out.println();

			System.out.println("======================");
			System.out.println("Returned Path Strings:");
			System.out.println("======================");
			for (String s : yourPaths)
				System.out.println(s);
		}

		// Let's get a pristine copy of that input maze.
		char [][] originalMaze = readMaze("input_files/maze23.txt");

		if (samesies(maze, originalMaze))
			System.out.println("Hooray! The maze remains uncorrupted.");
		else
			System.out.println("Whoops! Looks like the maze got corrupted.");
	}
}
