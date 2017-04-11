package ia;

import java.util.ArrayList;
import java.util.Random;


/**
 * Classe du vocabulaire de salutation
 * @author Thomas GRANJON
 *
 */
public class Salutations implements Vocabulaire{
	
	/**
	 * Liste des expressions de salutation
	 */
	static ArrayList<String> expressions;
	
	/**
	 * Constructeur de la liste des expressions de salutations
	 */
	public Salutations(){
		expressions.add("Bonjour");
		expressions.add("Bonsoir");
		expressions.add("Salut");
		expressions.add("Hey");
		expressions.add("Content de te parler");
	}
	
	/* (non-Javadoc)
	 * @see facebook.Vocabulaire#getLength()
	 */
	public int getLength(){
		return expressions.size();
	}

	/* (non-Javadoc)
	 * @see facebook.Vocabulaire#aleatoire()
	 */
	public String aleatoire() {

		Random rand = new Random();
		int nbAleatoire = rand.nextInt(((Vocabulaire) expressions).getLength()+1);
		return expressions.get(nbAleatoire);
	}

}
