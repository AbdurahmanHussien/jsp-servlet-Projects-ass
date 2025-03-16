package service;

import java.util.List;

import model.Item;
import model.ItemDetails;

public interface ItemService {
	List<Item> getAllItem();
	Item getItemById(int id);
	boolean addItem(Item item);
	boolean updateItemById(Item item);
	boolean removeItemById(int id);
	void addItemDetails(ItemDetails itemDetails);
	ItemDetails getItemDetailsByItemId(int itemId);
	boolean removeItemDetailsById(int id);
	
}
