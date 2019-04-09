package de.ralfb_web.services;

import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;

public class DataSourceSQLite {
	
	private SQLiteDataSource sds;
	private String dbLocation;
	public DataSourceSQLite(String dbLocation) {
		super();
		this.dbLocation = dbLocation;
	}
	
	public SQLiteDataSource getSQLiteDataSource() throws SQLException{
		sds = new SQLiteDataSource();
		sds.setUrl("jdbc:sqlite:" + dbLocation);		
		return sds;
	}
	
}
