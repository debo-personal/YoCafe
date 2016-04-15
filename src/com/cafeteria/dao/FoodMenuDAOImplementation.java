package com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cafeteria.model.Category;
import com.cafeteria.model.MenuItem;
import com.cafeteria.util.DBUtil;

public class FoodMenuDAOImplementation implements FoodMenuDAO{
	
	private Connection dBConnection;
	
	public FoodMenuDAOImplementation() {
		dBConnection = DBUtil.getConnection();
	}

	@Override
	public void addCategory(String category) {
		try {
			String query 				   = "insert into category (name) values (?)";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setString( 1, category);
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCategory(int categoryId) {
		try {
			String query 				   = "delete from category where Id = ?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt(1, categoryId);
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> allCategories = new ArrayList<Category>();
		try {
			String query 			 = "select * from category";
			Statement stmt 			 = dBConnection.createStatement();
			ResultSet categoriesSet  = stmt.executeQuery( query );
			
			while( categoriesSet.next()) {
				Category category = new Category();
				category.setCategoryName(categoriesSet.getString( "Name" ));
				category.setCategoryId( categoriesSet.getInt( "Id" ));
				allCategories.add(category);
			}
			
			categoriesSet.close();  // what is the need of this? If I don't write this line how will it impact?
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allCategories;
	}

	@Override
	public Category getCategory( int categoryId ){
		Category category = new Category();
		try {
			String query 				   = "select * from category where category_id=?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt( 1, categoryId );
			ResultSet categorySet 		   = preparedStmt.executeQuery();
			category.setCategoryName( categorySet.getString( "name" ));
			category.setCategoryId( categorySet.getInt( "id" ));
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}
	
	@Override
	public void addMenuItem(MenuItem item) {
		try {
			String query 				   = "insert into item ( category_id, item_name, quantity, unit_price ) values (?,?,?,?)";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt( 1, item.getCategory().getCategoryId());
			preparedStmt.setString( 2, item.getItemName());
			preparedStmt.setInt( 3, item.getQuantity());
			preparedStmt.setDouble( 4, item.getUnitPrice());
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMenuItem(int itemId) {
		try {
			String query 				   = "delete from item where id = ?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt(1, itemId);
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateMenuItem(MenuItem item) {
		try {
			String query 				   = "update item set category_id=?, item_name=?, quantity=?, unit_price=? where id=?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt( 1, item.getCategory().getCategoryId());
			preparedStmt.setString( 2, item.getItemName());
			preparedStmt.setInt( 3, item.getQuantity());
			preparedStmt.setDouble( 4, item.getUnitPrice());
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MenuItem> getAllMenuItems() {
		List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
		try {
			String query  		= "select * from item";
			Statement stmt 		= dBConnection.createStatement();
			ResultSet itemsSet  = stmt.executeQuery( query );
			allMenuItems 		= translateToMenuItems( itemsSet );
			itemsSet.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allMenuItems;
	}

	@Override
	public List<MenuItem> getItemsForCategory(int categoryId) {
		List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
		try {
			String query 			= "select * from item where category_id=?";
			PreparedStatement stmt 	= dBConnection.prepareStatement( query );
			stmt.setInt( 1, categoryId );
			ResultSet itemsSet  	= stmt.executeQuery( query );
			allMenuItems 			= translateToMenuItems( itemsSet );
			itemsSet.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allMenuItems;
	}
	
	@Override
	public List<String> getItemContents( int itemId ) {
		List<String> itemContents = new ArrayList<String>();
		try {
			String query = "select * from item_details where item_id=?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt( 1, itemId );
			ResultSet itemContentSet = preparedStmt.executeQuery();
			String contentStr = itemContentSet.getString( "item_contents" );
			itemContents = decatenateString( contentStr );
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemContents;
	}
	
	@Override
	public void addItemContents( int itemId, List<String> itemContents ) {
		try {
			String query = "insert into item_details (item_id,item_contents) values(?,?)";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setInt( 1, itemId );
			preparedStmt.setString( 2, concatenateList( itemContents ));
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateItemContents( int itemId, List<String> itemContents ) {
		try {
			String query = "update item_details set item_contents=? where item_id=?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setString( 1, concatenateList( itemContents ));
			preparedStmt.setInt( 2, itemId );
			preparedStmt.executeUpdate();
			preparedStmt.close();
		} catch ( SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Helper Methods */

	private List<String> decatenateString( String itemContentStr )  {
		List<String> lstItemContents = new ArrayList<String>();
		if( itemContentStr != null ) {
			lstItemContents = Arrays.asList( itemContentStr.split("\\|"));
		}
		return lstItemContents;
	}
	
	private String concatenateList ( List<String> itemContentList ) {
		if( itemContentList != null && itemContentList.size() > 0 ) {
			StringBuilder strItemContents = new StringBuilder();
		    String sep = "";
		    for(String s: itemContentList) {
		    	strItemContents.append(sep).append(s);
		        sep = "|";
		    }
		    return strItemContents.toString();
		}
		return null;
	}
	
	private List<MenuItem> translateToMenuItems(ResultSet itemsSet) {
		List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
		if (itemsSet != null ) {
			try {
				while( itemsSet.next()) {
					MenuItem item = new MenuItem();
					int cat_id = itemsSet.getInt( "category_id" );
					item.setCategory( getCategory( cat_id ));
					item.setItemName( itemsSet.getString( "item_name" ));
					item.setQuantity( itemsSet.getInt( "quantity" ));
					item.setUnitPrice( itemsSet.getDouble( "unit_price" ));
					allMenuItems.add(item);
				}
			} catch ( SQLException e) {
				e.printStackTrace();
			}
		}
		return allMenuItems;
	}

}
