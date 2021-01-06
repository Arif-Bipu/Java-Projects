// Sean Szumlanski
// COP 3503, Summer 2019

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.

// Arif Bipu
// COP 3503, Summer 2019
// NID ar455641


import java.io.*;
import java.util.*;

// Creates the BST nodes
class Node<AnyType>
{
	AnyType data;
	Node<AnyType> left, right;

	Node(AnyType data)
	{
		this.data = data;
	}
}

// Creates a BST that allows any type of input inside of it
public class GenericBST<AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;

	// Calls the private insert function, it returns the insert functions output
	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	// Inserts Data into BST based on current data, determined whether the node is empty, the data is larger or smaller
	// Inputs the node and data, expected output is the returned BST with data inserted 
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return new Node<AnyType>(data);
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}

	// Calls the private delete function, it returns the delete functions output  
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	// Deletes nodes similarly to inserting, it is determined by the input of the data and node. 
	// If the data is smaller than the root, it will delete the left, else the right. It returns the root node after checking through the BST
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return null;
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
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
	// Inputs the node, returns the data at the max
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Calls the contain function, returning the output from contains
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}

	// Determines whether the node contains that data type, it reads through the BST and sees if anything matches
	// Inputs are the node and the data, output is a true or a false value
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return false;
		}
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}

	// Returns the order using the private inorder function, output is printed on the screen
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// Does the inorder traversal, left, root, right, outputs the node at that location
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// Returns the order using the private preorder function, output is printed on the screen
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// Does the preorder traversal, root, left, right, outputs the node at that location
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// Returns the order using the private postorder function, output is printed on the screen
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// Does the postorder traversal, left, right, root, outputs the node at that location
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

//	public static void main(String [] args)
//	{
//		GenericBST myTree = new GenericBST();
//
// 	for (int i = 0; i < 5; i++)
// 	{
// 		int r = (int)(Math.random() * 100) + 1;
// 		System.out.println("Inserting " + r + "...");
// 		myTree.insert(r);
// 	}

// 	myTree.inorder();
// 	myTree.preorder();
// 	myTree.postorder();
// }

	public static double difficultyRating()
	{
		return 2.0;
	}

	public static double hoursSpent()
	{
		return 3.5;
	}
}
