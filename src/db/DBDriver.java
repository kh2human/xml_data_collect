package db;

public enum DBDriver {
	
	ORACLE("oracle.jdbc.driver.OracleDriver", "SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ", "TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')", "jdbc:oracle:thin:@//HOSTNAME:PORT/DBNAME"),
	ALTIBASE("Altibase.jdbc.driver.AltibaseDriver", "SELECT COLUMN_COUNT FROM SYSTEM_.SYS_TABLES_ WHERE TABLE_NAME = ", "SYSDATE", "jdbc:Altibase://HOSTNAME:PORT/DBNAME"),
	TIBERO("com.tmax.tibero.jdbc.TbDriver", "", "TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')", "jdbc:tibero:thin:@HOSTNAME:PORT:DBNAME"),
	MYSQL("com.mysql.jdbc.Driver", "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ", "SYSDATE()", "jdbc:mysql://HOSTNAME:PORT/DBNAME");
	
	private final String driver;
	private final String countColumnSql;
	private final String sysdate;
	private final String urlFormat;
	
	DBDriver(String driver, String countColumnSql, String sysdate, String urlFormat) {
		this.driver = driver;
		this.countColumnSql = countColumnSql;
		this.sysdate = sysdate;
		this.urlFormat = urlFormat;
	}
	
	public static String getUrlFormat(String type) {
		for (DBDriver driver: DBDriver.values()) {
			if (type.equals(driver.toString())) {
				return driver.getUrlFormat();
			}
		}
		return "";
	}
	
	public static String getUrlCommon(String type) {
		String common = "jdbc:";
		for (DBDriver driver: DBDriver.values()) {
			if (type.equals(driver.toString())) {
				common = driver.getUrlFormat();
				break;
			}
		}
		common = common.substring(0, common.indexOf("HOSTNAME"));
		return common;
	}
	
	public static String getDriver(String type) {
		for (DBDriver driver: DBDriver.values()) {
			if (type.equals(driver.toString())) {
				return driver.getDriver();
			}
		}
		return "";
	}
	
	public static String getCountColumnSql(String type) {
		for (DBDriver driver: DBDriver.values()) {
			if (type.equals(driver.toString())) {
				return driver.getCountColumnSql();
			}
		}
		return "";
	}
	
	public static String getSysdate(String type) {
		for (DBDriver driver: DBDriver.values()) {
			if (type.equals(driver.toString())) {
				return driver.getSysdate();
			}
		}
		return "";
	}
	
	private String getUrlFormat() {
		return urlFormat;
	}
	
	private String getSysdate() {
		return sysdate;
	}
	
	private String getCountColumnSql() {
		return countColumnSql;
	}
	
	private String getDriver() {
		return driver;
	}
}
