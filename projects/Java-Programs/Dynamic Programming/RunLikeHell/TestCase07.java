// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase07.java
// ============================


public class TestCase07
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {10, 2, 3, 10, 9, 10, 3, 10, 5, 2};
		int ans = 42;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
