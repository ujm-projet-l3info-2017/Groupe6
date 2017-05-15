package ia;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import allocine.Film;
import allocine.ParseurAllocine;
import allocine.RechercheAllocine;

public class ConversationIA
{
	// Initialisation du log
	static final Logger logger = LogManager.getLogger(ConversationIA.class.getName());

	private String nom; // Nom de l'utilisateur
	private Etape prochaineEtape; // Prochaine étape à lancer
	private RechercherMot recherche; // Pour rechercher des mots dans le message
	private String messageCorrige; // Message après passage par le dictionnaire
	private String messageOrigine; //Message sans correction
	
	//Pour proposition
	private boolean b_realisateur, b_recent, b_genre; // Devient false si l'utilisateur ne s'y interesse pas
	
	//Est-ce qu'on a fix� un film sur lequel donner son avis?
	private boolean b_film;
	
	private String s_realisateur, s_genre, titreFilm; // Contient les choix de l'utilisateur
	private boolean s_recent;
	//private String film; // Film selectionné
	private Film film; //le film dont on est en train de parler 

	public ConversationIA(String nom)
	{
		this.nom = nom;
		b_realisateur = false;
		b_recent = false;
		b_genre = false;
		b_film = false;
		s_realisateur = "";
		titreFilm = "";
		s_recent = false;
		s_genre = "";
		film = null;
		prochaineEtape = Etape.SALUTATION;
	}

	public String traitementMessage(String message)
	{
		recherche = new RechercherMot();
		messageCorrige = recherche.analysePhrase(message);
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
				return salut.aleatoire() + " " + nom + ". " +ReponseAleatoire.jeConnaisFilm();
			} 
			else if (Reconnaissance.avis(messageCorrige)) // Si il ne contient qu'une demande d'avis sur un film mais qu'il ne nous dit pas le film
			{
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom + ". " + ReponseAleatoire.avisSurQuelFilm();
			} 
			else if (Reconnaissance.recherche(messageCorrige)) // Si l'utilisateur recherche un film inconnu
			{
				prochaineEtape = Etape.PROPOSITION;
				return salut.aleatoire() + " " + nom + ". " + ReponseAleatoire.questionGenre();
			} 
			else // Si rien n'est reconnu
			{
				prochaineEtape = Etape.DEBUT;
				return salut.aleatoire() + " " + nom + ". " + ReponseAleatoire.queVeuxTu();
			}
		
			
		case DEBUT:
			
