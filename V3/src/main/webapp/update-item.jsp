<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>ADD Item</title>
  <link rel="stylesheet" href="css/add-item.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
    Update Item
  </div>
  <form action="ItemController" method="post">
  <input type="hidden" required  name="itemId" value="${itemSelected.id}">
    <div class="form-row">
      <div class="input-data">
       	<label>Name</label>
        <input type="text" required name="itemName" value="${itemSelected.name}">
        <div class="underline"></div>
         <div class="underline"></div>
       
      </div>
      <div class="input-data">
      	<label>PRICE</label>
        <input type="text" required name="itemPrice" value="${itemSelected.price}">
        <div class="underline"></div>
        
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
       <label>TOTAL NUMBER</label>
      
        <input type="text" required  name="itemTotalNumber" value="${itemSelected.totalNumber}">
        <div class="underline"></div>
      </div>
      <div class="input-data">
       <label>DESCRIPTION</label>
      
        <input type="text" required name="description" value="${itemDetails.description}">
        <div class="underline"></div>
      </div>
    </div>
    
    <div class="form-row">
      <div class="input-data">
     	<label>ISSUE DATE</label>
        <input type="text" required  name="issue_date" value="${itemDetails.issueDate}">
        <div class="underline"></div>
        
      </div>
      <div class="input-data">
        <label>EXPIRE DATE</label>
      
        <input type="text" required name="expire_date" value="${itemDetails.expireDate}">
        <div class="underline"></div>
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
