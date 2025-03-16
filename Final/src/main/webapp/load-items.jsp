<%@page import="model.Item"%>
<%@page import="model.ItemDetails"%>
<%@page import="model.Users"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Items</title>
    <link rel="stylesheet" href="css/show-items.css">
</head>
<body>
<div class="layer">

	<%
		List<Item> items = (List<Item>)request.getAttribute("allItems");
	
	
    	Users loggedInUser = (Users) session.getAttribute("loggedInUser");
   		 if (loggedInUser == null) {
   	       request.getRequestDispatcher("/login.jsp").forward(request, response);
   		 }
   		 else {
		%>
		<h2>Welcome, <%= loggedInUser.getName() %>!</h2>
		<% } %>
		
		<form action="UserController" method="post">
   		 <input type="hidden" name="action" value="logout">
    	<button class="A" type="submit">Logout</button>
		</form>

		
	
    <table>
        <h1>Items</h1>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Total Number</th>
            <th>Description</th>
            <th>Issue Date</th>
            <th>Expire Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
        	for(Item item: items){
        		ItemDetails itemDetails = item.getItemDetails();
        %>
        	<tr>
        		<td><%= item.getId() %></td>
        		<td><%= item.getName() %></td>
        		<td> $<%= item.getPrice() %> </td>
        		<td><%= item.getTotalNumber() %></td>
        		<td><%= itemDetails != null && itemDetails.getDescription() != null ? itemDetails.getDescription() : "-" %></td>
				<td><%= itemDetails != null && itemDetails.getIssueDate() != null ? itemDetails.getIssueDate() : "-" %></td>
				<td><%= itemDetails != null && itemDetails.getExpireDate() != null ? itemDetails.getExpireDate() : "-" %></td>
        		<td>
        		
        		<form action="ItemController" method="post" >
        				<input type="hidden" name="itemId" value="<%= item.getId() %>">
        				<input type="hidden" name="action" value="loadItem">
        				<input type="submit" value="Update Item">
        			</form>
        			<form action="ItemController" method="post"  >
        				<input type="hidden" name="itemId" value="<%= item.getId() %>">
        				<input type="hidden" name="action" value="deleteItem">
        				<input type="submit" value="Delete Item">
        			</form>
        			
        			 <% if (itemDetails == null || (itemDetails.getDescription() == null && itemDetails.getIssueDate() == null && itemDetails.getExpireDate() == null)) { %>
                <form action="ItemController" method="post">
                   <input type="hidden" name="itemId" value="<%= item.getId() %>">
                 <input type="hidden" name="action" value="loadItemId">
                    <input type="submit" value="Add Details">
                </form>
                <% } %>
                
                
                 <% if ( (itemDetails.getDescription() != null && itemDetails.getIssueDate() != null && itemDetails.getExpireDate() != null)) { %>
                <form action="ItemController" method="post">
                    <input type="hidden" name="itemId" value="<%= item.getId() %>"> 
                    <input type="hidden" name="action" value="DeleteItemDetails">
                    <input type="submit" value="Delete Details">
                </form>
                <% } %>
        			
        		</td>
        	</tr>
        <% } %>
        </tbody>
    </table>

    <button class="f"><a href="add-item.html">Add Item</a></button>
    

</div>

</body>
</html>
