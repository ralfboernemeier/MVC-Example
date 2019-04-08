package de.ralfb_web.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.ralfb_web.utils.ExceptionListener;

public class DAOService {

	/**
	 * Fields
	 */
	private ExceptionListener exceptionListener;
	private String dbVersionInfo;
	private String jdbcDriverVersionInfo;
	private String versionInfos;
	private Connection conn;
	private Statement stmt;
	private ResultSet rset;
	
	

	/**
	 * Constructor
	 */
	public DAOService() {
		super();
	}

	/**
	 * Method to register an ExceptionListener
	 * 
	 * @param l ExceptionListener
	 */
	public void registerExceptionListener(ExceptionListener l) {
		exceptionListener = l;
	}

	/**
	 * Method to remove (set to null) an exception listener
	 */
	public void removeExceptionListener() {
		exceptionListener = null;
	}

	/**
	 * Method to fire an exception using exceptionOccured() method
	 * 
	 * @param th Throwable
	 */
	private void fireException(Throwable th) {
		if (exceptionListener != null) {
			exceptionListener.exceptionOccurred(th);
		}
	}

	public String getDbVersionInfo(String dbVendor, String user, String passwd, String host, int port, String sid) {
		String queryGetDbVersion = null;
		try {
			switch (dbVendor) {
			case "Oracle":
				DataSourceOracle dso = new DataSourceOracle(user, passwd, host, port, sid);
				conn = dso.getOracleDataSource().getConnection();
				queryGetDbVersion = "select banner from v$version where banner like '%Oracle%'";
				break;
				
			case "MySQL":
				DataSourceMySQL dsm = new DataSourceMySQL(user, passwd, host, port, sid);
				conn = dsm.getMysqlDataSource().getConnection();
				queryGetDbVersion = "SELECT VERSION()";
				break;
			default:
				break;
			}
			DatabaseMetaData metaData = conn.getMetaData();
			jdbcDriverVersionInfo = metaData.getDriverVersion();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryGetDbVersion);
			if (rset.next()) {
				dbVersionInfo = rset.getString(1);
			}
			versionInfos = "JDBC Version: " + jdbcDriverVersionInfo + "\nOracle Database Version: " + dbVersionInfo;
		} catch (SQLException ex) {
			fireException(ex);
			return null;
		}
		finally {
			close();
		}
		return versionInfos;
	}
		
	public String getMySqlVersion(String user, String passwd, String host, int port, String db) {
		try {
			DataSourceMySQL dsm = new DataSourceMySQL(user, passwd, host, port, db);
			conn = dsm.getMysqlDataSource().getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			jdbcDriverVersionInfo = metaData.getDriverVersion();
			String querygetMySqlVersion = "SELECT VERSION()";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(querygetMySqlVersion);
			if (rset.next()) {
				dbVersionInfo = rset.getString(1);
			}
			versionInfos = "JDBC Version: " + jdbcDriverVersionInfo + "\nMySQL Database Version: " + dbVersionInfo;
			
		} catch (Exception ex) {
			fireException(ex);
			return null;
		}
		finally {
			close();
		}
		return versionInfos;
	}
	
	public void close() {
		try {
			if (rset != null) {
				rset.close();
				rset = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			
		} catch (Exception ex) {
			fireException(ex);
		}
		
	}
}
