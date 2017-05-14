package ia;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConversationIA_V2
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(ConversationIA_V2.class.getName());
		
	private String nom;
	private Etape prochaineEtape;
	private RechercherMot recherche;
	private ArrayList<String> motTrouves;
	private boolean b_realisateur, b_acteur, b_sortie, b_genre;
	private boolean proposition, satisfaction;
	protected String s_realisateur,s_acteur,s_sortie,s_genre;
	
	public ConversationIA_V2(String nom)
	{
		this.nom = nom;
		b_realisateur=true;
		b_acteur=true;
		b_sortie=true;
		b_genre=true;
		proposition=false;
		satisfaction=false;
		s_realisateur="";
		s_acteur="";
		s_sortie="";
		s_genre="";
		prochaineEtape = Etape.DEBUT;
		executerProchaineEtape();
	}
	
	public String traitementMessage(String message)
	{
		motTrouves = new ArrayList<String>();
		recherche = new RechercherMot();
		String messageCorrige = recherche.analysePhrase(message);
		motTrouves = recherche.chercherMotsCles(messageCorrige);
		
		return executerProchaineEtape();
	}
	
	public String executerProchaineEtape()
	{
		switch(prochaineEtape)
		{
		case DEBUT:
			if(motTrouves.contains("bonjour"))
			{
				prochaineEtape = Etape.SALUTATION;
			}
			else if(motTrouves.contains("avis"))
			{
				prochaineEtape = Etape.AVIS;
			}
			else if(motTrouves.contains("cherche"))
			{
				prochaineEtape = Etape.PROPOSITION;
				return "Avez-vous un genre favori ?";
			}
			else
			{
				return "Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
			}
			break;
		case SALUTATION:
			Salutations salut = new Salutations();
			return salut.aleatoire()+". Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
		case AVIS:
			
			break;
		case PROPOSITION:
			if((b_genre==true)&&(motTrouves.contains("non")))
			{
				b_genre = false;
				return "Preferez-vous un film récent ?";
			}
			if((b_genre==true)&&(motTrouves.contains("oui")))
			{
				// Rechercher genre dans message puis le stocker dans s_genre
				return "Preferez-vous un film récent ?";
			}
			if((b_genre==true)&&(b_sortie==true)&&(motTrouves.contains("non")))
			{
				b_sortie=false;
				// Recuperer film avec genre
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((b_genre==true)&&(b_sortie==true)&&(motTrouves.contains("oui")))
			{
				// Rechercher date dans message puis la stocker dans s_sortie
				// Recuperer film avec genre et date
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((b_sortie==true)&&(motTrouves.contains("non")))
			{
				b_sortie=false;
				return "Cherchez-vous un realisateur en particulier ?";
			}
			if((b_sortie==true)&&(motTrouves.contains("oui")))
			{
				// Rechercher date dans message puis la stocker dans s_sortie
				// Recuperer film avec date
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((b_realisateur==true)&&(motTrouves.contains("non")))
			{
				b_realisateur=false;
				return "Peut-être recherchez-vous un acteur en particulier";
			}
			if((b_realisateur==true)&&(motTrouves.contains("oui")))
			{
				// Recuperer nomRealisateur dans message puis le sotcker dans s_realisateur
				// Rechercher film avec realisateur
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((b_acteur==true)&&(motTrouves.contains("non")))
			{
				b_acteur=false;
				// Rechercher film aleatoire
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((b_acteur==true)&&(motTrouves.contains("oui")))
			{
				// Recuperer nomActeur dans message puis me stocker dans s_acteur
				// Rechercher film avec acteur
				proposition=true;
				Recommandation recom = new Recommandation();
				return recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			if((proposition=true)&&(motTrouves.contains("non")))
			{
				prochaineEtape = Etape.AVIS;
				//Comment stocker le film ?
			}
			if((proposition==true)&&(motTrouves.contains("oui")))
			{
				satisfaction = true;
				return "L'avez-vous aimé ?";				
			}
			if((satisfaction==true)&&(motTrouves.contains("non")))
			{
				prochaineEtape = Etape.AVIS;
				//Comment stocker le film ?
			}
			if((satisfaction==true)&&(motTrouves.contains("oui")))
			{
				// Rechercher un autre film
				Recommandation recom = new Recommandation();
				return "Un autre alors. " + recom.aleatoire();//+ resultat film +" L'avez-vous déjà vu ?";
			}
			return "Je n'ai pas compris, pouvez-vous reformuler ?";
		default:
			logger.error("executerProchaineEtape", "Etape non reconnue");
			return null;
		}
		return null;
	}
}
