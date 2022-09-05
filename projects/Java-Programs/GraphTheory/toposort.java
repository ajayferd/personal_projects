// Sean Szumlanski
// COP 3503, Spring 2021

// Plunk this into the Graph.java file where we wrote BFS and DFS, and
// presto-chango: you have the ability to topologically sort any arbitrary graph
// (if a valid topological sort for that graph actually exists).

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

		////////////////////////////////////////////////////////////////////////
		//
		// Alternatively, you could create an ArrayList (a class member) that
		// contains the names of these courses indexed by the node values, and
		// print them out like so:
		//
		// System.out.println(courseNames.get(node));
		//
		// We did that in class, but I leave it to you as an exercise to
		// replicate that functionality.
		//
		////////////////////////////////////////////////////////////////////////

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
