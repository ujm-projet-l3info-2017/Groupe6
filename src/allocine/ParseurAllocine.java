package allocine;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseurAllocine 
{	
	/**
	 * @return List<String>
	 */
	public static List<String> recupererFilms()
	{
		String url = "http://www.allocine.fr/films/notes/";
		return chercherFilms(url);
	}
	
	/**
	 * @param genre String
	 * @return List<String>
	 */
	public static List<String> recupererFilms(String genre)
	{
		String url = "http://www.allocine.fr/films/notes/";
		url += ajouterGenre(genre);	
		return chercherFilms(url);
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
		return chercherFilms(url);
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
		return chercherFilms(url);
	}
	
	/**
	 * @param url String
	 * @return List<String>
	 */
	private static List<String> chercherFilms(String url)
	{
		List<String> liste = new ArrayList<>();
		try 
		{
			Document doc = Jsoup.connect(url).get();
			
			Elements film = doc.select("a");
			Elements films = film.select(".meta-title-link");
			for (int i=0; i<films.size();i++)
			{
				liste.add(films.get(i).text());
			}
		} catch (IOException e) 
		{
			System.out.println("La recherche avec cet URL n'a rien trouvé.");
			e.printStackTrace();
		}
		return liste;
	}
	
	/**
	 * @param url String
	 * @return List<String>
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
					return ref;
				}
			}
			System.out.println("Aucune personne trouvée avec ce nom");
			return null;
		} 
		catch (IOException e) 
		{
			System.out.println("Aucune personne trouvée avec ce nom");
			return null;
		}
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
		if (genre.contains("comique") || genre.contains("comédie"))
			param+="05";
		if (genre.contains("dram"))
			param+="08";
		if (genre.contains("érotique") || genre.contains("sex"))
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
			
		return param+"/";
	}

	
	
}
