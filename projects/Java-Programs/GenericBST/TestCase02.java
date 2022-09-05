// Sean Szumlanski
// COP 3503, Spring 2021

// ===========================
// GenericBST: TestCase02.java
// ===========================
// A brief test case to help ensure you've implemented the difficultyRating()
// and hoursSpent() methods correctly, and that you can create and use a
// GenericBST.
//
// For detailed compilation and testing instructions, see the assignment PDF.


import java.io.*;
import java.util.*;

public class TestCase02
{
	public static void main(String [] args)
	{
		double difficulty = GenericBST.difficultyRating();
		System.out.println((difficulty < 1.0 || difficulty > 5.0) ? "fail whale :(" : "Hooray!");

		double hours = GenericBST.hoursSpent();
		System.out.println((hours <= 0.0) ? "fail whale :(" : "Hooray!");

		// Create a GenericBST.
		GenericBST<String> myTree = new GenericBST<String>();

		String[] array = {"Bent", "Roger", "Pumpkin", "AJ", "Jonathan", "Bacon", "Ramsey", "Helen", "Trey", "Jordan"};

		for (int i = 0; i < array.length; i++)
			myTree.insert(array[i]);

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
		myTree.delete("AJ");
		myTree.inorder();
	}
}
