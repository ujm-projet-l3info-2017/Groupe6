package allocine;

import com.moviejukebox.allocine.AllocineException;

public class FonctionsTest
{
	/**
	 *  M�thode de d�mo des fonctionnalit�s de l'api allocin�
<<<<<<< HEAD
	 * @param Le nom d'un film, virgule, le type de ressource demand�
	 * @return La ressource demand�
	 * @throws AllocineException 
=======
	 * @param Le nom d'un film, virgule, le type de ressource demand� String
	 * @return String La ressource demand�
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

