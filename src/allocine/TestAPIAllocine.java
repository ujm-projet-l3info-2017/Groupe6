package allocine;

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
		Film lefilm = RechercheAllocine.film("Intouchables");
			
		System.out.println(lefilm.titre());
		System.out.println(lefilm.realisateur());
		System.out.println(lefilm.affiche());
		
	
		
	} 

}
