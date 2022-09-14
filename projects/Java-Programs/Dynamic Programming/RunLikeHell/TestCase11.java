// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase11.java
// ============================


public class TestCase11
{
	public static void main(String [] args) throws Exception
	{
		// This will be very slow with the recursive approach.
		int [] blocks = new int[] {25, 72, 83, 56, 30, 65, 54, 77, 11, 93, 47, 94, 33, 24,
		                           9, 12, 93, 20, 25, 3, 3, 80, 86, 12, 10, 77, 47, 35, 79,
		                           98, 99, 3, 50, 93, 93, 66, 3, 85, 94, 1, 24, 80, 63, 79,
		                           81, 67, 81, 97, 7, 86, 70, 57, 77, 44, 99, 7, 89, 27, 46,
		                           24, 7, 99, 87, 15, 72, 6, 7, 60, 94, 70, 44, 22, 2, 15,
		                           57, 70, 4, 33, 41, 55, 41, 86, 51, 61, 35, 70, 98, 7, 94,
		                           29, 27, 24, 30, 39, 68, 75, 75, 46, 48, 48, 15, 19, 29, 
		                           49, 7, 48, 48, 7, 21, 49, 69, 19, 23, 98, 8, 30, 70, 81, 
		                           36, 9, 97, 57, 36, 16, 82, 63, 87, 40, 20, 31, 22, 13,
		                           67, 17, 37, 4, 14, 47, 86, 53, 24, 52, 9, 21, 44, 29, 8,
		                           25, 75, 36, 35, 15, 4, 72, 95, 12, 64, 40, 59, 97, 19, 
		                           100, 70, 41, 67, 85, 83, 1, 48, 32, 100, 3, 25, 52, 66,
		                           20, 68, 48, 65, 13, 25, 62, 79, 68, 7, 42, 16, 85, 7, 
		                           38, 36, 39, 6, 84, 79, 99, 55, 29, 97, 3, 52, 6, 8, 59,
		                           36, 10, 10, 2, 35, 97, 20, 50, 78, 23, 32, 51, 52, 36, 
		                           48, 76, 55, 13, 46, 2, 86, 44, 38, 73, 24, 33, 85, 17, 
		                           17, 9, 38, 54, 77, 38, 35, 12, 82, 48, 84, 82, 15, 7, 
		                           60, 19, 66, 71, 68, 60, 23, 2, 29, 8, 78, 37, 58, 50, 
		                           2, 88, 2, 49, 72, 18, 56, 0, 25, 91, 52, 81, 13, 58, 75, 
		                           63, 12, 87, 22, 63, 0, 16, 8, 73, 15, 2, 11, 95, 4, 67,
		                           96, 22, 93, 72, 48, 1, 16, 43, 11, 57, 7, 77, 20, 25, 7,
		                           85, 77, 46, 17, 89, 58, 32, 99, 13, 90, 9, 21, 20, 43, 92,
		                           38, 24, 53, 83, 17, 79, 60, 51, 4, 61, 44, 44, 4, 89, 13,
		                           64, 70, 53, 41, 88, 82, 37, 97, 82, 66, 40, 86, 42, 42, 68,
		                           46, 76, 20, 36, 82, 88, 3, 4, 73, 67, 45, 52, 61, 85, 60,
		                           59, 32, 75, 11, 84, 29, 76, 15, 63, 19, 31, 74, 28, 70, 87,
		                           31, 67, 16, 32, 30, 92, 80, 44, 80, 32, 83, 3, 30, 23, 21, 
		                           44, 29, 77, 50, 69};
		int ans = 11441;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}