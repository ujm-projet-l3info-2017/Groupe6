package facebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 	Classe permettant le chargement des param�tres contenu dans le fichier settings.ini, �tant les identifiants pour se connecter � l'api facebook
 * @author chris
 *
 */
public final class Token 
{
	/**
	 * 	Fonction pour charger les propri�t�s du fichier settings.ini
	 * @return Propri�t�s de settings.ini
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
	 * Utilise chargement, puis recherche une propri�t� et renvoi sa valeur
	 * @param propriete: Propri�t� � laquelle on renvoit la valeur
	 * @return La valeur de la propri�t�
	 */
	private static String recupererValeur(String propriete)
	{
		String valeur = "";
		try //C:\\Users\\chris\\Desktop\\RazBot\\razbot\\settings.ini
		{
			//On charge le fichier et on recup�re la valeur de la propri�t� TOKEN
			valeur = chargement(trouverLocalisationSettings()).getProperty(propriete, "vide"); ///usr/share/tomcat7/conf/settings.ini
		    return valeur;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("Fichier settings.ini non trouv�");
			System.exit(-1);
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			System.err.println("Probl�me d'entr�e sortie (Chargement Token)");
			System.exit(-1);
		}
		return "";	//Jamais atteint
	}

	private static String trouverLocalisationSettings()
	{
		if(System.getProperty("os.name").toLowerCase().indexOf("nux")>=0)	//Linux
			return "/usr/share/tomcat7/conf/settings.ini";
		else
			return "C:\\Users\\chris\\Desktop\\RazBot\\razbot\\settings.ini";
	}

	/**
	 * 	Fonction pour r�cup�rer le token de la page facebook du fichier settings.ini
	 * @return Le token de la page
	 */
	public static String getToken()
	{
		return recupererValeur("TOKEN");
	}
	
	/**
	 * 	Fonction pour r�cup�rer l'appid de la page facebook du fichier settings.in
	 * @return Le token de la page
	 */
	public static String getAppId()
	{
		return recupererValeur("APPID");
	}
	
	/**
	 * 	Fonction pour r�cup�rer le secret code de la page facebook du fichier settings.in
	 * @return Le token de la page
	 */
	public static String getSecretCode()
	{
		return recupererValeur("SECRETCODE");
	}
	
	/**
	 * 	Fonction pour d�finir quelle m�thode de reception de message on utilise du fichier settings.in
	 * @return Vrai si le webhook est activ�, faux sinon
	 */
	public static boolean isWebhookEnable()
	{
		return recupererValeur("WEBHOOKENABLE").compareTo("true")==0?true:false;
	}

	public static long getInverval()
	{
		if(recupererValeur("TESTINTERVAL")==null)
		{
//			System.out.println("Attention: Aucune valeur d'intervalle de temps n'a �t� sp�cifi� pour le rafraichissement des messages");
//			System.out.println("La valeur par d�faut a �t� appliqu�e: 15sec");
			return 15000; //15 sec par d�faut
		}
		return Long.valueOf(recupererValeur("TESTINTERVAL"));	//R�cup�r� du fichier
	}
}
