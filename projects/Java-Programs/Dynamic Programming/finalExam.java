// Alejandro Fernandez, al720940
// COP3503, Spring 2021

import java.util.*;
import java.awt.*;

public class finalExam
{
  public static int foo(int n, int b, int g)
{
   // Can generate 3908131989 distinct values.
   int f = (n * 31) ^ (g * 37 * 37) + b;
   return (f < 0) ? -f : f;
}

  public static int getFluxCapacitanceMemo(int n, int b, int g)
  {
    HashMap<Point, Integer> map = new HashMap<Point, Integer>();
    return getFluxCapacitanceMemo(n, b, g, map);
  }

  private static int getFluxCapacitanceMemo(int n, int b, int g, HashMap<Point, Integer> map)
  {
    if (n <= 0)
      return 2;

    // Note: The foo() method itself does not require memoization.
    int f = foo(n, b, g);

    Integer result = map.get(new Point(n, g));
    if (map != null)
      return result;

    if (f % 11 < 4)
      result = getFluxCapacitanceMemo(n - 1, b, g / 2, map) + getFluxCapacitanceMemo(n - 2, b, g / 2, map) + f;
    else if (f % 11 < 8)
      result = getFluxCapacitanceMemo(n - 2, b, g / 2, map) + getFluxCapacitanceMemo(n - 2, b, g / 2, map) + f;
    else
      result = getFluxCapacitanceMemo(n - 2, b, g / 2, map) + getFluxCapacitanceMemo(n - 2, b, g / 4, map) + f;

    map.put(new Point(n , g), result);
    return result;
  }
}
