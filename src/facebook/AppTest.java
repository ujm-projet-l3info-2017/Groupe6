package facebook;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.InboxResponseList;
import facebook4j.Message;
import facebook4j.PagableList;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.api.ConversationMethods;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;

public class AppTest
{
	public static void main(String args[])
	{
		PageRazbot razbot = new PageRazbot();
		
		System.out.println(razbot.existeMessagesNonLu());
		
		razbot.envoyerMessage("t_mid.1456287448561:f59d1b19d211d78343", "Test");
	}
}