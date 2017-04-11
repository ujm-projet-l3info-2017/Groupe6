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
		expressions.add("A la prochaine");
		expressions.add("J'espere te revoir");
	}
	
	/* (non-Javadoc)
	 * @see facebook.Vocabulaire#getLength()
	 */
	public int getLength(){
		int i=0;
		while(expressions.get(i)!=null)
		{
			i++;
		}
		return i;
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