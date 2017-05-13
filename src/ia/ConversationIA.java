package ia;

import java.util.ArrayList;


public class ConversationIA
{
	/**
	 * Contient le nom de l'utilisateur, les criteres (booleen et String) et l'avancement de la discussion
	 */
	static String nomUtilisateur;
	static boolean b_realisateur, b_acteur, b_sortie, b_genre;
	static String s_realisateur,s_acteur,s_sortie,s_genre;
	private String etape; // debut, discussion, p_critere, recherche, satisfaction, recommencer, erreur, fin
	RechercherMot recherche;
	ArrayList<String> dicoGenre;
	/**
	 * Initialisation de la conversation
	 * @param nom String
	 */
	public ConversationIA(String nom)
	{
		nomUtilisateur = nom;
		b_realisateur=false;
		b_acteur=false;
		b_sortie=false;
		b_genre=false;
		s_realisateur="";
		s_acteur="";
		s_sortie="";
		s_genre="";
		etape = "debut"; // Si il y a un seul appel de ConversationIA
	}
	

	/**
	 * Lis le message de l'utilisateur, effectue un traitement et renvoie un message
	 * @param message String
	 * @return String : La reponse de l'IA
	 */
	public String traitementMessage(String message)
	{
		if(etape=="debut")
		{
			etape="discussion";
			return Arbre.lancementArbre();
		}		
		ArrayList<String> dico = new ArrayList<String>();
		ArrayList<String> motTrouves = new ArrayList<String>();
		
		//Dictionnaire des mots cles
		dico.add("critere");
		dico.add("criteres");
		dico.add("aleatoire");
		dico.add("oui");
		dico.add("non");
		dico.add("realisateur");
		dico.add("acteur");
		dico.add("genre");
		dico.add("termine");
		dico.add("au revoir");
		dico.add("date de sortie");
		
		//Dictionnaire des genres
		dicoGenre = new ArrayList<String>();
		dicoGenre.add("action");
		dicoGenre.add("anim");
		dicoGenre.add("biopic");
		dicoGenre.add("aventur");
		dicoGenre.add("comique");
		dicoGenre.add("comédie");
		dicoGenre.add("drôle");
		dicoGenre.add("drole");
		dicoGenre.add("dram");
		dicoGenre.add("erotique");
		dicoGenre.add("sex");
		dicoGenre.add("histo");
		dicoGenre.add("fantaisi");
		dicoGenre.add("polic");
		dicoGenre.add("roman");
		dicoGenre.add("science");
		dicoGenre.add("fiction");
		dicoGenre.add("thriller");
		dicoGenre.add("western");
		
		recherche = new RechercherMot();
		String messageCorrige = recherche.analysePhrase(message, dico);
		motTrouves = recherche.chercherMotsCles(messageCorrige, dico);
		
		if((etape=="discussion")&&((motTrouves.contains("critere"))||(motTrouves.contains("criteres"))))
		{
			etape="p_critere";
			return Arbre.questionCritere();
		}
		if((etape=="p_critere")&&(!motTrouves.contains("termine")))
		{
			return critereUtilisateur(message, motTrouves);
		}
		if((etape=="p_critere")&&(motTrouves.contains("termine")))
		{
			etape="recherche";
			// Faire une recherche avec les criteres enregistr�s puis rappeler l'IA
			// Si on ne trouve aucun film appeler erreur()
			return "Cas termine";
		}
		if((etape=="discussion")&&(motTrouves.contains("aleatoire")))
		{
			etape="r_aleatoire";
			// Faire une recherche al�atoire et envoyer le resultat puis rappeler l'IA
			// Si on ne trouve aucun film (ce qui serait etrange) appeler erreur()
			return "Cas aleatoire";
		}
		if(etape=="recherche")
		{
			etape="satisfaction";
			return "Etes-vous satisfaits de cette proposition ?";
		}
		if((etape=="satisfaction")&&(motTrouves.contains("oui")))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
		if((etape=="satisfaction")&&(motTrouves.contains("non")))
		{
			etape="recommencer";
			b_realisateur=false;
			b_acteur=false;
			b_sortie=false;
			b_genre=false;
			return "Desole. On recommence ?";
		}
		if((etape=="recommencer")&&(motTrouves.contains("oui")))
		{
			etape="discussion";
			return "Que souhaitez-vous : une recherche de film par critere ou un film aleatoire ?";
		}
		if((etape=="recommencer")&&(motTrouves.contains("non")))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
		if(motTrouves.contains("au revoir"))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
				
		return "Je n'ai pas compris, pouvez-vous reformuler ?";
	}
	
