// Alejandro Fernandez, al720940
// COP3503, Spring 2021

import java.util.*;
import java.io.*;

public class RunLikeHell
{
  // wrapper method that calls the recursive version of maxGainRecursive
  public static int maxGainRecursive(int [] blocks)
  {
    return maxGainRecursive(blocks, 0);
  }

  // recursively calls the method to find the best way to collect a max of points,
  // serves as a starting place to transform into an iterative DP approach
  private static int maxGainRecursive(int [] blocks, int k)
  {
    // base case that prevents out of bounds
    if (blocks.length - k <= 0) return 0;

    // either we take the current block or we move to the next one and return the max value
    return Math.max(maxGainRecursive(blocks, k + 2) + blocks[k],
      maxGainRecursive(blocks, k + 1));
  }

  // wrapper method that calls the memoization version of maxGainMemoization
  public static int maxGainMemoization(int [] blocks)
  {
    HashMap<Integer, Integer> memoization = new HashMap<>(blocks.length);
    return maxGainMemoization(blocks, 0, memoization);
  }

  // recursively calls the memoization method with a HashMap for reduced computation and
  // storing previous values, in addition; very similar to 0-1 Knapsack approach from Dr. Szum
  private static int maxGainMemoization(int [] blocks, int k, HashMap<Integer, Integer> map)
  {
    // base case the prevents out of bounds
    if (blocks.length - k <= 0) return 0;

    // check if result is present in Hashmap, if not, this will be null
    Integer max = map.get(k);
    if (max != null) return max;

    // take maximum of two numbers in different sequenital indexes
    max = Math.max(maxGainMemoization(blocks, k + 2, map) + blocks[k],
      maxGainMemoization(blocks, k + 1, map));

    // Memoize maxiumum before returning
    map.put(k, max);
    return max;
  }

  // returns the maximum knowledge from a given positive integer array
  // while using iterative DP
  public static int maxGainDP(int [] blocks)
  {
    // checking base cases to prevent needless calculations
    // if we have an empty array just return 0
    if (blocks.length == 0) return 0;

    // if we have an array with 1 element, return that element
    else if (blocks.length == 1) return blocks[0];

    // if we have 2 elements, just return the max of the two
    else if (blocks.length == 2) return Math.max(blocks[0], blocks[1]);

    // if we have 3 elements given, return the max of the 2nd element or the 1st + the 3rd
    else if (blocks.length == 3) return Math.max(blocks[1], blocks[0] + blocks[2]);

    else
    {
      // declare new array to hold the previous max values
      int [] maxBlocks = new int [blocks.length + 4];

      // fill the new array with the maximum of taking or not taking the current block
      for (int i = 2; i < blocks.length + 2; i++)
        maxBlocks[i] = Math.max(maxBlocks[i - 2] + blocks[i - 2], maxBlocks[i - 1]);

      // returns the max value
      return maxBlocks[blocks.length + 1];
    }
  }

  // returns the maximum knowledge from a given positive integer array
  // while using awesome iterative DP
  public static int maxGain(int [] blocks)
  {
    // checking base cases to prevent needless calculations
    // if we have an empty array just return 0
    if (blocks.length == 0) return 0;

    // if we have an array with 1 element, return that element
    else if (blocks.length == 1) return blocks[0];

    // if we have 2 elements, just return the max of the two
    else if (blocks.length == 2) return Math.max(blocks[0], blocks[1]);

    // if we have 3 elements given, return the max of the 2nd element or the 1st + the 3rd
    else if (blocks.length == 3) return Math.max(blocks[1], blocks[0] + blocks[2]);

    else
    {
      // declare new array to hold the previous max values
      int [] maxBlocks = new int [4];

      // fill the new array with the maximum of taking or not taking current block.
      // mod 3 allows us to use less space since we only ever need the 3 most recent elements
      for (int i = 2; i < blocks.length + 2; i++)
        maxBlocks[i % 3] = Math.max(maxBlocks[(i - 2) % 3] + blocks[i - 2],
          maxBlocks[(i - 1) % 3]);

      // returns the max value
      return maxBlocks[(blocks.length + 1) % 3];
    }
  }

  public static double hoursSpent()
  {
    return 2.25;
  }

  public static double difficultyRating()
  {
    return 3;
  }
}
