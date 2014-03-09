package movie;

import db.DBUtil;

import java.lang.reflect.Array;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	public static MyModel getAllMovies() throws Exception {
		prepState = MovieModel.con.prepareStatement("SELECT * FROM movies");
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
	
	public static String DateFormatterForSQL(String date) throws ParseException {
		// parse the String as a FULL DATE
		java.util.Date oldDateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		
		// the FULL Date is then formatted to SQL format
		return new SimpleDateFormat("yyyy-MM-dd").format(oldDateFormat);
	}
	
	public static int insertMovie(String m_name, String m_trailer, String m_desc, String m_date) {
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
			
			newRecordID = result.getInt(1); // get the ID from ResultSet
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return newRecordID;
	}
	
	public static void updateMovie(int cat_id, String newM_name, String newM_trailer, String newM_desc, String newM_date) {
		try {
			// SQL date format from the formated Date as a String
			java.sql.Date sqlDateFormat = java.sql.Date.valueOf(MovieModel.DateFormatterForSQL(newM_date));

			prepState = MovieModel.con.prepareStatement("UPDATE movies SET m_name=?, m_trailer=?, m_desc=? m_date=? WHERE m_id=?");
			prepState.setString(1, newM_name);
			prepState.setString(2, newM_trailer);
			prepState.setString(3, newM_desc);
			prepState.setDate(4, sqlDateFormat);
			
			prepState.setInt(5, cat_id);
			prepState.execute();
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
	
	public static void deleteCategories(int[] ids) {
		for ( int i = 0; i < Array.getLength(ids); i++ ) {
			MovieModel.deleteMovie(ids[i]);
		}
	}

}
