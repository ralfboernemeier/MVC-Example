package de.ralfb_web.services;

import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceMySQL {

	private MysqlDataSource mds;
	private String user;
	private String passwd;
	private String host;
	private int port;
	private String db;

	public DataSourceMySQL(String user, String passwd, String host, int port, String db) {
		super();
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.db = db;
	}

	public MysqlDataSource getMysqlDataSource() throws SQLException {
		mds = new MysqlDataSource();
		mds.setUrl("jdbc:mysql://" + host + ":" + port + "/" + db + "?allowMultiQueries=true&useSSL=false");
		mds.setUser(user);
		mds.setPassword(passwd);
//		mds.setServerName(host);
//		mds.setPort(port);
//		mds.setDatabaseName(db);

		return mds;
	}

}
