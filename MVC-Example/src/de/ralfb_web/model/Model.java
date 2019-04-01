package de.ralfb_web.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Ralf Boernemeier
 *
 */
public class Model {
	
	/**
	 * Fields
	 */
	private String host;
	private String port;
	private String user;
	private String password;
	private String sid;
	private String dbConnectString;
	private StringProperty dbVersionProperty = new SimpleStringProperty();

	/**
	 * Constructor
	 */
	public Model() {
		super();
	}


	/*
	 * Getter and Setter
	 */
	
	
	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDbConnectString() {
		return dbConnectString;
	}
	
	public void setDbConnectString() {
		this.dbConnectString = "jdbc:oracle:thin:@" + this.getHost() + ":" + this.getPort() + "/" + this.getSid();
	}
	
	public StringProperty getDbVersionProperty() {
		return dbVersionProperty;
	}
	
	public void setDbVersionProperty(String dbVersion) {
		dbVersionProperty.set(dbVersion);
	}

}
