// Sean Szumlanski
// COP 3503, Spring 2021

// Graph.java
// ==========
// This is a framework for initializing a graph from an input file that contains
// an adjacency matrix. The first line of the input file indicates how many
// vertices are in the graph. The following n lines contain the adjacency matrix
// for the (zeros and ones). For example:
//
//    4
//    0 1 0 1
//    1 0 1 1
//    0 1 0 1
//    1 1 1 0
//
// ...which corresponds to the unweighted, undirected graph:
//
//    0---1
//     \ /|
//      X |
//     / \|
//    2---3
//
// These values are read into a boolean matrix, and we perform breadth-first and
// depth-first search traversals starting from an arbitrary vertex.
//
// The comments in this file are sparse in some places because I've already
// explained this code in class. In other places, the comments are unusually
// verbose because I want you to have a written copy of some of the comments I
// made about this code. In your programming assignments, please do not emulate
// the unbalanced commenting style I am employing here. :)


import java.util.*;
import java.io.*;

public class Graph
{
	boolean [][] matrix;

	// Initialize 'matrix' from input file
	public Graph(String filename) throws Exception
	{
		Scanner in = new Scanner(new File(filename));
		int N = in.nextInt();

		matrix = new boolean[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				matrix[i][j] = (in.nextInt() == 1);
	}

	public void topoSort()
	{
		int [] incoming = new int[matrix.length];
		int cnt = 0;
		// Count the number of incoming edges incident to each vertex. For sparse
		// graphs, this could be made more efficient by using an adjacency list.
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				incoming[j] += (matrix[i][j] ? 1 : 0);

		Queue<Integer> q = new ArrayDeque<Integer>();
		// Any vertex with zero incoming edges is ready to be visited, so add it to
		// the queue.
		for (int i = 0; i < matrix.length; i++)
			if (incoming[i] == 0)
				q.add(i);

		while (!q.isEmpty())
		{
			// Pull a vertex out of the queue and add it to the topological sort.
			// Note: If you change this to an ArrayList, you can shuffle it using
			// Collections.shuffle() before removing the next node. That will allow
			// you to produce different valid topological sorts (assuming there is
			// more than one valid topological sort for the graph).

			int node = q.remove();
			System.out.println(node);
			//////////////////////////////////////////////////////////////////////////// Alternatively, you could create an ArrayList (a class member) that
			// contains the names of these courses indexed by the node values, and
			// print them out like so:
			//// System.out.println(courseNames.get(node));
			//// We did that in class, but I leave it to you as an exercise to// replicate that functionality.
			//////////////////////////////////////////////////////////////////////////
			// Count the number of unique vertices we see.

			++cnt;

			// All vertices we can reach via an edge from the current vertex should
			// have their incoming edge counts decremented. If one of these hits
			// zero, add it to the queue, as it's ready to be included in our
			// topological sort.
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && --incoming[i] == 0)
				q.add(i);
		}
		// If we pass out of the loop without including each vertex in our
		// topological sort, we must have a cycle in the graph.
		if (cnt != matrix.length)
		System.out.println("Error: Graph contains a cycle!");
	}



	// Iterative BFS method.
	public void BFS(int start)
	{
		// Iterative BFS places vertices in a queue. When we pull a vertex out of
		// the queue, we process it (in this case, print it to the screen), then
		// place all its unvisited neighbors in the queue. We mark each vertex as
		// visited as it goes into the queue, because that ensures we never place
		// a vertex into the queue more than once. (That can significantly reduce
		// the space complexity of this algorithm when dealing with a large, dense
		// graph.)

		// A queue is an abstract data type. We need to decide what underlying
		// data structure to use to implement it. Java will not allow you to do,
		// e.g.: Queue<Integer> q = new Queue<Integer>();
		Queue<Integer> q = new LinkedList<Integer>();
		boolean [] visited = new boolean[matrix.length];

		// Start by adding the start vertex to the queue. It will be the first
		// thing dequeued, at which point we'll print it and add its neighbors
		// to the queue.
		q.add(start);
		visited[start] = true;

		while (!q.isEmpty())
		{
			// Remove a node from the queue and process it. If we were searching
			// for a particular node (a "goal"), this is where we would check
			// whether we had found it. If so, we would terminate the BFS. However,
			// since the goal of this BFS method is simply to print all nodes of a
			// graph in BFS order, we simply print this node and move on.
			int node = q.remove();
			System.out.println(node);

			// Add all neighbors of 'node' to the queue (as long as they haven't
			// been visited already).
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && !visited[i])
				{
					visited[i] = true;
					q.add(i);
				}
		}
	}

	// Wrapper to our recursive DFS method. This one sets up the 'visited' array.
	public void DFS(int start)
	{
		// Recall that I mentioned the Arrays.fill() method here. If we want to
		// fill a boolean array with all 'true' values, we could use, e.g.,
		// Arrays.fill(myArray, true).
		boolean [] visited = new boolean[matrix.length];
		DFS(start, visited);
	}

	private void DFS(int node, boolean [] visited)
	{
		// As soon as we encounter a node in our DFS traversal, we mark it as
		// visited. This ensures that we won't get stuck in an infinite loop if
		// our graph has a cycle.
		visited[node] = true;

		// If we were searching for a particular vertex (a "goal"), this is where we
		// would check whether we have found the goal. If so, we would terminate the
		// DFS. However, since the purpose of this particular method is simply to
		// print all vertices in DFS order, we just print this vertex and move on.
		System.out.println(node);

		// Now call DFS() on all of this node's neighbors that haven't already
		// been visited.
		for (int i = 0; i < matrix.length; i++)
			if (matrix[node][i] && !visited[i])
				DFS(i, visited);
	}

	public static void main(String [] args) throws Exception
	{
		Graph g = new Graph("g1.txt");
		System.out.println("BFS(0):"); g.BFS(0);
		System.out.println("BFS(0):"); g.BFS(0);
		System.out.println("BFS(0):"); g.BFS(2);
		System.out.println("DFS(0):"); g.DFS(0);
		System.out.println("DFS(3):"); g.DFS(3);
	}
}
