// Alejandro Fernandez, al720940
// COP 3503, Spring 2021

import java.lang.*;
import java.util.*;

public class SneakyKnights
{
  public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {
    // create HashSet, add knight locations
    HashSet<String> knightLocation = new HashSet<String>(coordinateStrings.size());
    for (int i = 0; i < coordinateStrings.size(); i++)
    {
      knightLocation.add(coordinateStrings.get(i));
    }

    return checkKnightMoves(knightLocation);
  }
  public static boolean checkKnightMoves(HashSet<String> knightLocation)
  {
    // locations a knight can attack from given coordinate pair
    int knightRows[] = {-2, -2, -1, -1, 1, 1, 2, 2};
    int knightCols[] = {-1, 1, -2, 2, -2, 2, -1, 1};

    // iterate through knights and check the 8 locations it can attack
    for (String knight : knightLocation)
    {
      for (int j = 0; j < knightRows.length; j++)
      {
        // if the HashSet contains a knight in danger return false
        if (knightLocation.contains(possibleMoves(knight, knightCols[j], knightRows[j])))
          return false;
      }
    }

    return true;
  }
  public static String possibleMoves(String coordinate, int columnOffset, int rowOffset)
  {
    // declare StringBuilder of size of coordinate
    StringBuilder newRow = new StringBuilder();
    StringBuilder newCol = new StringBuilder();
    int startOfNums = 0, endOfNums = 0, columnAddress = 0, indexSum = 0;
    for (int i = 0; i < coordinate.length(); i++)
    {
      // if the coordinate is a letter aka column
      if (Character.isLetter(coordinate.charAt(i)))
      {
        // append letters to stringbuilder while calculating the indexsum for column
        startOfNums = i;
        newCol.append(coordinate.charAt(i));
        indexSum = indexSum * 26 + (coordinate.charAt(i) - ('a' - 1));
      }
      // no more letters to read in
      else break;
    }
    // add offset to indexSum
    indexSum += columnOffset;

    // change the last letter accordingly from offset
    char indexQuotient = (char)(indexSum % 26 + 'a' - 1);

    newCol.replace(startOfNums, newCol.length(), Character.toString(indexQuotient));

    // create a number stringbuilder
    for (int j = startOfNums + 1; j < coordinate.length(); j++)
    {
      newRow.append(coordinate.charAt(j));
      endOfNums++;
    }

    // insert new row address into the row stringbuilder
    int rowAddress = Integer.parseInt(newRow.toString()) + rowOffset;
    newRow.replace(0, endOfNums, Integer.toString(rowAddress));

    // append both the column and row stringbuilders into one string to return
    newCol.append(newRow);
    return newCol.toString();
  }

  public static double difficultyRating()
  {
    return 4;
  }

  public static double hoursSpent()
  {
    return 17.1;
  }
}