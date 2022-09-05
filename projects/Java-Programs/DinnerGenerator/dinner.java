import java.io.*;
import java.util.*;

public class dinner
{

	public static void main (String[] args) throws IOException
	{
	 	ArrayList<String> dinnerList = new ArrayList<String>();
		ArrayList<String> dinnerHistory = new ArrayList<String>();
		int amountOfMeals = 0, recipes = 0;
		String currentfileName = "dinnerItems.txt";
		String oldfileName = "dinner.history";
		File currentfile = new File(currentfileName);
		File historyfile = new File(oldfileName);

		Scanner myScan = new Scanner(currentfile);
		Scanner historyScan = new Scanner(historyfile);
		try {
			amountOfMeals = Integer.parseInt(args[0]);
		}
		catch (Exception e){
			System.err.println("\nPlease rerun with the following input: java dinner <amount of meals to return>\n");
		}
		
		try
		{
			while(historyScan.hasNextLine())
			{
				String dinnerHistoryName = historyScan.nextLine();
				dinnerHistory.add(dinnerHistoryName);
			}
			while(myScan.hasNextLine())
			{
				String dinnerName = myScan.nextLine();
				if (dinnerHistory.contains(dinnerName))
					continue;
				dinnerList.add(dinnerName);
			}
		}
		catch(Exception e)
		{
			System.out.println("File failed to open, quitting...");
		}
		
		dinnerHistory.retainAll(dinnerList);
		// System.out.println("~~~~ Number of dinners for the week: " + amountOfMeals + " ~~~~~");
		// System.out.println(dinnerList);
		recipes = dinnerList.size();
		List<Integer> uniqueList = new ArrayList<Integer>(getUniqueIndexes(amountOfMeals, recipes));
		writeToFile(uniqueList, dinnerList);
		myScan.close();
		historyScan.close();
	}

	public static void writeToFile(List<Integer> uniqueList, ArrayList<String> dinnerItems)
	{
		String filename = "dinner.history";
		try{
			FileWriter writer = new FileWriter(filename);
			for (int i = 0; i < uniqueList.size(); i++)
			{
				writer.write(dinnerItems.get(uniqueList.get(i)));
				writer.write("\n");
				// System.out.println(dinnerItems.get(uniqueList.get(i)));
			}
			writer.close();
		}
		catch (IOException e){
			System.out.println("File error");
			e.printStackTrace();
		}

		// System.out.println("Elements in dinnerHistory: " + );
		return;
	}

	// This function takes in an array and determines if any values are duplicates.
	// Returns true if there is NO duplicates and false if there is ANY duplicates
	public static boolean findDuplicateInArray(int[] arr) {
		Set<Integer> unique = new HashSet<>();
		Set<Integer> duplicate = new HashSet<>();
	
		for (int val : arr)
			if (!unique.add(val))
				duplicate.add(val);
	
		return duplicate.isEmpty();
	}

	// This function takes in the total number of recipes in the dinner list and the amount of dinners requested
	// to form the size of the array of unique numbers
	public static HashSet<Integer> getUniqueIndexes(int sizeOfArray, int totalRecipes)
	{
		HashSet<Integer> uniqueValues = new HashSet<Integer>(sizeOfArray);
		while(uniqueValues.size() < sizeOfArray)
			uniqueValues.add((int)((Math.random() * 100) % totalRecipes));
		
		return uniqueValues;
	}

}
