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
	ArrayList<String> dicoGenre;
	ArrayList<String> dicoPrenomFeminin;
	ArrayList<String> dicoPrenomMasculin;

	/**
	 * Initialisation de la recherche
	 */
	public RechercherMot()
	{
		dicoFrancais = this.chargementDico("./src/ia/","dicoFrancais.txt");
		dicoMotsCles = this.chargementDico("./src/ia/","dicoMotsCles.txt");
		dicoGenre = this.chargementDico("./src/ia/","dicoGenre.txt");
		dicoPrenomFeminin = this.chargementDico("./src/ia/","dicoPrenomFeminin.txt");
		dicoPrenomMasculin = this.chargementDico("./src/ia/","dicoPrenomMasculin.txt");
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
		//Parcourt du dictionnaire des mots cles pour traiter le cas des mots cles composes
		for (int i = 0; i < dicoMotsCles.size(); i++)
		{
			motsCles = dicoMotsCles.get(i).split(" ");
			// Si le mot clÃ© est composÃ© d'au moins deux termes
			if (motsCles.length > 1)
			{
				// On parcourt la phrase
				for (int j = 0; j < phrase.length; j++)
				{
					boolean similaire = false;
					// Premier terme du mot cle est trouve
					if (phrase[j].compareTo(motsCles[0]) == 0)
					{
						//Booleen permettant de suivre le matching entre la phrase et les termes d'un meme mot cle
						similaire = true;
						// Pour les termes suivants du mot cle
						for (int k = 1; k < motsCles.length; k++)
						{
							//Si la phrase contient moins de mots que le mot cles courant 
							if(phrase.length-1 < j+k){
								similaire = false;
								break;
							}
							//Si le terme courant de la phrase et du mot cle sont egaux
							if ((phrase[j + k].compareTo(motsCles[k]) == 0) && (similaire == true))
							{
								similaire = true;
							} else
							{
								break;
							}
						}
					}
					//Si le mot cle a ete trouve
					if (similaire == true)
					{
						motCle.add(dicoMotsCles.get(i));
					}
				}
			}
		}

		//Parcourt des mots de la phrase pour trouver les mots cles constitue d'un seul terme
		for (int l = 0; l < phrase.length; l++)
		{
			String motCourant = phrase[l];
			//Si le mot de la phrase est un mot cle
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
		//On transforme la phrase en tableau de mots
		String[] phrase = message.split(" ");
		
		String p = "";
		
		//On parcourt la phrase
		for (int i = 0; i < phrase.length; i++)
		{
			String motCourant = phrase[i];
			
			//Si la taille du mot > 3 lettre et le premier caractere n'est pas une majuscule on corrige le mot
			if(motCourant.length()>3 && !(motCourant.substring(0, 1).matches("[A-Z]")))
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
		p = p.replace('ê', 'e');
		p = p.replace('è', 'e');
		p = p.replace('ë', 'e');
		p = p.replace('à', 'a');
		p = p.replace('î', 'i');
		p = p.replace('ï', 'i');
		p = p.replace('â', 'a');
		p = p.replace('û', 'u');
		p = p.replace('ô', 'o');
		p = p.replace('ç', 'c');
		
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
		boolean b;
		
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
		boolean b;
		
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
		
		//On parcourt les mots de la phrase
		for (int i=0; i<phrase.length; i++){
			int tailleMot = phrase[i].length();
			
			//On cherche un mot 4 caracteres
			if(tailleMot == 4){
				char premierCaractere;
				char deuxiemeCaractere;
				char troisiemeCaractere;
				char quatriemeCaractere;
				premierCaractere = phrase[i].charAt(0);
				deuxiemeCaractere = phrase[i].charAt(1);
				troisiemeCaractere = phrase[i].charAt(2);
				quatriemeCaractere = phrase[i].charAt(3);
				
				//On regarde si ce sont tous des chiiffres
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
		
		//On parcourt les mots de la phrase
		for (int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			
			//On parcourt le dictionnaire des genres
			for(int j=0; j<dicoGenre.size(); j++){
				int tailleGenre = dicoGenre.get(j).length();
				int tailleMotCourant = motCourant.length();
				
				//Si le genre correspond au debut du mot courant
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
		
		//On parcourt les mots de la phrase
		for (int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			
			//Premier caractere en majuscule
			char[] char_table = motCourant.toCharArray();
			char_table[0]=Character.toUpperCase(char_table[0]);
			motCourant = new String(char_table);
			
			//Si le mot courant existe dans le dictionnaire des prenoms
			if((dicoPrenomFeminin.contains(motCourant) || dicoPrenomMasculin.contains(motCourant)) && ((i+1)< phrase.length)){
				nom = phrase[i]+" "+phrase[i+1];
				return nom;
			}
		}
		return nom;
	}
}