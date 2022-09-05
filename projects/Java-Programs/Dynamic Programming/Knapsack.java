// Sean Szumlanski
// COP 3503, Spring 2021

// Knapsack.java
// =============
// Recursive, DP, and memoization solutions to the 0-1 Knapsack problem.


import java.util.*;  // for HashMap
import java.awt.*;   // for Point (see also: javax.vecmath's Point2d class)

class Treasure
{
	int weight, value;

	public Treasure(int w, int v)
	{
		this.weight = w;
		this.value  = v;
	}
}

public class Knapsack
{
	public static final int UNINITIALIZED = -1;

	public static int knapsack(Treasure [] treasures, int capacity)
	{
		return knapsack(treasures, capacity, treasures.length);
	}

	// Best-Case Runtime: O(n) (The last treasure's weight always exceeds the bag's capacity.)
	// Worst-Case Runtime: O(2^n) -- where n is the number of treasures
	// Space Complexity: O(n) -- a max of n calls could be on the call stack at any given time
	private static int knapsack(Treasure [] treasures, int capacity, int k)
	{
		// If the capacity is zero, or if there are zero remaining items to
		// choose from, we have no added value. Just return zero.
		if (capacity == 0 || k == 0)
			return 0;

		// If there's still room in our knapsack for this item, then we can either
		// pick it up or leave it behind. We look at the best possible result for
		// both of these paths, and then choose the one that maximizes our value.
		if (treasures[k-1].weight <= capacity)
			return Math.max(
				knapsack(treasures, capacity, k-1),
 				knapsack(treasures, capacity - treasures[k-1].weight, k-1) + treasures[k-1].value
			);
		// If we can't pick up the kth item (which is at position (k-1) in the
		// zero-indexed array), then our only choice is to maximize the value we
		// can attain by choosing from the remaining (k-1) items.
		else
			return knapsack(treasures, capacity, k-1);
	}

	// Two possible memoization approaches:
	//
	// HashMap<Point, Integer>
	//          ^          ^
	//     (capacity, k)   result
	//
	// HashMap<Integer, HashMap<Integer, Integer>
	//          ^                  ^        ^
	//      capacity               k      result
	//
	// The idea here is that capacity and k are the two inputs that unlock our result,
	// whether we're using a function or an array:
	//
	//    (capacity, k) -> FUNCTION -> result :)
	//    (capacity, k) -> MEMO -> cached result :)

	public static int knapsack_memo_array(Treasure [] t, int capacity)
	{
		int [][] memo = new int[capacity+1][t.length+1];

		for (int i = 0; i <= capacity; i++)
			Arrays.fill(memo[i], UNINITIALIZED);

		return knapsack_memo_array(t, capacity, t.length, memo);
	}

	private static int knapsack_memo_array(Treasure [] t, int capacity, int k, int [][] memo)
	{
		if (capacity <= 0 || k == 0)
			return 0;

		if (memo[capacity][k] != UNINITIALIZED)
			return memo[capacity][k];

		if (t[k-1].weight <= capacity)
			memo[capacity][k] = Math.max(
				t[k-1].value + knapsack_memo_array(t, capacity - t[k-1].weight, k-1, memo),
				knapsack_memo_array(t, capacity, k-1, memo));
		else
			memo[capacity][k] = knapsack_memo_array(t, capacity, k-1, memo);

		return memo[capacity][k];
	}

	public static int knapsack_memo_point(Treasure [] treasures, int capacity)
	{
		HashMap<Point, Integer> map = new HashMap<Point, Integer>();
		return knapsack_memo_point(treasures, capacity, treasures.length, map);
	}

	// To analyze the worst-case runtime of this function, you have to ask yourself
	// how many unique (capacity, k) ordered pairs these function calls can generate.
	//
	// That will also reveal something about the worst-space complexity, since that's
	// the max number of elements we'll add to the HashMap.
	private static int knapsack_memo_point(Treasure [] treasures, int capacity, int k, HashMap<Point, Integer> map)
	{
		if (capacity == 0 || k == 0)
			return 0;

		Integer result = map.get(new Point(capacity, k));

		// Check whether the result has been memoized. If so, this variable will
		// be non-null. On the other hand, if it's not in the map, this will be
		// null.
		if (result != null)
			return result;

		if (treasures[k-1].weight <= capacity)
			result = Math.max(
				knapsack_memo_point(treasures, capacity, k-1, map),
 				knapsack_memo_point(treasures, capacity - treasures[k-1].weight, k-1, map) + treasures[k-1].value
			);
		else
			result = knapsack_memo_point(treasures, capacity, k-1, map);

		// Memoize result before returning.
		map.put(new Point(capacity, k), result);
		return result;
	}

