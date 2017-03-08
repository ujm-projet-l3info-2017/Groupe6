package facebook;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.InboxResponseList;
import facebook4j.Message;
import facebook4j.PagableList;
import facebook4j.Reading;
import facebook4j.api.ConversationMethods;
import facebook4j.auth.AccessToken;

public class AppTest
{
	public static void main(String args[])
	{
		//Récupèration les identifiants de la page facebook (settings.ini)
		String appID = Token.getAppId();
		String secretCode = Token.getSecretCode();
		String pageToken = Token.getToken();
		
		//Création de l'objet facebook
		Facebook facebook = new FacebookFactory().getInstance();
		
		//Identification
		facebook.setOAuthAppId(appID,secretCode);
		AccessToken token = new AccessToken(pageToken);
		facebook.setOAuthAccessToken(token);
		
		//Tests
		try
		{
			InboxResponseList<Conversation> conversations;
			conversations = facebook.getConversations();
			Conversation conv1 = conversations.get(0);
			
			System.out.println(conv1);
			//facebook.answerConversation("t_mid.1456287448561:f59d1b19d211d78343", "Test");
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}
}