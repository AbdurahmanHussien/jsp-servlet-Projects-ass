<%@page import="model.Item"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="ItemController" method="post">
    <input type="hidden" name="action" value="saveDetails">

    <input type="hidden" name="itemId" value="${itemid}">

    <label>Description:</label>
    <textarea name="itemDescription" required></textarea><br>

    <label>Issue Date:</label>
    <input type="date" name="issueDate" required><br>

    <label>Expire Date:</label>
    <input type="date" name="expireDate" required><br>

    <button type="submit">Save Details</button>
</form>

</body>
</html>