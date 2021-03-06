package ia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import allocine.Film;
import allocine.ParseurAllocine;
import allocine.RechercheAllocine;


/**
 * Classe qui contient les m�thodes statiques de reconnaissance de mot cl�s dans une phrase
 *
 */
public class Reconnaissance
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(Reconnaissance.class.getName());
	
	/**
	 * Renvoie true si c'est une question, false sinon
	 * @param phrase Phrase � analyser
	 * @return si c'est une question ou pas
	 */
	public static boolean question(String phrase)
	{
		//Si la phrase contient un ? alors c'est une question
		return (phrase.contains("?"));
	}
	
	/**
	 * Renvoie true si l'user semble rechercher un film, false sinon
	 * @param phrase Phrase � analyser
	 * @return si c'est une recherche ou pas
	 */
	public static boolean recherche(String phrase)
	{
		//Le mot film pr�c�d� d'un article INDEFINI signifie qu'il cherche un film qu'il ne connait pas
		return (phrase.contains("un film") || phrase.contains("des film") || phrase.contains("cherche") || (phrase.contains("veu") && phrase.contains("regarde")) || phrase.contains("conseil") || phrase.contains("propose"));
	}
	
	/**
	 * Renvoie le film si on a trouv� un film dans la phrase et que l'utilisateur semble chercher des infos dessus, null sinon
	 * @param phrase Phrase � analyser
	 * @return le film si on l'a trouv�, null sinon
	 */
	public static Film avisFilm(String phrase)
	{
		//Article DEFINI => L'utilisateur parle d'un film qu'il connait et veut des avis / infos
		if (phrase.contains("le film") | phrase.contains("du film"))
		{
			//On va chercher si on trouve un film apr�s
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
	 * Analyse s'il y a potentiellement quelque chose avant le nom du film, ou cherche le film qui correspond � la phrase sinon
	 * @param phrase Phrase � analyser
	 * @return le film trouv� ou null
	 */
	public static Film reconnaitreFilm(String phrase)
	{
		if (avisFilm(phrase) != null)
			return avisFilm(phrase);
		else 
			return RechercheAllocine.film(phrase);
	}
	
	/**
	 * renvoie true si l'utilisateur veut un avis, false sinon
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur veut un avis, false sinon
	 */
	public static boolean avis(String phrase)
	{
		//Article DEFINI => L'utilisateur parle d'un film qu'il connait et veut des avis / infos
		return (phrase.contains("avis"));
	}
	
	/**
	 * Renvoie true si la phrase contient un message de salutation
	 * @param phrase Phrase � analyser
	 * @return boolean : true ou false
	 */
	public static boolean salutation(String phrase)
	{
		return (phrase.contains("bonjour") || phrase.contains("bonsoir") || phrase.contains("salut") || phrase.contains("hey") || phrase.contains("coucou") || phrase.contains("wesh") || phrase.contains("hi") || phrase.contains("hello"));
	}
	
	/**
	 * Renvoie true si l'utilisateur veut mettre fin � la conversation
	 * @param phrase Phrase � analyser
	 * @return boolean true ou false
	 */
	public static boolean sortie(String phrase)
	{
		return (phrase.contains("au revoir") || phrase.contains("ciao") || phrase.contains("a bientot"));
	}
	
	/**
	 * Renvoie un entier qui correspond � ce qu'on a compris
	 * @param phrase  Phrase � analyser
	 * return int 1 : la r�ponse est oui, 0 la r�ponse est non, 2 on n'a pas compris
	 */
	public static int ouiOuNon(String phrase)
	{
		if (phrase.contains("oui") || phrase.contains("evidemment") || phrase.contains("ouais") || phrase.contains("yes") || phrase.contains("bien sur"))
			return 1;
		if (phrase.contains("non") || phrase.contains("pas du tout") || phrase.contains("est egal") || phrase.contains("peu importe") || phrase.contains("pas particulierement") || phrase.contains("pas specialement") || phrase.contains("pas trop") || phrase.contains("pas vraiment") || phrase.contains("absolument pas") || phrase.contains("pas du tout"))
			return 0;
		return 2;
	}
	
	/**
	 *  Charge le fichier de dictionnaire des genres, en essayant plusieurs r�pertoires
	 * @return l'objet FileReader
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
	
	/**
	 *  Rnvoi le genre ou null
	 * @param phrase Phrase � analyser
	 * @return String ou null
	 */
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
					return line; //On a trouv� un genre dans la phrase
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
				if (i < (nbMots-1)) //Il y a encore un mot apr�s
				{
					if (lesMots[i+1].charAt(0)>='A' && lesMots[i+1].charAt(0)<='Z')
					{
						List<String> films = ParseurAllocine.chercherFilmDePersonne(lesMots[i]+" "+lesMots[i+1]);
						if (films!=null)
						{
							return lesMots[i]+" "+lesMots[i+1];
						}
					}
					else 
					{
						List<String> films = ParseurAllocine.chercherFilmDePersonne(lesMots[i]);
						if (films!=null)
						{
							return lesMots[i];
						}
					}
				}
				else
				{
					List<String> films = ParseurAllocine.chercherFilmDePersonne(lesMots[i]);
					if (films!=null)
					{
						return lesMots[i];
					}
				}
			}
		}
		return null;
			
	}
	
	
	/**
	 * Renvoie true si l'utilisateur demande le r�alisateur
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande le r�alisateur
	 */
	public static boolean realisateur(String phrase)
	{
		return (phrase.contains("realis"));
	}
	
	/**
	 * Renvoie true si l'utilisateur demande le synopsis
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande le synopsis
	 */
	public static boolean synopsis(String phrase)
	{
		return (phrase.contains("resume") || phrase.contains("synops") || phrase.contains("histoire"));
	}
	
	/**
	 * Renvoie true si l'utilisateur demande les acteurs
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande les acteurs
	 */
	public static boolean acteurs(String phrase)
	{
		return (phrase.contains("acteur") || phrase.contains("actrice") || phrase.contains("joue"));

	}
	
	/**
	 * Renvoie true si l'utilisateur demande l'affiche
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande l'affiche
	 */
	public static boolean affiche(String phrase)
	{
		return (phrase.contains("affiche"));

	}
	
	/**
	 * Renvoie true si l'utilisateur demande le genre du film
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande le genre du film
	 */
	public static boolean leGenre(String phrase)
	{
		return (phrase.contains("genre") || phrase.contains("type"));
	}
	
	/**
	 * Renvoie true si l'utilisateur demande l'ann�e du film
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande l'ann�e du film
	 */
	public static boolean annee(String phrase)
	{
		return (phrase.contains("annee") || phrase.contains("date"));
	}
	
	/**
	 * Renvoie true si l'utilisateur demande si le film est bien
	 * @param phrase Phrase � analyser
	 * @return true si l'utilisateur demande si le film est bien
	 */
	public static boolean avisPerso(String phrase)
	{
		if(!question(phrase) && phrase.contains("est bien"))
			return true;
		return ((question(phrase) && (phrase.contains("bien") || phrase.contains("aime"))) || phrase.contains("avis") || phrase.contains("impression") || phrase.contains("sentiment"));
	}
	
	/**
	 *  Renvoie true si l'utilisateur dit merci
	 * @param phrase  Phrase � analyser
	 * @return true si l'utilisateur dit merci
	 */
	public static boolean merci(String phrase)
	{
		return phrase.contains("merci");
	}
	
	/**
	 *  Renvoie true si l'utilisateur dit ok
	 * @param phrase  Phrase � analyser
	 * @return true si l'utilisateur dit ok
	 */
	public static boolean ok(String phrase)
	{
		return (phrase.contains("ok") || phrase.contains("d accord") || phrase.contains("cool"));
	}
}
