package com.cafeteria.model;

import java.util.List;

public class MenuItem {
	
	private int itemId;
	private String itemName;
	private int quantity;
	private double unitPrice;
	private Category category;
	private List<String> contents;
	
	public int getItemId() {
		return this.itemId;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	public void setItemName( String name ) {
		this.itemName = name;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity( int quantity ) {
		this.quantity = quantity;
	}
	
	public double getUnitPrice() {
		return this.unitPrice;
	}
	public void setUnitPrice( double price ) {
		this.unitPrice = price;
	}
	
	public Category getCategory() {
		return this.category;
	}
	public void setCategory( Category category ) {
		this.category = category;
	}
	
	public List<String> getContents() {
		return this.contents;
	}
	public void setContents( List<String> contents ) {
		this.contents = contents;
	}
}
