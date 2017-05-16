package ia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe de la recherche d'un mot dans un dictionnaire
 * 
 * @author RÃ©mi SIRACUSA
 *
 */
public class RechercherMot
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(RechercherMot.class.getName());

	/**
	 * Liste de mot constituant les dictionnaires
	 */
	ArrayList<String> dicoFrancais;
	ArrayList<String> dicoMotsCles;

	/**
	 * Initialisation de la recherche
	 */
	public RechercherMot()
	{
		dicoFrancais = this.chargementDico("./src/ia/","dicoFrancais.txt");
		dicoMotsCles = this.chargementDico("./src/ia/","dicoMotsCles.txt");
	}
	
	/**
	 * Permet de charger le dictionnaire dans une ArrayList
	 * @return ArrayList<String>
	 * @throws IOException 
	 * 
	 */
	private ArrayList<String> chargementDico(String chemin, String nom)
	{
		ArrayList<String> dico = new ArrayList<String>();
		try
		{
			//Ouverture du fichier et initialisation dubuffer
			FileReader f = new FileReader(chemin+nom);
			BufferedReader br = new BufferedReader(f);
			String line;
			//Creation du dictionnaire au format ArrayList
			dico = new ArrayList<>();
			while ((line = br.readLine()) != null)
			{
				dico.add(line);
			}
			br.close();
		}
		catch(IOException e)
		{
			//On essaye avec un autre chemin (Solution pour le serveur)
			if(chemin.compareTo("/var/lib/tomcat8/webapps/razbot/WEB-INF/classes/ia/")!=0)
			{
				chargementDico("/var/lib/tomcat8/webapps/razbot/WEB-INF/classes/ia/", nom);
			}
			else
				logger.catching(e);
		}
		return dico;
	}


	/**
	 * Permet de lancer les differentes phases d'une recherche
	 * @param phrase
	 * @return String
	 */
	protected String analysePhrase(String message)
	{
		String m = message.toLowerCase();
		String[] phrase = m.split(" ");
		
		String p = "";
		
		//On parcourt la phrase
		for (int i = 0; i < phrase.length; i++)
		{
			String motCourant = phrase[i];
			
			//Si la taille du mot > 3 lettre
			if(motCourant.length()>3)
			{
				//Si le mot est juste
				if (dicoFrancais.contains(motCourant))
				{
					if (i == 0)
					{
						p = motCourant;
					} else
					{
						p = p + " " + motCourant;
					}
				} 
				else
				{
					//On cherche en priorite si le mot mal orthographie se rapproche d'un mot cle
					String m1, m2, m3, m4;
					m1 = motPlusUneLettre(motCourant, dicoMotsCles);
					if (m1 != "")
					{
						m4 = m1;
					} else if ((m2 = motSubUneLettre(motCourant, dicoMotsCles)) != "")
					{
						m4 = m2;
					} else if ((m3 = motMoinsUneLettre(motCourant, dicoMotsCles)) != "")
					{
						m4 = m3;
					} else if ((m1 = motPlusUneLettre(motCourant, dicoFrancais)) != "")
					{
						m4 = m1;
					}else if((m2 = motSubUneLettre(motCourant, dicoFrancais)) != "")
					{
						m4 = m2;
					}else if((m3 = motMoinsUneLettre(motCourant, dicoFrancais)) != "")
					{
						m4 = m3;
					}else{
						m4 = motCourant;
					}
	
					if (i == 0)
					{
						p = m4;
					} else
					{
						p = p + " " + m4;
					}
				}
			//Sinon on ne le corrige pas
			}else{
				if (i == 0)
				{
					p = motCourant;
				} else
				{
					p = p + " " + motCourant;
				}
			}
			
		}
		
		//Suppression des accents dans la phrase
		p = suppressionAccents(p);
				
		return p;
	}

	/**
	 *  Supprime les accents du String en paramï¿½tre et renvoit la phrase
	 * @param p
	 * @return p sans accents
	 */
	private String suppressionAccents(String p)
	{
		p = p.replace('é', 'e');
		p = p.replace('è', 'e');
		p = p.replace('ê', 'e');
		p = p.replace('ë', 'e');
		p = p.replace('â', 'a');
		p = p.replace('î', 'i');
		p = p.replace('ï', 'i');
		p = p.replace('à', 'a');
		p = p.replace('û', 'u');
		p = p.replace('ô', 'o');
		p = p.replace('ç', 'c');
		p = p.replace('\'', ' ');
		
		return p;
	}

	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on ajoute
	 * une lettre au mot courant
	 * 
	 * @return String : Le mot trouve
	 * @param motCourant
	 *            String
	 */
	private String motPlusUneLettre(String motCourant, ArrayList<String> dictionnaireMots)
	{
		String debut, fin, motTrouve;
		char lettre;
		
		//On parcourt caractere par caractere
		for (int l = 0; l < motCourant.length(); l++)
		{
			lettre = 'a';
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l);
			
			//On parcourt toute les lettres de l'alphabet
			while (lettre < 'z')
			{
				//On rajoute une lettre
				motTrouve = debut + lettre + fin;
				
				//Si le nouveau mot existe
				if (dictionnaireMots.contains(motTrouve))
				{
					return motTrouve;
				}
				lettre++;
			}
		}
		return "";
	}

	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on enleve
	 * une lettre au mot courant
	 * 
	 * @return String : Le mot trouve
	 * @param motCourant
	 *            String
	 */
	private String motMoinsUneLettre(String motCourant, ArrayList<String> dictionnaireMots)
	{
		String debut, fin, motTrouve;
		
		//On parcourt caractere par caractere
		for (int l = 0; l < motCourant.length(); l++)
		{
			//Suppression d'une lettre
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l + 1);
			motTrouve = debut + fin;
			
			//Si le nouveau mot existe
			if (dictionnaireMots.contains(motTrouve))
			{
				return motTrouve;
			}
		}
		return "";
	}

	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on remplace
	 * une lettre dans le mot courant
	 * 
	 * @return String : Le mot trouve
	 * @param motCourant
	 *            String
	 */
	private String motSubUneLettre(String motCourant, ArrayList<String> dictionnaireMots)
	{
		String debut, fin, motTrouve;
		char lettre;
		
		//On parcourt caractere par caractere
		for (int l = 0; l < motCourant.length(); l++)
		{
			lettre = 'a';

			//On supprime une lettre
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l + 1);
			
			//On parcourt toute les lettres de l'alphabet
			while (lettre <= 'z')
			{
				//On rajoute une lettre
				motTrouve = debut + lettre + fin;
				
				//Si le mot existe dans le dictionnaire
				if (motTrouve != motCourant)
				{
					if (dictionnaireMots.contains(motTrouve))
					{
						return motTrouve;
					}
				}
				lettre++;
			}
		}
		return "";
	}
	
}