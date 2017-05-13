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
		expressions = new ArrayList<>();
		expressions.add("Bonjour");
		//expressions.add("Bonsoir"); //Est ce qu'on sait l'heure
		expressions.add("Salut");
		expressions.add("Hey");
		expressions.add("Content de te parler");
	}
	
	/* (non-Javadoc)
	 * @see ia.Vocabulaire#getLength()
	 */
	public int getLength(){
		return expressions.size();
	}

	/* (non-Javadoc)
	 * @see ia.Vocabulaire#aleatoire()
	 */
	public String aleatoire() {

		Random rand = new Random();
		int nbAleatoire = rand.nextInt(expressions.size()+1);
		return expressions.get(nbAleatoire);
	}
	
	public String alea()
	{
		return aleatoire();
	}

}
