package facebook;

import java.util.Date;

public class MessageRazbot
{
	private String message;
	private Date dateMessage;
	private String auteur;
	
	public MessageRazbot(String message, String date, String auteur)
	{
		this.message = message;
		dateMessage = Utilitaires.parserDateFacebook(date);	//Transforme la date de format Facebook à un type Date
		this.auteur = auteur;
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
	
	public String getAuteur()
	{
		return auteur;
	}

	public void setAuteur(String auteur)
	{
		this.auteur = auteur;
	}
	
	@Override
	public String toString()
	{
		return "\nMessage:"
			 + "\n\tAuteur: "+auteur
			 + "\n\tDate: "+dateMessage
			 + "\n\tTexte:\""+message+"\" \n\n";
	}
}
