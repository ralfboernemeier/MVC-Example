package de.ralfb_web.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import de.ralfb_web.utils.ExceptionListener;
import oracle.jdbc.pool.OracleDataSource;

public class DAOService {

	/**
	 * Fields
	 */
	private ExceptionListener exceptionListener;
	private String dbVersionInfo;
	private String jdbcDriverVersionInfo;

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
	 * Method to fire an exception using exceptionOcuured() method
	 * 
	 * @param th Throwable
	 */
	private void fireException(Throwable th) {
		if (exceptionListener != null) {
			exceptionListener.exceptionOccurred(th);
		}
	}

	public String getDbVersionInfo(String dbConnectString, String dbUserId, String dbPassword) {
		try {
			Connection conn = DriverManager.getConnection(dbConnectString, dbUserId, dbPassword);
			String queryGetDbVersion = "select banner from v$version where banner like '%Oracle%'";
			Statement stmtQueryGetDbVersion = conn.createStatement();
			ResultSet rsetGetDbVersion = stmtQueryGetDbVersion.executeQuery(queryGetDbVersion);
			if (rsetGetDbVersion.next()) {
				dbVersionInfo = rsetGetDbVersion.getString("BANNER");
			}
			stmtQueryGetDbVersion.close();
			conn.close();
		} catch (SQLException ex) {
			fireException(ex);
			return null;
		}
		return dbVersionInfo;
	}
	
	public String getJdbcDriverVersionInfo(String dbConnectString, String dbUserId, String dbPassword) {
		try {
			Connection conn = DriverManager.getConnection(dbConnectString, dbUserId, dbPassword);
			DatabaseMetaData metaData = conn.getMetaData();
			jdbcDriverVersionInfo = metaData.getDriverVersion();
		} catch (SQLException ex) {
			fireException(ex);
			return null;
		}
		return jdbcDriverVersionInfo;
	}
}
