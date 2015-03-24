package iCrawler; 
import java.sql.*;
 
public class Database {
	public Connection initiate=null;
 
	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/iCrawler";
			//user name = root and the space for password is left blank = no password
			initiate = DriverManager.getConnection(url, "root", ""); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
 
	public boolean sqlStart1(String sql) throws SQLException {
		Statement sta = initiate.createStatement();
		return sta.execute(sql);
	}
	
	public ResultSet sqlStart2(String sql) throws SQLException {
		Statement sta = initiate.createStatement();
		return sta.executeQuery(sql);
	}
 
	
 
	@Override
	protected void finalize() throws Throwable {
		if (initiate != null || !initiate.isClosed()) {
			initiate.close();
		}
	}
}