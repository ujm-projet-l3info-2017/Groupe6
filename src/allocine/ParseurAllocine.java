package allocine;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseurAllocine 
{	
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(ParseurAllocine.class.getName());
	/**
	 * @return List<String>
	 */
	public static List<String> recupererFilms()
	{
		String url = "http://www.allocine.fr/films/notes/";
		return chercherFilmsAvecURL(url);
	}
	
	/**
	 * @param genre String
	 * @return List<String>
	 */
	public static List<String> recupererFilms(String genre)
	{
		String url = "http://www.allocine.fr/films/notes/";
		url += ajouterGenre(genre);	
		return chercherFilmsAvecURL(url);
	}
	
	/**
	 * @param date boolean
	 * @return List<String>
	 */
	public static List<String> recupererFilms(boolean date)
	{
		String url = "http://www.allocine.fr/films/notes/";
		if (date)
			url += ajouterDate(date);
		return chercherFilmsAvecURL(url);
	}
	
	/**
	 * @param genre String
	 * @param date boolean
	 * @return List<String>
	 */
	public static List<String> recupererFilms(String genre, boolean date)
	{
		String url = "http://www.allocine.fr/films/notes/";
		url += ajouterGenre(genre);	
		if (date)
			url += ajouterDate(date);
		return chercherFilmsAvecURL(url);
	}
	
	/**
	 * @param url String
	 * @return List<String>
	 */
	private static List<String> chercherFilmsAvecURL(String url)
	{
		List<String> liste = new ArrayList<>();
		try 
		{
			Document doc = Jsoup.connect(url).get();
			
			Elements film = doc.select("a");
			Elements films = film.select(".meta-title-link");
			for (int i=0; i<films.size();i++)
			{
				if (films.get(i).attr("href").contains("film"))
					liste.add(films.get(i).text());
			}
			return liste;
		} 
		catch (IOException e) 
		{
			logger.error("GestionAllocine","La recherche avec cet URL n'a rien trouvé");
		}
		return null;
	}
	
	/**
	 * @param nom String
	 * @return url de la fiche de la persone
	 */
	public static String chercherPersonne(String nom)
	{
		String url = "http://www.allocine.fr/recherche/?q="+nom;
		try 
		{
			Document doc = Jsoup.connect(url).get();
			Elements link = doc.select("a");
			for (int i=0; i<link.size(); i++)
			{
				String ref = link.get(i).attr("abs:href");
				if (ref.contains("personne"))
				{
					System.out.println("Je renvoie : " + ref);
					return ref;
				}
			}
			logger.error("GestionAllocine","Aucune personne trouvée avec ce nom");
			return null;
		} 
		catch (IOException e) 
		{
			logger.error("GestionAllocine","Aucune personne trouvée avec ce nom");
			return null;
		}
	}
	
	/**
	 * Retourne une liste composé des meilleurs films d'un réalisateurs ou d'un acteurs
	 * @param nom String : le nom d'un acteur ou d'un réalisateur
	 * @return List<String> 
	 */
	public static List<String> chercherFilmDePersonne(String nom)
	{
		List<String> films = new ArrayList<>();
		String code = recupererCodePersonne(nom);
		String url = "http://www.allocine.fr/personne/fichepersonne-"+code+"/filmographie/top/";
		try
		{
			Document doc = Jsoup.connect(url).get(); //On récupère le code de la page
			Elements doc1 = doc.select(".col-left");
			Elements links = doc1.select("a"); //On récupère les balises sur lesquels il y a les films
			Elements liste_films = links.select(".meta-title-link");
			for (int i=0; i<liste_films.size();i++)
			{
				if (liste_films.get(i).attr("href").contains("film"))
					films.add(liste_films.get(i).text());
			}
			if (films.isEmpty())
				return null;
			else return films;
		} 
		catch (IOException e)
		{
			logger.error("GestionAllocine","Impossible de trouver les films de cette personne");
			return null;
		}
	}
	
	/**
	 * 
	 * @param nom String : nom de l'acteur ou du réalisateur
	 * @return String : le code de l'acteur à utiliser dans l'URL 
	 */
	private static String recupererCodePersonne(String nom)
	{
		String url = chercherPersonne(nom);
		String apresLeEgal = url.split("=")[1]; //On récupère ce qu'il y a après le égal
		String code = apresLeEgal.split("\\.")[0]; //Et ce qu'il y a avant le point
		return code;
	}
	
	/**
	 * @param date boolean
	 * @return String
	 */
	private static String ajouterDate(boolean date) 
	{
		return "decennie-2010/";
	}

	/**
	 * @param genre String 
	 * @return String
	 */
	private static String ajouterGenre(String genre) 
	{
		String param = "genre-130";

		if (genre.contains("action"))
			param+="25";
		if (genre.contains("anim"))
			param+="26";
		if (genre.contains("biopic"))
			param+="27";
		if (genre.contains("aventur"))
			param+="01";
		if (genre.contains("comique") || genre.contains("comedie"))
			param+="05";
		if (genre.contains("dram"))
			param+="08";
		if (genre.contains("erotique") || genre.contains("sex") || genre.contains("boule") || genre.contains("cul"))
			param+="10";
		if (genre.contains("histo"))
			param+="15";
		if (genre.contains("fantasti"))
			param+="12";
		if (genre.contains("polic"))
			param+="18";
		if (genre.contains("roman"))
			param+="24";
		if (genre.contains("science") || genre.contains("fiction"))
			param+="21";
		if (genre.contains("thriller"))
			param+="23";
		if (genre.contains("western"))
			param+="19";
		if (genre.contains("horreur") || genre.contains("epouvant"))
			param+="09";
		
		return param+"/";
	}

	
	
}
