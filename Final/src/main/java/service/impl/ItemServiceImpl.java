package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Item;
import model.itemDetails;
import service.ItemService;

public class ItemServiceImpl implements  ItemService {

	private final DataSource dataSource;
	
	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	@Override
	public List<Item> getAllItem() {
	    List<Item> items = new ArrayList<>();
	    String query = "SELECT i.id, i.name, i.price, i.total_number, " +
	                   "DBMS_LOB.SUBSTR(d.description, 4000, 1) AS description, " +
	                   "d.issue_date, d.expire_date " +
	                   "FROM Item i LEFT JOIN ItemDetails d ON i.id = d.item_id";

	    try (Connection connection = dataSource.getConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet resultSet = stmt.executeQuery(query)) {

	        while (resultSet.next()) {
	            Item item = new Item(
	                resultSet.getInt("id"),
	                resultSet.getString("name"),
	                resultSet.getDouble("price"),
	                resultSet.getInt("total_number")
	            );

	            itemDetails details = new itemDetails(
	                resultSet.getString("description"),
	                resultSet.getDate("issue_date"),
	                resultSet.getDate("expire_date")
	            );

	            item.setItemDetails(details);
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

	@Override
	public int addItem(Item item) {
		
        int generatedId = -1;

		String query = "INSERT INTO ITEM (NAME, PRICE, TOTAL_NUMBER) VALUES (?, ?, ?)";
        
		try (Connection conn = dataSource.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query, new String[]{"id"})) {
	            
	            stmt.setString(1, item.getName());
	            stmt.setDouble(2, item.getPrice());
	            stmt.setInt(3, item.getTotalNumber());

	            stmt.executeUpdate();
	            
	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    generatedId = rs.getInt(1);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return generatedId;
	    }

	@Override
	public boolean updateItemById(Item item) {
		 String query = "UPDATE ITEM SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
	        
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(query)) {
	            
	            stmt.setString(1, item.getName());
	            stmt.setDouble(2, item.getPrice());
	            stmt.setInt(3, item.getTotalNumber());
	            stmt.setInt(4, item.getId());
	            
	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (Exception e) {
	            throw new RuntimeException("Error updating item", e);
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
	
	
	public void addItemDetails(int itemId, itemDetails details) {
	    String sql = "INSERT INTO ITEMDETAILS (item_id, description, issue_date, expire_date) VALUES (?, ?, ?, ?)";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, itemId);
	        stmt.setString(2, details.getDescription());
	        stmt.setDate(3, details.getIssueDate() != null ? new java.sql.Date(details.getIssueDate().getTime()) : null);
	        stmt.setDate(4, details.getExpireDate() != null ? new java.sql.Date(details.getExpireDate().getTime()) : null);

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Error adding item details", e);
	    }
	}


}
	



