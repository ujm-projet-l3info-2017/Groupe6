package facebook;

import java.util.Date;

/**
 *  Classe contenant les (ou la) fonctions utilitaires
 */
public class Utilitaires
{
	/** Parse la date de format Facebook, d�sol� pour la deprecation
	 * @param date String
	 * @return Date
	 */
	@SuppressWarnings("deprecation")
	public static Date parserDateFacebook(String date) 
	{
		Date dateD;
		int annee = Integer.parseInt(date.substring(0, 4));
		int mois = Integer.parseInt(date.substring(5, 7));
		int jour = Integer.parseInt(date.substring(8, 10));
		int heure = Integer.parseInt(date.substring(11, 13));
		int minute = Integer.parseInt(date.substring(14, 16));
		int seconde = Integer.parseInt(date.substring(17, 19));
		
		dateD = new Date(annee,mois,jour,heure,minute,seconde);
		return dateD;
	}
}
