package allocine;


import java.util.List;

import com.moviejukebox.allocine.AllocineException;

import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;

public class Main 
{

	public static void main(String[] args) throws FacebookException, JSONException, AllocineException 
	{
	    RechercheAllocine recherche = new RechercheAllocine("Titanic");


		for (int i=0; i<recherche.liste_films().size();i++)
		{
			Film f = new Film(recherche.liste_films().get(i));
			System.out.println(f.titre());
			System.out.println(f.realisateur());
			System.out.println(f.dateSortie());
			System.out.println(f.affiche());
			
			List<String> genres = f.genres();
			for (int j=0; j<genres.size(); j++)
			{
				System.out.println("  "+genres.get(j));
			}
			
			System.out.println(f.synopsis());
			
			System.out.println(f.duree());
			
			System.out.println();
		}
		

	}
    
    

    

}
