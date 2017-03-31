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
	
	public ConversationRazbot(JSONObject json)
	{
		this.json = json;
		importer();
	}
	
	public void importer()
	{
		try
		{
			//Parcourt du json en ajoutant les infos à ArrayList<MessageRazbot> messages
			
			//Paramètres
			conversationId = json.getString("id");
			userName = json.getJSONObject("messages").getJSONArray("data").getJSONObject(1).getJSONObject("from").getString("name");
			userId = json.getJSONObject("messages").getJSONArray("data").getJSONObject(1).getJSONObject("from").getString("id");
			updatedTime = Utilitaires.parserDateFacebook(json.getString("updated_time"));
			nombreMessages = json.getInt("message_count");
			nonLu = json.getInt("unread_count");
			messages = new ArrayList<>();
			
			//Remplissage des messages
			JSONArray messageData = json.getJSONObject("messages").getJSONArray("data");
			for (int index = 0; index < messageData.length(); index++)
			{
				String message = messageData.getJSONObject(index).getString("message");
				String date = messageData.getJSONObject(index).getString("created_time");
				String auteur = messageData.getJSONObject(index).getJSONObject("from").getString("name");
				
				messages.add(new MessageRazbot(message, date, auteur));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public String getConversationId()
	{
		return conversationId;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getUserId()
	{
		return userId;
	}

	public ArrayList<MessageRazbot> getMessages()
	{
		return messages;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}

	public int getNonLu()
	{
		return nonLu;
	}

	public int getNombreMessages()
	{
		return nombreMessages;
	}

	public void setConversationId(String conversationId)
	{
		this.conversationId = conversationId;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void setMessages(ArrayList<MessageRazbot> messages)
	{
		this.messages = messages;
	}

	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public void setNonLu(int nonLu)
	{
		this.nonLu = nonLu;
	}

	public void setNombreMessages(int nombreMessages)
	{
		this.nombreMessages = nombreMessages;
	}

	@Override
	public String toString()
	{
		return "\nConversation:\n \tconversationId=" + conversationId + "\n \tuserName=" + userName + "\n \tuserId=" + userId
				+ "\n \tmessages=" + messages + "\n \tupdatedTime=" + updatedTime + "\n \tnonLu=" + nonLu + "\n \tnombreMessages="
				+ nombreMessages + "\n";
	}
}
