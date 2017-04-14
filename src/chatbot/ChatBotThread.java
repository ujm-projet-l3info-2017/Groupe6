package chatbot;

import java.util.ArrayList;
import java.util.Date;

import facebook.ConversationRazbot;
import facebook.PageRazbot;

public class ChatBotThread extends Thread
{
	PageRazbot razbot;
	boolean continuer = true;
	
	public ChatBotThread()
	{
		super();
	}
	
	public synchronized void run()
	{
		razbot = new PageRazbot(); 
		
//		try{System.in.read();}catch (IOException e){e.printStackTrace();} //GETCHAR
		
		while(continuer)
		{
			// Méthode récupération par interval de temps
				gestionMessagesInterval();
			
			// Méthode par webhook
				//gestionMessagesWebhook();
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
		}
	}

	private synchronized void gestionMessagesWebhook()
	{
		try
		{
			synchronized (razbot)
			{
				System.out.println("Programme en attente de webhook");
				wait();
				System.out.println("Webhook de nouveau message reçu");
				razbot.recupererConversations();
				
				ArrayList<ConversationRazbot> conversationsATraiter = razbot.getConversationsNouveauxMessages();
			
				for (ConversationRazbot conv : conversationsATraiter)
				{
					System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
					razbot.envoyerMessage(conv.getConversationId(), conv.getMessages().get(0).getMessage());
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void arret()
	{
		continuer = false;
	}
}