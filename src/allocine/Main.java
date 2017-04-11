package allocine;

import java.util.List;
import java.util.Map;

import com.moviejukebox.allocine.model.Movie;
import com.omertron.imdbapi.ImdbApi;
import com.omertron.imdbapi.ImdbException;
import com.omertron.imdbapi.search.SearchObject;

public class Main 
{

    public static void main(String[] args) 
	{
	    RechercheAllocine recherche= new RechercheAllocine("Titanic");

	    System.out.println();
	    System.out.println();
	    System.out.println();
	    
		for (int i=0; i<recherche.liste_films().size();i++)
		{
			Movie f = recherche.liste_films().get(i);
			
			System.out.println(RechercheAllocine.titre(f));
			System.out.println(RechercheAllocine.realisateur(f));
			System.out.println(RechercheAllocine.dateSortie(f));
			System.out.println(RechercheAllocine.affiche(f));
			try 
			{
				for (int j=0; j<RechercheAllocine.acteurs_principaux(f).size(); j++)
				{
					System.out.println(RechercheAllocine.acteurs_principaux(f).get(j));
				}
			}
			catch (Exception e)
			{
				System.out.println("Pas d'acteurs connus");
			}

			System.out.println();
			
		}

		
	}
    

}
