package facebook;

import java.util.Date;

public class MessageRazbot
{
	private String message;
	private Date dateMessage;
	private String auteur; 
	private String auteurID;
	
	public MessageRazbot(String message, String date, String auteur, String auteurID)
	{
		this.message = message;
		dateMessage = Utilitaires.parserDateFacebook(date);	//Transforme la date de format Facebook à un type Date
		this.auteur = auteur;
		this.auteurID = auteurID;
	}
	
	public String getMessage()
	{
		return message;
	}

	public Date getDateMessage()
	{
		return dateMessage;
	}
	
	public String getAuteur()
	{
		return auteur;
	}
	
	@Override
	public String toString()
	{
		return "\nMessage:"
			 + "\n\tAuteur: "+auteur
			 + "\n\tDate: "+dateMessage
			 + "\n\tTexte:\""+message+"\" \n\n";
	}

	public String getAuteurID()
	{
		return auteurID;
	}
}
