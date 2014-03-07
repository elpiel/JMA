package category;

import db.DBUtil;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import movie.MovieModel;
import db.MyModel;

public class CategoryModel {

	public static Connection con = DBUtil.getConnected();
	static Statement state = null;
	static PreparedStatement prepState = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	/**
	 * Gets all Categories from the Data Base
	 * 
	 * @return The model for the JTable
	 * @throws Exception
	 */
	public static MyModel getAllCategories() throws Exception {
		prepState = MovieModel.con.prepareStatement("SELECT * FROM categories");
		result = prepState.executeQuery();
		
		model = new MyModel(result);
		return model;
	}
	
	/**
	 * Get the Category by Category ID
	 * 
	 * @param cat_id Category ID to be fetched
	 * @return       ResultSet with One Category
	 */
	public static ResultSet getCategoryById(int cat_id) {
		try {
			prepState = CategoryModel.con.prepareStatement("SELECT * FROM categories WHERE cat_id=?");
			prepState.setInt(1, cat_id);
			result = prepState.executeQuery();
			
			result.first();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Insert Category
	 * 
	 * @param cat_name Category name
	 * @return Integer ID of new Category
	 */
	public static int insertCategory(String cat_name) {
		int newRecordID = -1;
		try {
			prepState = CategoryModel.con.prepareStatement("INSERT INTO categories(cat_id, cat_name) VALUES (null,?)"); 

			prepState.setString(1, cat_name);
			prepState.execute();
			
			ResultSet result = prepState.getGeneratedKeys(); // get the ID of the New record from Statement
			result.next(); // to use results call next()
			
			newRecordID = result.getInt(1); // get the ID from ResultSet
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newRecordID;
	}
	
	/**
	 * Update Category
	 * 
	 * @param cat_id
	 * @param newCat_name
	 */
	public static void updateCategory(int cat_id, String newCat_name) {
		try {
			prepState = CategoryModel.con.prepareStatement("UPDATE categories SET cat_name=? WHERE cat_id=?");
			prepState.setString(1, newCat_name);
			prepState.setInt(2, cat_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a Category
	 * 
	 * @param cat_id The Category ID to be deleted
	 */
	public static void deleteCategory(int cat_id) {
		try {
			prepState = CategoryModel.con.prepareStatement("DELETE FROM categories WHERE cat_id=?");
			prepState.setInt(1, cat_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete more than one Category
	 * @param ids Arrays of ids to be deleted 
	 */
	public static void deleteCategories(int[] ids) {
		for ( int i = 0; i < Array.getLength(ids); i++ ) {
			CategoryModel.deleteCategory(ids[i]);
		}
	}

}
