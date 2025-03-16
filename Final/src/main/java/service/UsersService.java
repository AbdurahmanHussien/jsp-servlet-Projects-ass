package service;

import model.Users;

public interface UsersService {
	
	
	void addUser(Users user);
	
	Users getUserByEmail(String email);
	

}
