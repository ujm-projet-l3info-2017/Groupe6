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
			Film f = new Film(recherche.liste_films().get(i));
			
			System.out.println(f.titre());
			System.out.println(f.realisateur());
			System.out.println(f.dateSortie());
			System.out.println(f.affiche());
			try 
			{
				for (int j=0; j<f.acteurs_principaux().size(); j++)
				{
					System.out.println(f.acteurs_principaux().get(j));
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
