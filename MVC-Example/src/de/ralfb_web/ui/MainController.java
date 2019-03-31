package de.ralfb_web.ui;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.utils.DAOServiceInjectable;
import de.ralfb_web.utils.ModelInjectable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements ModelInjectable, DAOServiceInjectable {

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

	/**
	 * Initialize Method will be executed after all FXML Nodes are ready. This is
	 * the place for Initialization of Nodes.
	 */
	public void initialize() {
		// Check the id of the injected objects to make sure we have the same reference
		// as in main class
		System.out.println("MainController Class: Model Object: " + model);
		System.out.println("MainController Class: DAO Object: " + dao);
		
		// Add Listener to the TextFields to recognize changes and set the data in the model
		host.textProperty().addListener((observable, oldValue, newValue) -> {
		    model.setHost(newValue);
		});
		
		port.textProperty().addListener((observable, oldValue, newValue) -> {
		    model.setPort(newValue);
		});
		
		sid.textProperty().addListener((observable, oldValue, newValue) -> {
		    model.setSid(newValue);
		});
		
		user.textProperty().addListener((observable, oldValue, newValue) -> {
		    model.setUser(newValue);
		});
		
		password.textProperty().addListener((observable, oldValue, newValue) -> {
		    model.setPassword(newValue);
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
		model.setDbConnectString();
		messages.appendText("Database Connect String: " + model.getDbConnectString() + " User: " + model.getUser() + " Password: " + model.getPassword() + "\n");
	}

}
