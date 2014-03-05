package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	
	static Connection connected = null;
	
	public static Connection getConnected(){
		try {
			Class.forName("org.h2.Driver");
			connected = DriverManager.getConnection("jdbc:h2:~/Desktop/H2_Data/test", "sa","");
			return connected;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}// end try/catch
		return connected;
	}
	
}