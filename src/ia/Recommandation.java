package facebook;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe du vocabulaire des expressions de recommandation
 * @author Thomas
 *
 */
public class Recommandation implements Vocabulaire{
	
	/**
	 * Liste des expressions de recommandation
	 */
	static ArrayList<String> expressions;
	
	/**
	 * Constructeur de la liste des expressions de recommandation
	 */
	public Recommandation(){
		expressions.add("Je te recommande");
		expressions.add("Connais-tu");
		expressions.add("As-tu-vu");
		expressions.add("Tu devrais voir");
		expressions.add("Tu devrais regarder");
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