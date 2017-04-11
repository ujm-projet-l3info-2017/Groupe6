package allocine;

import java.util.List;
import org.apache.http.impl.client.HttpClients;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Artwork;
import com.moviejukebox.allocine.model.CodeName;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.Search;



public class RechercheAllocine 
{
    private static final String PARTNER_KEY = "100043982026";
    private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";
    Search recherche;
    
    public RechercheAllocine(String film)
    {
        HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());
        

		try 
		{
			AllocineApi a = new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
			recherche = a.searchMovies(film);
		} 
		catch (AllocineException e) 
		{
			e.printStackTrace();
		}
    }
    
    public List<Movie> liste_films()
    {
    	return recherche.getMovies();
    }
    
    public static String titre(Movie film)
    {
		if (film.getTitle()==null)
			return film.getOriginalTitle();
		else return film.getTitle();
    }
    
    public static String realisateur(Movie film)
    {
    	return film.getCastingShort().getDirectors().get(0);
    }

    public static int dateSortie(Movie film)
    {
    	return film.getProductionYear();
    }
    
    public static String affiche(Movie film)
    {
    	Artwork x = film.getPoster();
    	String s;
		try 
		{
			s=x.getHref();
		}
		catch (Exception e)
		{
			s ="Il n'y a pas d'affiche pour ce film";
		}
		return s;
    }
    
    public static List<String> acteurs_principaux(Movie film)
    {
    	try 
    	{
    		return film.getCastingShort().getActors();
    	}
    	catch (Exception e)
    	{
    		System.out.println("Pas d'acteurs pour ce film");
    	}
		return null;
    }

    
}
