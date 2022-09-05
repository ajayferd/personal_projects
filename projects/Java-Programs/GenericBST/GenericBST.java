// Alejandro Fernandez, 4600541
// COP 3503, Spring 2021

// ====================
// GenericBST: GenericBST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.

import java.io.*;
import java.util.*;

// node class extends datatypes that are only comparable
class Node<DataType extends Comparable<DataType>>
{
	// structure for BST holding data with left and right trees
	DataType data;
	Node<DataType> left, right;

	// Node constructor for generic datatype
	Node(DataType data)
	{
		this.data = data;
	}
}

// generic BST extends datatypes that are only comparable
public class GenericBST <DataType extends Comparable<DataType>>
{
	private Node<DataType> root;

	// parameter: takes in data of any type and inserts into the tree, void return
	public void insert(DataType data)
	{
		root = insert(root, data);
	}

	// method overloads insert method and passes the root with data to insert
	private Node<DataType> insert(Node<DataType> root, DataType data)
	{
		// check if the root is null before inserting into an empty tree
		// or if we reach the correct position to insert element, then insert
		if (root == null)
		{
			return new Node<DataType>(data);
		}
		// compare if current value is less than node, if so
		// transverse to left node
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		// compare if current value is greater than node, if so
		// transverse to right node
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}
		// do nothing if duplicate values present in tree

		// return root of tree with newly inserted item
		return root;
	}

	// parameter: takes in data of any type to delete from tree
	public void delete(DataType data)
	{
		root = delete(root, data);
	}

	// method overloads with root, searches tree for node to delete
	private Node<DataType> delete(Node<DataType> root, DataType data)
	{
		// if root is null then there is no value to delete so return
		if (root == null)
		{
			return null;
		}

		// compare if current value is less than node, if so transverse
		// to the left node
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}

		// compare if current value is greater than node, if so transverse
		// to the right node
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}

		// depending if children of parent node exist, or not, determine how to delete
		else
		{
			// if node with no children simply delete and return
			if (root.left == null && root.right == null)
			{
				return null;
			}
			// if node has no left child return right child
			else if (root.left == null)
			{
				return root.right;
			}
			// else if node has no right child return left child
			else if (root.right == null)
			{
				return root.left;
			}
			// the node must be a parent, find the max value in the leftmost node to
			// replace the deleted node
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private DataType findMax(Node<DataType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// returns true if the tree contains a given element, false if not
	// parameter: takes in data of any type to check if present within tree
	public boolean contains(DataType data)
	{
		return contains(root, data);
	}
	// private method overloads with root
	private boolean contains(Node<DataType> root, DataType data)
	{
		// if root is empty return false because the tree has no values
		// or value is not present in the tree
		if (root == null)
		{
			return false;
		}
		// compare if current value is less than node, if so transverse
		// to the left node
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		// compare if current value is greater than node, if so transverse
		// to the right node
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		// value is found so return true
		else
		{
			return true;
		}
	}

	// method prints elements in inorder
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}
	// overloads method name with root
	private void inorder(Node<DataType> root)
	{
		// base case returns if root is null
		if (root == null)
			return;

		// recursively prints nodes in inorder
		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// method prints elements in preorder
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// overloads method name with root
	private void preorder(Node<DataType> root)
	{
		// base case returns if root is null
		if (root == null)
			return;

		// recursively prints nodes in preorder
		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// method prints elements in postorder
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// overloads method name with root
	private void postorder(Node<DataType> root)
	{
		// base case returns if root is null
		if (root == null)
			return;

		// recursively prints elements in postorder
		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static double difficultyRating()
	{
		return 2.0;
	}

	public static double hoursSpent()
	{
		return 6.0;
	}
}
