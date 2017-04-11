package br.com.contabilizei.jetty;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {

	public static void main(String[] args) throws Exception {
	    
		System.setProperty("file.encoding", "UTF-8");

		// ---------------------------
		// Configura o servidor Jetty
		// ---------------------------		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		// Registra o diret�rio de WebServices do Jersey
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "br.com.contabilizei.jersey");
		// ---------------------------
		jerseyServlet.setInitOrder(0);
		
		
		FilterHolder cors = context.addFilter(CrossOriginFilter.class,"/*",EnumSet.of(DispatcherType.REQUEST));
		cors.setInitParameter("Access-Control-Allow-Origin", "*");
		cors.setInitParameter("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT,DELETE");
		cors.setInitParameter("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("Access-Control-Allow-Credentials", "true");
		
		
		
		Server jettyServer = new Server(8080);
		// Create the server level handler list.
		HandlerList handlers = new HandlerList();
		// Make sure DefaultHandler is last (for error handling reasons)
		handlers.setHandlers(new Handler[] { context, new DefaultHandler() });

		jettyServer.setHandler(handlers);
		
		
		// ---------------------------
		// Inicia o servidor
		// ---------------------------
		try {
			jettyServer.start();
			jettyServer.join();
		} catch (Exception e) {
			jettyServer.destroy();
		}
		
	}

}
