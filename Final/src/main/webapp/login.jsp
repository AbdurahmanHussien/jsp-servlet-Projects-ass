<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login & Sign Up</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    
</head>
<body>
       <% 
       
       Cookie[] cookies = request.getCookies();
       String savedEmail = "";
       String savedPassword = "";

       if (cookies != null) {
           for (Cookie cookie : cookies) {
               if ("userEmail".equals(cookie.getName())) {
                   savedEmail = cookie.getValue();
               }
               if ("userPassword".equals(cookie.getName())) {
                   savedPassword = cookie.getValue();
               }

           }
           if (savedEmail != null && savedPassword != null && !savedEmail.isEmpty() && !savedPassword.isEmpty()) {
        	   response.sendRedirect("UserController?email=" + savedEmail + "&password=" + savedPassword + "&autoLogin=true");
        	    return;
       	}

       }
      
%>

    <div class="login-wrap">
        <div class="login-html">
            <input id="tab-1" type="radio" name="tab" class="sign-in" checked >
            <label for="tab-1" class="tab">Sign In</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up" >
            <label for="tab-2" class="tab">Sign Up</label>
            <div class="login-form">
                <!-- Sign In Form -->
                <form action="UserController" method="post">
                    <div class="sign-in-htm">
    
                        <div class="group">
                            <label for="user" class="label">Email</label>
                            <input type="email" required name="email" class="input">
                            
                        </div>
                        <div class="group">
                            <label for="pass" class="label">Password</label>
                            <input id="pass" type="password" required name="password" class="input" >
                        </div>
                        <div class="remember">
    					<input id="rememberMe" type="checkbox" name="autoLogin" value="true">
    					<label for="rememberMe" class="rememlabel">Remember Me</label>
						</div>
                        
                         
                        <div class="group">
                            <input type="submit" class="button" value="Sign In">
                            <%
   								 String errorMessage = (String) request.getSession().getAttribute("errorMessage");
    									if (errorMessage != null) {
									%>
 							   <p style="color: red; font-weight: bold;"><%= errorMessage %></p>
										<%
  									  }
									%>
                             <input type="hidden" required  name="action" value="login">
                            
                        </div>
                    </div>
                </form>
                
                <!-- Sign Up Form -->
                <form action="UserController" method="post">
                    <div class="sign-up-htm">
                        <div class="group">
                            <label for="new-user" class="label">Username</label>
                            <input id="new-user" type="text" required name="username" class="input">
                        </div>
                        <div class="group">
                            <label for="user" class="label">Email</label>
                            <input id="user" type="email" required name="email" class="input">
                        </div>
                        <div class="group">
                            <label for="new-pass" class="label">Password</label>
                            <input id="new-pass" type="password" required name="password" class="input">
                        </div>
                        <div class="group">
                            <input type="submit" class="button" value="Sign Up">
                             <%
   								 String error = (String) request.getSession().getAttribute("error");
    									if (error != null) {
									%>
 							   <p style="color: red; font-weight: bold;"><%= error %></p>
										<%
  									  }
									%>
									<%
   								 String error2 = (String) request.getSession().getAttribute("error2");
    									if (error2 != null) {
									%>
 							   <p style="color: red; font-weight: bold;"><%= error2 %></p>
										<%
  									  }
									%>
                           	<input type="hidden" required  name="action" value="sign">
                            
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
