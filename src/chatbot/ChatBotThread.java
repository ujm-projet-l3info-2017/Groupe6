package chatbot;

import java.util.ArrayList;
import java.util.Date;

import allocine.RechercheAllocine;
import facebook.ConversationRazbot;
import facebook.PageRazbot;
import facebook.Token;

public class ChatBotThread extends Thread
{
	public PageRazbot razbot;

	boolean continuer = true;
	
	public ChatBotThread()
	{
		super();

		razbot = new PageRazbot();
	}
	
	public synchronized void run()
	{
		
//		try{System.in.read();}catch (IOException e){e.printStackTrace();} //GETCHAR
		
		System.out.println("Démarrage du programme");
		while(continuer && !Thread.currentThread().isInterrupted())
		{
			if(Token.isWebhookEnable())
			{
				System.out.println("Mode WEBHOOK");
				// Méthode par webhook
				gestionMessagesWebhook();
			}
			else
			{
				System.out.println("Mode Interval");
				// Méthode récupération par interval de temps
				gestionMessagesInterval();
			}
		}
		System.out.println("Le programme a bien été arrêté");
	}

	private void gestionMessagesInterval()
	{
		ArrayList<ConversationRazbot> conversationsATraiter = razbot.attenteNouveauMessageAlternatif();

		for (ConversationRazbot conv : conversationsATraiter)
		{
			System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
			//razbot.envoyerMessage(conv.getConversationId(), conv.getMessages().get(0).getMessage());
			razbot.envoyerMessage(conv.getConversationId(), testApiAllocine(conv.getMessages().get(0).getMessage()));
		}
	}

	private String testApiAllocine(String message)
	{
		String infos[] = message.split(",");
		String resultat;
		
		System.out.println(infos.length);
		if(infos.length < 1)
		{
			resultat = "Introuvable";
		}
		else
		{
			resultat = RechercheAllocine.informationFilm(infos[0],infos[1]);
		}
		
		return RechercheAllocine.informationFilm(infos[0],infos[1]);
	}

	private synchronized void gestionMessagesWebhook()
	{
		try
		{
			synchronized (razbot)
			{
				razbot.recupererConversations();
				
				ArrayList<ConversationRazbot> conversationsATraiter = razbot.getConversationsNouveauxMessages();
			
				for (ConversationRazbot conv : conversationsATraiter)
				{
					System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
					razbot.envoyerMessage(conv.getConversationId(), testApiAllocine(conv.getMessages().get(0).getMessage()));
				}
				
				System.out.println("Programme en attente de webhook");
				wait();
				System.out.println("Webhook de nouveau message reçu");
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void arret()
	{
		System.out.println("Demande d'arrêt du programme envoyé");
		continuer = false;
	}
}