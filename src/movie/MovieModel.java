package movie;

import db.DBUtil;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.MyModel;

public class MovieModel {

	static Connection con = DBUtil.getConnected();
	static Statement state = null;
	static PreparedStatement prepState = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	public static MyModel getAllMovies() throws Exception {
		state = MovieModel.con.createStatement();
		result = state.executeQuery("SELECT * FROM movies");
		model = new MyModel(result);
		return model;
	}
	
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
	
	public static void insertMovie(String m_id) {
		try {
			prepState = MovieModel.con.prepareStatement("INSERT INTO movies VALUES (null,?)");
			prepState.setString(1, m_id);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
