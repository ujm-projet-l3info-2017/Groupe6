package chatbot;

import java.io.IOException;

public class LancementProgManuel
{
	public static void main(String args[])
	{
		//Ceci se retrouvera dans la page d'administration pour lancer le serveur
		
		ChatBotThread chatbot = new ChatBotThread();
		
		chatbot.start();
		
		try{System.in.read();}catch (IOException e){e.printStackTrace();}
		
		chatbot.arret();
		
//		synchronized (chatbot)
//		{
//			chatbot.notify();
//		}
	}
}