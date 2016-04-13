package com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	public void addMenuItem(MenuItem item) {
		try {
			String query 				   = "insert into item ( item_name, quantity, unit_price ) values (?,?,?)";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setString( 1, item.getItemName());
			preparedStmt.setInt( 2, item.getQuantity());
			preparedStmt.setDouble( 3, item.getUnitPrice());
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
			String query 				   = "update item set item_name=?, quantity=?, unit_price=? where id=?";
			PreparedStatement preparedStmt = dBConnection.prepareStatement( query );
			preparedStmt.setString( 1, item.getItemName());
			preparedStmt.setInt( 2, item.getQuantity());
			preparedStmt.setDouble( 3, item.getUnitPrice());
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
			String query 			 = "select * from item";
			Statement stmt 			 = dBConnection.createStatement();
			ResultSet itemsSet  	 = stmt.executeQuery( query );
			
			while( itemsSet.next()) {
				MenuItem item = new MenuItem();
				item.setItemName( itemsSet.getString( "item_name" ));
				item.setQuantity( itemsSet.getInt( "quantity" ));
				item.setUnitPrice( itemsSet.getDouble( "unit_price" ));
				allMenuItems.add(item);
			}
			
			itemsSet.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allMenuItems;
	}

	@Override
	public List<MenuItem> getCetegoryItems(int categoryId) {
		List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
		List<Integer> itemIds  = new ArrayList<Integer>();
		try {
			String itemIdQuery = "select item_id from item_category_mapping where category_id=?";
			PreparedStatement prepareStmt = dBConnection.prepareStatement( itemIdQuery );
			prepareStmt.setInt( 1, categoryId );
			ResultSet itemIdSet = prepareStmt.executeQuery();
			while( itemIdSet.next()) {
				itemIds.add( itemIdSet.getInt("item_id"));
			}
			prepareStmt.close();
			
			StringBuilder builder = new StringBuilder();

			for( int i = 0 ; i < itemIds.size(); i++ ) {
			    builder.append(",?");
			}
			String itemsQuery = "select * from item where id in " + builder.deleteCharAt(0).toString();
			PreparedStatement itemStatement = dBConnection.prepareStatement( itemIdQuery );
			
			int index = 1;
			for( int itemId : itemIds ) {
				itemStatement.setInt( index++, itemId );
			}
			ResultSet itemsSet = itemStatement.executeQuery();
			while( itemsSet.next()) {
				MenuItem menuItem = new MenuItem();
				menuItem.setItemName( itemsSet.getString( "item_name" ));
				menuItem.setQuantity( itemsSet.getInt( "quantity" ));
				menuItem.setUnitPrice( itemsSet.getDouble( "unit_price" ));
				allMenuItems.add(menuItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allMenuItems;
	}

}
