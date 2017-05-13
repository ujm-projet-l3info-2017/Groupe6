package ia;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe du vocabulaire de fin de conversation
 */
public class FinDeConversation implements Vocabulaire
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(FinDeConversation.class.getName());
	
	/**
	 * Liste des expressions de fin de conversation
	 */
	static ArrayList<String> expressions;

	/**
	 * Constructeur de la liste des expressions de fin de conversation
	 */
	public FinDeConversation()
	{
		expressions = new ArrayList<>();
		expressions.add("Au revoir");
		expressions.add("Bye");
		expressions.add("A bientot");
		expressions.add("A la prochaine");
		expressions.add("J'espere te revoir");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ia.Vocabulaire#getLength()
	 */
	public int getLength()
	{
		return expressions.size()-1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ia.Vocabulaire#aleatoire()
	 */
	public String aleatoire()
	{

		Random rand = new Random();
		int nbAleatoire = rand.nextInt(expressions.size() + 1);
		return expressions.get(nbAleatoire);
	}

}