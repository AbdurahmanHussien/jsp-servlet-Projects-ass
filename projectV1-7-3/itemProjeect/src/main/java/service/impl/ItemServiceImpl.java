package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Item;
import service.ItemService;

public class ItemServiceImpl implements  ItemService {

	private final DataSource dataSource;
	
	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Item> getAllItem() {
		List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM ITEM";
        
        try (Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query)){
               
               while (resultSet.next()) {
                   items.add(new Item(
                           resultSet.getInt("ID"),
                           resultSet.getString("NAME"),
                           resultSet.getDouble("PRICE"),
                           resultSet.getInt("TOTAL_NUMBER")
                   ));
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

}
