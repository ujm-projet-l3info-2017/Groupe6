package ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import allocine.Film;
import allocine.ParseurAllocine;
import allocine.RechercheAllocine;

public class ConversationIA_V2
{
	// Initialisation du log
	static final Logger logger = LogManager.getLogger(ConversationIA_V2.class.getName());

	private String nom; // Nom de l'utilisateur
	private Etape prochaineEtape; // Prochaine étape à lancer
	private RechercherMot recherche; // Pour rechercher des mots dans le message
	private ArrayList<String> motTrouves; // Liste des mots clés du message
	private String messageCorrige; // Message après passage par le dictionnaire
	private String messageOrigine; //Message sans correction
	
	//Pour proposition
	private boolean b_realisateur, b_acteur, b_recent, b_genre; // Devient false si l'utilisateur ne s'y interesse pas
	private boolean proposition, satisfaction, retour; // Sous-étapes
	
	//Pour avis
	private boolean b_film;
	
	private String s_realisateur, s_acteur, s_genre; // Contient les choix de l'utilisateur
	private boolean s_recent;
	private List<String> liste; // Liste de films trouvés
	//private String film; // Film selectionné
	private Film film; //le film dont on est en train de parler 

	public ConversationIA_V2(String nom)
	{
		this.nom = nom;
		b_realisateur = false;
		b_acteur = false;
		b_recent = false;
		b_genre = false;
		proposition = false;
		satisfaction = false;
		retour = false;
		b_film = false;
		s_realisateur = "";
		s_acteur = "";
		s_recent = false;
		s_genre = "";
		film = null;
		prochaineEtape = Etape.SALUTATION;
	}

	public String traitementMessage(String message)
	{
		motTrouves = new ArrayList<String>();
		recherche = new RechercherMot();
		messageCorrige = recherche.analysePhrase(message);
		motTrouves = recherche.chercherMotsCles(messageCorrige);
		messageOrigine = message;

		return executerProchaineEtape();
	}

	public String executerProchaineEtape()
	{
		switch (prochaineEtape)
		{
		case SALUTATION:  //Première étape. Lors du premier message de l'utilisateur, on dit bonjour
			
			Salutations salut = new Salutations(); //On dit bonjour
			if (Reconnaissance.salutation(messageCorrige)) // Si le message contient une salutation
			{
				prochaineEtape = Etape.DEBUT;
				return salut.aleatoire() + " " + nom + ".";
			} 
			else if (Reconnaissance.avisFilm(messageCorrige) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvé
			{
				film = Reconnaissance.avisFilm(messageCorrige);
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom
						+ ". Je connais ce film !";
			} 
			else if (Reconnaissance.avis(messageCorrige)) // Si il ne contient qu'une demande d'avis sur un film mais qu'il ne nous dit pas le film
			{
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom
						+ ". Vous avez besoin d'un avis sur quel film ?";
			} 
			else if (Reconnaissance.recherche(messageCorrige)) // Si l'utilisateur recherche un film inconnu
			{
				prochaineEtape = Etape.PROPOSITION;
				return salut.aleatoire() + " " + nom + ". Avez-vous un genre favori ?";
			} 
			else // Si rien n'est reconnu
			{
				return salut.aleatoire() + " " + nom
						+ ". Souhaitez-vous que je vous donne mon avis sur un film ou que je vous en propose un ?";
			}
			
		case DEBUT:
			
			if (Reconnaissance.salutation(messageCorrige)) // Si le message contient une salutation
			{
				return "Besoin d'un conseil ?";
			} 
			else if (Reconnaissance.avisFilm(messageCorrige) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvé
			{
				film = Reconnaissance.avisFilm(messageCorrige);
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return "Je connais ce film.";
			} 
			else if (Reconnaissance.avis(messageCorrige)) // Si il ne contient qu'une demande d'avis sur un film mais qu'il ne nous dit pas le film
			{
				prochaineEtape = Etape.AVIS;
				return "Sur quel film voulez-vous un avis ?";
			} 
			else if (Reconnaissance.recherche(messageCorrige)) // Si l'utilisateur recherche un film inconnu
			{
				prochaineEtape = Etape.PROPOSITION;
				return "Avez-vous un genre particulier de film que vous souhaitez regarder ?";
			} 
			else // Si rien n'est reconnu
			{
				return "Besoin d'un conseil ?";
			}
			
			
		case AVIS:
			//Recherche du film contenu dans le message / demande du film duquel il veut un avis
			if(!b_film)
			{
				return " Vous voulez mon avis sur quel film ?";
			}
			
			//On dit qui est le réalisateur et on met l'affiche, qui joue dedans etc.
			
			//Avis en fonction de la note des utilisateurs et de la presse
			
			break;
			
		case PROPOSITION:
			
			if (!b_genre) //L'utilisateur n'a pas r�pondu pour le genre
			{
				if (Reconnaissance.ouiOuNon(messageCorrige)==0) //L'utilisateur ne recherche pas de genre en particulier
				{
					b_genre = true; //Il faut plus demander
					return "Preferez-vous un film r�cent ?";
				}
				else if (Reconnaissance.genre(messageCorrige) != null)
				{
					b_genre = true; //Il faut plus demande
					s_genre = Reconnaissance.genre(messageCorrige);
					return "Film r�cent ?";
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//L'utilisateur a juste r�pondu oui ... il faut lui demander quel genre
					return "Quel genre de film ?";
				}
				else 
				{
					//On a pas compris (on a identifi� ni oui, ni non, ni un genre ...) 
					return "J'ai pas compris ...";
				}
			} 
			else if (!b_recent) //L'utilisateur n'a pas encore pr�cis� s'il voulait un film r�cent ou non
			{
				if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//Il veut un film r�cent
					s_recent = true;
					b_recent = true;
					if (s_genre != "")
						film = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre, true)));
					else 
						film = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(true)));
					
