package ia;

public class Arbre {
	private boolean b_realisateur=false;
	private boolean b_acteur=false;
	private boolean b_sortie=false;
	private boolean b_genre=false;
	private String s_realisateur,s_acteur,s_sortie,s_genre;
	
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
		if((b_realisateur==true)&&(b_acteur==true)&&(b_sortie==true)&&(b_genre==true))
		{
			rechercheCritere();
		}
		if(b_realisateur==false)
		{
			R=" realisateur";
		}
		if(b_acteur==false)
		{
			A=" acteur";
		}
		if(b_sortie==false)
		{
			S=" date de sortie";
		}
		if(b_genre==false)
		{
			G=" genre";
		}
		return "Quel critere recherchez vous:"+R+A+S+G+" ?";
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
			b_realisateur=false;
			b_acteur=false;
			b_sortie=false;
			b_genre=false;
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
		b_realisateur=false;
		b_acteur=false;
		b_sortie=false;
		b_genre=false;
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
