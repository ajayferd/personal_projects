// Alejandro Fernandez al720940
// COP 3503, Spring 2021

import java.util.*;
import java.io.*;

public class ConstrainedTopoSort
{
  boolean [][] adjacencyMatrix;
  boolean cycleExists = false;
  // example of input file
  // 4
  // 1 3
  // 2 3 4
  // 1 4
  // 0
  // inspired from Dr. Szumlanski's Graph.java
  public ConstrainedTopoSort(String filename) throws IOException
  {
    // takes in filename and total number of vertices
    Scanner input = new Scanner(new File(filename));
    int numOfVertices = input.nextInt();

    // initializes matrix representation from input
    adjacencyMatrix = new boolean[numOfVertices][numOfVertices];
    for (int source = 0; source < numOfVertices; source++)
    {
      int numOfEdges = input.nextInt();
      for (int j = 0; j < numOfEdges; j++)
      {
        int destination = input.nextInt();
        adjacencyMatrix[source][destination - 1] = true;
      }
    }
  }

  // using similar logic found in detectCycle to take in 2 vertices and determine
  // if x can come before y in a valid topological sort
  public boolean hasConstrainedTopoSort(int x, int y)
  {
    if (x == y)
      return false;

    // check for any cycles within matrix representation
    // if none are present, we can safely sort topologically
    detectCycle();
    if (cycleExists)
      return false;

    Queue<Integer> q = new ArrayDeque<>();
    HashSet<Integer> checkedValues = new HashSet<>();

    // add x to hashset and queue
    checkedValues.add(x);
    q.add(x);

    while (!q.isEmpty())
    {
      x = q.remove();

      // return false if x does not come before y
      if (adjacencyMatrix[y - 1][x - 1] == true)
        return false;

      // continue to add vertices to the queue if they lead to x
      for (int source = 1; source <= adjacencyMatrix.length; source++)
      {
        if (!checkedValues.contains(source))
        {
          if (adjacencyMatrix[source - 1][x - 1] == true)
          {
            q.add(source);
          }
        }

      }

    }
    // after going through every edge we can determine that x can come before y
    return true;
  }

  // checks if graph can be sorted topologically
  // heavily inspired from Dr. Szumlanski's Graph.java
  public void detectCycle()
  {
    // count number of incoming edges incident for every vertex
    int incomingEdges[] = new int[adjacencyMatrix.length];
    int uniqueVertces = 0;

    for (int i = 0; i < adjacencyMatrix.length; i++)
      for (int j = 0; j < adjacencyMatrix.length; j++)
        incomingEdges[j] += (adjacencyMatrix[i][j] ? 1 : 0);

    Queue<Integer> q = new ArrayDeque<Integer>();
    // Any vertex with zero incoming edges are added to the queue
    for (int w = 0; w < adjacencyMatrix.length; w++)
      if (incomingEdges[w] == 0)
        q.add(w);

    while (!q.isEmpty())
    {
      // pull vertices out of queue and count how many unique values are present
      int vertex = q.remove();
      uniqueVertces++;

      // add every vertex we can reach from an edge by the current vertex
      // while decrementing the ones with the incoming edges and adding them to q
      for (int i = 0; i < adjacencyMatrix.length; i++)
        if (--incomingEdges[i] == 0 && adjacencyMatrix[vertex][i])
          q.add(i);
    }

    // if we come out of our loop without inluding every vertex, there must be a
    // cycle present in graph so return false
    if (uniqueVertces != adjacencyMatrix.length)
      cycleExists = true;
  }

  public static double difficultyRating()
  {
    return 2.1;
  }

  public static double hoursSpent()
  {
    return 4;
  }
}
