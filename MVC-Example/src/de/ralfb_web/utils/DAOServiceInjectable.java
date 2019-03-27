package de.ralfb_web.utils;

import de.ralfb_web.services.DAOService;

public interface DAOServiceInjectable {
	
	void setDAO(DAOService dao);

}
