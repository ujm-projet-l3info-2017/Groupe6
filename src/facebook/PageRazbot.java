package facebook;

import java.util.ArrayList;
import java.util.Date;

import facebook4j.Conversation;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.InboxResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONObject;

public class PageRazbot
{
	/** L'API Facebook */
	private Facebook facebook;
	/** La liste des conversations*/
	private ArrayList<ConversationRazbot> conversations;
	/** L'id de l'app Facebook utilis�e*/
	private String appID;
	/** Le secret code de l'app Facebook utilis�e*/
	private String secretCode;
	/** Le token d'acc�s de la page Razbot*/
	private String pageToken;

	/**
	 *  Constucteur qui r�cup�re les ID/Tokens, initialise l'API Facebook, et r�cup�re les conversations
	 */
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
		facebook.setOAuthPermissions("pages_messaging,pages_messaging_subscriptions");
		
		recupererConversations();
	}

	/**
	 *  M�thode pour envoyer un message avec le bot
	 * @param conversation � laquelle on envoit un message String
	 * @param message � envoyer String
	 */
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
	
	/**
	 *  R�cup�re dans l'objet conversation les conversations � jour
	 */
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
				JSONObject json = facebook.callGetAPI(id+"?fields=updated_time,participants,senders,message_count,unread_count,messages{created_time,message,from}").asJSONObject();
				
				//On marque la conversation comme lu
				facebook.getCheckin(conversation.getId());
				
				//On la stock dans la liste de la classe
				conversations.add(new ConversationRazbot(json));
			}
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Attente des messages par utilisation d'appels � l'API Graph tout les x secondes (d�finis dans settings.ini)
	 * @return ArrayList
	 */
	public ArrayList<ConversationRazbot> attenteNouveauMessageAlternatif()
	{
		ArrayList<ConversationRazbot> conv;
		long interval = Token.getInverval();
		
		while(true)
		{
			//On v�rifie & R�cup�re la liste
			if(!(conv = getConversationsNouveauxMessages()).isEmpty())
				return conv;
			
			System.out.println(new Date()+": Aucun nouveau message");
			
			//Sinon on fait une boucle de plus
			try
			{
				Thread.sleep(interval); //Import� depuis le fichier de config
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Renvoi les conversations avec des nouveaux messages
	 * @return ArrayList<ConversationRazbot> La liste des conversations
	 */
	public ArrayList<ConversationRazbot> getConversationsNouveauxMessages()
	{
		recupererConversations();
		
		ArrayList<ConversationRazbot> conversationNonLu = new ArrayList<ConversationRazbot>();
		
		for (ConversationRazbot conv : conversations)
		{
			if(conv.getNonLu()>0)
			{
				conversationNonLu.add(conv);
			}
		}
		return conversationNonLu;
	}
	
	/**
	 *  Getter pour l'api facebook
	 * @return Facebook l'objet Facebook (API)
	 */
	public Facebook getFacebook()
	{
		return facebook;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PageRazbot:\n" + conversations;
	}

	/**
	 *  Fonction pour faire des requ�tes GET avec la m�thode de l'API Facebook
	 * @param url String
	 * @return JSONObject
	 */
	public JSONObject requeteGET(String url)
	{
		try
		{
			return (JSONObject) facebook.callGetAPI(url);
		}
		catch (FacebookException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}