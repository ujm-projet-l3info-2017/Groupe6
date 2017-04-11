package facebook;

import java.util.ArrayList;

/**
 * Interface des listes de vocabulaire
 * @author Thomas GRANJON
 *
 */
public interface Vocabulaire{
	
	/**
	 * Cette liste contiendra les expressions relatives � chaque occasion
	 */
	static ArrayList<String> expressions = new ArrayList<String>();
	

	/**
	 * Permet de conna�tre la taille de la liste de vocabulaire
	 * @return int : La taille de la liste
	 */
	public int getLength();
	
	/**
	 * Permet de choisir une expression al�atoire
	 * @return String : Une expression al�atoire parmi celles dans la liste expressions
	 */
	public String aleatoire();
}
