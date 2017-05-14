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
	private Etape prochaineEtape; // Prochaine �tape � lancer
	private RechercherMot recherche; // Pour rechercher des mots dans le message
	private ArrayList<String> motTrouves; // Liste des mots cl�s du message
	private String messageCorrige; // Message apr�s passage par le dictionnaire
	
	//Pour proposition
	private boolean b_realisateur, b_acteur, b_sortie, b_genre; // Devient false si l'utilisateur ne s'y interesse pas
	private boolean proposition, satisfaction, retour; // Sous-�tapes
	
	//Pour avis
	private boolean b_film;
	
	private String s_realisateur, s_acteur, s_sortie, s_genre; // Contient les choix de l'utilisateur
	private List<String> liste; // Liste de films trouv�s
	private String film; // Film selectionn�

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
			// Si le message contient juste bonjour
			if (motTrouves.contains("bonjour"))
			{
				prochaineEtape = Etape.SALUTATION;
			} else if (motTrouves.contains("avis"))
			{
				Salutations salut = new Salutations();
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom + ".";
			} else if (motTrouves.contains("cherche"))
			{
				Salutations salut = new Salutations();
				prochaineEtape = Etape.PROPOSITION;
				return salut.aleatoire() + " " + nom + ". Avez-vous un genre favori ?";
			} else
			{
				return "Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
			}
			break;
		case SALUTATION:
			Salutations salut = new Salutations();
			if (motTrouves.contains("avis"))
			{
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom
						+ ".";
			}
			if	(motTrouves.contains("cherche"))
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
			
			//On dit qui est le r�alisateur et on met l'affiche, qui joue dedans etc.
			
			//Avis en fonction de la note des utilisateurs et de la presse
			
			break;
		case PROPOSITION:
			if (b_genre && (motTrouves.contains("non")))
			{
				b_genre = false;
				return "Preferez-vous un film r�cent ?";
			}
			if (b_genre && ((motTrouves.contains("oui")) || retour))
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
				return "Preferez-vous un film r�cent ?";
			}
			if (b_genre && b_sortie && (motTrouves.contains("non") || retour))
			{
				b_sortie = false;
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
				proposition = true;
				liste = ParseurAllocine.recupererFilms(s_genre);
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (b_genre && b_sortie && (motTrouves.contains("oui") || retour))
			{
				// Comment se passe la r�cup�ration de date ?
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
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (b_sortie && motTrouves.contains("non"))
			{
				b_sortie = false;
				return "Cherchez-vous un realisateur en particulier ?";
			}
			if (b_sortie && (motTrouves.contains("oui") || retour))
			{
				// Comment se passe la r�cup�ration de date ?
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
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (b_realisateur && (motTrouves.contains("non")))
			{
				b_realisateur = false;
				return "Peut-�tre recherchez-vous un acteur en particulier";
			}
			if (b_realisateur && ((motTrouves.contains("oui")) || retour))
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
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (b_acteur && motTrouves.contains("non"))
			{
				b_acteur = false;
				liste = ParseurAllocine.recupererFilms();
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (b_acteur && ((motTrouves.contains("oui")) || retour))
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
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
			}
			if (proposition && motTrouves.contains("non"))
			{
				prochaineEtape = Etape.AVIS;
				// Le film est stock� dans film
			}
			if (proposition && motTrouves.contains("oui"))
			{
				satisfaction = true;
				return "L'avez-vous aim� ?";
			}
			if (satisfaction && motTrouves.contains("non"))
			{
				prochaineEtape = Etape.AVIS;
				// Le film est stock� dans film
			}
			if (satisfaction && motTrouves.contains("oui"))
			{
				film = tirageAleatoire(liste);
				Recommandation recom = new Recommandation();
				return recom.aleatoire() + " " + film + ". L'avez-vous d�j� vu ?";
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
