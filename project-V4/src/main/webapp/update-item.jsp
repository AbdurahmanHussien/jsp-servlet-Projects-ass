<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
  <title>Update Item</title>
  <link rel="stylesheet" href="css/add-item.css">

</head>
<body>

<div class="container">
  <div class="text"> Update Item </div>
  <form action="ItemController" method="post">
  <input type="hidden" required  name="itemId" value="${itemSelected.id}">
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemName" value="${itemSelected.name}">
        <div class="underline"></div>
       	<label>Name</label>
      </div>
      <div class="input-data">
        <input type="text" required name="itemPrice" value="${itemSelected.price}">
        <div class="underline"></div>
        <label>PRICE</label>
      </div>
      <div class="input-data">
        <input type="text" required  name="itemTotalNumber" value="${itemSelected.totalNumber}">
        <div class="underline"></div>
        <label>TOTAL NUMBER</label>        
      </div>
      <div class="input-data">
      
        <input type="text" required name="description" value="${itemDetails.description}">
        <div class="underline"></div>
        <label>DESCRIPTION</label>
      </div>
      <div class="input-data">
        <input type="date" required  name="issue_date" value="${itemDetails.issueDate}">
        <div class="underline"></div>
        <label>ISSUE DATE</label>
      </div>
      <div class="input-data">      
        <input type="date" required  name="expire_date" value="${itemDetails.expireDate}">
        <div class="underline"></div>
       <label>EXPIRE DATE</label>
      </div>
      </div>
    <input type="hidden" required  name="action" value="updateItem">
    <input type="submit" value="Update" class="button">
  </form>

  <p class="back">
    <a href="ItemController?action=loadItems" >Back to items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>
