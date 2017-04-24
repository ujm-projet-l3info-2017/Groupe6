package allocine;

import facebook.PageRazbot;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class Main 
{

    public static void main(String[] args) throws FacebookException, JSONException 
	{
	    RechercheAllocine recherche= new RechercheAllocine("Titanic");


		for (int i=0; i<recherche.liste_films().size();i++)
		{
			Film f = new Film(recherche.liste_films().get(i));
			
			System.out.println(f.titre());
			System.out.println(f.realisateur());
			System.out.println(f.dateSortie());
			System.out.println(f.affiche());
			System.out.println(f.code());

			System.out.println();
		}
		
		PageRazbot p = new PageRazbot();
		
		JSONObject a = p.requeteGET("http://api.allocine.fr/rest/v3/search?partner=100043982026&filter=movie%2Ctheater%2Cperson%2Cnews%2Ctvseries&count=5&page=1&q=avatar&format=json");

	}
    
    

    

}
