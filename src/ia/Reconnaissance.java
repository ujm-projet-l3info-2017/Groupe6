package ia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import allocine.Film;
import allocine.ParseurAllocine;
import allocine.RechercheAllocine;


/**
 * Classe qui contient les méthodes statiques de reconnaissance de mot clés dans une phrase
 *
 */
public class Reconnaissance
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(Reconnaissance.class.getName());
	
	/**
	 * Renvoie true si c'est une question, false sinon
	 * @param phrase
	 * @return
	 */
	public static boolean question(String phrase)
	{
		//Si la phrase contient un ? alors c'est une question
		if (phrase.contains("?"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie true si l'user semble rechercher un film, false sinon
	 * @param phrase
	 * @return
	 */
	public static boolean recherche(String phrase)
	{
		//Le mot film précédé d'un article INDEFINI signifie qu'il cherche un film qu'il ne connait pas
		if (phrase.contains("un film") || phrase.contains("des film") || phrase.contains("cherche") || (phrase.contains("veu") && phrase.contains("regarde")) || phrase.contains("conseil"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie le film si on a trouvé un film dans la phrase et que l'utilisateur semble chercher des infos dessus, null sinon
	 * @param phrase
	 * @return le film si on l'a trouvé, null sinon
	 */
	public static Film avisFilm(String phrase)
	{
		//Article DEFINI => L'utilisateur parle d'un film qu'il connait et veut des avis / infos
		if (phrase.contains("le film") | phrase.contains("du film"))
		{
			//On va chercher si on trouve un film après
			String film = phrase.split("film ")[1];
			return RechercheAllocine.film(film);
		}
		else if (phrase.contains("connais"))
		{
			return RechercheAllocine.film(phrase.split("connais ")[1]);
		}
		else if (phrase.contains("entendu parler de"))
		{
			return RechercheAllocine.film(phrase.split("entendu parler de ")[1]);
		}
		else if (phrase.contains("as vu"))
		{
			return RechercheAllocine.film(phrase.split("as vu ")[1]);
		}
		else return null;
	}
	
	
	/**
	 * Analyse s'il y a potentiellement quelque chose avant le nom du film, ou cherche le film qui correspond à la phrase sinon
	 * @param phrase
	 * @return le film trouvé ou null
	 */
	public static Film reconnaitreFilm(String phrase)
	{
		if (avisFilm(phrase) != null)
			return avisFilm(phrase);
		else 
			return RechercheAllocine.film(phrase);
	}
	
	/**
	 * renvoie true si le gars veut un avis, false sinon
	 * @param phrase
	 * @return boolean
	 */
	public static boolean avis(String phrase)
	{
		//Article DEFINI => L'utilisateur parle d'un film qu'il connait et veut des avis / infos
		if (phrase.contains("avis"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie true si la phrase contient un message de salutation
	 * @param phrase
	 * @return boolean : true ou false
	 */
	public static boolean salutation(String phrase)
	{
		if (phrase.contains("bonjour") || phrase.contains("bonsoir") || phrase.contains("salut") || phrase.contains("hey") || phrase.contains("coucou") || phrase.contains("wesh") || phrase.contains("hi") || phrase.contains("hello"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur veut mettre fin à la conversation
	 * @param phrase
	 * @return boolean true ou false
	 */
	public static boolean sortie(String phrase)
	{
		if (phrase.contains("au revoir") || phrase.contains("ciao") || phrase.contains("a bientot") || phrase.contains("salut"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie un entier qui correspond à ce qu'on a compris
	 * @param phrase
	 * return int 1 : la réponse est oui, 0 la réponse est non, 2 on n'a pas compris
	 */
	public static int ouiOuNon(String phrase)
	{
		if (phrase.contains("oui") || phrase.contains("evidemment") || phrase.contains("ouais") || phrase.contains("yes") || phrase.contains("bien sur"))
			return 1;
		if (phrase.contains("non") || phrase.contains("pas du tout") || phrase.contains("est egal") || phrase.contains("peu importe") || phrase.contains("pas particulierement") || phrase.contains("pas trop") || phrase.contains("pas vraiment") || phrase.contains("absolument pas") || phrase.contains("pas du tout"))
			return 0;
		return 2;
	}
	
	/**
	 *  Charge le fichier de dictionnaire des genres, en essayant plusieurs répertoires
	 * @return
	 */
	public static FileReader chargerGenre()
	{
		try
		{
			FileReader f = new FileReader("./src/ia/dicoGenre.txt");
			if(f != null)
			{
				return f;
			}
		}
		catch(FileNotFoundException e)
		{
			try
			{
				//Pour le serveur
				return new FileReader("/var/lib/tomcat8/webapps/razbot/WEB-INF/classes/ia/dicoGenre.txt");
			}
			catch (FileNotFoundException e1)
			{
				logger.error("Impossible de trouver le dictionnaire des genres");
				logger.catching(e1);
			}
		}
		return null;
	}
	
	public static String genre(String phrase)
	{
		try
		{
			FileReader f = chargerGenre();
			BufferedReader br = new BufferedReader(f);
			String line;
			while ((line = br.readLine()) != null)
			{
				if (phrase.contains(line))
				{
					br.close();
					f.close();
					return line; //On a trouvé un genre dans la phrase
				}
			}
			br.close();
			f.close();
		}
		catch(IOException e)
		{
			logger.catching(e);
		}
		return null;
	}
	
	public static String acteurRealisateur(String phrase)
	{
		String[] lesMots = phrase.split(" ");
		int nbMots = lesMots.length;
		for (int i=0; i<nbMots; i++)
		{
			if (lesMots[i].charAt(0)>='A' && lesMots[i].charAt(0)<='Z')
			{
				//Le mot commence par une majuscule
				if (i < (nbMots-1)) //Il y a encore un mot après
				{
					if (lesMots[i+1].charAt(0)>='A' && lesMots[i+1].charAt(0)<='Z')
					{
						String acteur = ParseurAllocine.chercherPersonne(lesMots[i]+" "+lesMots[i+1]);
						if (acteur != null)
						{
							return lesMots[i]+" "+lesMots[i+1];
						}
					}
					else 
					{
						String acteur = ParseurAllocine.chercherPersonne(lesMots[i]);
						if (acteur != null)
						{
							return lesMots[i];
						}
					}
				}
				else
				{
					String acteur = ParseurAllocine.chercherPersonne(lesMots[i]);
					if (acteur != null)
					{
						return lesMots[i];
					}
				}
			}
		}
		return null;
			
	}
	
	
	/**
	 * Renvoie true si l'utilisateur demande le réalisateur
	 * @param phrase
	 * @return
	 */
	public static boolean realisateur(String phrase)
	{
		if (phrase.contains("realis"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande le synopsis
	 * @param phrase
	 * @return
	 */
	public static boolean synopsis(String phrase)
	{
		if (phrase.contains("resume") || phrase.contains("synops"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande les acteurs
	 * @param phrase
	 * @return
	 */
	public static boolean acteurs(String phrase)
	{
		if (phrase.contains("acteur") || phrase.contains("actrice") || phrase.contains("joue"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande l'affiche
	 * @param phrase
	 * @return
	 */
	public static boolean affiche(String phrase)
	{
		if (phrase.contains("affiche"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande le genre du film
	 * @param phrase
	 * @return
	 */
	public static boolean leGenre(String phrase)
	{
		if (phrase.contains("genre") || phrase.contains("type"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande l'année du film
	 * @param phrase
	 * @return
	 */
	public static boolean annee(String phrase)
	{
		if (phrase.contains("annee") || phrase.contains("date"))
			return true;
		else return false;
	}
	
	/**
	 * Renvoie true si l'utilisateur demande si le film est bien
	 * @param phrase
	 * @return
	 */
	public static boolean bien(String phrase)
	{
		
		if (question(phrase) && (phrase.contains("bien") || phrase.contains("aimé")))
			return true;
		else return false;
	}
}
