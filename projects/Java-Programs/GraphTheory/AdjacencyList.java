import java.util.*;

// delcare class graph for use
public class AdjacencyList
{
  int vertex;
  ArrayList<ArrayList<Integer>> adjacencyLists;

  // Initialize the linked lists and build for each vertex
  public AdjacencyList(int vertex)
  {
    this.vertex = vertex;
    adjacencyLists = new ArrayList<ArrayList<Integer>>(vertex);
    for (int i = 0; i < vertex; i++)
      adjacencyLists.add(new ArrayList<Integer>());
  }

  public void addEdge(int source, int destination)
  {
    // add edge
    adjacencyLists.get(source).add(destination);
    // add back edge (for undirected)
    // adjacencyLists.get(destination).add(source);
  }

  public void printGraph()
  {
    for (int i = 0; i < adjacencyLists.size(); i++)
    {
        System.out.print("Vertex " + i + " is connected to: ");
        for (int j = 0; j < adjacencyLists.get(i).size(); j++)
            System.out.print(adjacencyLists.get(i).get(j) + " ");
        System.out.println();
    }
  }

  public static void main(String [] args)
  {
    AdjacencyList graph = new AdjacencyList(6);
    graph.addEdge(0, 1);
    graph.addEdge(0, 4);
    graph.addEdge(1, 2);
    graph.addEdge(1, 3);
    graph.addEdge(1, 4);
    graph.addEdge(2, 3);
    graph.addEdge(4, 2);
    graph.addEdge(5, 1);
    graph.addEdge(5, 2);
    graph.printGraph();
  }
}
