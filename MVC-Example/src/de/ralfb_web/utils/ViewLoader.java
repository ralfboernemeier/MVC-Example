package de.ralfb_web.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

/**
 * <h2>Loader for the JavaFX Scene Parent</h2>
 * @author Ralf Boernemeier
 *
 */
public class ViewLoader {
	
	/**
	 * Method that returns the JavaFX Parent to be loaded
	 * 
	 * @param fxmlPath
	 * @param controllerFactory
	 * @return Parent
	 */
	public static Parent load(String fxmlPath, Callback<Class<?>, Object> controllerFactory) {

		Parent parent = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(controllerFactory);
			loader.setLocation(ViewLoader.class.getResource(fxmlPath));
			parent = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parent;
	}

}
