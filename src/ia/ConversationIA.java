package ia;

import java.util.ArrayList;


public class ConversationIA
{
	/**
	 * Contient le nom de l'utilisateur, les criteres (booleen et String) et l'avancement de la discussion
	 */
	protected String nomUtilisateur;
	protected boolean b_realisateur, b_acteur, b_sortie, b_genre;
	protected String s_realisateur,s_acteur,s_sortie,s_genre;
	protected String etape; // debut, discussion, p_critere, recherche, satisfaction, recommencer, erreur, fin
	RechercherMot recherche;
	/**
	 * Initialisation de la conversation
	 * @param nom String
	 */
	public ConversationIA(String nom)
	{
		nomUtilisateur = nom;
		b_realisateur=false;
		b_acteur=false;
		b_sortie=false;
		b_genre=false;
		s_realisateur="";
		s_acteur="";
		s_sortie="";
		s_genre="";
		etape = "debut"; // Si il y a un seul appel de ConversationIA
	}
	

	/**
	 * Lis le message de l'utilisateur, effectue un traitement et renvoie un message
	 * @param message String
	 * @return String : La reponse de l'IA
	 */
	public String traitementMessage(String message)
	{
		if(etape=="debut")
		{
			etape="discussion";
			return Arbre.lancementArbre();
		}		
		
		ArrayList<String> motTrouves = new ArrayList<String>();
		recherche = new RechercherMot();
		
		String messageCorrige = recherche.analysePhrase(message);
		motTrouves = recherche.chercherMotsCles(messageCorrige);
		
		if((etape=="discussion")&&((motTrouves.contains("critere"))||(motTrouves.contains("criteres"))))
		{
			etape="p_critere";
			return critereUtilisateur(message, motTrouves);
		}
		if((etape=="p_critere")&&(!motTrouves.contains("termine")))
		{
			return critereUtilisateur(message, motTrouves);
		}
		if((etape=="p_critere")&&(motTrouves.contains("termine")))
		{
			etape="recherche";
			// Faire une recherche avec les criteres enregistrï¿½s puis rappeler l'IA
			// Si on ne trouve aucun film appeler erreur()
			return "Cas termine";
		}
		if((etape=="discussion")&&(motTrouves.contains("aleatoire")))
		{
			etape="r_aleatoire";
			// Faire une recherche alï¿½atoire et envoyer le resultat puis rappeler l'IA
			// Si on ne trouve aucun film (ce qui serait etrange) appeler erreur()
			return "Cas aleatoire";
		}
		if(etape=="recherche")
		{
			etape="satisfaction";
			return "Etes-vous satisfaits de cette proposition ?";
		}
		if((etape=="satisfaction")&&(motTrouves.contains("oui")))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
		if((etape=="satisfaction")&&(motTrouves.contains("non")))
		{
			etape="recommencer";
			reset(5);
			return "Desole. On recommence ?";
		}
		if((etape=="recommencer")&&(motTrouves.contains("oui")))
		{
			etape="discussion";
			return "Que souhaitez-vous : une recherche de film par critere ou un film de mon choix ?";
		}
		if((etape=="recommencer")&&(motTrouves.contains("non")))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
		if(motTrouves.contains("au revoir"))
		{
			etape="fin";
			return Arbre.fin();
			// Stopper l'execution
		}
				
		return "Je n'ai pas compris, pouvez-vous reformuler ?";
	}
	
	/**
	 * Stocke les criteres de l'utilisateur
	 * @param message String
	 * @param motTrouves ArrayList(String)
	 * @return String : La reponse de l'IA
	 */
	public String critereUtilisateur(String message, ArrayList<String> motTrouves)
	{
		if(motTrouves.contains("realisateur"))
		{
			b_realisateur=true;
			String realisateur = recherche.trouverPersonne(message);
			if(realisateur != ""){
				s_realisateur = realisateur;
			}else{
				reset(1);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		if(motTrouves.contains("acteur"))
		{
			b_acteur=true;
			String acteur = recherche.trouverPersonne(message);
			if(acteur != ""){
				s_acteur = acteur;
			}else{
				reset(2);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		if(motTrouves.contains("date de sortie"))
		{
			b_sortie=true;
			String date = recherche.trouverDate(message);
			if(date != ""){
				s_sortie = date;
			}else{
				reset(3);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
			
			
		}
		if(motTrouves.contains("genre"))
		{
			b_genre=true;
			String genre = recherche.trouverGenre(message);
			if(genre != ""){
				s_genre = genre;
			}else{
				reset(4);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		
		String R="";
		String A="";
		String S="";
		String G="";
		
		if(b_realisateur==false)
		{
			R=" realisateur ?";
		}
		if(b_acteur==false)
		{
			A=" acteur ?";
		}
		if(b_sortie==false)
		{
			S=" date de sortie ?";
		}
		if(b_genre==false)
		{
			G=" genre ?";
		}
		if((b_realisateur==true)&&(b_acteur==true)&&(b_sortie==true)&&(b_genre==true))
		{
			// Lancer la recherche puis rappeler l'IA
			// Si on ne trouve aucun film appeler erreur()
			return "Cas tous criteres";
		}
		
		return "Un autre critere:"+R+A+S+G+", ou avez-vous termine ?";
	}
	
	/**
	 * Si aucun film ne correspond aux critères, on relance la recherche
	 * @return String
	 */
	public String erreur(){
		etape="recommencer";
		reset(5);
		return "Desole, je n'ai rien trouvé. On recommence ?";
	}
	
	public void reset(int option)
	{
		switch(option)
		{
		case 1: // Reset realisateur
			b_realisateur=false;
			s_realisateur="";
			break;
		case 2: // Reset acteur
			b_acteur=false;
			s_acteur="";
			break;
		case 3: // Reset sortie
			b_sortie=false;
			s_sortie="";
			break;
		case 4: // Reset genre
			b_genre=false;
			s_genre="";
			break;
		case 5: // Reset total (Jean-Reset)
			b_realisateur=false;
			b_acteur=false;
			b_sortie=false;
			b_genre=false;
			s_realisateur="";
			s_acteur="";
			s_sortie="";
			s_genre="";
			break;
		}
	}

}