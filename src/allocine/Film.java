package allocine;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.moviejukebox.allocine.model.Artwork;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.MovieInfos;


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
	
    public double note_public()
    {
    	double x = 0;
    	try 
    	{
    		x = film.getStatistics().getDoubleStatistic("userRating");
    	}
    	catch (Exception e)
    	{
    		
    	}
    	return x;
    }
    
    public double note_presse()
    {
    	double x = 0;
    	try 
    	{
    		x = film.getStatistics().getDoubleStatistic("pressRating");
    	}
    	catch (Exception e)
    	{
    		
    	}
    	return x;
    }
    
    
    public String genre()
    {
    	String legenre = "genre pas trouv�";
    	MovieInfos mi = new MovieInfos();
    	Set<String> genres = mi.getGenres();
    	Iterator<String> iterateur = genres.iterator();
    	if (iterateur.hasNext())
    	{
    		legenre = iterateur.next();
    	}

    	return legenre;	
    }

}