					prochaineEtape = Etape.AVIS;
					return "Je te propose le film "+film.titre();
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==0)
				{
					//Il veut pas forc�ment un film r�cent
					s_recent = false;
					b_recent = true;
					if (s_genre != "")
					{
						film = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre)));
						prochaineEtape = Etape.AVIS;
						return "Je te propose le film "+film.titre();
					}
					else 
					{
						return "Il y a un r�alisateur, un acteur que tu aimes bien ?";
					}
				}
				else //On a pas compris
				{
					return "Ca te d�range si le film est vieux ?";
				}
			}
			if (! b_realisateur)
			{
				if (Reconnaissance.acteurRealisateur(messageCorrige)!= null)
				{
					//On a trouv� un r�alisateur ou un acteur qui existe
					b_realisateur = true;
					s_realisateur = Reconnaissance.acteurRealisateur(messageCorrige);
					film = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur)));
					prochaineEtape = Etape.AVIS;
					return "Je te propose le film "+film+".";
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//le mec a juste dit oui
					return "Qui donc ?";
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==0)
				{
					film = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms()));
					prochaineEtape = Etape.AVIS;
					return "Je te propose le film "+film+".";
				}
				else 
				{
					return "Pas compris";
				}
			}
			
			
			
//			if (!b_genre && Reconnaissance.ouiOuNon(messageCorrige)==0) // Si l'utilisateur ne veut pas de genre précis
//			{
//				b_genre = false;
//				return "Preferez-vous un film r�cent ?";
//			}
//			if (b_genre && Reconnaissance.ouiOuNon(messageCorrige)==1 && Reconnaissance  ) // Si l'utilisateur veut un genre précis ou si on ne reconnait pas le genre donné par l'utilisateur
//			{
//				String genre = recherche.trouverGenre(messageCorrige);
//				if (genre != "")
//				{
//					retour = false;
//					s_genre = genre;
//				} else
//				{
//					retour = true;
//					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
//				}
//				return "Preferez-vous un film récent ?";
//			}
//			if (b_genre && b_sortie && motTrouves.contains("non")) // Si l'utilisateur veut un genre particulier mais pas de date de sortie précise
//			{
//				b_sortie = false;
//				proposition = true;
//				liste = ParseurAllocine.recupererFilms(s_genre);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (b_genre && b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur veut un genre et une date de sortie particuliers ou si on ne reconnait pas la date donnée par l'utilisateur
//			{
//				// Comment se passe la récupération de date ?
//				String date = recherche.trouverDate(messageCorrige);
//				if (date != "")
//				{
//					retour = false;
//					s_sortie = date;
//				} else
//				{
//					retour = true;
//					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
//				}
//
//				liste = ParseurAllocine.recupererFilms(s_genre,true);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (b_sortie && motTrouves.contains("non")) // Si l'utilisateur ne veut pas de genre ni de date de sortie précis
//			{
//				b_sortie = false;
//				return "Cherchez-vous un realisateur en particulier ?";
//			}
//			if (b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur ne veut pas de genre précis mais une date de sortie précise ou si on ne comprend pas la date entrée par l'utilisateur
//			{
//				// Comment se passe la récupération de date ?
//				String date = recherche.trouverDate(messageCorrige);
//				if (date != "")
//				{
//					retour = false;
//					s_sortie = date;
//				} else
//				{
//					retour = true;
//					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
//				}
//
//				liste = ParseurAllocine.recupererFilms(true);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (b_realisateur && (motTrouves.contains("non"))) // Si l'utilisateur ne veut pas de réalisateur précis
//			{
//				b_realisateur = false;
//				return "Peut-être recherchez-vous un acteur en particulier";
//			}
//			if (b_realisateur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un réalissateur particulier ou si on ne comprend pas le realisateur entré par l'utilisateur
//			{
//				String realisateur = recherche.trouverPersonne(messageCorrige);
//				if (realisateur != "")
//				{
//					retour = false;
//					s_realisateur = realisateur;
//				} else
//				{
//					retour = true;
//					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
//				}
//				liste = ParseurAllocine.chercherFilmDePersonne(s_realisateur);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (b_acteur && motTrouves.contains("non")) // Si l'utilisateur ne veut pas d'acteur précis
//			{
//				b_acteur = false;
//				liste = ParseurAllocine.recupererFilms();
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (b_acteur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un acteur précis ou si on ne comprend pas l'acteur donné par l'utilisateur
//			{
//				String acteur = recherche.trouverPersonne(messageCorrige);
//				if (acteur != "")
//				{
//					retour = false;
//					s_acteur = acteur;
//				} else
//				{
//					retour = true;
//					return "Je n'ai pas bien compris, pouvez-vous reformuler ?";
//				}
//				liste = ParseurAllocine.chercherFilmDePersonne(s_realisateur);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
//			if (proposition && motTrouves.contains("non")) // On propose un film que l'utilisateur ne connait pas
//			{
//				prochaineEtape = Etape.AVIS;
//				// Le film est stocké dans film
//				break;
//			}
//			if (proposition && motTrouves.contains("oui")) // On propose un film que l'utilisateur connait
//			{
//				satisfaction = true;
//				return "L'avez-vous aimé ?";
//			}
//			if (satisfaction && motTrouves.contains("non")) // On propose un film que l'utilisateur n'aime pas
//			{
//				prochaineEtape = Etape.AVIS;
//				// Le film est stocké dans film
//				break;
//			}
//			if (satisfaction && motTrouves.contains("oui")) // On propose un film que l'utilisateur aime
//			{
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous déjà vu ?";
//			}
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
