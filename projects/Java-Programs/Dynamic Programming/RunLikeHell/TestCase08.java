// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase08.java
// ============================


public class TestCase08
{
	public static void main(String [] args) throws Exception
	{
		int [] blocks = new int[] {17, 20, 111, 142, 88, 199, 86, 90, 199, 51, 174, 
		                           194, 3, 190, 138, 198, 104, 74, 195, 43};
		int ans = 1337;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
