// Michael W.
// COP 3503, Summer 2019


// ===========================
// GenericBST: TestCase02.java
// ===========================
// A brief test case to help ensure you've implemented the contains(), insert(),
// delete() methods correctly, and that you can create and use a GenericBST.


import java.io.*;
import java.util.*;

public class TestCase02
{
	public static void main(String [] args)
	{
		// Create a GenericBST.
		GenericBST<String> myTree = new GenericBST<String>();
		boolean x = false;

		// "Apple", "Orange", "Watermelon", "Banana", "Grapefruit",
		// "Grape", "Strawberry", "Guava", "Dragonfruit", "Cherry"

		myTree.insert("Apple");
		myTree.insert("Orange");
		myTree.insert("Watermelon");
		myTree.insert("Banana");
		myTree.insert("Grapefruit");
		myTree.insert("Grape");
		myTree.insert("Strawberry");
		myTree.insert("Guava");
		myTree.insert("Dragonfruit");
		myTree.insert("Cherry");

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();

		x = myTree.contains("Apple");
		System.out.println(x);
		x = myTree.contains("Watermelon");
		System.out.println(x);

		myTree.delete("Apple");
		myTree.delete("Peach"); // doesnt exist
		myTree.delete("Apple"); // already deleted
		myTree.delete("Watermelon");

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();

		x = myTree.contains("Apple");
		System.out.println(x);
		x = myTree.contains("Watermelon");
		System.out.println(x);

		myTree.delete("Banana");
		myTree.delete("Grapefruit");
		myTree.delete("Guava");
		myTree.delete("Dragonfruit");

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();

		x = myTree.contains("Banana");
		System.out.println(x);
		x = myTree.contains("Grapefruit");
		System.out.println(x);
		x = myTree.contains("Grape");
		System.out.println(x);
		x = myTree.contains("Strawberry");
		System.out.println(x);
		x = myTree.contains("Guava");
		System.out.println(x);
		x = myTree.contains("Dragonfruit");
		System.out.println(x);
		x = myTree.contains("Orange");
		System.out.println(x);
		x = myTree.contains("Cherry");
		System.out.println(x);
	}
}
