package ia;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe du vocabulaire des expressions de recommandation
 * @author Thomas GRANJON
 *
 */
public class Recommandation implements Vocabulaire
{
	//Initialisation du log
		static final Logger logger = LogManager.getLogger(Recommandation.class.getName());
	
	/**
	 * Liste des expressions de recommandation
	 */
	static ArrayList<String> expressions;
	
	/**
	 * Constructeur de la liste des expressions de recommandation
	 */
	public Recommandation(){
		expressions = new ArrayList<>();
		expressions.add("Je vous recommande");
		expressions.add("Connaissez-vous");
		expressions.add("Vous devriez voir");
		expressions.add("Vous devriez regarder");
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
		int nbAleatoire = rand.nextInt(expressions.size());
		return expressions.get(nbAleatoire);
	}

}