// Sean Szumlanski
// COP 3503, Spring 2021
//
// WeightedGraph.java
// ==================
// This file includes my implementation of Dijkstra's algorithm. I'll be
// honest: I have not vetted this carefully. Let me know if you find bugs.
//
// The constructor is a variation on the constructor we saw from Graph.java
// last week, which only worked with unweighted graphs.


import java.util.*;
import java.io.*;

// This Vertex class allows us to pair vertex IDs and distance values so they
// can be plunked into a minheap.
class Vertex implements Comparable<Vertex>
{
	int id, dist;

	Vertex(int id, int dist)
	{
		this.id = id;
		this.dist = dist;
	}

	public int compareTo(Vertex v)
	{
		return this.dist - v.dist;
	}

	public String toString()
	{
		return "Cost of shortest path to vertex " + (char)(this.id + 'A') + ": " + this.dist;
	}
}

public class WeightedGraph
{
	private int [][] matrix;
	int n;

	static final int oo = (int)1e9;

	// Initialize 'matrix' from input file.
	public WeightedGraph(String filename) throws Exception
	{
		Scanner in = new Scanner(new File(filename));
		n = in.nextInt();
		
		matrix = new int[n][n];
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				matrix[i][j] = in.nextInt();
	}

	// Prints the length of the shortest path from 'source' to each other vertex
	// in the graph. Assumes non-negative edge weights.
	void runDijkstra(int source)
	{
		int [] dist = new int[n];
		boolean [] visited = new boolean[n];
		int numVisited = 0;

		// Java's PriorityQueue container is essentially a minheap.
		PriorityQueue<Vertex> minheap = new PriorityQueue<Vertex>();

		Arrays.fill(dist, oo);
		dist[source] = 0;

		for (int i = 0; i < n; i++)
			minheap.add(new Vertex(i, dist[i]));

		while (!minheap.isEmpty() && numVisited < n)
		{
			// Find the vertex with the smallest distance associated with it.
			Vertex v = minheap.remove();
			if (visited[v.id]) continue;

			visited[v.id] = true;
			System.out.println(v);
			numVisited++;

			for (int i = 0; i < n; i++)
			{
				// Note: Checking for !visited[i] here is actually not necessary. Can you see why?
				if (matrix[v.id][i] > 0 && !visited[i] && dist[v.id] + matrix[v.id][i] < dist[i])
				{
					// This is a bit lazy. A vertex might be thrown into the
					// heap multiple times. This is a fairly cheap operation,
					// though, and the duplicates don't affect our solution
					// because the ones with higher dist values will be
					// suppressed, while the once with lower dist values will
					// percolate to the top of the heap. Once each vertex has
					// been visited, we'll just stop coming into the while-loop,
					// and all those duplicates will be ignored.

					dist[i] = dist[v.id] + matrix[v.id][i];
					minheap.add(new Vertex(i, dist[i]));
				}
			}
		}

		// With the way we've coded this up, this condition will never be true.
		// Can you see why?
		if (numVisited != n)
			System.out.println("Oh no! You fed a disconnected graph to Dijkstra!");
	}

	public static void main(String [] args) throws Exception
	{
		WeightedGraph g = new WeightedGraph("dijkstra-graph.txt");
		g.runDijkstra(0);
	}
}
