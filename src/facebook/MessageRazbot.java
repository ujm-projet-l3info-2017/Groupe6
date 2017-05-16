package facebook;

import java.util.Date;

public class MessageRazbot
{
	private String message;
	private Date dateMessage;
	private String auteur; 
	private String auteurID;
	
	/**
	 * Constructeur MessageRazbot
	 * @param message String
	 * @param date String
	 * @param auteur String
	 * @param auteurID String
	 */
	public MessageRazbot(String message, String date, String auteur, String auteurID)
	{
		this.message = message;
		dateMessage = ConversationRazbot.parserDateFacebook(date);	//Transforme la date de format Facebook à un type Date
		this.auteur = auteur;
		this.auteurID = auteurID;
	}
	
	/**
	 * Accesseur Message
	 * @return String
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Accesseur DateMessage
	 * @return Date
	 */
	public Date getDateMessage()
	{
		return dateMessage;
	}
	
	/**
	 * Accesseur Auteur
	 * @return String
	 */
	public String getAuteur()
	{
		return auteur;
	}
	
	/**
	 * Accesseur AuteurID
	 * @return String
	 */
	public String getAuteurID()
	{
		return auteurID;
	}
	
	@Override
	public String toString()
	{
		return "MessageRazbot [message=" + message + ", dateMessage=" + dateMessage + ", auteur=" + auteur
				+ ", auteurID=" + auteurID + "]";
	}
}
