package facebook;

import java.util.ArrayList;
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
import facebook4j.Reading;
import facebook4j.FacebookResponse.Metadata;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class PageRazbot
{
	private Facebook facebook;
	private ArrayList<ConversationRazbot> conversations;
	private String appID;
	private String secretCode;
	private String pageToken;

	public PageRazbot()
	{
		// R�cup�ration les identifiants de la page facebook (settings.ini)
		appID = Token.getAppId();
		secretCode = Token.getSecretCode();
		pageToken = Token.getToken();

		// Cr�ation de l'objet facebook
		facebook = new FacebookFactory().getInstance();

		// Identification
		facebook.setOAuthAppId(appID, secretCode);
		AccessToken token = new AccessToken(pageToken);
		facebook.setOAuthAccessToken(token);
//		facebook.setOAuthPermissions("pages_messaging,pages_messaging_subscriptions");
		
		recupererConversations();
	}

	public Facebook getFacebook()
	{
		return facebook;
	}

	/**
	 * D�termine si il y a un nouveau message (toute conversation confondu)
	 * 
	 * @return Vrai si il y a un nouveau message
	 */
	public boolean existeMessagesNonLu()
	{
		return (nombreMessagesNonLu() > 0);
	}
	
	public void recupererConversations()
	{
		//On initialise la liste de conversation
		conversations = new ArrayList<ConversationRazbot>();
		
		//On r�cup�re la liste de facebook
		InboxResponseList<Conversation> conversationsFacebook;
		try
		{
			conversationsFacebook = facebook.getConversations();
		
			//On parcourt la liste
			for (Conversation conversation : conversationsFacebook)
			{
				String id = conversation.getId();
				
				//On r�cup�re la conversation en particulier
				JSONObject json = facebook.callGetAPI(id+"?fields=participants,message_count,unread_count,messages{id,message,to}").asJSONObject();
				
				//On la stock dans la liste de la classe
				conversations.add(new ConversationRazbot(json));
			}
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}

//	public JSONObject derniereConversationNonLuJSON()
//	{
//		try
//		{
//			InboxResponseList<Conversation> conversations;
//			conversations = facebook.getConversations();
//			
//			for (Conversation conversation : conversations)
//			{
//				String id = conversation.getId();
//				
//				JSONObject conversationJSON = facebook.callGetAPI(id+"?fields=participants,message_count,unread_count,messages{id,message,to}").asJSONObject();
//				
//				if(conversationJSON.getInt("unread_count") > 0)
//					return conversationJSON;
//			}
//		}	
//		catch (IllegalStateException e)
//		{
//			e.printStackTrace();
//		}
//		catch (FacebookException e)
//		{
//			e.printStackTrace();
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Conversation derniereConversationNonLu()
//	{
//		try
//		{
//			InboxResponseList<Conversation> conversations;
//			conversations = facebook.getConversations();
//			
//			for (Conversation conversation : conversations)
//			{
//				String id = conversation.getId();
//				
//				JSONObject conversationJSON = facebook.callGetAPI(id+"?fields=updated_time,participants,message_count,unread_count,messages{id,message,to}").asJSONObject();
//				
//				if(conversationJSON.getInt("unread_count") > 0)
//					return creerConversation(conversationJSON);
//			}
//		}	
//		catch (IllegalStateException e)
//		{
//			e.printStackTrace();
//		}
//		catch (FacebookException e)
//		{
//			e.printStackTrace();
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private Conversation creerConversation(JSONObject conversationJSON)
//	{
//		//Non test� & Non termin�
//		Conversation conversation = new Conversation()
//		{
//			JSONObject conversation = conversationJSON;
//			@Override
//			public Metadata getMetadata()
//			{
//				return null;
//			}
//			
//			@SuppressWarnings("deprecation")
//			@Override
//			public Date getUpdatedTime()
//			{
//				String date;
//				Date dateD = null;
//				try
//				{
//					date = conversation.getString("updated_time");
//					int annee = Integer.parseInt(date.substring(0, 4));
//					int mois = Integer.parseInt(date.substring(5, 7));
//					int jour = Integer.parseInt(date.substring(8, 10));
//					int heure = Integer.parseInt(date.substring(11, 13));
//					int minute = Integer.parseInt(date.substring(14, 16));
//					int seconde = Integer.parseInt(date.substring(17, 19));
//					
//					dateD = new Date(annee,mois,jour,heure,minute,seconde);
//				}
//				catch (JSONException e)
//				{
//					e.printStackTrace();
//				}
//				return dateD;
//			}
//			
//			@Override
//			public Integer getUnreadCount()
//			{
//				return nombreMessagesNonLu();
//			}
//			
//			@Override
//			public List<IdNameEntity> getSenders()
//			{
//				return null;
//			}
//			
//			@Override
//			/**
//			 * Provient de l'API Facebook4j, modifi� car non fonctionnel
//			 */
//			public PagableList<Message> getMessages()
//			{
//				
//				return null;
//			}
//			
//			@Override
//			public Integer getMessageCount()
//			{
//				return null;
//			}
//			
//			@Override
//			public String getId()
//			{
//				String id = "";
//				try
//				{
//					id = conversation.getString("id");
//				}
//				catch (JSONException e)
//				{
//					e.printStackTrace();
//				}
//				return id;
//			}
//
//			@Override
//			public String getConversation()
//			{
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//		return conversation;
//	}

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
//
//	public InboxResponseList<Conversation> recupererConversations()
//	{
//		InboxResponseList<Conversation> conversations = null;
//		try
//		{
//			conversations = facebook.getConversations();
//		} catch (IllegalStateException e)
//		{
//			e.printStackTrace();
//		} catch (FacebookException e)
//		{
//			e.printStackTrace();
//		}
//		return conversations;
//	}
//}

// //Tests
// try
// {
// InboxResponseList<Conversation> conversations;
// conversations = facebook.getConversations();
//
// for (Conversation conversation : conversations)
// {
// System.out.println("Conversation(0): "+conversation);
// System.out.println("Message Count(int�gr�):
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
 }
