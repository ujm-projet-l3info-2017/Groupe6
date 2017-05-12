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
	
	/**
	 * @param f Movie
	 * @throws AllocineException
	 */
	public Film(Movie f) throws AllocineException
	{
		this.film = f;
		String code = String.valueOf(film.getCode());
		infos = new RechercheAllocine("").a.getMovieInfos(code);
	}
	
    /**
     * @return String
     */
    public String titre()
    {
		if (film.getTitle()==null)
			return film.getOriginalTitle();
		else return film.getTitle();
    }
    
    /**
     * @return String
     */
    public String realisateur()
    {
    	return film.getCastingShort().getDirectors().get(0);
    }
    
    /**
     * @return int
     */
    public int dateSortie()
    {
    	return film.getProductionYear();
    }
    
    /**
     * @return String
     */
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
			s ="Je n'ai pas trouvé l'affiche de ce film";
		}
		return s;
    }
    
    /**
     * @return List<String>
     */
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


    /**
     * @return String
     */
    public String motsClefs()
    {
    	return film.getKeywords();
    }
    
    /**
     * @return int
     */
    public int code()
    {
    	return film.getCode();
    }
	
    /**
     * @return double
     */
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
    
    /**
     * @return double
     */
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
    
    
    /**
     * @return List<String>
     */
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
    
    
    /**
     * @return String
     */
    public String synopsis()
    {
    	if (infos.getSynopsis() == null)
    		return "Je ne connais pas suffisamment ce film pour te faire un résumé.";
    	return infos.getSynopsis();
    }
    
    /**
     * @return int
     */
    public int duree()
    {
    	return infos.getRuntime();
    }
    
}
