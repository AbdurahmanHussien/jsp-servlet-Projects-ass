package model;

public class Item {

	private int id;
	
	private String name;
	
	private double price;
	
	private int totalNumber;
	
	private itemDetails itemDetails;

	public Item() {
		
	}
	public Item(String name, double price, int totalNumber) {
		this.name = name;
		this.price = price;
		this.totalNumber = totalNumber;
	}
	
	public Item(int id, String name, double price, int totalNumber) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.totalNumber = totalNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public itemDetails getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(itemDetails itemDetails) {
		this.itemDetails = itemDetails;
	}
	
	
}
