// Alejandro Fernandez, al720940
// COP 3503, Spring 2021

import java.lang.*;
import java.util.*;

public class SneakyQueens
{
  public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
  {
    // check if the ArrayList has contents before continuing
    if (coordinateStrings.isEmpty())
      return false;

    // check to see if queens outnumber boardSize before inital calculations
    // this case causes an immediate return
    if (coordinateStrings.size() > boardSize)
      return false;

    // initalize arrays for rows, columns, and diagonals
    int[] queenColumn = new int[boardSize];
    int[] queenRow = new int[boardSize];
    int[] diagonalToTheRight = new int[boardSize * 2];
    int[] diagonalToTheLeft = new int[boardSize * 2];

    for (int i = 0; i < coordinateStrings.size(); i++)
    {
      // convert the whole coordinate strings into individual strings for R&C
      String columnLocation = extractColumnAddressFromString(coordinateStrings.get(i));
      String rowLocation = extractRowAddressFromString(coordinateStrings.get(i));

      // convert string into actual indexes
      int columnIndexHelper = convertStringToColumnIndex(columnLocation);
      int rowIndexHelper = Integer.parseInt(rowLocation);

      // checks if either index is larger than the board size
      if (columnIndexHelper > boardSize || rowIndexHelper > boardSize)
        return false;

      // check if current column or row is empty, if not fill with 1
      // -1 because of nature of size of arrays...
      if (queenColumn[columnIndexHelper - 1] == 0)
      {
        queenColumn[columnIndexHelper - 1] = 1;
      }
      else if (queenRow[rowIndexHelper - 1] == 0)
      {
        queenRow[rowIndexHelper - 1] = 1;
      }
      else return false;

      // calculate index of left diagonals
      int diagonalLeftIndex =
      diagonalLeftCalculator(rowIndexHelper, columnIndexHelper);
      if (diagonalToTheLeft[diagonalLeftIndex - 1] == 0)
      {
        // -1 to avoid out of bounds
        diagonalToTheLeft[diagonalLeftIndex - 1] = 1;
      }
      else return false;

      // calculate index of right diagonals
      int diagonalRightIndex =
        diagonalRightCalculator(rowIndexHelper, columnIndexHelper, boardSize);
      if (diagonalToTheRight[diagonalRightIndex] == 0)
      {
        diagonalToTheRight[diagonalRightIndex] = 1;
      }
      else return false;
    }
    return true;
  }

  public static int diagonalLeftCalculator(int row, int column)
  {
    // any coordinate that returns the same value
    // lies somewhere within the left diagonal \
    return row + column + 1;
  }

  public static int diagonalRightCalculator(int row, int column, int size)
  {
    // any coordinate that returns the same value
    // lies somewhere within the right diagonal /
    return size + row - column;
  }

  public static int convertStringToColumnIndex(String columnCoordinate)
  {
    int indexSum = 0;
    for (int i = 0; i < columnCoordinate.length(); i++)
    {
      // mutiply sum by powers of 26 to represent 'aa' = 27...
      // subtract 96 from char to derive values from 1 - 26
      indexSum = indexSum * 26 + (columnCoordinate.charAt(i) - ('a' - 1));
    }
    return indexSum;
  }

  public static String extractColumnAddressFromString(String coordinate)
  {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < coordinate.length(); i++)
    {
      // only build a new string of letters
      if (coordinate.charAt(i) >= 'a' && coordinate.charAt(i) <= 'z')
        builder.append(coordinate.charAt(i));
	    else
        break;
    }
    return builder.toString();
  }

  public static String extractRowAddressFromString(String coordinate)
  {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < coordinate.length(); i++)
    {
      // only build a new string of numbers
      if (!(coordinate.charAt(i) >= 'a' && coordinate.charAt(i) <= 'z'))
        builder.append(coordinate.charAt(i));
    }
    return builder.toString();
  }


  public static double difficultyRating()
  {
    return 3.85;
  }

  public static double hoursSpent()
  {
    return 10.5;
  }
}
