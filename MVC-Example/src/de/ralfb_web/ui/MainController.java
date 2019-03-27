package de.ralfb_web.ui;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.utils.DAOServiceInjectable;
import de.ralfb_web.utils.ModelInjectable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	TextField user;

	@FXML
	TextField password;

}
