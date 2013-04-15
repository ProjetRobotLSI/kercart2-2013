package kercar.raspberry.tomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kercar.comAPI.IMessage;
import kercar.raspberry.IComRaspberry;
import kercar.raspberry.core.Core;
import kercar.comAPI.json.JSONParser;

/**
 * Servlet implementation class ServletRaspberry
 */
public class ServletRaspberry extends HttpServlet {
	
	private static final long serialVersionUID = 9159473095987189986L;
	private IComRaspberry raspberry;

    /**
     * Default constructor. 
     */
    /*public ServletRaspberry(IComRaspberry raspberry) {
        this.raspberry = raspberry;
    }*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String JSONMessage = (String)request.getParameter("message");
		System.out.println(JSONMessage);
		//int resultat = raspberry.recevoirMessage(message);
		
		ServletContext appli = this.getServletConfig().getServletContext();
		Core core = (Core) appli.getAttribute(Initializer.INDEX_CORE);
		
		JSONParser jso = new JSONParser();
		IMessage message =jso.decode(JSONMessage);
		
		core.messageReceived(message);
		
		PrintWriter out = response.getWriter();
		
		try{
			// Code 200 si tout est OK
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e){
			System.err.println("Internal error");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
