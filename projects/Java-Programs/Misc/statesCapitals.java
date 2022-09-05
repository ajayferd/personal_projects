import java.util.*;
import java.io.*;


public class statesCapitals
{
  public static int minNumberOfVowels = 0;
  public static HashMap<String, String> getStates(String filename) throws IOException
  {
    Scanner input = new Scanner(new File(filename));

    int totalStates = input.nextInt();
    HashMap<String, String> statesMap = new HashMap<String, String>(totalStates);
    String [] reservedWords = new String[] {"New", "South", "North", "East", "West", "Rhode"};
    for (int i = 0; i < totalStates; i++)
    {
      String state = input.next();

      for (int j = 0; j < reservedWords.length; j++)
      {
        if (state.equals(reservedWords[j]))
          state += (' ' + input.next());
      }
      String capital = input.nextLine();
      statesMap.put(state, capital);
    }

    return statesMap;
  }
  public static void main(String [] args) throws IOException
  {
    HashMap<String, String> statesCapitals = new HashMap<String, String>();
    statesCapitals = getStates("states&capitals.txt");
    minNumberOfVowels = Integer.parseInt(args[0]);
    int stateCount = 0;
    // char [] vowels = new char[] {'a','e','i','o','u'};

    for (Map.Entry m : statesCapitals.entrySet())
    {
      String state = m.getKey().toString().toLowerCase();
      int vowelCount = 0;
      for (int j = 0; j < state.length(); j++)
      {
        if (state.charAt(j) == 'a' || state.charAt(j) == 'e' || state.charAt(j) == 'i'
        || state.charAt(j) == 'o' || state.charAt(j) == 'u')
        vowelCount++;
      }

      if (vowelCount >= minNumberOfVowels && minNumberOfVowels > 0)
      {
        System.out.println(m.getKey() + " " + m.getValue());
        stateCount++;
      }
    }
    if (stateCount == 0)
      System.out.println("There are zero states that contain " + minNumberOfVowels + " vowels...");
    else if (minNumberOfVowels < 1)
      System.out.println("There are zero states that contain " + minNumberOfVowels + " vowels...");
    else
      System.out.println("There are " + stateCount +" states with at least " + minNumberOfVowels + " vowels...");
  }
}
