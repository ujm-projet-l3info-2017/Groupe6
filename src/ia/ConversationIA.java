package ia;

import java.util.ArrayList;

/**
 * Contient le nom de l'utilisateur, les criteres (booleen et String) et l'avancement de la discussion
 */
public class ConversationIA
{
	protected String nomUtilisateur;
	protected boolean b_realisateur, b_acteur, b_sortie, b_genre;
	protected String s_realisateur,s_acteur,s_sortie,s_genre;
	/**
	 *  debut, discussion, p_critere, recherche, satisfaction, recommencer, erreur, fin, r_aleatoire
	 */
	protected Etape etape;
	RechercherMot recherche;
	
	/**
	 * Initialisation de la conversation
	 * @param nom String
	 */
	public ConversationIA(String nom)
	{
		nomUtilisateur=nom;
		b_realisateur=false;
		b_acteur=false;
		b_sortie=false;
		b_genre=false;
		s_realisateur="";
		s_acteur="";
		s_sortie="";
		s_genre="";
		etape = Etape.DEBUT; // Si il y a un seul appel de ConversationIA
	}
	

	/**
	 * Lis le message de l'utilisateur, effectue un traitement et renvoie un message
	 * @param message String
	 * @return String : La reponse de l'IA
	 */
	public String traitementMessage(String message)
	{
		if(etape==Etape.DEBUT)
		{
			etape=Etape.DISCUSSION;
			return Arbre.lancementArbre();
		}		
		
		ArrayList<String> motTrouves = new ArrayList<String>();
		recherche = new RechercherMot();
		
		String messageCorrige = recherche.analysePhrase(message);
		motTrouves = recherche.chercherMotsCles(messageCorrige);
		
		if((etape==Etape.DISCUSSION)&&((motTrouves.contains("critere"))||(motTrouves.contains("criteres"))))
		{
			etape=Etape.P_CRITERE;
			return critereUtilisateur(message, motTrouves);
		}
		if((etape==Etape.P_CRITERE)&&(!motTrouves.contains("termine")))
		{
			return critereUtilisateur(message, motTrouves);
		}
		if((etape==Etape.P_CRITERE)&&(motTrouves.contains("termine")))
		{
			etape=Etape.RECHERCHE;
			// Faire une recherche avec les criteres enregistrï¿½s puis rappeler l'IA
			// Si on ne trouve aucun film appeler erreur()
			return "Cas termine";
		}
		if((etape==Etape.DISCUSSION)&&(motTrouves.contains("aleatoire")))
		{
			etape=Etape.R_ALEATOIRE;
			// Faire une recherche alï¿½atoire et envoyer le resultat puis rappeler l'IA
			// Si on ne trouve aucun film (ce qui serait etrange) appeler erreur()
			return "Cas aleatoire";
		}
		if(etape==Etape.RECHERCHE)
		{
			etape=Etape.SATISFACTION;
			return "Etes-vous satisfaits de cette proposition ?";
		}
		if((etape==Etape.SATISFACTION)&&(motTrouves.contains("oui")))
		{
			etape=Etape.FIN;
			return Arbre.fin();
			// Stopper l'execution
		}
		if((etape==Etape.SATISFACTION)&&(motTrouves.contains("non")))
		{
			etape=Etape.RECOMMENCER;
			reset(5);
			return "Desole. On recommence ?";
		}
		if((etape==Etape.RECOMMENCER)&&(motTrouves.contains("oui")))
		{
			etape=Etape.DISCUSSION;
			return "Que souhaitez-vous : une recherche de film par critere ou un film de mon choix ?";
		}
		if((etape==Etape.RECOMMENCER)&&(motTrouves.contains("non")))
		{
			etape=Etape.FIN;
			return Arbre.fin();
			// Stopper l'execution
		}
		if(motTrouves.contains("au revoir"))
		{
			etape=Etape.FIN;
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
			if(realisateur != "")
			{
				s_realisateur = realisateur;
			}
			else
			{
				reset(1);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		if(motTrouves.contains("acteur"))
		{
			b_acteur = true;
			String acteur = recherche.trouverPersonne(message);
			if (acteur != "")
			{
				s_acteur = acteur;
			}
			else
			{
				reset(2);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		if(motTrouves.contains("date de sortie"))
		{
			b_sortie=true;
			String date = recherche.trouverDate(message);
			if (date != "")
			{
				s_sortie = date;
			}
			else
			{
				reset(3);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
			
			
		}
		if(motTrouves.contains("genre"))
		{
			b_genre=true;
			String genre = recherche.trouverGenre(message);
			if (genre != "")
			{
				s_genre = genre;
			}
			else
			{
				reset(4);
				return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
			}
		}
		
		String R="";
		String A="";
		String S="";
		String G="";
		
		if(!b_realisateur)
		{
			R=" realisateur ?";
		}
		if(!b_acteur)
		{
			A=" acteur ?";
		}
		if(!b_sortie)
		{
			S=" date de sortie ?";
		}
		if(!b_genre)
		{
			G=" genre ?";
		}
		if(b_realisateur && b_acteur && b_sortie && b_genre)
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
	public String erreur()
	{
		etape=Etape.RECOMMENCER;
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
		// Faut il un reset absolu qui supprime aussi le nomUtilisateur ?
		}
	}

}