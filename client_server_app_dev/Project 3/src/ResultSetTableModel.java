/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment Title: Project Three – Two-Tier Client-Server Application Development With MySQL andJDBC
Date: November 6th, 2020
*/

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel
{
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numRows;
	
	
	private boolean DBConnectStatus = false;
	
	public ResultSetTableModel(String query, Connection connection) throws SQLException, ClassNotFoundException, IOException
	{
		try 
		{
			this.connection = connection;
			if(!this.connection.isClosed())
			{
				DBConnectStatus = true;
				
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				if(query.contains("select") || query.contains("SELECT")) 
				{
					try
					{
						setQuery(query);
					} catch(IllegalStateException | SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				else 
				{
					try
					{
						setUpdate(query);
					} catch(IllegalStateException | SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public Class getColumnClass(int column) throws IllegalStateException
	{
		
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		try 
		{
			String className = metaData.getColumnClassName(column + 1);
			
			return Class.forName(className);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return Object.class;

	}
	
	
	public int getColumnCount() throws IllegalStateException
	{
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		try
		{
			return metaData.getColumnCount();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return 0;
	}
	
	public String getColumnName (int column) throws IllegalStateException
	{
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		try {
			return metaData.getColumnName(column + 1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return "";
	}
	
	public int getRowCount() throws IllegalStateException
	{
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		return numRows;
	}
	
	public Object getValueAt(int row, int column) throws IllegalStateException
	{
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		try
		{
			resultSet.next();
			resultSet.absolute(row+1);
			return resultSet.getObject(column+1);
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return "";
	}
	
	
	public void setQuery(String query) throws IllegalStateException, SQLException 
	{
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		resultSet = statement.executeQuery(query);
		metaData = resultSet.getMetaData();
		resultSet.last();
		numRows = resultSet.getRow();
		
		fireTableStructureChanged();
	}
	
	public void setUpdate(String query) throws SQLException, IllegalStateException
	{
		int res;
		
		if(!DBConnectStatus)
			throw new IllegalStateException("Not Connected to Database");
		
		res = statement.executeUpdate(query);
				
		fireTableStructureChanged();
	}
		
	
}