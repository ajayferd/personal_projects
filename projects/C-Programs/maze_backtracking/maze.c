// Sean Szumlanski
// COP 3503, Spring 2021
//
// This is adapted from my COP 3502 course (hence the use of C instead of Java).

// maze.c
// ======
// This program reads an ASCII maze from an input file and uses backtracking to
// help guide a person (signified by a '@' symbol) to the maze's exit (signified
// by the 'e' symbol).
//
// Try resizing your terminal so you can only see one maze at a time, and it
// will look like the output is animated. :)
//
// NOTE: maze4.txt demonstrates the fact that a DFS backtracking approach will
//       always find a solution, but it won't always be the best/shortest path
//       to the goal. :)


#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Number of seconds to wait between calls to print_maze(). This affects how
// fast the "animation" effect works.
#define DEFAULT_WAIT_TIME 0.5


// Do nothing for wait_time_in_seconds number of seconds.
void wait(double wait_time_in_seconds)
{
	clock_t start_ticks, end_ticks;

	start_ticks = clock();
	end_ticks = start_ticks + CLOCKS_PER_SEC * wait_time_in_seconds;

	while (clock() < end_ticks)
		;
}

// Print the maze. This function call is best followed by a call to wait(), so
// there's a moment's pause before printing the maze again. That's where the
// faux "animation" effect comes from when running this program.
//
// The 'searching' parameter indicates whether we are exploring a new space in
// the maze, backtracking from a dead end, or exiting the maze.
void print_maze(char **maze, int height, int width, int searching)
{
	int i, j;

	printf("\n");
	if (searching == 1)
		printf("Searching...\n");
	else if (searching == 2)
		printf("Exit found!\n");
	else
		printf("Backtracking...\n");

	for (i = 0; i < height; i++)
		for (j = 0; j < width; j++)
			printf("%c%s", maze[i][j], (j == width - 1) ? "\n" : "");
}

// Read an ASCII maze from a file. See the attached files for the expected
// format. The recognized characters are:
//
//   '@' - person (there should be exactly one of these per maze)
//   '#' - wall
//   ' ' - a space where the person can walk
//   'e' - exit (there can be multiple exits or none at all)
//
// This function returns 0 if it fails to open the input file or if it fails to
// find the '@' symbol in the maze. Otherwise, it returns 1 to indicate that
// the maze was read successfully. It doesn't have a lot of error checking.
int read_maze(char *filename, char ***maze, int *height, int *width,
              int *start_i, int *start_j)
{
	int i, j;
	FILE *ifp = fopen(filename, "r");

	// Initialize starting coordinates to something invalid. If these don't get
	// updated before the function terminates, we know we failed to find our
	// person ('@') in the maze.
	*start_i = *start_j = -1;

	if (ifp == NULL)
		return 0;

	// The input file starts with the height and width of the maze.
	fscanf(ifp, "%d %d", height, width);

	// Allocate space for the 2D maze. Because 'maze' is a pointer to the
	// (char **) that we declared in main(), we have to dereference it to go to
	// that (char **), and then we can create (and access) the array we need
	// here.
	*maze = malloc(sizeof(char *) * *height);
	for (i = 0; i < *height; i++)
		(*maze)[i] = malloc(sizeof(char) * *width);

	// Read the maze from the text file.
	for (i = 0; i < *height; i++)
	{
		for (j = 0; j < *width; j++)
		{
			fscanf(ifp, "%c", &((*maze)[i][j]));

			// If we encounter end-of-line characters, move on to the next
			// character. We can't use our " %c" trick with scanf() to ignore all
			// whitespace characters, because there are ' ' characters in the input
			// file that we do want to read.
			//
			// The inclusion of '\r' here is to make this program Windows friendly,
			// but I haven't tested it on Windows. I hope it works. :)
			while (((*maze)[i][j] == '\n') || ((*maze)[i][j] == '\r'))
				fscanf(ifp, "%c", &((*maze)[i][j]));

			// Keep track of the location of the '@' character in the maze.
			if ((*maze)[i][j] == '@')
			{
				*start_i = i;
				*start_j = j;
			}
		}
	}

	fclose(ifp);

	// If we failed to find the '@' character, return 0 to indicate failure.
	if (*start_i == -1)
		return 0;

	return 1;
}

// Destroy the dynamically allocated 2D maze.
char **destroy_maze(char **maze, int height, int width)
{
	int i;

	for (i = 0; i < height; i++)
		free(maze[i]);
	free(maze);

	return NULL;
}

// Take a maze and returns a 'visited' array of the same dimensions. Initializes
// all walls to 1 (visited), and initializes the place where the person ('@') is
// standing to 1 as well. All other spaces are unvisited (0).
char **make_visited_array(char **maze, int height, int width)
{
	int i, j;
	char **visited;

	// Create 2D char array. If you want to be really safe, check whether these
	// malloc() calls return NULL.
	visited = malloc(sizeof(char *) * height);
	for (i = 0; i < height; i++)
		visited[i] = malloc(sizeof(char) * width);

	// Notice that I'm using a char array for these zeros and ones. Recall that
	// all chars have an underlying integer representation in C, so this is okay.
	// I'm actually saving a lot of space here, since a char is just 1 byte, but
	// an int would be (on most systems) 4 bytes.
	for (i = 0; i < height; i++)
		for (j = 0; j < width; j++)
			visited[i][j] = ((maze[i][j] == '#') || (maze[i][j] == '@')) ? 1 : 0;

	return visited;
}

