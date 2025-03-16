package contoller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Item;
import model.ItemDetails;
import service.ItemService;
import service.impl.ItemServiceImpl;

/*
 * for search
 * request.getRequestDispatcher()
 * response.sendRedirect()
 */

/**
 * Servlet implementation class ItemController
 */
// http://localhost:8080/items-services-project/ItemController?action=loadItems
//http://localhost:8080/items-services-project/ItemController?action=loadItem
//http://localhost:8080/items-services-project/ItemController?action=addItem
//http://localhost:8080/items-services-project/ItemController?action=deleteItem
//http://localhost:8080/items-services-project/ItemController?action=updateItem
//http://localhost:8080/items-services-project/ItemController?action=xxxx
//http://localhost:8080/items-services-project/ItemController

// http://localhost:8080/item-service-projeect-903/ItemController
// http://localhost:8080/item-service-projeect-903/ItemController?

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {


	@Resource(name = "jdbc/item")
	private DataSource dataSource;
	private ItemService itemService;

	
	public void init() throws ServletException {
	    itemService = new ItemServiceImpl(dataSource);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadItems(request, response);
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	    	 Item item = new Item(
	    			 Integer.parseInt(request.getParameter("itemId")),
    					request.getParameter("itemName"),
    					Double.parseDouble(request.getParameter("itemPrice")),
    					Integer.parseInt(request.getParameter("itemTotalNumber"))
    	            );
                ItemDetails itemDetails = new ItemDetails(
        				request.getParameter("description"),
        				Date.valueOf(request.getParameter("issue_date")),
        				Date.valueOf(request.getParameter("expire_date")));
                
                item.setItemDetails(itemDetails);

	        itemService.updateItemById(item);
	        response.sendRedirect("ItemController?action=loadItems");
	    } catch (Exception e) {
	        throw new ServletException("Error updating item", e);
	    }
	}


	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
		try {
		itemService.removeItemById(Integer.parseInt(request.getParameter("itemId")));
        response.sendRedirect("ItemController?action=loadItems");
	}
		catch (Exception e) {
	        throw new ServletException("Error Deleting item", e);
			
		}
	}
	private void deleteItemDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
		try {
		itemService.removeItemDetailsById(Integer.parseInt(request.getParameter("itemId")));
        response.sendRedirect("ItemController?action=loadItems");
	}
		catch (Exception e) {
	        throw new ServletException("Error Deleting item", e);
			
		}
	}
	
	
	
	
	private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
		Item item = new Item(
				request.getParameter("itemName"),
				Double.parseDouble(request.getParameter("itemPrice")),
				Integer.parseInt(request.getParameter("itemTotalNumber"))
		);
		
		itemService.addItem(item);
        response.sendRedirect("ItemController?action=loadItems");
	} catch (Exception e) {
        throw new ServletException("Error Deleting item", e);
		
		}
	}

	
	private void loadItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			Item item = itemService.getItemById(Integer.parseInt(request.getParameter("itemId")));
			ItemDetails itemDetails = itemService.getItemDetailsByItemId(Integer.parseInt(request.getParameter("itemId")));
			request.setAttribute("itemSelected", item);
			request.setAttribute("itemDetails", itemDetails);
			request.getRequestDispatcher("/update-item.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("===> " + e.getMessage());
		}	
	}
	
	private void loadItemId(HttpServletRequest request, HttpServletResponse response) {
		int iD = Integer.parseInt(request.getParameter("itemId"));
		request.setAttribute("ID", iD);
		try {
			request.getRequestDispatcher("/add-item-details.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void loadItems(HttpServletRequest request, HttpServletResponse response) {
		
		List<Item> items = itemService.getAllItem();
		
		request.setAttribute("allItems", items);
		try {
			request.getRequestDispatcher("/load-items.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("===> " + e.getMessage());
		}
	}
	private void addItemDetails (HttpServletRequest request, HttpServletResponse response) {
		
		ItemDetails itemDetails = new ItemDetails(
				Integer.parseInt(request.getParameter("itemId")),
				request.getParameter("description"),
				Date.valueOf(request.getParameter("issue_date")),
				Date.valueOf(request.getParameter("expire_date")));
		
		itemService.addItemDetails(itemDetails);
		
        try {
			response.sendRedirect("ItemController?action=loadItems");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "loadItems";
		}
		
		switch(action) {
			case "loadItems":
				loadItems(request, response);
				break;
			case "loadItem":
				loadItem(request, response);
				break;
			case "addItem":
				addItem(request, response);
				break;
			case "deleteItem":
				deleteItem(request, response);
				break;
			case "updateItem":
				updateItem(request, response);
				break;
			case "addItemDetails":
				addItemDetails(request, response);
				break;
			case "loadItemId":
				loadItemId(request, response);
				break;
			case "DeleteItemDetails":
				deleteItemDetails(request, response);
				break;
			default:
				loadItems(request, response);
				
		}
	}

}
