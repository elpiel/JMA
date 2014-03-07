package movie;

import db.DBUtil;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import category.CategoryModel;
import db.MyModel;

public class MovieModel {

	public static Connection con = DBUtil.getConnected();
	static Statement state = null;
	static PreparedStatement prepState = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	/**
	 * Get all Movies for Movie Table
	 * 
	 * @return MyModel
	 * @throws Exception
	 */
	
	public static MyModel getAllMovies() throws Exception {
		prepState = MovieModel.con.prepareStatement("SELECT * FROM movies");
		result = prepState.executeQuery();
		
		model = new MyModel(result);
		return model;
	}
	
	/**
	 * Get the Movie by Movie ID
	 * 
	 * @param m_id
	 * @return ResultSet|null
	 */
	public static ResultSet getMovieById(int m_id) {
		try {
			prepState = MovieModel.con.prepareStatement("SELECT * FROM movies WHERE m_id=?");
			prepState.setInt(1, m_id);
			result = prepState.executeQuery();
			
			result.first();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Getting the Categories of the Movie by Movie ID
	 * 
	 * @param m_id
	 * @return ResultSet|null
	 */
	public static ResultSet getMovieCatsById(int m_id) {
		try {
			prepState = CategoryModel.con.prepareStatement(
					"SELECT c.cat_name FROM categories AS c"
					+ "LEFT JOIN movies_categories AS mc"
					+ "ON (c.cat_id=mc.m_id)"
					+ "WHERE mc.m_id=?"
			);
			prepState.setInt(1, m_id);
			result = prepState.executeQuery();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int insertMovie(String m_id) {
		int newRecordID = -1;
		try {
			prepState = MovieModel.con.prepareStatement("INSERT INTO movies VALUES (null,?)"); 

			prepState.setString(1, m_id);
			prepState.execute();
			
			ResultSet result = prepState.getGeneratedKeys(); // get the ID of the New record from Statement
			result.next(); // to use results call next()
			
			newRecordID = result.getInt(1); // get the ID from ResultSet
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newRecordID;
	}
	
	public static void updateMovie(int cat_id, String newCat_name) {
		try {
			prepState = MovieModel.con.prepareStatement("UPDATE movies SET cat_name=? WHERE cat_id=?");
			prepState.setString(1, newCat_name);
			prepState.setInt(2, cat_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteMovie(int m_id) {
		try {
			prepState = MovieModel.con.prepareStatement("DELETE FROM movies WHERE m_id=?");
			prepState.setInt(1, m_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCategories(int[] ids) {
		for ( int i = 0; i < Array.getLength(ids); i++ ) {
			MovieModel.deleteMovie(ids[i]);
		}
	}

}
