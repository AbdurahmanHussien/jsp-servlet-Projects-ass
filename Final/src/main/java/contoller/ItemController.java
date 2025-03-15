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
import model.itemDetails;
import service.ItemService;
import service.impl.ItemServiceImpl;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
    
    @Resource(name = "jdbc/item")
    private DataSource dataSource;
    private ItemService itemService;

    public void init() throws ServletException {
        itemService = new ItemServiceImpl(dataSource);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "loadItems";
        
        switch (action) {
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
            case "loadItemDetailsForm":
                loadItemDetailsForm(request, response);
                break;
            case "addItemDetails":
                addItemDetails(request, response);
                break;
            default:
                loadItems(request, response);
        }
    }

    private void loadItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> items = itemService.getAllItem();
        request.setAttribute("allItems", items);
        request.getRequestDispatcher("/load-items.jsp").forward(request, response);
    }
    
    private void loadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Item item = itemService.getItemById(itemId);
            request.setAttribute("itemSelected", item);
            request.getRequestDispatcher("/update-item.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading item", e);
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Item item = new Item(
                request.getParameter("itemName"),
                Double.parseDouble(request.getParameter("itemPrice")),
                Integer.parseInt(request.getParameter("itemTotalNumber"))
            );
            int itemId = itemService.addItem(item);
            response.sendRedirect("ItemController?action=loadItemDetailsForm&itemId=" + itemId);
        } catch (Exception e) {
            throw new ServletException("Error adding item", e);
        }
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

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            itemService.removeItemById(itemId);
            response.sendRedirect("ItemController?action=loadItems");
        } catch (Exception e) {
            throw new ServletException("Error deleting item", e);
        }
    }

    private void loadItemDetailsForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemIdStr = request.getParameter("itemId");

        if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
            throw new ServletException("Error: itemId is missing in loadItemDetailsForm.");
        }

        request.setAttribute("itemId", itemIdStr);
        request.getRequestDispatcher("/item-details-form.jsp").forward(request, response);
    }


    private void addItemDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String itemIdStr = request.getParameter("itemId");
            System.out.println("Received itemId: " + itemIdStr); // طباعة القيم المستقبلة

            if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
                throw new ServletException("Error: itemId is missing or empty.");
            }

            int itemId = Integer.parseInt(itemIdStr);
            String description = request.getParameter("itemDescription");
            String issueDateStr = request.getParameter("issueDate");
            String expireDateStr = request.getParameter("expireDate");

            Date issueDate = (issueDateStr != null && !issueDateStr.isEmpty()) ? Date.valueOf(issueDateStr) : null;
            Date expireDate = (expireDateStr != null && !expireDateStr.isEmpty()) ? Date.valueOf(expireDateStr) : null;

            itemDetails details = new itemDetails(description, issueDate, expireDate);
            itemService.addItemDetails(itemId, details);

            response.sendRedirect("ItemController?action=loadItems");
        } catch (NumberFormatException e) {
            throw new ServletException("Error: Invalid itemId format.", e);
        } catch (Exception e) {
            throw new ServletException("Error adding item details", e);
        }
    }


}
