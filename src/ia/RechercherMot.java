package ia;

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

	/**
	 * Constructeur pour effectuer la recherche
	 * @param phraseBrut String
	 * @param dico ArrayList<String>
	 * @throws IOException
	 */
	public RechercherMot(String phraseBrut, ArrayList<String> dico) throws IOException{
		this.dico = dico;
		String phrase[] = phraseBrut.split(" ");
		ArrayList<String> motTrouves = new ArrayList<>();
		motTrouves = this.analysePhrase(phrase);
		for(int i=0; i<motTrouves.size(); i++){
			System.out.println(motTrouves.get(i));
		}
	}
	
	/**
	 * Permet de lancer les differentes phases d'une recherche
	 * @param phrase String
	 * @return void
	 */
	private ArrayList<String> analysePhrase(String phrase[]){
		ArrayList<String> motTrouves = new ArrayList<>();
		for(int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			
			if(dico.contains(motCourant.toLowerCase())){
				motTrouves.add(motCourant);
				return motTrouves;
			}else{
				motTrouves = motPlusUneLettre(motCourant.toLowerCase(), motTrouves);
				motTrouves = motMoinsUneLettre(motCourant.toLowerCase(), motTrouves);
				motTrouves = motSubUneLettre(motCourant.toLowerCase(), motTrouves);
			}
		}
		return motTrouves;
	}
	
	
	/**
	 * Permet de trouver les mots presents dans le dictionnaire lorsqu'on ajoute une lettre au mot courant
	 * @return ArrayList<String> : Les mots trouves
	 * @param motCourant String
	 * @param motTrouves ArrayList<String>
	 */
	private ArrayList<String> motPlusUneLettre(String motCourant, ArrayList<String> motTrouves){
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
					motTrouves.add(motTrouve);
				}
			lettre++;
			}
		}		
		return motTrouves;
	}
	
	/**
	 * Permet de trouver les mots presents dans le dictionnaire lorsqu'on enleve une lettre au mot courant
	 * @return ArrayList<String> : Les mots trouves
	 * @param motCourant String
	 * @param motTrouves ArrayList<String>
	 */
	private ArrayList<String> motMoinsUneLettre(String motCourant, ArrayList<String> motTrouves){
		
		String debut, fin, motTrouve;
		boolean b;
		for(int l=0; l< motCourant.length(); l++){	
			debut = motCourant.substring(0, l);			
			fin = motCourant.substring(l+1);
			motTrouve = debut+fin;
			b = dico.contains(motTrouve);
			if(b == true){
				motTrouves.add(motTrouve);
			}
		}
		return motTrouves;
	}
	
	/**
	 * Permet de trouver les mots presents dans le dictionnaire lorsqu'on remplace une lettre dans le mot courant
	 * @return ArrayList<String> : Les mots trouves
	 * @param motCourant String
	 * @param motTrouves ArrayList<String>
	 */
	private ArrayList<String> motSubUneLettre(String motCourant, ArrayList<String> motTrouves){
		
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
						motTrouves.add(motTrouve);
					}
				}
			lettre++;
			}
		}	
		return motTrouves;
	}	
}