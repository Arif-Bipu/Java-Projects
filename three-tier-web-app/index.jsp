<!doctype html>

<%--

/*  Name:  Arif Bipu
 * Course:CNT 4714–Fall2020–
 * Project Four
 * Assignment title:A Three-Tier Distributed Web-Based Application
 * Date:December 4, 2020 */
 --%>


 <% 
	String textBox = (String) session.getAttribute("textBox");
	String result = (String) session.getAttribute("result");
	if(result == null)
		{
			result = " ";
		}
	if(textBox == null)
		{
			textBox = " ";
		}
%>



<html lang="en">
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	<title>Project 4</title>

	<script src="reset.js"></script>

	<style type="text/css">
		<!--
		body{background-color: lightblue;}
		-->
	</style>

</head>
<body>

	<div class="container-fluid ">
        <row class="row justify-content-center">
            <h1 class="text-white">Welcome to the Fall 2020 Project 4 Enterprise Systems</h1>
			<h1 class="text-center col-sm-12 col-md-12 col-lg-12">A Remote Database Management System </h1>
            <div class="text-center col-sm-12 col-md-12 col-lg-12"> You are connected to the Project 4 Database.</div>
            <div class="text-center col-sm-12 col-md-12 col-lg-12"> Please enter any valid sql query or update statement.</div>
            <div class="text-center col-sm-12 col-md-12 col-lg-12"> If no query/update command is given the Execute button will display all supplier information in the database.</div>
            <div class="text-center col-sm-12 col-md-12 col-lg-12">All execution results will appear below</div>
            <form action = "/Project4/SQLQueryServlet" method = "post" style="margin-top: 15px;" class="text-center">
                <div class="form-group row">
                    <div class=" col-sm-12 col-md-12 col-lg-12">
                        <textarea name="textBox" class="form-control" id="textBox" rows="8" cols="50"><%= textBox %></textarea>
                    </div>
                </div>

                <button style="margin-bottom: 15px;" type="submit" class="btn btn-dark">Execute Command</button>
                <button onClick="reset();" style="margin-bottom: 15px;" type="reset" class="btn btn-dark">Clear Form</button>
            </form>
        </row>
    </div>


    <div class="text-center">
       
        <%= result %>
    </div>


    </div>



</body>

</html>