package allocine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Artwork;
import com.moviejukebox.allocine.model.CodeName;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.MovieInfos;


public class Film 
{
	private Movie film;
	private MovieInfos infos;
	
	public Film(Movie f) throws AllocineException
	{
		this.film = f;
		String code = String.valueOf(film.getCode());
		infos = new RechercheAllocine("").a.getMovieInfos(code);
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
			s ="Je n'ai pas trouv� l'affiche de ce film";
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
    	{}
    	return x;
    }
    
    
    public List<String> genres()
    {
    	List<String> liste = new ArrayList<>();
    	Iterator<String> i = infos.getGenres().iterator();
    	while (i.hasNext())
    	{
    		liste.add(i.next().toLowerCase());
    	}
    	return liste;
    }
    
    
    public String synopsis()
    {
    	if (infos.getSynopsis() == null)
    		return "Je ne connais pas suffisamment ce film pour te faire un r�sum�.";
    	return infos.getSynopsis();
    }
    
    public int duree()
    {
    	return infos.getRuntime();
    }
    
}
