package ia;

import allocine.Film;
import allocine.RechercheAllocine;


/**
 * Classe qui contient les méthodes statiques de reconnaissance de mot clés dans une phrase
 *
 */
public class Reconnaissance
{
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
		if (phrase.contains("avis") | phrase.contains("conseil"))
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
		if (phrase.contains(" oui ") || phrase.contains("evidemment") || phrase.contains(" ouais ") || phrase.contains(" yes ") || phrase.contains(" bien sur "))
			return 1;
		if (phrase.contains(" non ") || phrase.contains(" pas du tout ") || phrase.contains(" pas particulierement ") || phrase.contains(" pas trop ") || phrase.contains(" pas vraiment ") || phrase.contains(" absolument pas ") || phrase.contains(" pas du tout "))
			return 0;
		return 2;
	}
	
	
	
	
}
