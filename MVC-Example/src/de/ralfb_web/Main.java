package de.ralfb_web;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.utils.ControllerFactory;
import de.ralfb_web.utils.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * @author Ralf Boernemeier
 * @version 1.0
 * @since 2019-03-27
 *
 */
public class Main extends Application {

	/**
	 * Constructor
	 */
	public Main() {
		super();
	}

	/**
	 * Fields
	 */
	private Model model = new Model();
	private DAOService dao = new DAOService();

	/**
	 * The application initialization method. This method is called immediately
	 * after the Application class is loaded and constructed. Overrides init() in
	 * Application class, which does nothing.
	 */
	@Override
	public void init() throws Exception {
		// Print the id's of the objects that will be injected to the MainController
		System.out.println("Main Class: Model Object: " + model);
		System.out.println("Main Class: DAO Object: " + dao);
	}

	/**
	 * The main entry point for all JavaFX applications.The start method is called
	 * after the init() method has returned, and after the system is ready for the
	 * application to begin running.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = ViewLoader.load("/de/ralfb_web/ui/Main.fxml",
					clazz -> ControllerFactory.controllerForClass(clazz, model, dao));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("MVC Example");
			primaryStage.setOnCloseRequest(event -> {
				System.out.println("Application closed.");
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when the application should stop, and provides a
	 * convenient place to prepare for application exit and destroy resources.
	 */
	@Override
	public void stop() {
		// Do something at stop here ...
	}

	/**
	 * Main Method - Main task is to Launch the application
	 * 
	 * @param args All Parameters given during start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
