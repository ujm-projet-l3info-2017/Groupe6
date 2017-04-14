package facebook;

import java.util.ArrayList;
import java.util.Date;

public class AppTest
{
	public static void main(String args[])
	{
		PageRazbot razbot = new PageRazbot(); 
		
//		try{System.in.read();}catch (IOException e){e.printStackTrace();} //GETCHAR
		
		while(true)
		{
			//Attente nouveaux messages
			ArrayList<ConversationRazbot> conversationsATraiter = razbot.attenteNouveauMessageAlternatif();
			
			for (ConversationRazbot conv : conversationsATraiter)
			{
				System.out.println(new Date()+": "+conv.getNonLu()+" nouveau(x) message(s) dans la conversation avec: "+conv.getUserName());
				razbot.envoyerMessage(conv.getConversationId(), conv.getMessages().get(0).getMessage());
			}
		}
	}
}