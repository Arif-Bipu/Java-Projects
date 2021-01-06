/*  Name:  Arif Bipu
 * Course:CNT 4714–Fall2020–
 * Project Four
 * Assignment title:A Three-Tier Distributed Web-Based Application
 * Date:December 4, 2020 */


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;


public class SQLQueryServlet extends HttpServlet
{
	private Connection connection;
	private Statement statement;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		
		super.init(config);
		
		try {
			Class.forName(config.getInitParameter("databaseDriver"));
			connection = DriverManager.getConnection(config.getInitParameter("databaseName"), config.getInitParameter("username"), config.getInitParameter("password"));
			statement = connection.createStatement();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UnavailableException(e.getMessage());
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String textBox = request.getParameter("textBox");
		String textBoxLowerCase = textBox.toLowerCase();
		String result = null;
		
		if(textBoxLowerCase.contains("select"))
		{
			try
			{
				result = doSelectQuery(textBoxLowerCase);
			} catch(SQLException e) {
				result = "<span>" + e.getMessage() + "<span>";
				e.printStackTrace();
 			}
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("result", result);
		session.setAttribute("textBox", textBox);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	public String doSelectQuery(String textBox) throws SQLException 
	{
		String result;
		ResultSet table = statement.executeQuery(textBox);
		
		ResultSetMetaData metaData = table.getMetaData();
		
		int numOfColumns = metaData.getColumnCount();
		String tableOpeningHTML = "<div class='container-fluid'><div class='row justify-content-center'><div class='table-responsive-sm-10 table-responsive-md-10 table-responsive-lg-10'><table class='table'>";
		
		String tableColumnsHTML = "<thead class='thead-dark'><tr>";
		for (int i = 1; i <= numOfColumns; i++)
		{
			tableColumnsHTML += "<th scope='col'>" + metaData.getColumnName(i) + "</th>";
		}
		
		
		tableColumnsHTML += "</tr></thead>";
		
		String tableBodyHTML = "<tbody>";
		
		while(table.next())
		{
			tableBodyHTML += "<tr>";
			
			for(int i = 1; i<= numOfColumns; i++)
			{
				if(i == 1)
					tableBodyHTML += "<td scope'row'>" + table.getString(i) + "</th>";
				else
					tableBodyHTML += "<td>" + table.getString(i) + "</th>";
			}
			
			tableBodyHTML += "</tr>";
		
		}
		
		tableBodyHTML += "</tbody>";
		
		//close html
		String tableClosingHTML = "</table></div></div></div>";
		result = tableOpeningHTML + tableColumnsHTML + tableBodyHTML + tableClosingHTML;
		
		return result;
			
		
	}
	
	
	private String doUpdateQuery(String textBoxLowerCase) throws SQLException
	{
		String result = null;
		int numOfRowsUpdated = 0;
		
		ResultSet beforeQuantityCheck = statement.executeQuery("select COUNT(*) from shipments where quantity >= 100");
		beforeQuantityCheck.next();
		int numOfShipmentsWithQuantityGreaterThan100Before = beforeQuantityCheck.getInt(1);
		
		statement.executeUpdate("create table shipmentsBeforeUpdate like shipments");
		statement.executeUpdate("insert into shipmentsBeforeUpdate select * from shipments");
		
		numOfRowsUpdated = statement.executeUpdate(textBoxLowerCase);
		result = "<div> The Statement has been executed successfully </div><div>" + numOfRowsUpdated + " row(s) affected </div>";
		
		ResultSet afterQuantityCheck = statement.executeQuery("select COUNT(*) from shipments where quantity >= 100");
		afterQuantityCheck.next();
		int numOfShipmentsWithQuantityGreaterThan100After = afterQuantityCheck.getInt(1);
		
		result += "<div>" + numOfShipmentsWithQuantityGreaterThan100Before + " < " + numOfShipmentsWithQuantityGreaterThan100After + "</div>";
		
		if(numOfShipmentsWithQuantityGreaterThan100Before < numOfShipmentsWithQuantityGreaterThan100After)
		{
			int numberOfRowsAffectedAfterIncrementBy5 = statement.executeUpdate("update suppliers set status = status + where 5 snum in ( select distinct snum from shipments left join shipmentsBeforeUpdate using (snum, pnum, jnum, quantity) where shipmentsBeforeUpdate.snum is null)");
			result += "<div>Business Logic Detected! - Updating Supplier Status</div>";
			result += "<div>Business Logic Updated " + numberOfRowsAffectedAfterIncrementBy5 + " Supplier(s) status marks</div>";
		}

		statement.executeUpdate("drop table shipmentsBeforeUpdate");
		
		return result;
	}
	

	
}