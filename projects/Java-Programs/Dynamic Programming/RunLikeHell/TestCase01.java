// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase01.java
// ============================


public class TestCase01
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {15, 3, 6, 17, 2, 1, 20};
		int ans = 52;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
