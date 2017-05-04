package ia;

public class ConversationIA
{
	String nomUtilisateur;
	
	/*
	 * Initialisation de la conversation
	 */
	public ConversationIA(String nom)
	{
		nomUtilisateur = nom;
	}
	
	/*
	 * Envoi d'un message
	 */
	public String traitementMessage(String message)
	{
		return "Salut "+nomUtilisateur;
	}

}
