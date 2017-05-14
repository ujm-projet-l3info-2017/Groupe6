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
 * @author Rémi SIRACUSA
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
	ArrayList<String> dicoGenre;
	ArrayList<String> dicoPrenomFeminin;
	ArrayList<String> dicoPrenomMasculin;

	/**
	 * Initialisation de la recherche
	 */
	public RechercherMot(){
		dicoFrancais = this.chargementDico("./src/ia/dicoFrancais.txt");
		dicoMotsCles = this.chargementDico("./src/ia/dicoMotsCles.txt");
		dicoGenre = this.chargementDico("./src/ia/dicoGenre.txt");
		dicoPrenomFeminin = this.chargementDico("./src/ia/dicoPrenomFeminin.txt");
		dicoPrenomMasculin = this.chargementDico("./src/ia/dicoPrenomMasculin.txt");
	}
	
	/**
	 * Permet de charger le dictionnaire dans une ArrayList
	 * 
	 * @throws IOException
	 */
	private ArrayList<String> chargementDico(String chemin)
	{
		ArrayList<String> dico = new ArrayList<String>();
		try
		{

			FileReader f = new FileReader(chemin);
			BufferedReader br = new BufferedReader(f);
			String line;
			dico = new ArrayList<>();
			while ((line = br.readLine()) != null)
			{
				dico.add(line);
			}
			br.close();
		}
		catch (IOException e)
		{
			logger.error("chargementDico","Erreur lors de l'ouverture du dictionnaire");
		}
		return dico;
	}

	/**
	 * Permet de lancer une recherche de mots cles
	 * 
	 * @param phraseBrut
	 *            String
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> chercherMotsCles(String phraseBrut)
	{
		String phrase[] = phraseBrut.split(" ");
		// Liste de mots cles trouves
		ArrayList<String> motCle = new ArrayList<String>();
		String[] motsCles;
		for (int i = 0; i < dicoMotsCles.size(); i++)
		{
			motsCles = dicoMotsCles.get(i).split(" ");
			// Si le mot clé est composé d'au moins deux termes
			if (motsCles.length > 1)
			{
				// On parcourt la phrase
				for (int j = 0; j < phrase.length; j++)
				{
					boolean similaire = false;
					// Premier terme du mot cle est trouve
					if (phrase[j].compareTo(motsCles[0]) == 0)
					{
						similaire = true;
						// Pour les termes suivants du mot cle
						for (int k = 1; k < motsCles.length; k++)
						{
							if(phrase.length-1 < j+k){
								similaire = false;
								break;
							}
							if ((phrase[j + k].compareTo(motsCles[k]) == 0) && (similaire == true))
							{
								similaire = true;
							} else
							{
								similaire = false;
							}
						}
					}
					if (similaire == true)
					{
						motCle.add(dicoMotsCles.get(i));
					}
				}
			}
		}

		for (int l = 0; l < phrase.length; l++)
		{
			String motCourant = phrase[l];
			if (dicoMotsCles.contains(motCourant))
			{
				motCle.add(motCourant);
			}
		}
		return motCle;
	}

	/**
	 * Permet de lancer les differentes phases d'une recherche
	 * 
	 * @param phrase
	 *            String
	 * @return String
	 */
	protected String analysePhrase(String message)
	{
		String[] phrase = message.split(" ");
		String p = "";
		for (int i = 0; i < phrase.length; i++)
		{
			String motCourant = phrase[i];

			if (dicoFrancais.contains(motCourant))
			{
				if (i == 0)
				{
					p = motCourant;
				} else
				{
					p = p + " " + motCourant;
				}
			} else
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
		}
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
		boolean b;
		
		for (int l = 0; l < motCourant.length(); l++)
		{
			lettre = 'a';
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l);
			while (lettre < 'z')
			{
				motTrouve = debut + lettre + fin;
				b = dictionnaireMots.contains(motTrouve);
				if (b == true)
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
		boolean b;
		for (int l = 0; l < motCourant.length(); l++)
		{
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l + 1);
			motTrouve = debut + fin;
			b = dictionnaireMots.contains(motTrouve);
			if (b == true)
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
		boolean b;
		for (int l = 0; l < motCourant.length(); l++)
		{
			lettre = 'a';
			debut = motCourant.substring(0, l);
			fin = motCourant.substring(l + 1);
			while (lettre <= 'z')
			{
				motTrouve = debut + lettre + fin;
				// System.out.println(motTrouve);
				if (motTrouve != motCourant)
				{
					b = dictionnaireMots.contains(motTrouve);
					if (b == true)
					{
						return motTrouve;
					}
				}
				lettre++;
			}
		}
		return "";
	}
	
	/**
	 * Permet de trouver la date present dans la phrase
	 * 
	 * @return String : La date
	 * @param message
	 *            String
	 */
	public String trouverDate(String message){
		String date="";
		String phrase[] = message.split(" ");
		for (int i=0; i<phrase.length; i++){
			int tailleMot = phrase[i].length();
			if(tailleMot == 4){
				char premierCaractere;
				char deuxiemeCaractere;
				char troisiemeCaractere;
				char quatriemeCaractere;
				premierCaractere = phrase[i].charAt(0);
				deuxiemeCaractere = phrase[i].charAt(1);
				troisiemeCaractere = phrase[i].charAt(2);
				quatriemeCaractere = phrase[i].charAt(3);
				if (Character.isDigit(premierCaractere) && Character.isDigit(deuxiemeCaractere) && Character.isDigit(troisiemeCaractere) && Character.isDigit(quatriemeCaractere)){
				   return date = String.valueOf(premierCaractere)+String.valueOf(deuxiemeCaractere)+String.valueOf(troisiemeCaractere)+String.valueOf(quatriemeCaractere);
			    }
			}
		}
		return date;
	}
	
	/**
	 * Permet de trouver le genre present dans la phrase
	 * 
	 * @return String : Le genre
	 * @param message String
	 */
	public String trouverGenre(String message){
		String phrase[] = message.split(" ");
		for (int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			for(int j=0; j<dicoGenre.size(); j++){
				int tailleGenre = dicoGenre.get(j).length();
				int tailleMotCourant = motCourant.length();
				if((tailleMotCourant >= tailleGenre) && (dicoGenre.get(j).compareTo(motCourant.substring(0, tailleGenre)) == 0)){
					return dicoGenre.get(j);
				}
			}
		}
		return "";
	}
	
	/**
	 * Permet de trouver le realisateur ou l'acteur present dans la phrase
	 * 
	 * @return String : Le nom et le prenom de la personne
	 * @param message String
	 */
	public String trouverPersonne(String message){
		String nom = "";
		String phrase[] = message.split(" ");
		for (int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			//Premier caractere en majuscule
			char[] char_table = motCourant.toCharArray();
			char_table[0]=Character.toUpperCase(char_table[0]);
			motCourant = new String(char_table);
			
			if((dicoPrenomFeminin.contains(motCourant) || dicoPrenomMasculin.contains(motCourant)) && ((i+1)< phrase.length)){
				nom = phrase[i]+" "+phrase[i+1];
				return nom;
			}
		}
		return nom;
	}
}