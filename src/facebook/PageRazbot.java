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
		facebook.setOAuthPermissions("pages_messaging,pages_messaging_subscriptions");
		
		recupererConversations();
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
		}
		catch (FacebookException e) 
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
	 * Attente des messages par utilisation d'appels à l'API Graph tout les x secondes (définis dans settings.ini)
	 * @return ArrayList
	 */
	public ArrayList<ConversationRazbot> attenteNouveauMessageAlternatif()
	{
		ArrayList<ConversationRazbot> conv;
		long interval = Token.getInverval();
		
		while(true)
		{
			//On vérifie & Récupère la liste
			if(!(conv = getConversationsNouveauxMessages()).isEmpty())
				return conv;
			
			System.out.println(new Date()+": Aucun nouveau message");
			
			//Sinon on fait une boucle de plus
			try
			{
				Thread.sleep(interval); //Importé depuis le fichier de config
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Renvoi les conversations avec des nouveaux messages
	 * @return La liste des conversations
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
	
	@Override
	public String toString()
	{
		return "PageRazbot:\n" + conversations;
	}

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