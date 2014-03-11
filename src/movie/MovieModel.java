package movie;

import db.DBUtil;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import category.CategoryModel;
import db.MyModel;

public class MovieModel {

	public static Connection con = DBUtil.getConnected();
	static Statement state = null;
	static PreparedStatement prepState = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	/**
	 * Get all Movies from Data Base
	 * 
	 * @return The model for the JTable
	 * @throws Exception
	 */
	public static MyModel getAllMovies(String type) throws Exception {
		
		String sql;
		
		String selectFields = "m.m_id, m.m_name, m.m_trailer, m.m_date";
		
		if ( type.equals("sql1") ) {
			sql = "SELECT " + selectFields + " FROM movies AS m WHERE m.m_name LIKE 'Страшен%' AND m.m_date > '2000-01-01'";
		}else if ( type.equals("sql2") ) {
			sql = "SELECT " + selectFields + " FROM movies AS m "
					+ "LEFT JOIN movies_categories AS mc ON m.m_id=mc.m_id "
					+ "WHERE m.m_id > 3 AND mc.cat_id=(SELECT cat_id FROM categories WHERE cat_name='Екшън') ";
		}else{
			sql = "SELECT " + selectFields + " FROM movies AS m";
		}
		
		
		prepState = MovieModel.con.prepareStatement(sql);
		result = prepState.executeQuery();
		
		model = new MyModel(result);
		return model;
	}
	
	/**
	 * Search in Movies from Data Base
	 * 
	 * @return The model for the JTable
	 * @throws Exception
	 */
	public static MyModel getMoviesSearch(String m_name, int cat_id) throws Exception {
		
		String sql;
		
		sql = "SELECT m.m_id, m.m_name, m.m_trailer, m.m_date FROM movies AS m "
				+ "LEFT JOIN movies_categories AS mc ON m.m_id=mc.m_id "
				+ "WHERE m.m_name LIKE ? AND mc.cat_id=?";
		
		prepState = MovieModel.con.prepareStatement(sql);
		prepState.setString(1, "%" + m_name + "%");
		prepState.setInt(2, cat_id);
		
		result = prepState.executeQuery();
		
		model = new MyModel(result);
		return model;
	}
	
	/**
	 * Get the Movie by Movie ID
	 * 
	 * @param m_id Movie ID to be fetched
	 * @return       ResultSet with One Movie
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
					"SELECT c.cat_name, c.cat_id FROM categories AS c "
					+ "LEFT JOIN movies_categories AS mc "
					+ "ON (c.cat_id=mc.m_id) "
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
	
	public static String DateFormatterForSQL(String date) throws ParseException {
		// parse the String as a FULL DATE
		java.util.Date oldDateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		
		// the FULL Date is then formatted to SQL format
		return new SimpleDateFormat("yyyy-MM-dd").format(oldDateFormat);
	}
	
	public static String DateFormatterForField(String date) throws ParseException {
		// parse the String as a FULL DATE
		java.util.Date oldDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		// the FULL Date is then formatted for Field format
		return new SimpleDateFormat("dd-MM-yyyy").format(oldDateFormat);
	}
	
	public static int insertMovie(String m_name, String m_trailer, String m_desc, String m_date, ArrayList<Integer> categoriesIds) {
		int newRecordID = -1;
		try {
			// SQL date format from the formated Date as a String
			java.sql.Date sqlDateFormat = java.sql.Date.valueOf(MovieModel.DateFormatterForSQL(m_date));
			
			prepState = MovieModel.con.prepareStatement("INSERT INTO movies VALUES (null,?,?,?,?)");
			
			prepState.setString(1, m_name);
			prepState.setString(2, m_trailer);
			prepState.setString(3, m_desc);
			prepState.setDate(4, sqlDateFormat);
			prepState.execute();
			
			ResultSet result = prepState.getGeneratedKeys(); // get the ID of the New record from Statement
			result.next(); // to use results call next()
			
			newRecordID = result.getInt(1); // get the ID from ResultSetselected
			
			// Convert from List to Array to work with them
			//Object[] categoriesArray = categoriesList.toArray();
			// insert all categories that have been selected for this movie
			MovieModel.insertCategories(newRecordID, categoriesIds);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return newRecordID;
	}
	
	public static void updateMovie(int m_id, String newM_name, String newM_trailer, String newM_desc, String newM_date, ArrayList<Integer> categoriesIds) {
		try {
			// SQL date format from the formated Date as a String
			java.sql.Date sqlDateFormat = java.sql.Date.valueOf(MovieModel.DateFormatterForSQL(newM_date));

			prepState = MovieModel.con.prepareStatement("UPDATE movies SET m_name=?, m_trailer=?, m_desc=?, m_date=? WHERE m_id=?");
			prepState.setString(1, newM_name);
			prepState.setString(2, newM_trailer);
			prepState.setString(3, newM_desc);
			prepState.setDate(4, sqlDateFormat);
			
			prepState.setInt(5, m_id);
			prepState.execute();
			
			// TODO: Make the sync of the categories
			MovieModel.insertCategories(m_id, categoriesIds);
		} catch (SQLException | ParseException e) {
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
	
	public static void deleteMovies(int[] ids) {
		for ( int i = 0; i < Array.getLength(ids); i++ ) {
			MovieModel.deleteMovie(ids[i]);
		}
	}
	
	public static void insertCategories( int m_id, ArrayList<Integer> categoriesIds ) {
		if( categoriesIds.size() > 0) {
			try {
				String query = "INSERT INTO movies_categories SET m_id=?, cat_id=?";
				prepState = MovieModel.con.prepareStatement(query);
				for (int i = 0; i < categoriesIds.size(); i++) {
					prepState.setInt(1, m_id);
					// the
					prepState.setInt(2, categoriesIds.get(i) );
					prepState.execute();
				}
				
				//prepState.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteCategories( int[] ids ) {
		if( ids.length > 0) {
			try {
				String query = "DELETE FROM movies_categories WHERE cat_id=?";
				prepState = MovieModel.con.prepareStatement(query);
				for (int i = 0; i <= ids.length; i++) {
					prepState.setInt(i+1, ids[i]);
					prepState.execute();
				}
				
				prepState.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
