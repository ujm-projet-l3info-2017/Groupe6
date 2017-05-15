package ia;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Classe du vocabulaire de salutation
 */
public class Salutations implements Vocabulaire
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(Salutations.class.getName());
	  
	/**
	 * Liste des expressions de salutation
	 */
	static ArrayList<String> expressions;
	
	/**
	 * Constructeur de la liste des expressions de salutations
	 */
	public Salutations()
	{
		expressions = new ArrayList<>();
		expressions.add("Bonjour");
		//expressions.add("Bonsoir"); //Est ce qu'on sait l'heure
		expressions.add("Salut");
		expressions.add("Hey");
		expressions.add("Salutations");
		expressions.add("Content de te parler");
	}
	
	/* (non-Javadoc)
	 * @see ia.Vocabulaire#getLength()
	 */
	public int getLength()
	{
		return expressions.size()-1;
	}

	/* (non-Javadoc)
	 * @see ia.Vocabulaire#aleatoire()
	 */
	public String aleatoire()
	{
		Random rand = new Random();
		int nbAleatoire = rand.nextInt(expressions.size());
		return expressions.get(nbAleatoire);
	}

}
