// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase06.java
// ============================


public class TestCase06
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {7, 10, 18, 16, 17, 12, 14, 9};
		int ans = 56;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
