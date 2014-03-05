package category;

import db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.MyModel;

public class CategoryModel {

	static Connection con = DBUtil.getConnected();
	static Statement state = null;
	static PreparedStatement prepState = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	public static MyModel getAllCategories() throws Exception {
		state = CategoryModel.con.createStatement();
		result = state.executeQuery("select * from categories");
		model = new MyModel(result);
		return model;
	}
	
	public static void insertCategory(String cat_name) {
		try {
			prepState = CategoryModel.con.prepareStatement("insert into categories values (null,?)");
			prepState.setString(1, cat_name);
			prepState.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
