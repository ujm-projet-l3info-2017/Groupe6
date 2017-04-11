package facebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 	Classe permettant le chargement des paramètres contenu dans le fichier settings.ini, étant les identifiants pour se connecter à l'api facebook
 * @author chris
 *
 */
public final class Token
{
	/**
	 * 	Fonction pour charger les propriétés du fichier settings.ini
	 * @return Propriétés de settings.ini
	 */
	private static Properties chargement(String fichier) throws IOException, FileNotFoundException
	{
	      Properties p = new Properties();
	      FileInputStream input = new FileInputStream(fichier);
	      
	      try
	      {
	         p.load(input);
	         return p;
	      }
          finally
          {
	         input.close();
	      }
	}
	
	/**
	 * Utilise chargement, puis recherche une propriété et renvoi sa valeur
	 * @param propriete: Propriété à laquelle on renvoit la valeur
	 * @return La valeur de la propriété
	 */
	private static String recupererValeur(String propriete)
	{
		String valeur = "";
		try
		{
			//On charge le fichier et on recupère la valeur de la propriété TOKEN
			valeur = chargement("settings.ini").getProperty(propriete, "vide");
		    return valeur;
		}
		catch (FileNotFoundException e)
		{
			//e.printStackTrace();
			System.err.println("Fichier settings.ini non trouvé");
			System.exit(-1);
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			System.err.println("Problème d'entrée sortie (Chargement Token)");
			System.exit(-1);
		}
		return "";	//Jamais atteint
	}

	/**
	 * 	Fonction pour récupérer le token de la page facebook du fichier settings.ini
	 * @return Le token de la page
	 */
	public static String getToken()
	{
		return recupererValeur("TOKEN");
	}
	
	/**
	 * 	Fonction pour récupérer l'appid de la page facebook du fichier settings.in
	 * @return Le token de la page
	 */
	public static String getAppId()
	{
		return recupererValeur("APPID");
	}
	
	/**
	 * 	Fonction pour récupérer le secret code de la page facebook du fichier settings.in
	 * @return Le token de la page
	 */
	public static String getSecretCode()
	{
		return recupererValeur("SECRETCODE");
	}

	public static long getInverval()
	{
		if(recupererValeur("TESTINTERVAL")=="vide")
		{
//			System.out.println("Attention: Aucune valeur d'intervalle de temps n'a été spécifié pour le rafraichissement des messages");
//			System.out.println("La valeur par défaut a été appliquée: 15sec");
			return 15000; //15 sec par défaut
		}
		return Long.valueOf(recupererValeur("TESTINTERVAL"));	//Récupéré du fichier
	}
}
