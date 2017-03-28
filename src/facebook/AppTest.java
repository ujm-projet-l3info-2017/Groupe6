package facebook;

import facebook4j.Conversation;
import facebook4j.internal.org.json.JSONException;

public class AppTest
{
	public static void main(String args[])
	{
		PageRazbot razbot = new PageRazbot();
		
		razbot.recupererConversations();
		
//		System.out.println("Existe des messages non lu? "+razbot.existeMessagesNonLu());
//		
//		Conversation derniereNonLu = razbot.derniereConversationNonLu();
//		if(derniereNonLu != null)
//		{
//			System.out.println("Conversation non lu trouvée("+derniereNonLu.getId()+"), "+derniereNonLu.getUnreadCount()+" messages non lus");
//			System.out.println("Updated time = "+derniereNonLu.getUpdatedTime());
////			System.out.println(derniereNonLu.getMessages().toString());
//		}
//		else
//		{
//			System.out.println("Aucune conversation non lu trouvée");
//		}
		
		
		
//		razbot.envoyerMessage("t_mid.1456287448561:f59d1b19d211d78343", "Test");
	}
}