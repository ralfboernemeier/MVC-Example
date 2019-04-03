package de.ralfb_web.services;

import java.sql.SQLException;

import de.ralfb_web.model.Model;
import oracle.jdbc.pool.OracleDataSource;

public class DataSourceOracle {

	private Model model;
	private OracleDataSource ods;
	
	
	public DataSourceOracle(Model model) {
		super();
		this.model = model;
	}
	
	public OracleDataSource getOracleDataSource () throws SQLException {
		ods = new OracleDataSource();
		ods.setDriverType("thin");
		ods.setUser(model.getUser());
		ods.setPassword(model.getPassword());
		ods.setServerName(model.getHost());
		ods.setServiceName(model.getSid());
		ods.setPortNumber(Integer.parseInt(model.getPort()));
		return ods;
	}
}
