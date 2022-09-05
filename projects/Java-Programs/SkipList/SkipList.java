// Alejandro Fernandez, al720940
// COP3503, Spring 2021
import java.util.*;
import java.lang.*;

class Node<DataType extends Comparable<DataType>>
{
  //  visualization...
  //   List   2  |  | - - - - - - - - - - - - - - > |  |
  //   List   1  |  | - - - - - -> |  | - - - - - > |  |
  //   List   0  |  | - > |  | - > |  | - > |  | -> |  |
  public List<Node<DataType>> nextNodes = new ArrayList<>();
  private DataType data;
  private int dynamicHeight;

  // creates a new (head) node with a specified height > 0
  Node(int height)
  {
    this.dynamicHeight = height;

    // create new null references according to passed height
    for (int i = 0; i < height; i++)
      nextNodes.add(null);
  }

  // creates a new node with given data and a specified height > 0
  Node(DataType data, int height)
  {
    this.dynamicHeight = height;
    this.data = data;

    // create new null references according to passed height
    for (int i = 0; i < height; i++)
      nextNodes.add(null);
  }

  // returns the value stored at this node
  public DataType value()
  {
    return this.data;
  }

  // returns height of this node
  public int height()
  {
    return this.dynamicHeight;
  }

  // returns the next node in the skip list at given level*
  public Node<DataType> next(int level)
  {
    return (level < 0 || level > (dynamicHeight - 1)) ? null : nextNodes.get(level);
  }

  // grow this node by one level
  public void grow()
  {
    nextNodes.add(null);
    dynamicHeight++;
  }

  // half the time grow!
  public void maybeGrow()
  {
    if(Math.random() >= 0.5)
      grow();
  }

  // reduce a node's reference until height is reached, update new height
  public void trim(int height)
  {
    int h = height;
    nextNodes.set(h, null);
    dynamicHeight = height;
  }
}

public class SkipList <DataType extends Comparable<DataType>>
{
  private Node<DataType> head;
  private int nodeCount;
  private int maxLevel;

  // constructor for a new skip list
  SkipList()
  {
    maxLevel = 0;
    nodeCount = 0;

    // create new head node with height of 0
    // create a new null reference
    head = new Node<DataType>(0);
    head.nextNodes.add(null);
  }

  // create a new skip list and initializes the head node to given height
  SkipList(int height)
  {
    maxLevel = height;
    nodeCount = 0;

    // create new head node with given height
    // create a new null reference
    head = new Node<DataType>(height);
    head.nextNodes.add(null);
  }

  // returns number of nodes in skiplist - not including head
  public int size()
  {
    return nodeCount;
  }

  // returns current height of skiplist aka height of head
  public int height()
  {
    return this.maxLevel;
  }

  // returns head of skiplist
  public Node<DataType> head()
  {
    return head;
  }

  // returns reference to node that contains the given data, else return null
  public Node<DataType> get(DataType data)
  {
    // if data is not present in skiplist return null
    if(head.nextNodes.contains(data))
      return null;

    Node<DataType> currentNode = head;
    Node<DataType> foundNode = head;

    // search for location of node through lists
    for (int i = maxLevel - 1; i >= 0; i--)
    {
      // iterate through the list until we reach the null references
      while(currentNode.next(i) != null)
      {
        if (data.compareTo(currentNode.nextNodes.get(i).value()) > 0)
          break;

        // found location of node that contains data, break. return node
        if (data.compareTo(currentNode.nextNodes.get(i).value()) == 0)
        {
          foundNode = currentNode;
          break;
        }

        currentNode = currentNode.nextNodes.get(i);
      }
    }

    return foundNode;
  }
  // insert data into skiplist
  public void insert(DataType data)
  {
    // update counter and generate new height for skiplist
    nodeCount++;
    int level = generateRandomHeight(maxLevel);

    Node<DataType> currentNode = head;
    Node<DataType> newNode = new Node<DataType>(data, level);

    // search for location to insert newly created node
    for(int i = maxLevel - 1; i >= 0; i--)
    {
      // iterate through list before dropping down to next level
      while(currentNode.next(i) != null)
      {
        // locate correct node to stop before inserting
        if (data.compareTo(currentNode.nextNodes.get(i).value()) > 0)
          break;

        currentNode = currentNode.nextNodes.get(i);
      }
      // found correct position and insert new node
      if(level == i)
      {
        head = newNode.next(i);
        return;
      }
    }
  }

  // insert data with node's height set to input
  public void insert(DataType data, int height)
  {
    // update node count and create new node with respect to input
    nodeCount++;

    // check if growing the skiplist is needed
    if(maxLevel > height)
    {
      height = maxLevel;
      growSkipList();
    }
    
    Node<DataType> newNode = new Node<DataType>(data, height);
    Node<DataType> currentNode = head;

    // iterate through list and drop down when appropriate
    for(int i = maxLevel - 1; i >= 0; i--)
    {
      while(currentNode.next(i) != null)
      {
        // locate correct node to stop before inserting
        if (data.compareTo(currentNode.nextNodes.get(i).value()) > 0)
          break;

        currentNode = currentNode.nextNodes.get(i);
      }
      // found correct level and insert new node
      if(height == i)
      {
          head = newNode.next(i);
          return;
      }
    }
  }

  // returns true if value is present in skiplist, else false
  public boolean contains(DataType data)
  {
    Node<DataType> currentNode = head;

    // iterate through list starting from the top and working the way down
    for (int i = maxLevel - 1; i >= 0; i--)
    {
      while(currentNode.nextNodes.get(i) != null)
      {
        // make comparison to each appropriate node to determine if node is found
        if (data.compareTo(currentNode.nextNodes.get(i).value()) > 0)
          break;
        if (data.compareTo(currentNode.nextNodes.get(i).value()) == 0)
          return true;

        currentNode = currentNode.nextNodes.get(i);
      }
    }
    return false;
  }

  // delete a single occurrence of data from the skiplist else do nothing
  public void delete(DataType data)
  {
    // if value is not present do nothing
    if (head.nextNodes.contains(data))
      return;

    Node<DataType> currentNode = head;
    // go through until node is found
    for (int i = maxLevel - 1; i >= 0; i--)
    {
      while(currentNode.nextNodes.get(i) != null)
      {
        if (data.compareTo(currentNode.nextNodes.get(i).value()) > 0)
          break;

        // node position located, move pointer to next location to delete
        if (data.compareTo(currentNode.nextNodes.get(i).value()) == 0)
        {
          currentNode = currentNode.nextNodes.get(i + 1);

          // update counter
          nodeCount--;
          // check if we need to trim after deleting node
          if(Math.log(nodeCount) < maxLevel)
            trimSkipList();

          return;
        }

        currentNode = currentNode.nextNodes.get(i);
      }
    }
  }

  private static int generateRandomHeight(int maxHeight)
  {
    int newHeight = 1;

    // 50/50 to continue to half or return newHeight and newHeight is less than maxHeight
    while ((int)(Math.random() * 2) == 1 && newHeight < maxHeight)
    {
      newHeight++;
    }

    return newHeight;
  }


  // if when inserting we need to increase height to maintain time complexity
  private void growSkipList()
  {
    head.maybeGrow();
  }

  // if when deleting we need to decrease height to maintain time complexity
  private void trimSkipList()
  {
    if(head == null)
      return;

    head.trim(maxLevel);
    maxLevel--;
  }

  public static double difficultyRating()
  {
    return 4.6;
  }
  public static double hoursSpent()
  {
    return 19;
  }
}
