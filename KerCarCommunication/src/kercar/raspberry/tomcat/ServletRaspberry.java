package kercar.raspberry.tomcat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import kercar.comAPI.IMessage;
import kercar.comAPI.IRawMessage;
import kercar.comAPI.json.JSONParser;
import kercar.raspberry.core.Core;

/**
 * Servlet implementation class ServletRaspberry
 */
public class ServletRaspberry extends HttpServlet {
	
	private static final long serialVersionUID = 9159473095987189986L;
	
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
		String JSONMessage = request.getParameter("message");
		System.out.println("Message reçu");
		System.out.println(JSONMessage);
		//int resultat = raspberry.recevoirMessage(message);
		
		ServletContext appli = this.getServletConfig().getServletContext();
		Core core = (Core) appli.getAttribute(Initializer.INDEX_CORE);
		
		JSONParser jso = new JSONParser();
		appli.log("Message reçu : " + JSONMessage);
		IRawMessage message =jso.decode(JSONMessage);
		
		try{
			PrintWriter out = response.getWriter();
			if (message.getType() := Message.GET_STATE) {
				core.messageReceived((IMessage)message);
				appli.log("Message décodé");
				// Code 200 si tout est OK
				response.setStatus(HttpServletResponse.SC_OK);
			}
			else {
				Message etat = core.getEtat();
				out.print(etat.toString());
			}
		} catch (Exception e){
			System.err.println("Internal error");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
