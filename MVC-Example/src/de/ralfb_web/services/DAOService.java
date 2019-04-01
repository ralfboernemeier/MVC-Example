package de.ralfb_web.services;

import java.sql.Connection;
import java.sql.DriverManager;
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

	/**
	 * Method to close a given database connection
	 * 
	 * @param conn Connection to be closed
	 */
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			fireException(ex);
		}
	}

	/**
	 * Method to close a give database statement
	 * 
	 * @param stmt Statement to be closed
	 */
	public void closeStatement(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception ex) {
			fireException(ex);
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
}
