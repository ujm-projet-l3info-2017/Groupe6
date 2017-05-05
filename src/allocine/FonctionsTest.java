package allocine;

public class FonctionsTest
{
	/**
	 *  M�thode de d�mo des fonctionnalit�s de l'api allocin�
	 * @param Le nom d'un film, virgule, le type de ressource demand� String
	 * @return String La ressource demand�
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

