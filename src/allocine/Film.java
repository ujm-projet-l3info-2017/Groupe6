package allocine;

import java.util.List;

import com.moviejukebox.allocine.model.Artwork;
import com.moviejukebox.allocine.model.Movie;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;


public class Film 
{
	private Movie film;
	
	public Film(Movie f)
	{
		this.film = f;
	}
	
    public String titre()
    {
		if (film.getTitle()==null)
			return film.getOriginalTitle();
		else return film.getTitle();
    }
    
    public String realisateur()
    {
    	return film.getCastingShort().getDirectors().get(0);
    }

    public int dateSortie()
    {
    	return film.getProductionYear();
    }
    
    public String affiche()
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
    
    public List<String> acteursPrincipaux()
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


    public String motsClefs()
    {
    	return film.getKeywords();
    }
    
    public int code()
    {
    	return film.getCode();
    }
	

}
