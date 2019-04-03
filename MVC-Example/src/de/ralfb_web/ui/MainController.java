package de.ralfb_web.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.services.DataSourceOracle;
import de.ralfb_web.utils.DAOServiceInjectable;
import de.ralfb_web.utils.ExceptionListener;
import de.ralfb_web.utils.ModelInjectable;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements ExceptionListener, ModelInjectable, DAOServiceInjectable {

	/**
	 * Constructor
	 */
	public MainController() {
		super();
	}

	/**
	 * Fields
	 */
	private Model model;
	private DAOService dao;

	@Override
	public void setDAO(DAOService dao) {
		this.dao = dao;

	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void exceptionOccurred(Throwable th) {
		String msg = String.valueOf(th);
		messages.appendText(msg + "\n");
	}

	/**
	 * Initialize Method will be executed after all FXML Nodes are ready. This is
	 * the place for Initialization of Nodes.
	 */
	public void initialize() {
		// Check the id of the injected objects to make sure we have the same reference
		// as in main class
		System.out.println("MainController Class: Model Object: " + model);
		System.out.println("MainController Class: DAO Object: " + dao);

		/**
		 * Register ExceptionListener with DAOService class to get Exceptions from
		 * DAOService class in Controller for showing in the View.
		 */
		dao.registerExceptionListener(th -> {
			String msg = String.valueOf(th);
			messages.appendText(msg + "\n");
		});

		// Add Listener to the TextFields to recognize changes and set the data in the
		// model
		host.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setHost(newValue);
			model.setDbConnectString();
		});

		port.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setPort(newValue);
			model.setDbConnectString();
		});

		sid.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setSid(newValue);
			model.setDbConnectString();
		});

		user.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setUser(newValue);
			model.setDbConnectString();
		});

		password.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setPassword(newValue);
			model.setDbConnectString();
		});

		// Add Listener to the dbVersionProperty of the Model class
		model.getDbVersionProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				messages.appendText(
						"New Database Connection.\nVersion: " + model.getDbVersionProperty().getValue() + "\n");
			}
		});

		// Add Listener to the jdbcDriverVersionProperty of the Model class
		model.getJdbcDriverVersionProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				messages.appendText("JDBC Driver Version: " + model.getJdbcDriverVersionProperty().getValue() + "\n");
			}
		});

	}

	/**
	 * Initialize FXML Nodes (fx:id)
	 */
	@FXML
	Button exit;

	@FXML
	Button connectDB;

	@FXML
	TextArea messages;

	@FXML
	TextField host;

	@FXML
	TextField port;

	@FXML
	TextField sid;

	@FXML
	TextField user;

	@FXML
	TextField password;
	
	@FXML
	ProgressIndicator pi;
	
	@FXML
	ImageView imageNotOk = new ImageView();

	/**
	 * Method that will be executed if the exit Button was clicked. This Method will
	 * fire a Window Event WINDOW_CLOSE_REQUEST. This event will be handled by
	 * "Stage.setOnCloseRequest(event { ... }); defined when creating the Stage.
	 * 
	 * @param event ActionEvent
	 */
	public void exitButtonTapped(ActionEvent event) {
		// Cast the Window of UI Control Button exit to Stage
		((Stage) exit.getScene().getWindow())
				.fireEvent(new WindowEvent(((Stage) exit.getScene().getWindow()), WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Method that will be executed if the "Connect Database" Button is tapped.
	 */
	public void connectButtonTapped() {
//		model.setDbConnectString();
//		model.setJdbcDriverVersionProperty(
//				dao.getJdbcDriverVersionInfo(model.getDbConnectString(), model.getUser(), model.getPassword()));
//		model.setDbVersionProperty(
//				dao.getDbVersionInfo(model.getDbConnectString(), model.getUser(), model.getPassword()));
		
//		String dbVersionInfo = null;
//		DataSourceOracle dso = new DataSourceOracle(model);
//		try {
//			Connection conn = dso.getOracleDataSource().getConnection();
//			String queryGetDbVersion = "select banner from v$version where banner like '%Oracle%'";
//			Statement stmtQueryGetDbVersion = conn.createStatement();
//			ResultSet rsetGetDbVersion = stmtQueryGetDbVersion.executeQuery(queryGetDbVersion);
//			if (rsetGetDbVersion.next()) {
//				dbVersionInfo = rsetGetDbVersion.getString("BANNER");
//			}
//			System.out.println(dbVersionInfo);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		model.setDbVersionProperty(dao.getDbVersionInfo2(model.getUser(), model.getPassword(), model.getHost(), Integer.parseInt(model.getPort()), model.getSid()));	
	}

}
