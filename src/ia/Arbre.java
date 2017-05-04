package ia;

public class Arbre {
	private boolean b_realisateur=false;
	private boolean b_acteur=false;
	private boolean b_sortie=false;
	private boolean b_genre=false;
	private String s_realisateur,s_acteur,s_sortie,s_genre;
	
	public String lancementArbre(){
		return "Bonjour"; //Voir avec les listes de vocabulaire
	}
	
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
	
	private String rechercheCritere() {
		
		return "Voila";//+resultatFilm; //Voir avec listes de vocabulaire
	}

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
	
	public String erreur(){
		//Envoi message d'erreur
		b_realisateur=false;
		b_acteur=false;
		b_sortie=false;
		b_genre=false;
		questionCritere();
		return "truc";
	}
	
	public String fin()
	{
		return "Au revoir"; //Utiliser les liste de vocabulaire
	}
}
