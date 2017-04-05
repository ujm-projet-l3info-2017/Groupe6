package testFB;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

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
//		if(request.getParameter("hub.verify_token")=="a")
//			response.getWriter().append((CharSequence) request.getParameter("hub.challenge"));		
//		
//		String a = request.getParameterNames();
//		
//		while(a.hasMoreElements())
//		{
//			response.getWriter().append((CharSequence) a);
//			a=a.nextElement();
//		}
//		
		
		//response.getWriter().append((CharSequence) request.getServletContext().getAttribute("logpost"));
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		InputStream in = request.getInputStream();
		
		int i;
		String loge="";
		while((i = in.read())!=-1)
		{
			loge+=(((char)i+""));
		}
		request.getServletContext().setAttribute("logpost", loge);
		log("RECU "+loge);
		doGet(request, response);
	}

}
