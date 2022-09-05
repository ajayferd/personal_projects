// Sean Szumlanski
// COP 3503, Spring 2021

// ============================
// RunLikeHell: TestCase09.java
// ============================


public class TestCase09
{
	public static void main(String [] args) throws Exception
	{
		// This will be very slow with the recursive approach.
		int [] blocks = new int[] {251, 675, 579, 469, 388, 421, 51, 563, 680, 358, 778,
		                           686, 590, 484, 180, 474, 460, 32, 896, 676, 867, 369,
		                           510, 908, 709, 424, 771, 58, 666, 374, 802, 246, 907,
		                           722, 244, 506, 798, 996, 790, 776, 627, 516, 569, 436,
		                           409, 823, 267, 117, 535, 414, 578, 121, 717, 567, 156,
		                           477, 603, 804, 594, 542, 195, 359, 957, 187, 384, 59,
		                           733, 20, 468, 629, 753, 214, 651, 645, 832, 429, 269,
		                           287, 873, 751, 867, 419, 890, 699, 578, 196, 594, 505,
		                           697, 209, 278, 497, 217, 932, 832, 851, 708, 842, 441,
		                           974};
		int ans = 31729;

		// Print whether the test case passes or fails, based on expected answer (ans)
		System.out.println((RunLikeHell.maxGain(blocks) == ans) ? "Yasssssss!!" : "fail whale :(");
	}
}