// Determine whether i and j are in bounds for the 2D array, given the height
// and width of that array.
int in_bounds(int height, int width, int i, int j)
{
	return ((i >= 0) && (i < height) && (j >= 0) && (j < width));
}

// A function to flash a '*' symbol when the person ('@') reaches the exit ('e').
void win(char **maze, int height, int width, int i, int j, double wait_time)
{
	int w, num_widgets = 17;

	char widgets[] = {'|', '/', '-', '\\', '|', '/', '-', '\\',
	                  '|', '/', '-', '\\', '|', '/', '-', '\\', '|'};

	for (w = 0; w < num_widgets; w++)
	{
		maze[i][j] = widgets[w];
		print_maze(maze, height, width, 2);
		wait(0.0833);
	}

	maze[i][j] = 'e';
	print_maze(maze, height, width, 2);
	wait(wait_time);
}

// Recursive backtracking function. Returns 1 if the exit was found, 0 otherwise.
int maze_runner(char **maze, char **visited, int height, int width, int i, int j, double wait_time)
{
	int new_i, new_j, m;

	// You can set this to ' ' if you don't want to leave a visual trail of
	// breadcrumbs to show where the character has been.
	char breadcrumb = '.';

	// When we covered this in class, I started by having four separate
	// if-statements to handle the four moves that are possible (left, right, up,
	// and down), and then refined the code to use this array to manage the
	// possible moves instead. This is a very common and useful trick for this
	// sort of thing.
	int moves[4][2] = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };

	// Start by printing the maze with a small delay before we make a move.
	print_maze(maze, height, width, 1);
	wait(wait_time);

	// If we found the exit, call the win "animation" function, then return 1.
	if (visited[i][j] == 'e')
	{
		win(maze, height, width, i, j,  wait_time);
		return 1;
	}

	// This for-loop handles the four directions we can move in. If we find the
	// exit at some point, we'll return 1 without moving on to any remaining
	// iterations of this loop.
	for (m = 0; m < 4; m++)
	{
		// Calculate the coordinate we're moving to.
		new_i = i + moves[m][0];
		new_j = j + moves[m][1];

		// Ensure the new coordinates are within bounds for the 2D array.
		if (!in_bounds(height, width, new_i, new_j))
			continue;

		// Don't go somewhere we've already been before! Otherwise, you could get
		// stuck in an infinite loop, going back and forth between the same couple
		// of cells in the maze. (Try commenting out these lines and seeing what
		// effect it has on the program, as we did in class.)
		if (visited[new_i][new_j] == 1)
			continue;

		// Before moving to the next position, we'll mark it as visited so that we
		// never try to visit it again. I'm also tracking the exit here, because
		// if the person moves onto the exit, we will overwrite the 'e' with the
		// '@' symbol for display purposes.
		if (maze[new_i][new_j] == 'e')
			visited[new_i][new_j] = 'e';
		else
			visited[new_i][new_j] = 1;

		// Move person marker to new position.
		maze[i][j] = breadcrumb;
		maze[new_i][new_j] = '@';

		// Make recursive call.
		if (maze_runner(maze, visited, height, width, new_i, new_j, wait_time))
			return 1;

		// On failure, the person returns to the current position.
		maze[new_i][new_j] = breadcrumb;
		maze[i][j] = '@';

		// When we return from a recursive call, print maze and wait a moment.
		// The last parameter (0) indicates that we are not in "search" mode; we
		// are in "backtracking" mode. (This is printed to the screen.)
		print_maze(maze, height, width, 0);
		wait(wait_time);
	}

	return 0;
}

int main(int argc, char **argv)
{
	int height, width, start_i, start_j;
	char **maze = NULL, **visited = NULL;
	double wait_time;

	// You must provide the name of an input file at the command line.
	if (argc < 2)
	{
		printf("Proper syntax: %s <filename>\n", argv[0]);
		return 1;
	}

	// Attempt to read maze from input file.
	if (!read_maze(argv[1], &maze, &height, &width, &start_i, &start_j))
	{
		printf("Failed to read maze from file: %s\n", argv[1]);
		return 1;
	}

	// Parse wait time if specified at command line. Otherwise, go with default.
	wait_time = (argc > 2) ? atof(argv[2]) : DEFAULT_WAIT_TIME;

	// Create 'visited' array and kick off backtracking.
	visited = make_visited_array(maze, height, width);
	maze_runner(maze, visited, height, width, start_i, start_j, wait_time);

	// Memory management.
	maze = destroy_maze(maze, height, width);
	visited = destroy_maze(visited, height, width);

	return 0;
}
