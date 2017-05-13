package allocine;

import java.util.List;
import org.apache.http.impl.client.HttpClients;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.Search;
import com.moviejukebox.allocine.model.ShortPerson;
import com.moviejukebox.allocine.model.TvSeries;

public class RechercheAllocine 
{
    private static final String PARTNER_KEY = "100043982026";
    private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";
    Search recherche;
    AllocineApi a;
    
    /**
     * Constructeur RechercheAllocine
     * @param requete String
     */
    public RechercheAllocine(String requete)
    {
        HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());

		try 
		{
			a = new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
			recherche = a.searchMovies(requete);
		} 
		catch (AllocineException e) 
		{
			e.printStackTrace();
		}
    }
    
    /**
     * @param film String
     * @param element String
     * @return String
     * @throws AllocineException
     */
    public static String informationFilm(String film, String element)
    {
    	RechercheAllocine recherche= new RechercheAllocine(film);
    	Film f;
		f = new Film(recherche.liste_films().get(0));
		switch (element)
		{
			case "titre":
				return f.titre();
			case "realisateur":
				return f.realisateur();
			case "dateSortie":
				return String.valueOf(f.dateSortie());
			case "affiche":
				return f.affiche();
			case "acteurPrincipal":
				return f.acteursPrincipaux().get(0);
			case "note":
				return String.valueOf(f.note_public());
		}
    	
    	return "Usage : titre, realisateur, dateSortie, affiche, acteurPrincipal, note";
    	
    }
    
    /**
     * @return List<Movie>
     */
    public List<Movie> liste_films()
    {
    	return recherche.getMovies();
    }
    
    /**
     * @return List<TvSeries>
     */
    public List<TvSeries> liste_series()
    {
    	return recherche.getTvSeries();
    }
    
    
    public List<ShortPerson> liste_personne()
    {
    	return recherche.getPersons();
    }
  

    
}
