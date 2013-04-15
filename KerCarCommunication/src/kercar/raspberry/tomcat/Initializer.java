package kercar.raspberry.tomcat;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import kercar.raspberry.core.Core;

public class Initializer extends GenericServlet implements ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 130593068386L;
	public static final String INDEX_CORE = "core";
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Core core = new Core();
		core.start();
		ServletContext appli;
		appli = arg0.getServletContext();
		appli.setAttribute(INDEX_CORE, core);
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
	}

}
