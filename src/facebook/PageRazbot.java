package facebook;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.InboxResponseList;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;

public class PageRazbot
{
	private Facebook facebook;
	private String appID;
	private String secretCode;
	private String pageToken;

	public PageRazbot()
	{
		// Récupèration les identifiants de la page facebook (settings.ini)
		appID = Token.getAppId();
		secretCode = Token.getSecretCode();
		pageToken = Token.getToken();

		// Création de l'objet facebook
		facebook = new FacebookFactory().getInstance();

		// Identification
		facebook.setOAuthAppId(appID, secretCode);
		AccessToken token = new AccessToken(pageToken);
		facebook.setOAuthAccessToken(token);
	}

	public Facebook getFacebook()
	{
		return facebook;
	}
	
	/**
	 * Détermine si il y a un nouveau message (toute conversation confondu)
	 * @return Vrai si il y a un nouveau message
	 */
	public boolean existeMessagesNonLu()
	{
		return (nombreMessagesNonLu() > 0);
	}

	public int nombreMessagesNonLu()
	{
		int messagesNonLu = 0;
		
		try
		{
			messagesNonLu = facebook.callGetAPI("me?fields=unread_message_count").asJSONObject().getInt("unread_message_count");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
		return messagesNonLu;
	}
	
	public void envoyerMessage(String conversation, String message)
	{
		try
		{
			facebook.answerConversation(conversation, message);
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}
}
	
//			//Tests
//			try
//			{
//				InboxResponseList<Conversation> conversations;
//				conversations = facebook.getConversations();
//				
//				for (Conversation conversation : conversations)
//				{
//					System.out.println("Conversation(0): "+conversation);
//					System.out.println("Message Count(intégré): "+conversation.getMessageCount());
//					
//					RawAPIResponse getMessageCount = facebook.callGetAPI("t_mid.1456287448561:f59d1b19d211d78343?fields=message_count");
//					
//					try
//					{
//						System.out.println("Message Count(manuel): "+getMessageCount.asJSONObject().getInt("message_count"));
//					}
//					catch (JSONException e)
//					{
//						e.printStackTrace();
//					}
//					
//				}
//				
//				
//				//facebook.answerConversation("t_mid.1456287448561:f59d1b19d211d78343", "Test");
//			}
//			catch (IllegalStateException e)
//			{
//				e.printStackTrace();
//			}
//			catch (FacebookException e)
//			{
//				e.printStackTrace();
//			}
