// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase05.java
// ============================


public class TestCase05
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {3, 5, 7, 3, 11, 5, 9, 8};
		int ans = 30;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
