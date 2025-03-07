package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Item;
import model.ItemDetails;
import service.ItemService;

public class ItemServiceImpl implements  ItemService {

	private final DataSource dataSource;
	
	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Item> getAllItem() {
		List<Item> items = new ArrayList<>();
		String query = "SELECT i.ID,i.NAME,i.PRICE, i.TOTAL_NUMBER,d.DESCRIPTION,d.ISSUE_DATE,d.EXPIRE_DATE FROM ITEM i LEFT JOIN ITEM_DETAILS d ON i.ID = d.ITEM_ID";        
        try (Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query)){
               
               while (resultSet.next()) {
            	   Item item = new Item(
       	                resultSet.getInt("id"),
       	                resultSet.getString("name"),
       	                resultSet.getDouble("price"),
       	                resultSet.getInt("total_number")
       	            );
                   ItemDetails itemDetails = new ItemDetails(
                		resultSet.getString("description"),
       	                resultSet.getDate("issue_date"),
    	                resultSet.getDate("expire_date"));
                   
                   item.setItemDetails(itemDetails);
                   items.add(item);
               }
           } catch (Exception e) {
               throw new RuntimeException("Error fetching items", e);
           }
           return items;
	}
	@Override
	public Item getItemById(int id) {
		String query = "SELECT * FROM ITEM WHERE ID = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Item(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getDouble("PRICE"),
                            resultSet.getInt("TOTAL_NUMBER")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching item by ID", e);
        }
        return null;
	}
	
	public ItemDetails getItemDetailsByItemId(int itemId) {
	    String query = "SELECT * FROM ITEM_DETAILS WHERE ITEM_ID = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {
	        
	        stmt.setInt(1, itemId);
	        
	        try (ResultSet resultSet = stmt.executeQuery()) {
	            if (resultSet.next()) {
	                return new ItemDetails(
	                        resultSet.getInt("ITEM_ID"),
	                        resultSet.getString("DESCRIPTION"),
	                        resultSet.getDate("ISSUE_DATE"),
	                        resultSet.getDate("EXPIRE_DATE")
	                );
	            }
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error fetching item details by ITEM_ID", e);
	    }
	    return null;
	}


	@Override
	public boolean addItem(Item item) {
		String query = "INSERT INTO ITEM (NAME, PRICE, TOTAL_NUMBER) VALUES (?, ?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getTotalNumber());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error adding item", e);
        }
	}

	@Override
	public boolean updateItemById(Item item) {
	    String updateItemQuery = "UPDATE ITEM SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
	    String updateItemDetailsQuery = "UPDATE ITEM_DETAILS SET DESCRIPTION = ?, ISSUE_DATE = ?, EXPIRE_DATE = ? WHERE ITEM_ID = ?";

	    try (Connection connection = dataSource.getConnection()) {
	        connection.setAutoCommit(false); 

	        try (PreparedStatement stmt1 = connection.prepareStatement(updateItemQuery);
	             PreparedStatement stmt2 = connection.prepareStatement(updateItemDetailsQuery)) {

	            stmt1.setString(1, item.getName());
	            stmt1.setDouble(2, item.getPrice());
	            stmt1.setInt(3, item.getTotalNumber());
	            stmt1.setInt(4, item.getId());
	            stmt1.executeUpdate();

	            ItemDetails itemDetails = item.getItemDetails();
	            if (itemDetails != null) {
	                stmt2.setString(1, itemDetails.getDescription());
	                stmt2.setDate(2, itemDetails.getIssueDate());
	                stmt2.setDate(3, itemDetails.getExpireDate());
	                stmt2.setInt(4, item.getId());
	                stmt2.executeUpdate();
	            }

	            connection.commit();
	            return true;

	        } catch (Exception e) {
	            connection.rollback(); 
	            throw new RuntimeException("Error updating item", e);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Database connection error", e);
	    }
	}


	@Override
	public boolean removeItemById(int id) {
		 String query = "DELETE FROM ITEM WHERE ID = ?";
	        
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {
	            
	            stmt.setInt(1, id);
	            
	            return stmt.executeUpdate() > 0;
	        } catch (Exception e) {
	            throw new RuntimeException("Error deleting item", e);
	        }
	}
	@Override
	public boolean removeItemDetailsById(int id) {
		 String query = "DELETE FROM ITEM_DETAILS WHERE item_id = ?";
	        
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {
	            
	            stmt.setInt(1, id);
	            
	            return stmt.executeUpdate() > 0;
	        } catch (Exception e) {
	            throw new RuntimeException("Error deleting item Details", e);
	        }
	}
	@Override
	public void addItemDetails(ItemDetails itemDetails) {
		
	    String query = "INSERT INTO ITEM_DETAILS (item_id, description, issue_date, expire_date) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setInt(1, itemDetails.getItemId());
            stmt.setString(2, itemDetails.getDescription());
            stmt.setDate(3, itemDetails.getIssueDate());
            stmt.setDate(4, itemDetails.getExpireDate());

            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Error adding item", e);
        }
	

}
}
