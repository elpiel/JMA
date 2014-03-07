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
	
	public static MyModel getAllCategories() throws Exception {
		prepState = MovieModel.con.prepareStatement("SELECT * FROM categories");
		result = prepState.executeQuery();
		
		model = new MyModel(result);
		return model;
	}
	
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
	
	public static void insertCategory(String cat_name) {
		try {
			prepState = CategoryModel.con.prepareStatement("INSERT INTO categories VALUES (null,?)");
			prepState.setString(1, cat_name);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void deleteCategory(int cat_id) {
		try {
			prepState = CategoryModel.con.prepareStatement("DELETE FROM categories WHERE cat_id=?");
			prepState.setInt(1, cat_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCategories(int[] ids) {
		for ( int i = 0; i < Array.getLength(ids); i++ ) {
			CategoryModel.deleteCategory(ids[i]);
		}
	}

}
