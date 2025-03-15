<%@page import="model.Item"%>
<%@page import="model.itemDetails"%>
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
	%>
    <table>
        <h1>Items</h1>
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PRICE</th>
            <th>TOTAL_NUMBER</th>
            <th>DESCRIPTION</th>
            <th>ISSUE DATE</th>
            <th>EXPIRE DATE</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
        	for(Item item: items){
        		itemDetails details = item.getItemDetails();
        %>
        	<tr>
        		<td><%= item.getId() %></td>
        		<td><%= item.getName() %></td>
        		<td> $<%= item.getPrice() %> </td>
        		<td><%= item.getTotalNumber() %></td>
        		<td><%= details != null && details.getDescription() != null ? details.getDescription() : "N/A" %></td>
				<td><%= details != null && details.getIssueDate() != null ? details.getIssueDate() : "N/A" %></td>
				<td><%= details != null && details.getExpireDate() != null ? details.getExpireDate() : "N/A" %></td>

        		<td>
        		<form action="ItemController" method="post" >
        			<input type="hidden" name="itemId" value="<%= item.getId() %>">
        			<input type="hidden" name="action" value="loadItem">
        			<input type="submit" value="update">
        		</form>
        		<form action="ItemController" method="post"  >
        			<input type="hidden" name="itemId" value="<%= item.getId() %>">
        			<input type="hidden" name="action" value="deleteItem">
        			<input type="submit" value="Delete">
        		</form>
                <% if (details == null || (details.getDescription() == null && details.getIssueDate() == null && details.getExpireDate() == null)) { %>
                <form action="ItemController" method="post">
                    <input type="hidden" name="itemId" value="${item.id}">
                    <input type="hidden" name="action" value="addItemDetails">
                    <input type="submit" value="Add Details">
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
