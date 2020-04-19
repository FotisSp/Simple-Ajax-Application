package org.webapp.app;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig{
	public Application() {
		packages("org.webapp.services");
	}
}
