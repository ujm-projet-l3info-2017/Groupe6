package ia;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe du vocabulaire de fin de conversation
 * @author Thomas GRANJON
 *
 */
public class FinDeConversation implements Vocabulaire{
	
	/**
	 * Liste des expressions de fin de conversation
	 */
	static ArrayList<String> expressions;
	
	/**
	 * Constructeur de la liste des expressions de fin de conversation
	 */
	public FinDeConversation(){
		expressions.add("Au revoir");
		expressions.add("Bye");
		expressions.add("A bientot");
		expressions.add("A la prochaine");
		expressions.add("J'espere te revoir");
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
		int nbAleatoire = rand.nextInt(((Vocabulaire) expressions).getLength()+1);
		return expressions.get(nbAleatoire);
	}

}