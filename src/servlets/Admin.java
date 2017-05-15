package servlets;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chatbot.ChatBotThread;
import ia.ConversationIA_V2;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet
{
	// Initialisation du log
	static final Logger logger = LogManager.getLogger(Admin.class.getName());

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
		//Vérification du webhook par Facebook
		if(request.getParameter("hub.verify_token")!=null)
		{
			verificationWebhook(request,response); 
		}
		else
		{
			verificationConnexion(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Commande reçu
		if(request.getParameter("selection")!=null)
		{
			gestionCommandeRecu(request,response);
		}
		else
		{
			//Recu connexion
			if(request.getParameter("code")!=null)
			{
				connexion(request,response);
			}
			else //Reçu POST de Facebook
			{
				traitementAlerteNouveauMessage(request,response);
			}
		}
	}

	private void gestionCommandeRecu(HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Servlet ADMIN","Recu POST commande serveur");
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
					//Envoi d'un notify au thread qui gère l'IA
					if(chatbot.getState()==Thread.State.WAITING)
						chatbot.notify();
				}
			}
		}
		try
		{
			doGet(request,response);
		}
		catch (ServletException e)
		{
			logger.catching(e);
		}
		catch (IOException e)
		{
			logger.catching(e);
		}
	}

	/**
	 *  Vérification de la connexion dans la session
	 * @param request
	 * @param response
	 */
	private void verificationConnexion(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			//Vérification du code d'accès
			HttpSession session = request.getSession();
			
			String code = (String) session.getAttribute("code");
			
			if(code!=null)	
			{
				if(code.compareTo("lecode") == 0)
				{
					logger.info("Connexion réussi");
					//Redirection vers la JSP d'administration
					RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					RequestDispatcher dispatcher = request.getRequestDispatcher("connexion_admin.jsp");
					dispatcher.forward(request, response);
				}
			}
			else	//Pas de code d'accès
			{
				//Redirection vers la JSP d'authentification
				RequestDispatcher dispatcher = request.getRequestDispatcher("connexion_admin.jsp");
				dispatcher.forward(request, response);
			}
		}
		catch(IOException e)
		{
			logger.catching(e);
		}
		catch (ServletException e)
		{
			logger.catching(e);
		}
	}

	/**
	 *  Vérification du webhook par facebook: Vérification de certains paramètres de la requete et renvoit de hub.challenge
	 *  @param request HttpServletRequest
	 *  @param response HttpServletResponse
	 *  @throws IOException
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
				response.getWriter().append("Paramètre manquant pour la validation du webhook");
			}
		}
	}

	/**
	 *  Gère les alertes de nouveaux messages provenant de facebook
	 * @param request
	 * @param response
	 */
	private void traitementAlerteNouveauMessage(HttpServletRequest request, HttpServletResponse response)
	{
		if(chatbot!=null)
		{
			logger.info("Servlet ADMIN","Reçu POST de Facebook");
			synchronized(chatbot)
			{
				if(chatbot.getState()==Thread.State.WAITING)
					chatbot.notify();
				else
					logger.info("Servlet ADMIN","Impossible de notify le thread car il n'est pas en wait");
			}
		}
		try
		{
			response.getWriter().append("HTTP 200 OK");
		}
		catch (IOException e)
		{
			logger.catching(e);
		}
	}

	/**
	 *  Etabli dans la session la connexion, provenant du formulaire de connexion_admin.jsp
	 * @param request
	 * @param response
	 */
	private void connexion(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		
		try
		{
			//Si le code n'a pas déjà été fixé
			if(session.getAttribute("code")==null)
			{
				if(request.getParameter("code").compareTo("lecode")==0)
				{
					session.setAttribute("code", "lecode");
					//Redirection vers la page
					doGet(request, response);
				}
				else //Le code n'est pas bon
				{
					//Redirection vers la JSP d'authentification
					logger.error("Connexion refusé : Erreur de mot de passe");
					RequestDispatcher dispatcher = request.getRequestDispatcher("connexion_admin.jsp");
					dispatcher.forward(request, response);
				}
			}
			else //Le code est déjà fixé
			{
				verificationConnexion(request, response);
			}
		}
		catch (ServletException e)
		{
			logger.catching(e);
		}
		catch (IOException e)
		{
			logger.catching(e);
		}
	}

	/**
	 *  Lance le programme pour l'IA en tant que Thread séparé
	 */
	private void lancementServeur()
	{
		if(chatbot != null)	//A déjà été lancé
		{
			if(chatbot.getState()==Thread.State.TERMINATED) //Est terminé
			{
				chatbot = new ChatBotThread();
				chatbot.start();	//On le relance
			}
			else
				logger.warn("Servlet ADMIN","Impossible de lancer le chatbot, le thread est déjà lancé");
		}
		else	//N'a jamais été lancé
		{
			chatbot = new ChatBotThread();
			chatbot.start();
		}
		
	}

	/**
	 *  Arrête le thread du programme gérant l'IA
	 */
	private void fermetureServeur()
	{
		if(chatbot != null)
			chatbot.arret();
	}

}
