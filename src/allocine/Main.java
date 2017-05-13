package allocine;


import java.util.List;

import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.ShortPerson;

import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;

public class Main 
{

	/**
	 * @param args String[]
	 * @throws FacebookException
	 * @throws JSONException
	 * @throws AllocineException
	 */
	public static void main(String[] args) throws FacebookException, JSONException, AllocineException 
	{
//	    RechercheAllocine recherche = new RechercheAllocine("Titanic");
//
//
//		for (int i=0; i<2;i++)
//		{
//			Film f = new Film(recherche.liste_films().get(i));
//			System.out.println(f.titre());
//			System.out.println(f.realisateur());
//			System.out.println(f.dateSortie());
//			System.out.println(f.affiche());
//			
//			List<String> genres = f.genres();
//			for (int j=0; j<genres.size(); j++)
//			{
//				System.out.println("  "+genres.get(j));
//			}
//			
//			System.out.println(f.synopsis());
//			
//			System.out.println(f.duree());
//			
//			System.out.println();
//			
//		}
		
//		Personne p = new Personne(recherche.liste_personne().get(0));
//		String film = p.film();
//		RechercheAllocine r = new RechercheAllocine(film);
//		Film f = new Film(r.liste_films().get(0));
//		f.affiche_infos();
		
		List<String> films = ParseurAllocine.chercherFilmDePersonne("Jean Dujardin");
		for (int i=0; i<films.size();i++)
		{
			Film f = RechercheAllocine.film(films.get(i));
			f.affiche_infos();
			System.out.println();
		}
		
		
	} 

}
