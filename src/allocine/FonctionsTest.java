package allocine;

import com.moviejukebox.allocine.AllocineException;

public class FonctionsTest
{
	/**
	 *  Méthode de démo des fonctionnalités de l'api allociné
<<<<<<< HEAD
	 * @param Le nom d'un film, virgule, le type de ressource demandé
	 * @return La ressource demandé
	 * @throws AllocineException 
=======
	 * @param Le nom d'un film, virgule, le type de ressource demandé String
	 * @return String La ressource demandé
>>>>>>> branch 'develop' of https://github.com/ujm-projet-l3info-2017/Groupe6
	 */
	private String testApiAllocine(String message) throws AllocineException
	{
		String infos[] = message.split(",");
		String resultat;
		
		System.out.println(infos.length);
		if(infos.length <= 1)
		{
			resultat = "Introuvable";
		}
		else
		{
			resultat = RechercheAllocine.informationFilm(infos[0],infos[1]);
		}
		
		return resultat;
	}
}

