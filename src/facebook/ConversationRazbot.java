package facebook;

import java.util.ArrayList;
import java.util.Date;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class ConversationRazbot
{
	JSONObject json;
    private String conversationId;
    private String userName;
    private String userId;
    private ArrayList<MessageRazbot> messages;
    private Date updatedTime;
    private int nonLu;
    private int nombreMessages;
	
	/**
	 * @param json JSONObject
	 */
	public ConversationRazbot(JSONObject json)
	{
		this.json = json;
		importer();
	}
	
	/**
	 * 
	 */
	public void importer()
	{
		try
		{
			//Parcourt du json en ajoutant les infos à ArrayList<MessageRazbot> messages
			
			//Paramètres
			conversationId = json.getString("id");

			if(json.getJSONObject("senders").getJSONArray("data").getJSONObject(0).optString("id") == "156013618115881")
			{
				userName = json.getJSONObject("senders").getJSONArray("data").getJSONObject(1).getString("name");
				userId = json.getJSONObject("senders").getJSONArray("data").getJSONObject(1).getString("id");
			}
			else
			{
				userName = json.getJSONObject("senders").getJSONArray("data").getJSONObject(0).getString("name");
				userId = json.getJSONObject("senders").getJSONArray("data").getJSONObject(0).getString("id");
			}
			
			updatedTime = Utilitaires.parserDateFacebook(json.getString("updated_time"));
			nombreMessages = json.getInt("message_count");
			messages = new ArrayList<>();
			
			//Remplissage des messages
			JSONArray messageData = json.getJSONObject("messages").getJSONArray("data");
			for (int index = 0; index < messageData.length(); index++)
			{
				String message = messageData.getJSONObject(index).getString("message");
				String date = messageData.getJSONObject(index).getString("created_time");
				String auteur = messageData.getJSONObject(index).getJSONObject("from").getString("name");
				String auteurID = messageData.getJSONObject(index).getJSONObject("from").getString("id");
				
				messages.add(new MessageRazbot(message, date, auteur, auteurID));
			}
			
			nonLu = compterNonLu();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return int
	 */
	private int compterNonLu()
	{
		int compteur = 0;
		
		for(int i=0; i<messages.size(); i++)	
		{
			if(!messages.get(i).getAuteurID().equals("156013618115881"))	//Tant qu'on trouve pas un message de la page
			{
				compteur++;
			}
			else
				break;
		}
		
		return compteur;
	}

	/**
	 * Accesseur ConversationId
	 * @return String
	 */
	public String getConversationId()
	{
		return conversationId;
	}

	/**
	 * Accesseur UserName
	 * @return String
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Accesseur UserId
	 * @return String
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * Accesseur Messages
	 * @return ArrayList<MessageRazbot>
	 */
	public ArrayList<MessageRazbot> getMessages()
	{
		return messages;
	}

	/**
	 * Accesseur UpdatedTime
	 * @return Date
	 */
	public Date getUpdatedTime()
	{
		return updatedTime;
	}

	/**
	 * Accesseur NonLu
	 * @return int
	 */
	public int getNonLu()
	{
		return nonLu;
	}

	/**
	 * Accesseur NombreMessages
	 * @return int
	 */
	public int getNombreMessages()
	{
		return nombreMessages;
	}

	/**
	 * Setter ConversationId
	 * @param conversationId String
	 */
	public void setConversationId(String conversationId)
	{
		this.conversationId = conversationId;
	}

	/**
	 * Setter UserName
	 * @param userName String
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * Setter UserId
	 * @param userId String
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * Setter Messages
	 * @param messages ArrayList<MessageRazbot>
	 */
	public void setMessages(ArrayList<MessageRazbot> messages)
	{
		this.messages = messages;
	}

	/**
	 * Setter UpdatedTime
	 * @param updatedTime Date
	 */
	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	/**
	 * Setter NonLu
	 * @param nonLu int
	 */
	public void setNonLu(int nonLu)
	{
		this.nonLu = nonLu;
	}

	/**
	 * Setter NombreMessages
	 * @param nombreMessages int
	 */
	public void setNombreMessages(int nombreMessages)
	{
		this.nombreMessages = nombreMessages;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "\nConversation:\n \tconversationId=" + conversationId + "\n \tuserName=" + userName + "\n \tuserId=" + userId
				+ "\n \tmessages=" + messages + "\n \tupdatedTime=" + updatedTime + "\n \tnonLu=" + nonLu + "\n \tnombreMessages="
				+ nombreMessages + "\n";
	}
}
