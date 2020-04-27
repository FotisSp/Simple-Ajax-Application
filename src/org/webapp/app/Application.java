package org.webapp.app;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Fotis Spanopoulos
 *
 */
public class Application extends ResourceConfig {
	/**
	 * Resource Configuration class
	 */
	public Application() {
		packages("org.webapp.services");
	}
}
