package allocine;

import java.util.List;
import org.apache.http.impl.client.HttpClients;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.Search;



public class RechercheAllocine 
{
    private static final String PARTNER_KEY = "100043982026";
    private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";
    List<Movie> liste_films;
    
    public RechercheAllocine(String film)
    {
        HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());
        

		try 
		{
			AllocineApi a = new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
			Search s = a.searchMovies("titanic");
			liste_films=s.getMovies();
		} 
		catch (AllocineException e) 
		{
			e.printStackTrace();
		}

    }
}
