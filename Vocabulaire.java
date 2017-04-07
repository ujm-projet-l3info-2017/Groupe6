package facebook;

import java.util.ArrayList;

/**
 * Interface des listes de vocabulaire
 * @author Thomas GRANJON
 *
 */
public interface Vocabulaire{
	
	/**
	 * Cette liste contiendra les expressions relatives à chaque occasion
	 */
	static ArrayList<String> expressions = new ArrayList<String>();
	

	/**
	 * Permet de connaître la taille de la liste de vocabulaire
	 * @return int : La taille de la liste
	 */
	public int getLength();
	
	/**
	 * Permet de choisir une expression aléatoire
	 * @return String : Une expression aléatoire parmi celles dans la liste expressions
	 */
	public String aleatoire();
}
