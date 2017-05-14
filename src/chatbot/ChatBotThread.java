package chatbot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import facebook.ConversationRazbot;
import facebook.PageRazbot;
import facebook.Token;
import ia.ConversationIA;

public class ChatBotThread extends Thread
{
	/** Initialisation du log */
	static final Logger logger = LogManager.getLogger(ChatBotThread.class.getName());
	  
	/** L'objet qui gère les conversations */
	public PageRazbot razbot;
	/** Les IA associées à chaque conversation */
	private Hashtable<String,ConversationIA> conversationsIA;
	/** Sert à stopper le thread proprement */
	boolean continuer = true;
	
	/**
	 *  On initialise la table de hash et la page
	 */
	public ChatBotThread()
	{
		super();
		razbot = new PageRazbot();
		conversationsIA = new Hashtable<>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public synchronized void run()
	{
		
//		try{System.in.read();}catch (IOException e){e.printStackTrace();} //GETCHAR
		
		logger.info("ThreadProgramme","Démarrage du programme");
		while(continuer && !Thread.currentThread().isInterrupted())
		{
			if(Token.isWebhookEnable())
			{
				logger.info("ThreadProgramme","Mode WebHook");
				// Méthode par webhook
				gestionMessagesWebhook();
			}
			else
			{
				logger.info("ThreadProgramme","Mode vérification ");
				// Méthode récupération par interval de temps
				gestionMessagesInterval();
			}
		}
		logger.info("ThreadProgramme","Le programme a bien été arrêté");
	}

	/**
	 *  Gestion des nouveaux messages par méthode de vérification ponctuelle
	 */
	private synchronized void gestionMessagesInterval()
	{
		synchronized (razbot)
		{
			//On récupère la liste des conversations avec des messages à traiter
			ArrayList<ConversationRazbot> conversationsATraiter = razbot.attenteNouveauMessageAlternatif();
			
			//On les traite une par une
			for (ConversationRazbot conv : conversationsATraiter)
			{
				logger.info(conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
				
				//On charge la conversation correspondante (ou on la crée)
				ConversationIA convIA = correspondanceConversation(conv.getConversationId(), conv.getUserName());
				//On fait traiter le message
				String reponse = convIA.traitementMessage(conv.getMessages().get(0).getMessage());
				//On renvoit la réponse
				razbot.envoyerMessage(conv.getConversationId(), reponse);
			}
		}
	}

	/**
	 *  Gestion des nouveaux messages par méthode du webhook (uniquement fonctionnel sur le serveur associé au nom de domaine razbot.jeamme.fr)
	 */
	private synchronized void gestionMessagesWebhook()
	{
		try
		{
			synchronized (razbot)
			{
				//On met les conversations de la page à jour
				razbot.recupererConversations();
				
				//On récupère la liste des conversations avec des messages à traiter
				ArrayList<ConversationRazbot> conversationsATraiter = razbot.getConversationsNouveauxMessages();
			
				//On les traite une par une
				for (ConversationRazbot conv : conversationsATraiter)
				{
					logger.info(conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
					
					//On charge la conversation correspondante (ou on la crée)
					ConversationIA convIA = correspondanceConversation(conv.getConversationId(), conv.getUserName());
					//On fait traiter le message
					String reponse = convIA.traitementMessage(conv.getMessages().get(0).getMessage());
					//On renvoit la réponse
					razbot.envoyerMessage(conv.getConversationId(), reponse);
			
					//razbot.envoyerMessage(conv.getConversationId(), testApiAllocine(conv.getMessages().get(0).getMessage())); //Test Allocine
				}
				
				logger.info("ThreadProgramme","Programme en attente de webhook");
				wait();
				logger.info("ThreadProgramme","Webhook de nouveau message reçu");
			}
		}
		catch (InterruptedException e)
		{
			logger.error("ThreadProgramme","Interruption de la boucle");
			e.printStackTrace();
		}
	}

	/**
	 *  Récupère ou crée l'objet ConversationIA associé
	 * @param conversationId Id de la conversation String
	 * @param username Nom de l'utilisateur avec qui on parle String
	 * @return ConversationIA l'objet contenant l'IA de cette conversation
	 */
	private ConversationIA correspondanceConversation(String conversationId, String username)
	{
		//Si la l'IA de cette conversation existe déjà
		if(conversationsIA.containsKey(conversationId))
		{
			logger.info("ThreadProgramme","Utilisation d'un objet de gestion d'IA existant pour cette conversation");
			return conversationsIA.get(conversationId);
		}
		else //Sinon on la crée
		{
			logger.info("ThreadProgramme","Création d'un objet de gestion d'IA pour cette conversation");
			conversationsIA.put(conversationId, new ConversationIA(username));
			return conversationsIA.get(conversationId);
		}
	}

	/**
	 *  Arrêt du thread
	 */
	public void arret()
	{
		System.out.println("Demande d'arrêt du programme reçu");
		continuer = false;
	}
}