	/**
	 * Stocke les criteres de l'utilisateur
	 * @param message String
	 * @param motTrouves ArrayList(String)
	 * @return String : La reponse de l'IA
	 */
	public String critereUtilisateur(String message, ArrayList<String> motTrouves)
	{
		if(motTrouves.contains("realisateur"))
		{
			b_realisateur=true;
			// Recuperer le nom du realisateur et stocker dans s_realisateur
			// Si le nom n'est pas dans le message reposer la question
		}
		if(motTrouves.contains("acteur"))
		{
			b_acteur=true;
			// Recuperer le nom de l'acteur et stocker dans s_acteur
			// Si le nom n'est pas dans le message reposer la question
		}
		if(motTrouves.contains("sortie"))
		{
			b_sortie=true;
			String date = recherche.trouverDate(message);
			if(date != ""){
				s_sortie = date;
			}else{
				b_sortie=false;
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
			
			
		}
		if(motTrouves.contains("genre"))
		{
			b_genre=true;
			String genre = recherche.trouverGenre(message, dicoGenre);
			if(genre != ""){
				s_genre = genre;
			}else{
				b_genre=false;
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		
		String R="";
		String A="";
		String S="";
		String G="";
		
		if(b_realisateur==false)
		{
			R=" realisateur ?";
		}
		if(b_acteur==false)
		{
			A=" acteur ?";
		}
		if(b_sortie==false)
		{
			S=" date de sortie ?";
		}
		if(b_genre==false)
		{
			G=" genre ?";
		}
		
		return "Un autre critere:"+R+A+S+G+", ou avez-vous termine ?";
	}


	/**
	 * Retourne le nomUtilisateur
	 * @return String : Le nomUtilisateur
	 */
	public static String getNomUtilisateur() {
		return nomUtilisateur;
	}


	/**
	 * Change le nomUtilisateur
	 * @param nomUtilisateur String
	 */
	public static void setNomUtilisateur(String nomUtilisateur) {
		ConversationIA.nomUtilisateur = nomUtilisateur;
	}


	/**
	 * Retourne le b_realisateur
	 * @return boolean : Le b_realisateur
	 */
	public static boolean isB_realisateur() {
		return b_realisateur;
	}


	/**
	 * Change le b_realisateur
	 * @param b_realisateur boolean
	 */
	public static void setB_realisateur(boolean b_realisateur) {
		ConversationIA.b_realisateur = b_realisateur;
	}


	/**
	 * Retourne le b_acteur
	 * @return boolean : Le b_acteur
	 */
	public static boolean isB_acteur() {
		return b_acteur;
	}


	/**
	 * Change le b_acteur
	 * @param b_acteur boolean
	 */
	public static void setB_acteur(boolean b_acteur) {
		ConversationIA.b_acteur = b_acteur;
	}


	/**
	 * Retourne le b_sortie
	 * @return boolean : Le b_sortie
	 */
	public static boolean isB_sortie() {
		return b_sortie;
	}


	/**
	 * Change le b_sortie
	 * @param b_sortie boolean
	 */
	public static void setB_sortie(boolean b_sortie) {
		ConversationIA.b_sortie = b_sortie;
	}


	/**
	 * Retourne le b_genre
	 * @return boolean : Le b_genre
	 */
	public static boolean isB_genre() {
		return b_genre;
	}


	/**
	 * Change le b_genre
	 * @param b_genre boolean
	 */
	public static void setB_genre(boolean b_genre) {
		ConversationIA.b_genre = b_genre;
	}


	/**
	 * Retourne le s_realisateur
	 * @return String : Le s_realisateur
	 */
	public static String getS_realisateur() {
		return s_realisateur;
	}


	/**
	 * Change le s_realisateur
	 * @param s_realisateur String
	 */
	public static void setS_realisateur(String s_realisateur) {
		ConversationIA.s_realisateur = s_realisateur;
	}


	/**
	 * Retourne le s_acteur
	 * @return String : Le s_acteur
	 */
	public static String getS_acteur() {
		return s_acteur;
	}


	/**
	 * Change le s_acteur
	 * @param s_acteur String
	 */
	public static void setS_acteur(String s_acteur) {
		ConversationIA.s_acteur = s_acteur;
	}


	/**
	 * Retourne le s_sortie
	 * @return String : Le s_sortie
	 */
	public static String getS_sortie() {
		return s_sortie;
	}


	/**
	 * Change le s_sortie
	 * @param s_sortie String
	 */
	public static void setS_sortie(String s_sortie) {
		ConversationIA.s_sortie = s_sortie;
	}


	/**
	 * Retourne le s_genre
	 * @return String : Le s_genre
	 */
	public static String getS_genre() {
		return s_genre;
	}


	/**
	 * Change le s_genre
	 * @param s_genre String
	 */
	public static void setS_genre(String s_genre) {
		ConversationIA.s_genre = s_genre;
	}

}
