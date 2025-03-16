package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Users;
import service.UsersService;

public class UsersServiceImpl implements UsersService{

	
	private  DataSource dataSource;
	
	public UsersServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	 public void addUser(Users user) {
	        String sql = "INSERT INTO users (USERNAME, email, password) VALUES (?, ?, ?)";

	        try (Connection conn = dataSource.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, user.getName());
	            stmt.setString(2, user.getEmail());
	            stmt.setString(3, user.getPassword());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	@Override
	 public Users getUserByEmail(String email) {
		    String sql = "SELECT * FROM users WHERE email = ?";
		    try (Connection conn = dataSource.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, email);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return new Users(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null;
		}


	
}
