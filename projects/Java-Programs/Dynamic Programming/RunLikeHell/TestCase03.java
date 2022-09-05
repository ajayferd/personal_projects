// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase03.java
// ============================


public class TestCase03
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {9, 20, 13, 16, 9, 6, 9};
		int ans = 45;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
