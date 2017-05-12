package ia;

public class ConversationIA
{
	/**
	 * Contient le nom de l'utilisateur, les criteres (booleen et String) et l'avancement de la discussion
	 */
	static String nomUtilisateur;
	static boolean b_realisateur, b_acteur, b_sortie, b_genre;
	static String s_realisateur,s_acteur,s_sortie,s_genre;
	static String etape; // Quelle est la meilleure maniere de marquer l'avancement : String ou int ?
	
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
	}
	

	/**
	 * Lis et renvoie un message
	 * @param message String
	 * @return String : La reponse
	 */
	public String traitementMessage(String message)
	{
		if(etape=="debut")
		{
			return "Bonjour. Que souhaitez-vous : une recherche de film par critere ou un film aleatoire ?";
		}
		// Il faut lire le message et chercher un mot clé (critere, aleatoire, oui, non, au revoir, realisateur, acteur, date de sortie, genre, termine)
		
		return "Je n'ai pas compris, pouvez-vous reformuler ?";
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


	/**
	 * Retourne l'etape actuelle
	 * @return String : L'etape
	 */
	public static String getEtape() {
		return etape;
	}


	/**
	 * Change l'etape actuelle
	 * @param etape String
	 */
	public static void setEtape(String etape) {
		ConversationIA.etape = etape;
	}

}
