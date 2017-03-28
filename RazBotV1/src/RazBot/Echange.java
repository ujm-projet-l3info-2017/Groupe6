package RazBot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Echange {
	FileReader f;
	ArrayList<String> dico;
	public Echange(String phraseBrut) throws IOException{
		String phrase[] = phraseBrut.split(" ");
		for(int i=0; i<phrase.length; i++){
			System.out.print(phrase[i]+" ");
		}
		this.chargementDico();
		//this.afficherDico();
		this.analysePhrase(phrase);
		
	}
	
	private void chargementDico() throws IOException{
		//Création de l'objet File
	    try{
			f = new FileReader("dico.txt");
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
	
	//Fonction debug
	/*
	private void afficherDico(){
		Collections.sort(dico);
		for(int i=0; i<dico.size(); i++){
			System.out.println(dico.get(i)+"\n");
		}
	}
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
	
	//Mot avec une lettre de plus
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
	
	//Mot avec une lettre en moins
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
	
	//Mot avec remplacement d'une lettre
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
