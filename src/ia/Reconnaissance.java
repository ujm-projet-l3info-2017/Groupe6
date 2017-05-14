package ia;

import allocine.Film;
import allocine.RechercheAllocine;

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
	 * @return
	 */
	public static Film avis(String phrase)
	{
		//Article DEFINI => L'utilisateur parle d'un film qu'il connait et veut des avis / infos
		if (phrase.contains("le film") | phrase.contains("du film"))
		{
			//On va chercher si on trouve un film après
			String film = phrase.split("film")[1];
			return RechercheAllocine.film(film);
		}
		else return null;
	}
}
