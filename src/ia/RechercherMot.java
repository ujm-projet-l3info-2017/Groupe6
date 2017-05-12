package ia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Classe de la recherche d'un mot dans un dictionnaire
 * @author Rémi SIRACUSA
 *
 */
public class RechercherMot{

	/**
	 * Liste de mot constituant le dictionnaire
	 */
	ArrayList<String> dico;
	FileReader f;

	/**
	 * Constructeur pour corriger une phrase
	 * @param phraseBrut String
	 * @throws IOException
	 */
	public RechercherMot(String phraseBrut) throws IOException{
		chargementDico();
		String phrase[] = phraseBrut.split(" ");
		String phraseFinale;
		phraseFinale = this.analysePhrase(phrase);
		System.out.println(phraseFinale);
	}
	
	/**
	 * Constructeur pour effectuer la recherche
	 * @param dictionnaire ArrayList<String>
	 * @throws IOException
	 */
	public RechercherMot(ArrayList<String> dictionnaire) throws IOException{
		dico = dictionnaire;
	}
	
	/**
	 * Permet de charger le dictionnaire dans une ArrayList
	 * @throws IOException
	 */
	private void chargementDico() throws IOException{
		try{
			f = new FileReader("./src/ia/dico");
			System.out.println("\nOuverture du dictionnaire réussi !");
		}catch (IOException e){
		   	System.out.println("Erreur lors de l'ouverture du dictionnaire");
		}
		
		BufferedReader br = new BufferedReader(f);
		String line;
		dico = new ArrayList<>();
		while ((line = br.readLine()) != null) {
		   dico.add(line);
		}
		br.close();
	}
	
	/**
	 * Permet de lancer une recherche de mots cles
	 * @param phraseBrut String
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> chercherMotsCles(String phraseBrut){
		String phrase[] = phraseBrut.split(" ");
		//Liste de mots cles trouves
		ArrayList<String> motCle = new ArrayList<String>();
		//Liste de gestion des mots cles constitue de plusieurs termes
		ArrayList<String> plusieursMotsCles = new ArrayList<String>();
		String[] motsCles;
		for(int i=0; i<dico.size(); i++){
			motsCles=dico.get(i).split(" ");
			//Si le mot clé est composé d'au moins deux termes
			if(motsCles.length > 1){
				//On parcourt la phrase
				System.out.println(motsCles.length);
				for(int j=0; j<phrase.length; j++){
					boolean similaire = false;
					//Premier terme du mot cle est trouve
					System.out.println("première cdt :"+phrase[j]+" "+motsCles[0]+"");
					if(phrase[j].compareTo(motsCles[0]) == 0){
						System.out.println("passé");
						similaire = true;
						//Pour les termes suivants du mot cle
						for(int k=1; k<motsCles.length; k++){
							System.out.println("Deuxième condition :"+phrase[j+k]+" "+motsCles[k]);
							if((phrase[j+k].compareTo(motsCles[k]) == 0) && (similaire == true)){
								similaire = true;
							}else{
								similaire = false;
							}
						}
					}
					if(similaire == true){
						motCle.add(dico.get(i));
					}
				}
			}
		}
		
		for(int l=0; l<phrase.length; l++){
			String motCourant = phrase[l];
			if(dico.contains(motCourant.toLowerCase())){
				motCle.add(motCourant);
			}
		}
		return motCle;
	}
	
	/**
	 * Permet de lancer les differentes phases d'une recherche
	 * @param phrase String
	 * @return String
	 */
	private String analysePhrase(String phrase[]){
		String p="";
		for(int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			
			if(dico.contains(motCourant.toLowerCase())){
				if(i==0){
					p=motCourant;
				}else{
					p=p+" "+motCourant;
				}
			}else{
				String m1 = motPlusUneLettre(motCourant.toLowerCase());
				String m2 = motSubUneLettre(motCourant.toLowerCase());
				String m3 = motMoinsUneLettre(motCourant.toLowerCase());
				String m4;
				if(m1 != ""){
					m4=m1;
				}else if(m2 != ""){
					m4=m2;
				}else if(m3 != ""){
					m4=m3;
				}else{
					m4=motCourant;
				}
				
				if(i==0){	
					p=m4;
				}else{
					p=p+" "+m4;
				}
			}
		}
		return p;
	}
	
	
	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on ajoute une lettre au mot courant
	 * @return String : Le mot trouve
	 * @param motCourant String
	 */
	private String motPlusUneLettre(String motCourant){
		String debut, fin, motTrouve;
		char lettre;
		boolean b;
		for(int l=0; l< motCourant.length(); l++){
			lettre = 'a';		
			debut = motCourant.substring(0, l);			
			fin = motCourant.substring(l);
			while(lettre < 'z'){
				motTrouve = debut+lettre+fin;
				b = dico.contains(motTrouve);
				if(b == true){				
					return motTrouve;
				}
			lettre++;
			}
		}
		return "";
	}
	
	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on enleve une lettre au mot courant
	 * @return String : Le mot trouve
	 * @param motCourant String
	 */
	private String motMoinsUneLettre(String motCourant){
		String debut, fin, motTrouve;
		boolean b;
		for(int l=0; l< motCourant.length(); l++){	
			debut = motCourant.substring(0, l);			
			fin = motCourant.substring(l+1);
			motTrouve = debut+fin;
			b = dico.contains(motTrouve);
			if(b == true){
				return motTrouve;
			}
		}
		return "";
	}
	
	/**
	 * Permet de trouver le mot present dans le dictionnaire lorsqu'on remplace une lettre dans le mot courant
	 * @return String : Le mot trouve
	 * @param motCourant String
	 */
	private String motSubUneLettre(String motCourant){
		String debut, fin, motTrouve;
		char lettre;
		boolean b;
		for(int l=0; l< motCourant.length(); l++){
			lettre = 'a';		
			debut = motCourant.substring(0, l);			
			fin = motCourant.substring(l+1);
			while(lettre <= 'z'){
				motTrouve = debut+lettre+fin;
				//System.out.println(motTrouve);
				if(motTrouve != motCourant){
					b = dico.contains(motTrouve);
					if(b == true){
						return motTrouve;
					}
				}
			lettre++;
			}
		}	
		return "";
	}	
}