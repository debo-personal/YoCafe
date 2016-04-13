package com.cafeteria.dao;

import java.util.List;
import com.cafeteria.model.*;

public interface FoodMenuDAO {
	
	public void addCategory( String category );
	
	public void deleteCategory( int categoryId );
	
	public List<Category> getAllCategories();
	
	public void addMenuItem( MenuItem item );
	
	public void deleteMenuItem( int itemId );
	
	public void updateMenuItem( MenuItem item );
	
	public List<MenuItem> getAllMenuItems();
	
	public List<MenuItem> getCetegoryItems( int categoryId );
}
