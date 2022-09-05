import java.util.*;

public class exampleProgram
{
  public static void main(String... args) throws Exception
  {
    OUTER_LABEL: for (int x = 0, y = 0; x < 10 && y > -10; x++, y--)
      System.out.println("Hello!");

    String result = "AniMaL ".trim().toLowerCase().replace('a', 'A');
    System.out.println(result);

    String start = "AniMaL ";
    String trimmed = start.trim();               // "AniMaL"
    String lowercase = trimmed.toLowerCase();    // "animal"
    String Result = lowercase.replace('a', 'A'); // "AnimAl"
    System.out.println(Result);

    if (args.length == 0)
      System.out.println("No arguments");
    else
      System.out.println("Total arguments: " + args.length);
  }
}
