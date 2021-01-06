/*
Name: Arif Bipu
Course: CNT 4714 Fall 2020
Assignment Title: Project Three – Two-Tier Client-Server Application Development With MySQL andJDBC
Date: November 6th, 2020
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class DisplayGui extends JFrame
{
	private JLabel jlbDriver;
	private JLabel jlbDataBaseUrl;
	private JLabel jlbUsername;
	private JLabel jlbPassword;
	private JLabel jlbConnectionStatus;
	
	private JComboBox DriverList;
	private JComboBox DataBaseURLList;
	
	private JTextField jtfUsername;
	private JPasswordField jpfPassword;
	
	private JTextArea jtaSQLCom;
	private JButton jbtConnectToDB;
	private JButton jbtClearSQLCom;
	private JButton jbtExecuteSQLCom;
	private JButton jbtClearResultWindow;
	
	private ResultSetTableModel tableModel = null;
	private JTable table;
	
	private Connection connection;
	private boolean DBConnectStatus = false;
	
	public DisplayGui() throws ClassNotFoundException, SQLException, IOException
	{
		
		this.createInstanceGUIComponents();
		
		this.jbtConnectToDB.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						try
						{
							Class.forName(String.valueOf(DriverList.getSelectedItem()));
						} catch (ClassNotFoundException e) {
							jlbConnectionStatus.setText("No Connection");
							jlbConnectionStatus.setForeground(Color.RED);
							e.printStackTrace();
							
							//clearing table
							table.setModel(new DefaultTableModel());
							tableModel = null;
						}
						
						try 
						{
							if(DBConnectStatus == true)
							{
								connection.close();
								jlbConnectionStatus.setText("No Connection");
								jlbConnectionStatus.setForeground(Color.RED);
								DBConnectStatus = false;
								
								table.setModel(new DefaultTableModel());
								tableModel = null;
								
							}
							 
							connection = DriverManager.getConnection(String.valueOf(DataBaseURLList.getSelectedItem()), jtfUsername.getText(), jpfPassword.getPassword().toString());
							
							jlbConnectionStatus.setText("Connected to " + String.valueOf(DataBaseURLList.getSelectedItem()));
							jlbConnectionStatus.setForeground(Color.RED);
							
							DBConnectStatus = true;
							
						} catch(SQLException e) {
							jlbConnectionStatus.setText("No Connection");
							jlbConnectionStatus.setForeground(Color.RED);
							
							table.setModel(new DefaultTableModel());
							tableModel = null;
							e.printStackTrace();
						}
					}
				}
				);
		
		
		this.jbtClearSQLCom.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						jtaSQLCom.setText("");
					}
				}
				
				
			);
		
		this.jbtExecuteSQLCom.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						
						if (DBConnectStatus == true && tableModel == null)
						{
							try 
							{
								tableModel = new ResultSetTableModel(jtaSQLCom.getText(), connection);
								table.setModel(tableModel);
							} catch (ClassNotFoundException | SQLException | IOException e) {
								table.setModel(new DefaultTableModel());
								tableModel = null;
								
								JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							
						}
						else
							if(DBConnectStatus == true && tableModel != null)
							{
								String query = jtaSQLCom.getText();
								if (query.contains("select") || query.contains("SELECT"))
								{
									try {
										tableModel.setQuery(query);
									} catch(IllegalStateException | SQLException e) {
										table.setModel(new DefaultTableModel());
										tableModel = null;
										JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
									}
								}
								else {
									try {
										tableModel.setUpdate(query);
										
										table.setModel(new DefaultTableModel());
										tableModel = null;
									} catch (IllegalStateException | SQLException e) {
										table.setModel(new DefaultTableModel());
										tableModel = null;
										JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
									}
								}
							}
					}
				}
				
				
				
				);
		
		
		this.jbtClearResultWindow.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						table.setModel(new DefaultTableModel());
						tableModel = null;
					}
				}

				);
		
		//button panel
		JPanel buttons = new JPanel(new GridLayout(1,4));
		buttons.add(this.jlbConnectionStatus);
		buttons.add(this.jbtConnectToDB);
		buttons.add(this.jbtClearSQLCom);
		buttons.add(this.jbtExecuteSQLCom);
		
		//text field panel
		
		JPanel labelsAndText = new JPanel(new GridLayout(4,2));
		labelsAndText.add(this.jlbDriver);
		labelsAndText.add(this.DriverList);
		labelsAndText.add(this.jlbDataBaseUrl);
		labelsAndText.add(this.DataBaseURLList);
		labelsAndText.add(this.jlbUsername);
		labelsAndText.add(this.jtfUsername);
		labelsAndText.add(this.jlbPassword);
		labelsAndText.add(this.jpfPassword);

		
		JPanel top = new JPanel(new GridLayout(1,2));
		top.add(labelsAndText);
		top.add(this.jtaSQLCom);
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout(20,0));
		south.add(new JScrollPane(this.table), BorderLayout.NORTH);
		south.add(this.jbtClearResultWindow, BorderLayout.SOUTH);
		
		add(top, BorderLayout.NORTH);
		add(buttons, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosed( WindowEvent event)
			{
				try {
					if(!connection.isClosed())
						connection.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
				System.exit( 0 );
			}
		});
		
		
		
	}
	
	public void createInstanceGUIComponents() throws ClassNotFoundException, SQLException, IOException
	{
		String[] driverString = {"com.mysql.cj.jdbc.Driver"};
		String[] dataBaseURLString = {"jdbc:mysql://127.0.0.1:3306/project3"};
		
		this.jlbDriver = new JLabel("JDBC Driver");
		this.jlbDataBaseUrl = new JLabel("DataBase URL");
		this.jlbUsername = new JLabel("Username");
		this.jlbPassword = new JLabel("Password");
		this.jlbConnectionStatus = new JLabel("No Connection");
		this.jlbConnectionStatus.setForeground(Color.RED);
		
		this.DriverList = new JComboBox(driverString);
		this.DriverList.setSelectedIndex(0);
		this.DataBaseURLList = new JComboBox(dataBaseURLString);
		
		this.jtfUsername = new JTextField();
		this.jpfPassword = new JPasswordField();
		
		this.jtaSQLCom = new JTextArea(3, 75);
		this.jtaSQLCom.setWrapStyleWord(true);
		this.jtaSQLCom.setLineWrap(true);
		
		this.jbtConnectToDB = new JButton("Connect to Database");
		this.jbtClearSQLCom = new JButton("Clear SQL Command");
		this.jbtExecuteSQLCom = new JButton("Execute SQL Command");
		this.jbtClearResultWindow = new JButton("Clear Result Window");
		
		this.table = new JTable();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}