package model;

import java.sql.Date;

public class itemDetails {
	private int id;
    private int itemId;
    private String description;
    private Date issueDate;
    private Date expireDate;

    public itemDetails(int itemId, String description, Date issueDate, Date expireDate) {
        this.itemId = itemId;
        this.description = description;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }
    public itemDetails () {}
    
    public itemDetails( String description, Date issueDate, Date expireDate) {
     
        this.description = description;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }
    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
