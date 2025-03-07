<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Item" %>

<html>
<head>
    <title>Add Item Details</title>
    <link rel="stylesheet" href="css/item-details.css">
    
</head>
<body>
    <h2>Add Item Details</h2>
    <div class="form-container">
        <form action="ItemController" method="POST">
            <!-- Hidden input to define the action -->
            <input type="hidden" required name="itemId" value="${ID}">
            <label for="description">Description:</label>
            <input type="text" name="description" required>

            <label for="issue_date">Issue Date:</label>
            <input type="date" name="issue_date" required>

            <label for="expire_date">Expire Date:</label>
            <input type="date" name="expire_date" required>

            <input type="hidden" name="action" value="addItemDetails">
            <button type="submit">Add Item Details</button>
        </form>
    </div>
</body>
</html>
