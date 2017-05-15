package ia;

import java.util.ArrayList;
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
	private Etape prochaineEtape; // Prochaine Ã©tape Ã  lancer
	private RechercherMot recherche; // Pour rechercher des mots dans le message
	private String messageCorrige; // Message aprÃ¨s passage par le dictionnaire
	private String messageOrigine; //Message sans correction
	
	//Pour proposition
	private boolean b_realisateur, b_recent, b_genre; // Devient false si l'utilisateur ne s'y interesse pas
	
	//Est-ce qu'on a fixé un film sur lequel donner son avis?
	private boolean b_film;
	
	private String s_realisateur, s_genre, titreFilm; // Contient les choix de l'utilisateur
	private boolean s_recent;
	//private String film; // Film selectionnÃ©
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
		case SALUTATION:  //PremiÃ¨re Ã©tape. Lors du premier message de l'utilisateur, on dit bonjour
			
			Salutations salut = new Salutations(); //On dit bonjour
			if (Reconnaissance.salutation(messageCorrige)) // Si le message contient une salutation
			{
				prochaineEtape = Etape.DEBUT;
				return salut.aleatoire() + " " + nom + ".";
			} 
			else if (Reconnaissance.avisFilm(messageCorrige) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvÃ©
			{
				film = Reconnaissance.avisFilm(messageCorrige);
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return salut.aleatoire() + " " + nom + ". " +ReponseAleatoire.jeConnaisFilm()+" Qu'est ce que tu veux savoir ?";
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
			else if (Reconnaissance.avisFilm(messageCorrige) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvÃ©
			{
				film = Reconnaissance.avisFilm(messageCorrige);
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return ReponseAleatoire.jeConnaisFilm()+" Qu'est ce que tu veux savoir ?";
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
			//Il veut un avis mais il n'a pas fixé de film
			if(!b_film)
			{
				film = Reconnaissance.reconnaitreFilm(messageCorrige);
				if (film == null)
				{
					return "J'ai pas compris de quel film tu me parles";
				}
				else 
				{
					b_film = true;
					return "Que veux tu savoir sur ce film ?";
				}
			}
			//Il a fixé un film
			if(b_film)
			{
				if (Reconnaissance.realisateur(messageCorrige))
					return "Ce film a été réalisé par "+film.realisateur();
				
				if (Reconnaissance.affiche(messageCorrige))
					return "Voici l'affiche de ce film : "+film.affiche();
				
				if (Reconnaissance.annee(messageCorrige))
					return "Ce film est sorti en "+film.affiche();
				
				if (Reconnaissance.acteurs(messageCorrige))
				{
					List<String> acteurs = film.acteursPrincipaux();
					if (acteurs != null)
						return "Les acteurs principaux sont "+acteurs.get(0)+ " et "+acteurs.get(1)+".";
					else 
						return "Je ne me rappelle plus des acteurs de ce film, désolé ...";
				}
				
				if (Reconnaissance.leGenre(messageCorrige))
				{
					String genre = film.genres().get(0);
					if (genre.charAt(0)=='a' || genre.charAt(0)=='h' || genre.charAt(0)=='é')
						return "C'est un film d'"+genre;
					else 
						return "C'est un film de "+genre;
				}
				
				if (Reconnaissance.synopsis(messageCorrige))
				{
					String synopsis = film.synopsis();
					if (synopsis == null)
						return "Je ne me souviens plus de l'histoire exacte, je te laisse le regarder.";
					else 
						return synopsis;
				}
				
				if (Reconnaissance.bien(messageCorrige))
				{
					if (film.note_public() > 4)
					{
						return "Oui ce film est vraiment génial !";
					}
					if (film.note_presse() < 3)
					{
						return "La presse ne lui a pas donné de bonnes notes, moi je l'ai bien aimé, mais ce n'est que mon avis...";
					}
					else
						return "Oui ça va, c'est pas un chef d'oeuvre mais on passe un bon moment";
				}
				else 
				{
					return "Je n'ai pas compris";
				}
			}
			
			
		case PROPOSITION:
			
			if (!b_genre) //L'utilisateur n'a pas répondu pour le genre
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
					//L'utilisateur a juste répondu oui ... il faut lui demander quel genre
					return ReponseAleatoire.questionGenre();
				}
				else 
				{
					//On a pas compris (on a identifié ni oui, ni non, ni un genre ...) 
					return ReponseAleatoire.incomprehension();
				}
			} 
			else if (!b_recent) //L'utilisateur n'a pas encore précisé s'il voulait un film récent ou non
			{
				if (Reconnaissance.ouiOuNon(messageCorrige)==1)
				{
					//Il veut un film récent
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
					//Il veut pas forcément un film récent
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
					//On a trouvé un réalisateur ou un acteur qui existe
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
				//Aie aie aie, l'utilisateur connait le film qu'on lui a proposé
				film = chercherFilmAvecMemeCaracteristiques();
				return "Je te propose le film "+film.titre()+". Tu le connais celui la ?";
			}
			else
			{
				film = RechercheAllocine.film(titreFilm); //On crée l'objet Film uniquement si on va en avoir bespon
				prochaineEtape = Etape.VOULOIR_INFOS;
				return "Est ce que tu veux des informations sur ce film ?";
			}
			
		case VOULOIR_INFOS :
			
			if (Reconnaissance.ouiOuNon(messageCorrige) == 1)
			{
				//L'utilisateur veut des infos, on passe dans l'état AVIS
				prochaineEtape = Etape.AVIS;
				return "Qu'est ce que tu veux savoir ?";
			}
			else if (Reconnaissance.ouiOuNon(messageCorrige) == 2)
			{
				//L'utilisateur veut pas en sevoir plus
				prochaineEtape = Etape.DEBUT;
				return "Si tu as besoin d'autre chose, n'hésite pas...";
			}
			else 
			{
				return "Je peux te donner le synopsis, te dire ce que les gens en ont pensé, les acteurs principaux ...";
			}
			
			
			
			
			
			
//			if (!b_genre && Reconnaissance.ouiOuNon(messageCorrige)==0) // Si l'utilisateur ne veut pas de genre prÃ©cis
//			{
//				b_genre = false;
//				return "Preferez-vous un film récent ?";
//			}
//			if (b_genre && Reconnaissance.ouiOuNon(messageCorrige)==1 && Reconnaissance  ) // Si l'utilisateur veut un genre prÃ©cis ou si on ne reconnait pas le genre donnÃ© par l'utilisateur
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
//				return "Preferez-vous un film rÃ©cent ?";
//			}
//			if (b_genre && b_sortie && motTrouves.contains("non")) // Si l'utilisateur veut un genre particulier mais pas de date de sortie prÃ©cise
//			{
//				b_sortie = false;
//				proposition = true;
//				liste = ParseurAllocine.recupererFilms(s_genre);
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (b_genre && b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur veut un genre et une date de sortie particuliers ou si on ne reconnait pas la date donnÃ©e par l'utilisateur
//			{
//				// Comment se passe la rÃ©cupÃ©ration de date ?
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
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (b_sortie && motTrouves.contains("non")) // Si l'utilisateur ne veut pas de genre ni de date de sortie prÃ©cis
//			{
//				b_sortie = false;
//				return "Cherchez-vous un realisateur en particulier ?";
//			}
//			if (b_sortie && (motTrouves.contains("oui") || retour)) // Si l'utilisateur ne veut pas de genre prÃ©cis mais une date de sortie prÃ©cise ou si on ne comprend pas la date entrÃ©e par l'utilisateur
//			{
//				// Comment se passe la rÃ©cupÃ©ration de date ?
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
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (b_realisateur && (motTrouves.contains("non"))) // Si l'utilisateur ne veut pas de rÃ©alisateur prÃ©cis
//			{
//				b_realisateur = false;
//				return "Peut-Ãªtre recherchez-vous un acteur en particulier";
//			}
//			if (b_realisateur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un rÃ©alissateur particulier ou si on ne comprend pas le realisateur entrÃ© par l'utilisateur
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
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (b_acteur && motTrouves.contains("non")) // Si l'utilisateur ne veut pas d'acteur prÃ©cis
//			{
//				b_acteur = false;
//				liste = ParseurAllocine.recupererFilms();
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (b_acteur && ((motTrouves.contains("oui")) || retour)) // Si l'utilisateur veut un acteur prÃ©cis ou si on ne comprend pas l'acteur donnÃ© par l'utilisateur
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
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
//			if (proposition && motTrouves.contains("non")) // On propose un film que l'utilisateur ne connait pas
//			{
//				prochaineEtape = Etape.AVIS;
//				// Le film est stockÃ© dans film
//				break;
//			}
//			if (proposition && motTrouves.contains("oui")) // On propose un film que l'utilisateur connait
//			{
//				satisfaction = true;
//				return "L'avez-vous aimÃ© ?";
//			}
//			if (satisfaction && motTrouves.contains("non")) // On propose un film que l'utilisateur n'aime pas
//			{
//				prochaineEtape = Etape.AVIS;
//				// Le film est stockÃ© dans film
//				break;
//			}
//			if (satisfaction && motTrouves.contains("oui")) // On propose un film que l'utilisateur aime
//			{
//				film = RechercheAllocine.film(tirageAleatoire(liste));
//				Recommandation recom = new Recommandation();
//				return recom.aleatoire() + " " + film.titre() + ". L'avez-vous dÃ©jÃ  vu ?";
//			}
		default:
			logger.error("IA", "Etape non reconnue");
			return null;
		}
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
					//Tant qu'il est pas différent de celui d'avant
					f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre, true)));
				}
				return f;			
			}
			else 
			{
				Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms(s_genre)));
				while (f.code() == leFilmActuel.code())
				{
					//Tant qu'il est pas différent de celui d'avant
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
					//Tant qu'il est pas différent de celui d'avant
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
						//Tant qu'il est pas différent de celui d'avant
						f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur)));
					}
					return f;
				}
				else
				{
					Film f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms()));
					while (f.code() == leFilmActuel.code())
					{
						//Tant qu'il est pas différent de celui d'avant
						f = RechercheAllocine.film(tirageAleatoire(ParseurAllocine.recupererFilms()));
					}
					return f;
				}
			}
		}
	}
}
