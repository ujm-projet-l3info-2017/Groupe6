package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chatbot.ChatBotThread;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	ChatBotThread chatbot = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Admin()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//V�rification du webhook par Facebook
		if(request.getParameter("hub.verify_token")!=null)
		{
			verificationWebhook(request,response); 
		}
		else
		{
			//V�rification du code d'acc�s
			if(request.getParameter("code")!=null)	
			{
				if(request.getParameter("code").compareTo("lecode") == 0)
				{
					//Redirection vers la JSP d'administration
					RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
					dispatcher.forward(request, response);
				}
			}
			else	//Pas de code d'acc�s
			{
				//Redirection vers la JSP d'authentification
				RequestDispatcher dispatcher = request.getRequestDispatcher("connexion_admin.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	/**
	 *  V�rification du webhook par facebook: V�rification de certains param�tres de la requete et renvoit de hub.challenge
	 */
	private void verificationWebhook(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if (request.getParameter("hub.mode").compareTo("subscribe") == 0) // Validation du webhook
		{
			if(request.getParameter("hub.verify_token")!=null && request.getParameter("hub.challenge")!=null)
			{
				String testToken = request.getParameter("hub.verify_token");
				
				if(testToken.compareTo("tokenmagiquedelavage") == 0)
					response.getWriter().append((CharSequence) request.getParameter("hub.challenge"));		
			}
			else
			{
				response.getWriter().append("Param�tre manquant pour la validation du webhook");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Commande re�u
		if(request.getParameter("selection")!=null)
		{
			System.out.println("Recu POST commande serveur");
			//Commande de lancement du serveur
			if (request.getParameter("selection").compareTo("start") == 0)
			{
				lancementServeur();
			}
			else 
			{
				//Commande de fermeture du serveur
				if (request.getParameter("selection").compareTo("stop") == 0 && chatbot != null)
				{
					fermetureServeur(); 
					synchronized(chatbot)         
					{
						//Envoi d'un notify au thread qui g�re l'IA
						if(chatbot.getState()==Thread.State.WAITING)
							chatbot.notify();
					}
				}
			}
			doGet(request,response);
		}
		else //Re�u POST de Facebook
		{
			if(chatbot!=null)
			{
				System.out.println("Re�u POST de Facebook");
				synchronized(chatbot)
				{
					if(chatbot.getState()==Thread.State.WAITING)
						chatbot.notify();
					else
						System.out.println("Impossible de notify le thread car il n'est pas en wait");
				}
			}
			response.getWriter().append("HTTP 200 OK");
		}
	}

	/**
	 *  Lance le programme pour l'IA en tant que Thread s�par�
	 */
	private void lancementServeur()
	{
		if(chatbot != null)	//A d�j� �t� lanc�
		{
			if(chatbot.getState()==Thread.State.TERMINATED) //Est termin�
			{
				chatbot = new ChatBotThread();
				chatbot.start();	//On le relance
			}
			else
				System.out.println("Impossible de lancer le chatbot, le thread est d�j� lanc�");
		}
		else	//N'a jamais �t� lanc�
		{
			chatbot = new ChatBotThread();
			chatbot.start();
		}
		
	}

	/**
	 *  Arr�te le thread du programme g�rant l'IA
	 */
	private void fermetureServeur()
	{
		if(chatbot != null)
			chatbot.arret();
	}

}
