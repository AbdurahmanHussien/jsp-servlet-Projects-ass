package contoller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Users;
import service.ItemService;
import service.impl.ItemServiceImpl;
import service.impl.UsersServiceImpl;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name = "jdbc/item")
	private DataSource dataSource;
	private UsersServiceImpl userService;

	
	public void init() throws ServletException {
		userService = new UsersServiceImpl(dataSource);
	}
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		        login(request, response);
		  
		}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	
		String email =  request.getParameter("email");
				
		String password = request.getParameter("password");
				
		String autoLogin = request.getParameter("autoLogin");

		

	    if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
	        request.setAttribute("errorMessage", "Please enter email and password.");
	        request.getRequestDispatcher("/login.jsp").forward(request, response);
	        return;
	    }

	    Users existingUser = userService.getUserByEmail(email);

	    if (existingUser != null && existingUser.getPassword().equals(password)) {
	    	
	        HttpSession session = request.getSession();
	        session.setAttribute("loggedInUser", existingUser);

	        if ("true".equals(autoLogin)) {
	        	
	            Cookie emailCookie = new Cookie("userEmail", email);
	            emailCookie.setMaxAge(60 * 60 * 24 * 2); 
	            response.addCookie(emailCookie);

	            Cookie passwordCookie = new Cookie("userPassword", password);
	            passwordCookie.setMaxAge(60 * 60 * 24 * 2);
	            response.addCookie(passwordCookie);
	        }
	        response.sendRedirect("ItemController?action=loadItems");
	        
	}
	    
	    
	    else {
          request.getSession().setAttribute("errorMessage", "Invalid Email or Password");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}

	private void signUp(HttpServletRequest request, HttpServletResponse response) {
		
		String userName = request.getParameter("username");
		String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    
	    Users existingUser = userService.getUserByEmail(email);
	   
	    if(existingUser != null) {
	        request.getSession().setAttribute("error", "This email is already registered!");
	        try {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }
	    
	    Users newUser=  new Users(userName,email ,password);
	    userService.addUser(newUser);
	    
	    request.getSession().setAttribute("loggedInUser", newUser);
	    
	    Cookie emailCookie = new Cookie("userEmail", email);
	    emailCookie.setMaxAge(60 * 60 * 24 * 2);
	    response.addCookie(emailCookie);
	    
	    

	    Cookie passCookie = new Cookie("userPassword", password);
	    passCookie.setMaxAge(60 * 60 * 24 * 2);
	    response.addCookie(passCookie);
	    
	    
        try {
        	response.sendRedirect("ItemController?action=loadItems");
        	}
        catch (IOException e) {
			e.printStackTrace();
		}

	    
		
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Deleted cookie: ");

		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }

	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	                cookie.setValue("");
	                cookie.setMaxAge(0); 
	                
	                System.out.println("Deleted cookie: " + cookie.getName());

	                response.addCookie(cookie);
	           
	        }
	    }

	    response.sendRedirect("login.jsp");
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "login";
		}
		
		if (action.equals("login")) {
			login(request, response);

		} else if (action.equals("sign")) {
			signUp(request, response);

		} else if (action.equals("logout")) {
			logout(request, response);
			
		} 
		
		
	}


	


	

}
