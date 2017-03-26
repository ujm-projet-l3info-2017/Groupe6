package testFB;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.net.ssl.*;

/**
 * Servlet implementation class RecepteurPOST
 */
@WebServlet("/RecepteurPOST")
public class RecepteurPOST extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecepteurPOST()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Servlet de réception POST HTTPS<br/><br/><br/>Circulez, il y a rien à voir ici<br/><img src=\"https://i.skyrock.net/2092/64272092/pics/2586735683_small_1.jpg\">");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.getServerPort();
		System.out.println("Reçu POST: "+request.toString());
		doGet(request, response);
	}

}
