package edu.gtech.sidetracker.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import edu.gtech.sidetracker.web.guice.SideTrackerServletModule;

public class Main extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		final ResourceConfig rc = new PackagesResourceConfig( "edu.gtech.sidetracker.web.server" );

		return Guice.createInjector(new SideTrackerServletModule(rc));
	}
}
