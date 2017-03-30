package facebook;

import java.util.Date;

public class MessageRazbot
{
	private String message;
	private Date dateMessage;
	
	public MessageRazbot(String message, String date)
	{
		this.message = message;
		dateMessage = Utilitaires.parserDateFacebook(date);
	}
	
	public String getMessage()
	{
		return message;
	}

	public Date getDateMessage()
	{
		return dateMessage;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setDateMessage(Date dateMessage)
	{
		this.dateMessage = dateMessage;
	}
}