	// Best-Case Runtime: O(nk) -- where n is number of treasures and k is capacity
	// Worst-Case Runtime: O(nk)
	// Space Complexity: O(nk)
	public static int knapsack_dp(Treasure [] treasures, int capacity)
	{
		int [][] matrix = new int[treasures.length + 1][capacity + 1];

		// Initializing these base cases is unnecessary in Java, since the matrix
		// is initialized to zero. I'm just putting this here for posterity.
		for (int i = 0; i <= treasures.length; i++)
			matrix[i][0] = 0;
		for (int i = 0; i <= capacity; i++)
			matrix[0][i] = 0;

		// Start at i=1 and j=1 since we already dealt with i=0 and j=0.
		// Notice that i is acting as our varying number of items to choose from,
		// and j is acting as our varying capacity, so I rewrote the recurrence
		// relation in terms of i and j instead of capacity and k.
		for (int i = 1; i <= treasures.length; i++)
			for (int j = 1; j <= capacity; j++)
				if (treasures[i-1].weight <= j)
					matrix[i][j] = Math.max(
						matrix[i-1][j],
						matrix[i-1][j-treasures[i-1].weight] + treasures[i-1].value
					);
				else
					matrix[i][j] = matrix[i-1][j];

		// This is where we have the result that chooses from all k items in the
		// array and utilizes the full knapsack capacity.
		return matrix[treasures.length][capacity];
	}

	// Just for funsies, let's reduce the space complexity. We only ever need
	// two rows in memory at any given time.
	public static int knapsack_dp_compressed(Treasure [] treasures, int capacity)
	{
		int [][] matrix = new int[2][capacity + 1];

		// Initializing these base cases is unnecessary in Java, since the matrix
		// is initialized to zero. I'm just putting this here for posterity.
		for (int i = 0; i <= 1; i++)
			matrix[i][0] = 0;
		for (int i = 0; i <= capacity; i++)
			matrix[0][i] = 0;

		// Start at i=1 and j=1 since we already dealt with i=0 and j=0.
		// Notice that i is acting as our varying number of items to choose from,
		// and j is acting as our varying capacity, so I rewrote the recurrence
		// relation in terms of i and j instead of capacity and k.
		for (int i = 1; i <= treasures.length; i++)
			for (int j = 1; j <= capacity; j++)
				if (treasures[i-1].weight <= j)
					matrix[i%2][j] = Math.max(
						matrix[(i-1)%2][j],
						matrix[(i-1)%2][j-treasures[i-1].weight] + treasures[i-1].value
					);
				else
					matrix[i%2][j] = matrix[(i-1)%2][j];

		// This is where we have the result that chooses from all k items in the
		// array and utilizes the full knapsack capacity.
		return matrix[treasures.length%2][capacity];
	}

	// Below is an alternate DP approach where capacity governs the number of
	// rows and t.length governs the number of columns.

	//   0   1   2   3   <-- n = 3
	// +---+---+---+---+
	// | 0 | 0 | 0 | 0 | 0
	// +---+---+---+---+
	// | 0 |   |   |   | 1
	// +---+---+---+---+
	// | 0 |   |   |   | 2
	// +---+---+---+---+
	// | 0 |   |   |XXX| 3
	// +---+---+---+---+
	// | 0 |   |   |   | 4
	// +---+---+---+---+
	// | 0 |   |   |   | 5
	// +---+---+---+---+
	// | 0 |   |   |   | 6
	// +---+---+---+---+
	// | 0 |   |   |XXX| 7
	// +---+---+---+---+
	//                   ^ capacity = 7

	private static int knapsack_dp_rotated(Treasure [] t, int capacity)
	{
		int [][] matrix = new int[capacity+1][t.length+1];

		// Initializing these base cases is unnecessary in Java, since the matrix
		// is initialized to zero. I'm just putting this here for posterity.

		// If capacity == 0, we can't carry any treasure.
		for (int i = 0; i <= t.length; i++)
			matrix[0][i] = 0;

		// If t.length == 0, we don't have any treasures to take.
		for (int i = 0; i <= capacity; i++)
			matrix[i][0] = 0;

		for (int col = 1; col <= t.length; col++)
		{
			for (int row = 1; row <= capacity; row++)
			{
				if (t[col-1].weight <= row)
					matrix[row][col] = Math.max(
						t[col-1].value + matrix[row - t[col-1].weight][col-1],
						matrix[row][col-1]);
				else
					matrix[row][col] = matrix[row][col-1];
			}
		}

		return matrix[capacity][t.length];
	}

	public static void main(String [] args)
	{
		// The expected result here is 19, from taking the treasures at indices
		// 1, 2, 3, and 5.
		Treasure [] treasures = new Treasure[] {
			new Treasure(4, 6),
			new Treasure(2, 4),
			new Treasure(3, 5),
			new Treasure(1, 3),
			new Treasure(6, 9),
			new Treasure(4, 7)
		};

		System.out.println(knapsack(treasures, 10));
		System.out.println(knapsack_dp(treasures, 10));
		System.out.println(knapsack_dp_compressed(treasures, 10));
		System.out.println(knapsack_dp_rotated(treasures, 10));
		System.out.println(knapsack_memo_array(treasures, 10));
		System.out.println(knapsack_memo_point(treasures, 10));
	}
}
