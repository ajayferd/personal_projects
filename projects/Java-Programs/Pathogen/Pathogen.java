// Alejandro Fernandez, al720940
// COP 3503, Spring 2021

import java.io.*;
import java.util.*;

public class Pathogen
{
	// Used to toggle "animated" output on and off (for debugging purposes).**
	private static boolean animationEnabled = false;

	// "Animation" frame rate (frames per second).
	private static double frameRate = 4.0;
	public static int count = 0;

	// Setters. Note that for testing purposes you can call enableAnimation()
	// from your backtracking method's wrapper method (i.e., the first line of
	// your public findPaths() method) if you want to override the fact that the
	// test cases are disabling animation. Just don't forget to remove that
	// method call before submitting!
	public static void enableAnimation() { Pathogen.animationEnabled = true; }
	public static void disableAnimation() { Pathogen.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathogen.frameRate = fps; }
	public static int getCount() {return Pathogen.count;}
	// Maze constants.
	private static final char WALL       = '#';
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char CORONA     = '*';
	private static final char BREADCRUMB = '.';  // visited
	private static final char SPACE      = ' ';  // unvisited

	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, and it's not a wall).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	                                   int row, int col, int height, int width)
	{
		// check if either index for row and col go out of bounds
		if(row < 0 || col < 0 || row >= height || col >= width)
			return false;

		if (maze[row][col] == WALL || visited[row][col] == BREADCRUMB || maze[row][col] == CORONA)
			return false;

		return true;
	}

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

	// takes in a given maze, collects information, then passes it to a recursive method
	// of the same name that finds all valid paths
	public static HashSet<String> findPaths(char [][] maze)
	{
		// initialize variables
		int height = maze.length;
		int width = maze[0].length;
		StringBuilder newPath = new StringBuilder();
		HashSet<String> newSet = new HashSet<String>();
		char [][] visitedStates = new char[height][width];

		for (int i = 0; i < height; i++)
			Arrays.fill(visitedStates[i], SPACE);

		// Find starting position (location of the '@' character).
		int startRow = -1;
		int startCol = -1;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (maze[i][j] == PERSON)
				{
					startRow = i;
					startCol = j;
				}
			}
		}
		// after getting necessary information we begin the recursive call
		return findPaths(maze, visitedStates, startRow, startCol, height, width, newPath, newSet);
	}

	// finds all valid paths in a passed maze at most once and returns a HashSet
	// filled with solution strings
	private static HashSet<String> findPaths(char [][] maze, char [][] visited,
	                                 int currentRow, int currentCol,
	                                 int height, int width, StringBuilder path, HashSet<String> set)
	{
		// base case, check if we found a solution
		if (visited[currentRow][currentCol] == 'e')
		{
			// deletes last space in String and adds it to Hashset
			path.setLength(path.length() - 1);
			set.add(path.toString());
			path.append(" ");
			count++;
			return set;
		}
		int exitRow = -1;
		int exitCol = -1;
		int [][] possibleMoves = new int[][]
		// left		right			up		down
		{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

		for (int i = 0; i < possibleMoves.length; i++)
		{
			// generate new moves
			int newRow = currentRow + possibleMoves[i][0];
			int newCol = currentCol + possibleMoves[i][1];

			// check if move is safe and valid
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
				continue;

			// generate appropriate characters for path
			switch (i)
			{
				case 0 :
				{
					// left
					path.append("l ");
					break;
				}

				case 1 :
				{
					// right
					path.append("r ");
					break;
				}
				case 2 :
				{
					// up
					path.append("u ");
					break;
				}
				case 3 :
				{
					// down
					path.append("d ");
					break;
				}
			}

			// mark exit to maze to investiage other paths
			if (maze[newRow][newCol] == EXIT)
			{
				visited[newRow][newCol] = EXIT;
				exitRow = newRow;
				exitCol = newCol;
			}

			// mark progress within maze
			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;
			maze[newRow][newCol] = PERSON;

			// recursive descent with new state
			findPaths(maze, visited, newRow, newCol, height, width, path, set);

			// undo state changes
			maze[newRow][newCol] = SPACE;
			maze[currentRow][currentCol] = PERSON;
			visited[currentRow][currentCol] = SPACE;

			// if we reach a deadend, remove the steps taken
			if (path.length() >= 2)
				path.delete(path.length() - 2, path.length());
		}

		// clean up crumbs and put the exit back, leave maze as passed in
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[0].length; j++)
				if (maze[i][j] == BREADCRUMB)
					maze[i][j] = SPACE;
		}
		if (isLegalMove(maze, visited, exitRow, exitCol, height, width))
			maze[exitRow][exitCol] = 'e';

		// return the contents of the Hashset
		return set;
	}

	public static double difficultyRating()
	{
		return 3.12;
	}

	public static double hoursSpent()
	{
		return 7;
	}
}
