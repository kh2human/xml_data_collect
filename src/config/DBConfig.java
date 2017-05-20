package config;

import java.net.InetAddress;

import db.DBDriver;

public class DBConfig {

	private String dbType;
	private String hostName;
	private int port;
	private String dbName;
	private String id;
	private String pw;
	
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getDbType() {
		return dbType;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public String getUrl() {
		String url = DBDriver.getUrlFormat(dbType);
		try {
			String ip = InetAddress.getByName(hostName).getHostAddress();
			url = url.replaceFirst("HOSTNAME", ip);
		} catch (java.net.UnknownHostException e) {
			System.out.println(e);
			url = url.replaceFirst("HOSTNAME", hostName);
		}
		url = url.replaceFirst("PORT", String.valueOf(port));
		url = url.replaceFirst("DBNAME", dbName);
		return url;
	}
}
