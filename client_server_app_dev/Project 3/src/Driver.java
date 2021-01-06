/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment Title: Project Three – Two-Tier Client-Server Application Development With MySQL andJDBC
Date: November 6th, 2020
*/


import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.SQLException;

public class Driver
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		DisplayGui myFrame = new DisplayGui();
		myFrame.setVisible(true);
		myFrame.pack();
		myFrame.setLayout(new BorderLayout(2,0));
		
	}
}