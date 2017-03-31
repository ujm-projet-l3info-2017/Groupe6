package facebook;

import java.util.ArrayList;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.InboxResponseList;
import facebook4j.auth.AccessToken;
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
//		facebook.setOAuthPermissions("pages_messaging,pages_messaging_subscriptions");
		
		recupererConversations();
		
		System.out.println(conversations.toString());
	}

	public Facebook getFacebook()
	{
		return facebook;
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
	
	public void recupererConversations()
	{
		//On initialise la liste de conversation
		conversations = new ArrayList<ConversationRazbot>();
		
		//On récupère la liste de facebook
		InboxResponseList<Conversation> conversationsFacebook;
		try
		{
			conversationsFacebook = facebook.getConversations();
		
			//On parcourt la liste
			for (Conversation conversation : conversationsFacebook)
			{
				String id = conversation.getId();
				
				//On récupère la conversation en particulier
				JSONObject json = facebook.callGetAPI(id+"?fields=updated_time,participants,message_count,unread_count,messages{created_time,message,from}").asJSONObject();
				
				//On la stock dans la liste de la classe
				conversations.add(new ConversationRazbot(json));
			}
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}

	
	@Override
	public String toString()
	{
		return "PageRazbot:\n" + conversations;
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
//		//Non testé & Non terminé
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
//			 * Provient de l'API Facebook4j, modifié car non fonctionnel
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
//
//	public int nombreMessagesNonLu()
//	{
//		//Emmerdant, on a les valeur de non lu uniquement pour 1 conversation..
//	}
//	
//	/**
//	 * Détermine si il y a un nouveau message (toute conversation confondu)
//	 * 
//	 * @return Vrai si il y a un nouveau message
//	 */
//	public boolean existeMessagesNonLu()
//	{
//		return (nombreMessagesNonLu() > 0);
//	}
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
	
}