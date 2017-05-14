package ia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
		//Le mot film pr�c�d� d'un article INDEFINI signifie qu'il cherche un film qu'il ne connait pas
		if (phrase.contains("un film") || phrase.contains("des film") || phrase.contains("cherche") || (phrase.contains("veu") && phrase.contains("regarde")) || phrase.contains("conseil"))
			return true;
		return false;
	}
	
	/**
	 * Renvoie le film si on a trouv� un film dans la phrase et que l'utilisateur semble chercher des infos dessus, null sinon
	 * @param phrase
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
		else return null;
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
	 * Renvoie true si l'utilisateur veut mettre fin � la conversation
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
	 * Renvoie un entier qui correspond � ce qu'on a compris
	 * @param phrase
	 * return int 1 : la r�ponse est oui, 0 la r�ponse est non, 2 on n'a pas compris
	 */
	public static int ouiOuNon(String phrase)
	{
		if (phrase.contains("oui") || phrase.contains("evidemment") || phrase.contains("ouais") || phrase.contains("yes") || phrase.contains("bien sur"))
			return 1;
		if (phrase.contains("non") || phrase.contains("pas du tout") || phrase.contains("est egal") || phrase.contains("peu importe") || phrase.contains("pas particulierement") || phrase.contains("pas trop") || phrase.contains("pas vraiment") || phrase.contains("absolument pas") || phrase.contains("pas du tout"))
			return 0;
		return 2;
	}
	
	public static String genre(String phrase)
	{
		try
		{
			FileReader f = new FileReader("./src/ia/dicoGenre.txt");
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
		catch (IOException e)
		{
			logger.error("chargementDico","Erreur lors de l'ouverture du dictionnaire");
			return null;
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
	
	
	
}