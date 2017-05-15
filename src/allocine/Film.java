package allocine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.Artwork;
import com.moviejukebox.allocine.model.CodeName;
import com.moviejukebox.allocine.model.Movie;
import com.moviejukebox.allocine.model.MovieInfos;


public class Film 
{
	private Movie film;
	private MovieInfos infos;
	
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(ParseurAllocine.class.getName());
	
	/**
	 * @param f Movie
	 * @throws AllocineException
	 */
	public Film(Movie f)
	{
		this.film = f;
		String code = String.valueOf(film.getCode());
		try
		{
			infos = new RechercheAllocine("").a.getMovieInfos(code);
		}
		catch (AllocineException e)
		{
			logger.error( "Les informations du film n'ont pas été trouvées");
		}
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
		try 
		{
			return x.getHref();
		}
		catch (Exception e)
		{
			logger.error("Affiche du film non trouvé");
		}
		return null;
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
    		logger.error("Impossible de trouvé les acteurs de ce film");
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
    	try 
    	{
    		return film.getStatistics().getDoubleStatistic("userRating");
    	}
    	catch (Exception e)
    	{
    		logger.error("Note de la presse non disponible");
    	}
    	return -1;
    }
    
    /**
     * @return double
     */
    public double note_presse()
    {
    	try 
    	{
    		return film.getStatistics().getDoubleStatistic("pressRating");
    	}
    	catch (Exception e)
    	{
    		logger.error("Note de la presse non disponible");
    	}
    	return -1;
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
    
    public void affiche_infos()
    {
    	System.out.println(titre());
		System.out.println(realisateur());
		System.out.println(dateSortie());
		System.out.println(affiche());
		
		List<String> genres = genres();
		for (int j=0; j<genres.size(); j++)
		{
			System.out.println("  "+genres.get(j));
		}
		
		System.out.println(synopsis());
		
		System.out.println(duree());
		
		System.out.println();
    }
    
}
