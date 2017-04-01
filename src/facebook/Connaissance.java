package facebook;

import java.util.ArrayList;
import java.util.Random;

public class Connaissance implements Vocabulaire{
	
	static ArrayList<String> expressions;
	
	public int getLength(){
		int i=0;
		while(expressions.get(i)!=null)
		{
			i++;
		}
		return i;
	}

	public String aleatoire() {

		Random rand = new Random();
		int nbAleatoire = rand.nextInt(((Vocabulaire) expressions).getLength()+1);
		return expressions.get(nbAleatoire);
	}

}