package contoller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Item;
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
			request.setAttribute("itemSelected", item);
			request.getRequestDispatcher("/update-item.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("===> " + e.getMessage());
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
			default:
				loadItems(request, response);
				
		}
	}

}
