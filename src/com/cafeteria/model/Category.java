package com.cafeteria.model;

public class Category {

	private int categoryId;
	private String categoryName;
	
	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId( int id ) {
		this.categoryId = id;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}
	public void setCategoryName( String categoryName ) {
		this.categoryName = categoryName;
	}
}
