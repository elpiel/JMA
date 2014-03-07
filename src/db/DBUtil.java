package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	
	static Connection connected = null;
	
	public static Connection getConnected(){
		try {
			
			Class.forName("org.h2.Driver");
			if ( connected == null ) {
				connected = DriverManager.getConnection("jdbc:h2:file:h2_db/test", "sa","");
			}
			return connected;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}// end try/catch
		return connected;
	}
	
}