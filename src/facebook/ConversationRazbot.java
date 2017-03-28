package facebook;

import java.util.Date;
import java.util.List;

import facebook4j.IdNameEntity;
import facebook4j.PagableList;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class ConversationRazbot
{
	JSONObject json;
    private String id;
    private String nameFrom;
    private int idFrom;
    private String nameTo;
    private int idTo;
    private String message;
    private Date createdTime;
    private Date updatedTime;
    private Integer unread;
    private Integer unseen;
	
	public ConversationRazbot(JSONObject json)
	{
		this.json = json;
		importer();
	}
	
	public void importer()
	{
		try
		{
			id = json.getString("id");
			System.out.println(json);
			//System.out.println(json.getJSON("to"));
			//nameFrom = json.getJSONObject("to").getString("name");
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}
