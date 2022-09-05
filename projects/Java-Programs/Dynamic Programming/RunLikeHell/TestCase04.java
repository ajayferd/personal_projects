// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase04.java
// ============================


public class TestCase04
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {16, 10, 15, 12, 2, 20, 2, 16};
		int ans = 67;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