			if (Reconnaissance.salutation(messageCorrige)) // Si le message contient une salutation
			{
				return ReponseAleatoire.queVeuxTu();
			} 
			else if (Reconnaissance.avisFilm(messageCorrige) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvé
			{
				film = Reconnaissance.avisFilm(messageCorrige);
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return ReponseAleatoire.jeConnaisFilm();
			} 
			else if (Reconnaissance.avis(messageCorrige)) // Si il ne contient qu'une demande d'avis sur un film mais qu'il ne nous dit pas le film
			{
				prochaineEtape = Etape.AVIS;
				return ReponseAleatoire.avisSurQuelFilm();
			} 
			else if (Reconnaissance.recherche(messageCorrige)) // Si l'utilisateur recherche un film inconnu
			{
				prochaineEtape = Etape.PROPOSITION;
				return ReponseAleatoire.questionGenre();
			} 
			else // Si rien n'est reconnu
			{
				return ReponseAleatoire.queVeuxTu();
			}
			
			
			
		case AVIS:
			//Il veut un avis mais il n'a pas fix� de film
			if(!b_film)
			{
				return ReponseAleatoire.avisSurQuelFilm();
			}
			//Il a fix� un film
			if(b_film)
			{
				
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
					return ReponseAleatoire.questionAnnee();
				}
				else if (Reconnaissance.genre(messageCorrige) != null)
				{
					b_genre = true; //Il faut plus demande
					s_genre = Reconnaissance.genre(messageCorrige);
					return ReponseAleatoire.questionAnnee();
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//L'utilisateur a juste r�pondu oui ... il faut lui demander quel genre
					return ReponseAleatoire.questionGenre();
				}
				else 
				{
					//On a pas compris (on a identifi� ni oui, ni non, ni un genre ...) 
					return ReponseAleatoire.incomprehension();
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
						titreFilm = tirageAleatoire(ParseurAllocine.recupererFilms(s_genre, true));
					else 
						titreFilm = tirageAleatoire(ParseurAllocine.recupererFilms(true));
					
					prochaineEtape = Etape.CONNAIT_OU_PAS;
					return ReponseAleatoire.proposeLeFilm()+titreFilm+" "+ReponseAleatoire.connaisQuestion2();
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==0)
				{
					//Il veut pas forc�ment un film r�cent
					s_recent = false;
					b_recent = true;
					if (s_genre != "")
					{
						titreFilm = tirageAleatoire(ParseurAllocine.recupererFilms(s_genre));
						prochaineEtape = Etape.CONNAIT_OU_PAS;
						return ReponseAleatoire.proposeLeFilm()+titreFilm+". "+ReponseAleatoire.connaisQuestion2();
					}
					else 
					{
						return ReponseAleatoire.questionCriterePersonne();
					}
				}
				else //On a pas compris
				{
					return ReponseAleatoire.questionAnnee();
				}
			}
			if (! b_realisateur)
			{
				if (Reconnaissance.acteurRealisateur(messageOrigine)!= null)
				{
					//On a trouv� un r�alisateur ou un acteur qui existe
					b_realisateur = true;
					s_realisateur = Reconnaissance.acteurRealisateur(messageOrigine);
					titreFilm = tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur));
					prochaineEtape = Etape.CONNAIT_OU_PAS;
					return ReponseAleatoire.proposeLeFilm()+titreFilm+" "+ReponseAleatoire.connaisQuestion2();
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//le mec a juste dit oui
					return ReponseAleatoire.qui();
				}
				else if (Reconnaissance.ouiOuNon(messageCorrige)==0)
				{
					b_realisateur = true;
					titreFilm = tirageAleatoire(ParseurAllocine.recupererFilms());
					prochaineEtape = Etape.CONNAIT_OU_PAS;
					return ReponseAleatoire.proposeLeFilm()+titreFilm+" "+ReponseAleatoire.connaisQuestion2();
				}
				else 
				{
					return ReponseAleatoire.incomprehension();

				}
			}
			else 
			{
				return ReponseAleatoire.incomprehension(); //NORMALEMENT ON EST JAMAIS ICI
			}
		
		case CONNAIT_OU_PAS :
			
			if (Reconnaissance.ouiOuNon(messageCorrige) == 1)
			{
				//Aie aie aie, l'utilisateur connait le film qu'on lui a propos�
				film = chercherFilmAvecMemeCaracteristiques();
				return "Je te propose le film "+film.titre()+". Tu le connais celui la ?";
			}
			else
			{
				film = RechercheAllocine.film(titreFilm); //On cr�e l'objet Film uniquement si on va en avoir bespon
				prochaineEtape = Etape.AVIS;
				return "Est ce que tu veux des informations sur ce film ?";
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
	
	private Film chercherFilmAvecMemeCaracteristiques()
	{
		Film leFilmActuel = RechercheAllocine.film(titreFilm);
		if (s_genre != null)
		{
			if (s_recent)
			{
				Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre, true)));
				while (f.code() == leFilmActuel.code())
				{
					//Tant qu'il est pas diff�rent de celui d'avant
					f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre, true)));
				}
				return f;			
			}
			else 
			{
				Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre)));
				while (f.code() == leFilmActuel.code())
				{
					//Tant qu'il est pas diff�rent de celui d'avant
					f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre)));
				}
				return f;
			}
		}
		else 
		{
			if (s_recent)
			{
				Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(true)));
				while (f.code() == leFilmActuel.code())
				{
					//Tant qu'il est pas diff�rent de celui d'avant
					f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(true)));
				}
				return f;			
			}
			else 
			{
				if (s_realisateur != null)
				{
					Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur)));
					while (f.code() == leFilmActuel.code())
					{
						//Tant qu'il est pas diff�rent de celui d'avant
						f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur)));
					}
					return f;
				}
				else
				{
					Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms()));
					while (f.code() == leFilmActuel.code())
					{
						//Tant qu'il est pas diff�rent de celui d'avant
						f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms()));
					}
					return f;
				}
			}
		}
	}
}
