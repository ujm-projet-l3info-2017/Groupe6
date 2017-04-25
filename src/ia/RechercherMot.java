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
	 */
	public RechercherMot(String phraseBrut, ArrayList<String> dico) throws IOException{
		this.dico = dico;
		String phrase[] = phraseBrut.split(" ");
		for(int i=0; i<phrase.length; i++){
			System.out.print(phrase[i]+" ");
		}
		this.analysePhrase(phrase);
		
	}
	
	/**
	 * Permet de lancer les differentes phases d'une recherche
	 * @return void
	 */
	private void analysePhrase(String phrase[]){
		for(int i=0; i<phrase.length; i++){
			String motCourant = phrase[i];
			ArrayList<String> motTrouves = new ArrayList<>();
			if(dico.contains(motCourant.toLowerCase())){
				System.out.println(motCourant+" ");
			}else{
				System.out.println("Mot introuvable : "+motCourant);
				motTrouves = motPlusUneLettre(motCourant, motTrouves);
				System.out.println("Mot plus une lettre :");
				for(int j=0; j<motTrouves.size(); j++){
					System.out.println(motTrouves.get(j));
				}
				int fin1 = motTrouves.size();
				motTrouves = motMoinsUneLettre(motCourant, motTrouves);
				System.out.println("Mot moins une lettre :");
				for(int j=fin1; j<motTrouves.size(); j++){
					System.out.println(motTrouves.get(j));
				}
				int fin2 = motTrouves.size();
				motTrouves = motSubUneLettre(motCourant, motTrouves);
				System.out.println("Mot avec remplacement d'une lettre :");
				for(int j=fin2; j<motTrouves.size(); j++){
					System.out.println(motTrouves.get(j));
				}
				
			}
		}	           
	}
	
	
	/**
	 * Permet de trouver les mots presents dans le dictionnaire lorsqu'on ajoute une lettre au mot courant
	 * @return ArrayList<String> : Les mots trouves
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
					//System.out.println(motTrouve);
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
				//System.out.println(motTrouve);
				motTrouves.add(motTrouve);
			}
		}
		return motTrouves;
	}
	
	/**
	 * Permet de trouver les mots presents dans le dictionnaire lorsqu'on remplace une lettre dans le mot courant
	 * @return ArrayList<String> : Les mots trouves
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
						//System.out.println(motTrouve);
						motTrouves.add(motTrouve);
					}
				}
			lettre++;
			}
		}	
		return motTrouves;
	}	
}