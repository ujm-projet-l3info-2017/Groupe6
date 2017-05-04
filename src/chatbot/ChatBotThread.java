package chatbot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import facebook.ConversationRazbot;
import facebook.PageRazbot;
import facebook.Token;
import ia.ConversationIA;

public class ChatBotThread extends Thread
{
	/** L'objet qui g�re les conversations*/
	public PageRazbot razbot;
	/** Les IA associ�es � chaque conversation */
	private Hashtable<String,ConversationIA> conversationsIA;
	/** Sert � stopper le thread proprement */
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
	
	public synchronized void run()
	{
		
//		try{System.in.read();}catch (IOException e){e.printStackTrace();} //GETCHAR
		
		System.out.println("D�marrage du programme");
		while(continuer && !Thread.currentThread().isInterrupted())
		{
			if(Token.isWebhookEnable())
			{
				System.out.println("Mode WEBHOOK");
				// M�thode par webhook
				gestionMessagesWebhook();
			}
			else
			{
				System.out.println("Mode Interval");
				// M�thode r�cup�ration par interval de temps
				gestionMessagesInterval();
			}
		}
		System.out.println("Le programme a bien �t� arr�t�");
	}

	/**
	 *  Gestion des nouveaux messages par m�thode de v�rification ponctuelle
	 */
	private synchronized void gestionMessagesInterval()
	{
		synchronized (razbot)
		{
			//On r�cup�re la liste des conversations avec des messages � traiter
			ArrayList<ConversationRazbot> conversationsATraiter = razbot.attenteNouveauMessageAlternatif();
			
			//On les traite une par une
			for (ConversationRazbot conv : conversationsATraiter)
			{
				System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
				
				//On charge la conversation correspondante (ou on la cr�e)
				ConversationIA convIA = correspondanceConversation(conv.getConversationId(), conv.getUserName());
				//On fait traiter le message
				String reponse = convIA.traitementMessage(conv.getMessages().get(0).getMessage());
				//On renvoit la r�ponse
				razbot.envoyerMessage(conv.getConversationId(), reponse);
			}
		}
	}

	/**
	 *  Gestion des nouveaux messages par m�thode du webhook (uniquement fonctionnel sur le serveur associ� au nom de domaine razbot.jeamme.fr)
	 */
	private synchronized void gestionMessagesWebhook()
	{
		try
		{
			synchronized (razbot)
			{
				//On met les conversations de la page � jour
				razbot.recupererConversations();
				
				//On r�cup�re la liste des conversations avec des messages � traiter
				ArrayList<ConversationRazbot> conversationsATraiter = razbot.getConversationsNouveauxMessages();
			
				//On les traite une par une
				for (ConversationRazbot conv : conversationsATraiter)
				{
					System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
					
					//On charge la conversation correspondante (ou on la cr�e)
					ConversationIA convIA = correspondanceConversation(conv.getConversationId(), conv.getUserName());
					//On fait traiter le message
					String reponse = convIA.traitementMessage(conv.getMessages().get(0).getMessage());
					//On renvoit la r�ponse
					razbot.envoyerMessage(conv.getConversationId(), reponse);
			
					//razbot.envoyerMessage(conv.getConversationId(), testApiAllocine(conv.getMessages().get(0).getMessage())); //Test Allocine
				}
				
				System.out.println("Programme en attente de webhook");
				wait();
				System.out.println("Webhook de nouveau message re�u");
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  R�cup�re ou cr�e l'objet ConversationIA associ�
	 * @param conversationId Id de la conversation
	 * @param username Nom de l'utilisateur avec qui on parle
	 * @return l'objet contenant l'IA de cette conversation
	 */
	private ConversationIA correspondanceConversation(String conversationId, String username)
	{
		//Si la l'IA de cette conversation existe d�j�
		if(conversationsIA.containsKey(conversationId))
		{
			System.out.println("Utilisation d'un objet de gestion d'IA existant pour cette conversation\n");
			return conversationsIA.get(conversationId);
		}
		else //Sinon on la cr�e
		{
			System.out.println("Cr�ation d'un objet de gestion d'IA pour cette conversation\n");
			conversationsIA.put(conversationId, new ConversationIA(username));
			return conversationsIA.get(conversationId);
		}
	}

	/**
	 *  Arr�t du thread
	 */
	public void arret()
	{
		System.out.println("Demande d'arr�t du programme re�u");
		continuer = false;
	}
}

