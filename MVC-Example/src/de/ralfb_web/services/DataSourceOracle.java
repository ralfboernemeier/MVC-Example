package de.ralfb_web.services;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class DataSourceOracle {

	private OracleDataSource ods;
	private String user;
	private String passwd;
	private String host;
	private int port;
	private String sid;

	public DataSourceOracle(String user, String passwd, String host, int port, String sid) {
		super();
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.sid = sid;
	}

	public OracleDataSource getOracleDataSource() throws SQLException {
		ods = new OracleDataSource();
		ods.setDriverType("thin");
		ods.setUser(user);
		ods.setPassword(passwd);
		ods.setServerName(host);
		ods.setServiceName(sid);
		ods.setPortNumber(port);
		return ods;
	}
}
