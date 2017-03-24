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

public class Main 
{

    public static void main(String[] args) 
	{
	    RechercheAllocine recherche= new RechercheAllocine("titanic");
		
		for (int i=0; i<recherche.liste_films.size();i++)
		{
			System.out.println(recherche.liste_films.get(i).getOriginalTitle());
			System.out.println(recherche.liste_films.get(i).getProductionYear());
			System.out.println();
		}
		
	    
	}

}
