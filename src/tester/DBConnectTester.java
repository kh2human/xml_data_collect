package tester;

import config.DBConfig;

//import java.net.InetAddress;

import db.DBDriver;

public class DBConnectTester {
	
//	private String driver = "";
//	private final String url;
//	private final String id;
//	private final String pw; 

	/*public DBConnectTester(String type, String url, String id, String pw) {
		driver = DBDriver.getDriver(type);
		this.url = url;
		this.id = id;
		this.pw = pw;
	}*/
	
	public static boolean test(DBConfig config) {
//	public static boolean test(String type, String url, String id, String pw) {
		String type = config.getDbType();
		final String driver = DBDriver.getDriver(type);
		final java.sql.Connection connection;
		java.sql.PreparedStatement st = null;
		java.sql.ResultSet rs = null;
		String sql = "SELECT 'test' FROM DUAL";
		String result = "";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
		try {
			String url = config.getUrl();
			String id = config.getId();
			String pw = config.getPw();
//			int ipStartIndex = url.indexOf("//") + "//".length();
//			String domain = url.substring(ipStartIndex, url.indexOf(":", ipStartIndex));
//			String ip = InetAddress.getByName(domain).getHostAddress();
//			url = url.replaceFirst("//.*:", "//" + ip + ":");
			connection = java.sql.DriverManager.getConnection(url, id, pw);
			st = connection.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			if (!st.isClosed())
				st.close();
			if (!connection.isClosed())
				connection.close();
		} catch (java.sql.SQLException e) {
			System.out.println(e.getMessage());
		} /*catch (java.net.UnknownHostException e) {
			System.out.println(e.getMessage());
		}*/
		
		if (result.equals("test")) {
			return true;
		}
		
		return false;
	}
	
	/*public String getDriver() {
		return driver;
	}*/

	/*public String getUrl() {
		return url;
	}*/

	/*public String getId() {
		return id;
	}*/

	/*public String getPw() {
		return pw;
	}*/

}
