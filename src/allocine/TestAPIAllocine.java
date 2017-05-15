package allocine;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.moviejukebox.allocine.AllocineException;

import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;

public class TestAPIAllocine 
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(TestAPIAllocine.class.getName());

	/**
	 * @param args String[]
	 * @throws FacebookException
	 * @throws JSONException
	 * @throws AllocineException
	 */
	public static void main(String[] args)
	{		
		List<String> films = ParseurAllocine.chercherFilmDePersonne("Jean Dujardin");
		for (int i=0; i<films.size();i++)
		{
			Film f = RechercheAllocine.film(films.get(i));
			if (f != null) 
				f.affiche_infos();
			System.out.println();
		}
		
		
	} 

}
