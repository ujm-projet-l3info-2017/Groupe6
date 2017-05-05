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
		dateMessage = Utilitaires.parserDateFacebook(date);	//Transforme la date de format Facebook à un type Date
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "\nMessage:"
			 + "\n\tAuteur: "+auteur
			 + "\n\tDate: "+dateMessage
			 + "\n\tTexte:\""+message+"\" \n\n";
	}

}
