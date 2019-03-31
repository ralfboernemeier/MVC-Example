package de.ralfb_web.utils;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.ui.MainController;

public class ControllerFactory {

	/**
	 * Method to be called when loading the scene by ViewLoader Class to inject e.g.
	 * Model, DB to the Controller.
	 * 
	 * @param clazz      Class
	 * @param model      Model Class to be injected
	 * @param dao        DAOService to be injected
	 * @param controller Controller Class to be injected
	 * @param            <T> Generic
	 * @return ControllerInstance
	 */
	public static <T> T controllerForClass(Class<T> clazz, Model model, DAOService dao, MainController controller) {
		try {
			// controllerInstance#someArbitraryMethodCall if necessary
			// will be triggered *before* the initialize method!
			T controllerInstance = clazz.newInstance();
			// Set Model using Setter in Controller1
			if (ModelInjectable.class.isAssignableFrom(clazz)) {
				ModelInjectable c = (ModelInjectable) controllerInstance;
				c.setModel(model);
			}

			if (ControllerInjectable.class.isAssignableFrom(clazz)) {
				ControllerInjectable c = (ControllerInjectable) controllerInstance;
				c.setController(controller);
			}

			if (DAOServiceInjectable.class.isAssignableFrom(clazz)) {
				DAOServiceInjectable c = (DAOServiceInjectable) controllerInstance;
				c.setDAO(dao);
			}

			return controllerInstance;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T controllerForClass(Class<T> clazz, Model model) {
		return controllerForClass(clazz, model, null, null);
	}
	
	public static <T> T controllerForClass(Class<T> clazz, Model model, DAOService dao) {
		return controllerForClass(clazz, model, dao, null);
	}

}
