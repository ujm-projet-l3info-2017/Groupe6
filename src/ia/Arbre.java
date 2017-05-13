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
	public static String lancementArbre(){
		Salutations salut = new Salutations();
		return salut.aleatoire()+". Que souhaitez-vous : une recherche de film par critere ou un film aleatoire ?";
		//return "Bonjour. Que souhaitez-vous : une recherche de film par critere ou un film aleatoire ?";
	}
	
	/**
	 * Renvoit un film en fonction des critères proposés par l'utilisateur
	 * @return String
	 */
	private static String rechercheCritere() {
		
		Recommandation recom = new Recommandation();
		return recom.aleatoire();//+resultatFilm;
	}
	
	/**
	 * Salue l'utilisateur et arrête l'IA
	 * @return String
	 */
	public static String fin()
	{
		FinDeConversation fin = new FinDeConversation();
		return fin.aleatoire();
		//return "Au revoir.";
	}
}
