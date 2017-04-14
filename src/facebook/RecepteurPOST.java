package facebook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RecepteurPOST
 */
@WebServlet("/RecepteurPOST")
public class RecepteurPOST extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	//String resultatRequete;
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecepteurPOST()
	{
		super();
		//resultatRequete="";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getParameter("hub.mode").compareTo("subscribe")==0) //Validation du webhook
		{
			if(request.getParameter("hub.verify_token")!=null && request.getParameter("hub.challenge")!=null)
			{
				String testToken = request.getParameter("hub.verify_token");
				
				if(testToken.compareTo("tokenmagiquedelavage")==0)
					response.getWriter().append((CharSequence) request.getParameter("hub.challenge"));		
			}
			else
			{
				response.getWriter().append("Paramètre manquant pour la validation du webhook");
			}
		}
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
		System.out.println("RECU POST: "+loge+"\n");
		
		
		response.getWriter().append("HTTP 200 OK");
		
//		StringBuffer jb = new StringBuffer();
//		String line = null;
//		try
//		{
//			BufferedReader reader = request.getReader();
//			while ((line = reader.readLine()) != null)
//				jb.append(line);
//		}
//		catch (Exception e)
//		{
//			System.out.println("Erreur lecture JSON");
//		}
//
//		try
//		{
//			JSONObject jsonObject = HTTP.toJSONObject(jb.toString());
//			System.out.println(jsonObject);
//		}
//		catch (JSONException e)
//		{
//			// crash and burn
//			throw new IOException("Error parsing JSON request string OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
//		}
		
		
//		//Exploration des paramètres reçus
//		Enumeration<String> a = request.getParameterNames();
//		while(a.hasMoreElements())
//		{
//			String next = a.nextElement();
//			resultatRequete.concat(next);
//		}
//		response.getWriter().append((CharSequence) resultatRequete);
		
		//doGet(request, response);
	}

}
