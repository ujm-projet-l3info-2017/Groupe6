package facebook;

import java.util.Date;
import java.util.List;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.IdNameEntity;
import facebook4j.InboxResponseList;
import facebook4j.Message;
import facebook4j.PagableList;
import facebook4j.RawAPIResponse;
import facebook4j.FacebookResponse.Metadata;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

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
	 * 
	 * @return Vrai si il y a un nouveau message
	 */
	public boolean existeMessagesNonLu()
	{
		return (nombreMessagesNonLu() > 0);
	}

	public Conversation derniereConversationNonLu()
	{
		try
		{
			InboxResponseList<Conversation> conversations;
			conversations = facebook.getConversations();
			
			for (Conversation conversation : conversations)
			{
				String id = conversation.getId();
				
				JSONObject conversationJSON = facebook.callGetAPI(id+"?fields=participants,message_count,unread_count,messages{id,message,to}").asJSONObject();
				
				if(conversationJSON.getInt("unread_count") > 0)
					return creerConversation(conversationJSON);
			}
		}	
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private Conversation creerConversation(JSONObject conversationJSON)
	{
		//Non testé & Non terminé
		Conversation conversation = new Conversation()
		{
			JSONObject conversation;
			@Override
			public Metadata getMetadata()
			{
				return null;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public Date getUpdatedTime()
			{
				String date;
				try
				{
					date = conversation.getString("updated_time");
					int annee = Integer.getInteger(date.substring(0, 3));
					int mois = Integer.getInteger(date.substring(4, 5));
					int jour = Integer.getInteger(date.substring(6, 7));
					int heure = Integer.getInteger(date.substring(8, 9));
					int minute = Integer.getInteger(date.substring(11, 12));
					int seconde = Integer.getInteger(date.substring(14, 15));

					return new Date(annee,mois,jour,heure,minute,seconde);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public Integer getUnreadCount()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<IdNameEntity> getSenders()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PagableList<Message> getMessages()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Integer getMessageCount()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getId()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getConversation()
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
		return conversation;
	}

	public int nombreMessagesNonLu()
	{
		int messagesNonLu = 0;

		try
		{
			messagesNonLu = facebook.callGetAPI("me?fields=unread_message_count").asJSONObject()
					.getInt("unread_message_count");
		} catch (JSONException e)
		{
			e.printStackTrace();
		} catch (FacebookException e)
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
		} catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}

	public InboxResponseList<Conversation> recupererConversations()
	{
		InboxResponseList<Conversation> conversations = null;
		try
		{
			conversations = facebook.getConversations();
		} catch (IllegalStateException e)
		{
			e.printStackTrace();
		} catch (FacebookException e)
		{
			e.printStackTrace();
		}
		return conversations;
	}
}

// //Tests
// try
// {
// InboxResponseList<Conversation> conversations;
// conversations = facebook.getConversations();
//
// for (Conversation conversation : conversations)
// {
// System.out.println("Conversation(0): "+conversation);
// System.out.println("Message Count(intégré):
// "+conversation.getMessageCount());
//
// RawAPIResponse getMessageCount =
// facebook.callGetAPI("t_mid.1456287448561:f59d1b19d211d78343?fields=message_count");
//
// try
// {
// System.out.println("Message Count(manuel):
// "+getMessageCount.asJSONObject().getInt("message_count"));
// }
// catch (JSONException e)
// {
// e.printStackTrace();
// }
//
// }
//
//
// //facebook.answerConversation("t_mid.1456287448561:f59d1b19d211d78343",
// "Test");
// }
// catch (IllegalStateException e)
// {
// e.printStackTrace();
// }
// catch (FacebookException e)
// {
// e.printStackTrace();
// }
