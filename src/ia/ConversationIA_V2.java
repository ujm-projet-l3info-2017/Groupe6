package ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import allocine.ParseurAllocine;

public class ConversationIA_V2
{
	// Initialisation du log
	static final Logger logger = LogManager.getLogger(ConversationIA_V2.class.getName());

	private String nom; // Nom de l'utilisateur
	private Etape prochaineEtape; // Prochaine étape à lancer
	private RechercherMot recherche; // Pour rechercher des mots dans le message
	private ArrayList<String> motTrouves; // Liste des mots clés du message
	private String messageCorrige; // Message après passage par le dictionnaire
	
	//Pour proposition
	private boolean b_realisateur, b_acteur, b_sortie, b_genre; // Devient false si l'utilisateur ne s'y interesse pas
	private boolean proposition, satisfaction, retour; // Sous-étapes
	
	//Pour avis
	private boolean b_film;
	
	private String s_realisateur, s_acteur, s_sortie, s_genre; // Contient les choix de l'utilisateur
	private List<String> liste; // Liste de films trouvés
	private String film; // Film selectionné

	public ConversationIA_V2(String nom)
	{
		this.nom = nom;
		b_realisateur = true;
		b_acteur = true;
		b_sortie = true;
		b_genre = true;
		proposition = false;
		satisfaction = false;
		retour = false;
		b_film = false;
		s_realisateur = "";
		s_acteur = "";
		s_sortie = "";
		s_genre = "";
		prochaineEtape = Etape.DEBUT;
	}

	public String traitementMessage(String message)
	{
		motTrouves = new ArrayList<String>();
		recherche = new RechercherMot();
		messageCorrige = recherche.analysePhrase(message);
		motTrouves = recherche.chercherMotsCles(messageCorrige);

		return executerProchaineEtape();
	}

	public String executerProchaineEtape()
	{
		switch (prochaineEtape)
		{
		case DEBUT:
			if (motTrouves.contains("bonjour")) // Si le message contient une salutation
			{
				prochaineEtape = Etape.SALUTATION;
			} else if (motTrouves.contains("avis")) // Si il ne contient qu'une demande d'avis
			{
				Salutations salut = new Salutations();
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom + ".";
			} else if (motTrouves.contains("cherche")) // Si il ne contient qu'une demande de proposition
			{
				Salutations salut = new Salutations();
				prochaineEtape = Etape.PROPOSITION;
				return salut.aleatoire() + " " + nom + ". Avez-vous un genre favori ?";
			} else // Si rien n'est reconnu
			{
				Salutations salut = new Salutations();
				return salut.aleatoire() + " " + nom
						+ ". Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
			}
			break;
		case SALUTATION:
			Salutations salut = new Salutations();
			if (motTrouves.contains("avis")) // Si il y a aussi une demande d'avis après les salutations
			{
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom
						+ ".";
			}
			if	(motTrouves.contains("cherche")) // Si il y a aussi une demande de proposition après les salutations
			{
				prochaineEtape = Etape.PROPOSITION;
				return salut.aleatoire() + " " + nom
						+ ".";
			}
			return salut.aleatoire() + " " + nom
					+ ". Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
		case AVIS:
			//Recherche du film contenu dans le message / demande du film duquel il veut un avis
			if(!b_film)
			{
				return " Vous voulez mon avis sur quel film?";
			}
			
			//On dit qui est le réalisateur et on met l'affiche, qui joue dedans etc.
			
			//Avis en fonction de la note des utilisateurs et de la presse
			
			break;
		case PROPOSITION:
			if (b_genre && (motTrouves.contains("non"))) // Si l'utilisateur ne veut pas de genre précis
			{
				b_genre = false;
				return "Preferez-vous un film récent ?";
			}
			if (b_genre && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un genre précis ou si on ne reconnait pas le genre donné par l'utilisateur
			{
				String genre = recherche.trouverGenre(messageCorrige);
				if (genre != "")
				{
					retour = false;
					s_genre = genre;
				} else
				{
					retour = true;
					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
				}
				return "Preferez-vous un film récent ?";
			}
			if (b_genre && b_sortie && motTrouves.contains("non")) // Si l'utilisateur veut un genre particulier mais pas de date de sortie précise
			{
				b_sortie = false;
				proposition = true;
				liste = ParseurAllocine.recupererFilms(s_genre);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (b_genre && b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur veut un genre et une date de sortie particuliers ou si on ne reconnait pas la date donnée par l'utilisateur
			{
				// Comment se passe la récupération de date ?
				String date = recherche.trouverDate(messageCorrige);
				if (date != "")
				{
					retour = false;
					s_sortie = date;
				} else
				{
					retour = true;
					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
				}

				liste = ParseurAllocine.recupererFilms(s_genre,true);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (b_sortie && motTrouves.contains("non")) // Si l'utilisateur ne veut pas de genre ni de date de sortie précis
			{
				b_sortie = false;
				return "Cherchez-vous un realisateur en particulier ?";
			}
			if (b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur ne veut pas de genre précis mais une date de sortie précise ou si on ne comprend pas la date entrée par l'utilisateur
			{
				// Comment se passe la récupération de date ?
				String date = recherche.trouverDate(messageCorrige);
				if (date != "")
				{
					retour = false;
					s_sortie = date;
				} else
				{
					retour = true;
					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
				}

				liste = ParseurAllocine.recupererFilms(true);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (b_realisateur && (motTrouves.contains("non"))) // Si l'utilisateur ne veut pas de réalisateur précis
			{
				b_realisateur = false;
				return "Peut-être recherchez-vous un acteur en particulier";
			}
			if (b_realisateur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un réalissateur particulier ou si on ne comprend pas le realisateur entré par l'utilisateur
			{
				String realisateur = recherche.trouverPersonne(messageCorrige);
				if (realisateur != "")
				{
					retour = false;
					s_realisateur = realisateur;
				} else
				{
					retour = true;
					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
				}
				liste = ParseurAllocine.chercherFilmDePersonne(s_realisateur);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (b_acteur && motTrouves.contains("non")) // Si l'utilisateur ne veut pas d'acteur précis
			{
				b_acteur = false;
				liste = ParseurAllocine.recupererFilms();
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (b_acteur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un acteur précis ou si on ne comprend pas l'acteur donné par l'utilisateur
			{
				String acteur = recherche.trouverPersonne(messageCorrige);
				if (acteur != "")
				{
					retour = false;
					s_acteur = acteur;
				} else
				{
					retour = true;
					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
				}
				liste = ParseurAllocine.chercherFilmDePersonne(s_realisateur);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			if (proposition && motTrouves.contains("non")) // On propose un film que l'utilisateur ne connait pas
			{
				prochaineEtape = Etape.AVIS;
				// Le film est stocké dans film
				break;
			}
			if (proposition && motTrouves.contains("oui")) // On propose un film que l'utilisateur connait
			{
				satisfaction = true;
				return "L'avez-vous aimé ?";
			}
			if (satisfaction && motTrouves.contains("non")) // On propose un film que l'utilisateur n'aime pas
			{
				prochaineEtape = Etape.AVIS;
				// Le film est stocké dans film
				break;
			}
			if (satisfaction && motTrouves.contains("oui")) // On propose un film que l'utilisateur aime
			{
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous déjà vu ?";
			}
			return "Je n'ai pas compris, pouvez-vous reformuler ?";
		default:
			logger.error("IA", "Etape non reconnue");
			return null;
		}
		return null;
	}

	public String tirageAleatoire(List<String> films)
	{
		Random rand = new Random();
		int nbAleatoire = rand.nextInt(films.size());
		return films.get(nbAleatoire);
	}
}
