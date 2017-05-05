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
	 * Constructeur pour effectuer la recherche
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
	 * Permet de charger le dictionnaire dans une ArrayList
	 * @return void
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
	 * Permet de lancer les differentes phases d'une recherche
	 * @param phrase String
	 * @return void
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