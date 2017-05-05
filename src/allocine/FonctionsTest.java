package allocine;

public class FonctionsTest
{
	/**
	 *  Méthode de démo des fonctionnalités de l'api allociné
	 * @param Le nom d'un film, virgule, le type de ressource demandé String
	 * @return String La ressource demandé
	 */
	private String testApiAllocine(String message)
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

