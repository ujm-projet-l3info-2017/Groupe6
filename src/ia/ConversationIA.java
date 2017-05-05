package ia;

public class ConversationIA
{
	/**
	 * Contient le nom de l'utilisateur
	 */
	String nomUtilisateur;
	
	/**
	 * Initialisation de la conversation
	 * @param nom String
	 */
	public ConversationIA(String nom)
	{
		nomUtilisateur = nom;
	}
	

	/**
	 * Lis et renvoit un message
	 * @param message String
	 * @return String
	 */
	public String traitementMessage(String message)
	{
		return ((Vocabulaire) Salutations.expressions).aleatoire()+nomUtilisateur;
	}

}
