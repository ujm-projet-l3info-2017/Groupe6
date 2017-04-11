package testFB;

import java.io.IOException;
import java.io.InputStream;
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
		
//		String a = request.getParameterNames();
//		
//		while(a.hasMoreElements())
//		{
//			response.getWriter().append((CharSequence) a);
//			a=a.nextElement();
//		}
		
		
		response.getWriter().append((CharSequence) request.getServletContext().getAttribute("logpost"));
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
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
