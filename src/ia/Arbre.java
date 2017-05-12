package ia;

/**
 * Classe de l'arbre de décision de l'IA
 *
 */
public class Arbre {
	
	
	/**
	 * Lance l'IA et salue l'utilisateur
	 * @return String
	 */
	public String lancementArbre(){
		return ((Vocabulaire) Salutations.expressions).aleatoire();
	}
	
	/**
	 * Demande à l'utilisateur quels critère de recherche l'intéressent
	 * @return String
	 */
	public String questionCritere()
	{
		String R="";
		String A="";
		String S="";
		String G="";
		// Si Terminé, rechercheCritere();
		if((ConversationIA.isB_realisateur()==true)&&(ConversationIA.isB_acteur()==true)&&(ConversationIA.isB_sortie()==true)&&(ConversationIA.isB_genre()==true))
		{
			rechercheCritere();
		}
		if(ConversationIA.isB_realisateur()==false)
		{
			R=" realisateur";
		}
		if(ConversationIA.isB_acteur()==false)
		{
			A=" acteur";
		}
		if(ConversationIA.isB_sortie()==false)
		{
			S=" date de sortie";
		}
		if(ConversationIA.isB_genre()==false)
		{
			G=" genre";
		}
		return "Quel critere recherchez vous:"+R+A+S+G+" ? Ou bien avez-vous termine ?";
	}
	
	/**
	 * Renvoit un film en fonction des critères proposés par l'utilisateur
	 * @return String
	 */
	private String rechercheCritere() {
		
		return ((Vocabulaire) Recommandation.expressions).aleatoire();//+resultatFilm;
	}

	/**
	 * Demande à l'utilisateur si il est satisfait de notre proposition
	 * @param satisfait boolean
	 */
	public void satisfaction(boolean satisfait){
		if(satisfait==false)
		{
			ConversationIA.setB_realisateur(false);
			ConversationIA.setB_acteur(false);
			ConversationIA.setB_sortie(false);
			ConversationIA.setB_genre(false);
			questionCritere();
		}
		else{
			//Fin conversation
		}
	}
	
	/**
	 * Si aucun film ne correspond aux critères, on relance la recherche
	 * @return String
	 */
	public String erreur(){
		//Envoi message d'erreur
		ConversationIA.setB_realisateur(false);
		ConversationIA.setB_acteur(false);
		ConversationIA.setB_sortie(false);
		ConversationIA.setB_genre(false);
		questionCritere();
		return "truc";
	}
	
	/**
	 * Salue l'utilisateur et arrête l'IA
	 * @return String
	 */
	public String fin()
	{
		return ((Vocabulaire) FinDeConversation.expressions).aleatoire();
	}
}
