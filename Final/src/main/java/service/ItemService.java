package service;

import java.util.List;

import model.Item;
import model.itemDetails;

public interface ItemService {
	List<Item> getAllItem();
	Item getItemById(int id);
	int addItem(Item item);
	boolean updateItemById(Item item);
	boolean removeItemById(int id);
	void addItemDetails(int itemId,itemDetails itemDetails);
}
