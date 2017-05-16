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
		this.nom = nom.split(" ")[0]; //Le prénom
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
		if (Reconnaissance.sortie(messageCorrige))
		{
			reset();
			return ReponseAleatoire.aurevoir();
		}
		return executerProchaineEtape();
	}
	
	public String executerProchaineEtape()
	{
		if(null != casSpeciaux())
			return casSpeciaux();
		switch (prochaineEtape)
		{
		case SALUTATION:  //PremiÃ¨re Ã©tape. Lors du premier message de l'utilisateur, on dit bonjour
			
			return ReponseAleatoire.bonjour() + " " + nom + ". " + depart(messageCorrige);
				
		case DEBUT:
			
			if (Reconnaissance.merci(messageCorrige))
				return ReponseAleatoire.deRien();
			else if (Reconnaissance.ok(messageCorrige))
				return ReponseAleatoire.smileyContent();
			else
				return depart(messageCorrige);
				
		case AVIS:

			if(!b_film)
			{
				film = Reconnaissance.reconnaitreFilm(messageOrigine);
				if (film == null)
				{
					return "J'ai pas compris de quel film tu me parles.";
				}
				else 
				{
					b_film = true;
					return "Que veux-tu savoir sur ce film ?";
				}
			}
			//Il a fixé un film
			if(b_film)
			{
				if (Reconnaissance.realisateur(messageCorrige))
					if (film.realisateur() == null)
						return ReponseAleatoire.saitPlusQuiFaitFilm();
					else 
						return "Ce film a été réalisé par "+film.realisateur()+".";
				
				if (Reconnaissance.affiche(messageCorrige))
					if (film.affiche() == null)
						return "Je n'ai pas trouvé l'affiche";
					else 
						return "Voici l'affiche de ce film : "+film.affiche();
				
				if (Reconnaissance.annee(messageCorrige))
					if (film.dateSortie() == 0)
						return ReponseAleatoire.saitPlusQuandFilmSorti();
					else return "Ce film est sorti en "+film.dateSortie()+".";
				
				if (Reconnaissance.acteurs(messageCorrige))
				{
					List<String> acteurs = film.acteursPrincipaux();
					if (acteurs != null)
						return "Les acteurs principaux sont "+acteurs.get(0)+ " et "+acteurs.get(1)+".";
					else 
						return ReponseAleatoire.saitPlusActeur();
				}
				
				if (Reconnaissance.leGenre(messageCorrige))
				{
					String genre = film.genres().get(0);
					if (genre.charAt(0)=='a' || genre.charAt(0)=='h' || genre.charAt(0)=='e')
						return "C'est un film d'"+genre+".";
					else 
						return "C'est un film de "+genre+".";
				}
				
				if (Reconnaissance.synopsis(messageCorrige))
				{
					String synopsis = film.synopsis();
					if (synopsis == null)
						return ReponseAleatoire.saitPlusHistoire();
					else 
						return synopsis;
				}
				
				if (Reconnaissance.avisPerso(messageCorrige))
				{
					if (film.note_presse() < 3 && film.note_public() > 3.5)
					{
						return ReponseAleatoire.jaimeMaisPasPresse();
					}
					if (film.note_public() > 4)
					{
						return ReponseAleatoire.jadore();
					}
					if (film.note_public() > 3)
					{
						return ReponseAleatoire.jaime();
					}
					if (film.note_public() > 2)
					{
						return ReponseAleatoire.jaimePas();
					}
					return ReponseAleatoire.jeDeteste();
					
				}
				else if (Reconnaissance.merci(messageCorrige) || Reconnaissance.ok(messageCorrige))
				{
					prochaineEtape = Etape.AUTRE_CHOSE;
					if (Reconnaissance.merci(messageCorrige))
						return "Je t'en prie. Tu as besoin d'autre chose ?"; 
					else
						return "Tu as besoin d'autre chose ?";
				}
				else 
				{
					return ReponseAleatoire.incomprehension();
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
					return ReponseAleatoire.proposeLeFilm(titreFilm)+" "+ReponseAleatoire.connaisQuestion2();
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
						return ReponseAleatoire.proposeLeFilm(titreFilm)+" "+ReponseAleatoire.connaisQuestion2();
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
					return ReponseAleatoire.proposeLeFilm(titreFilm)+" "+ReponseAleatoire.connaisQuestion2();
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
					return ReponseAleatoire.proposeLeFilm(titreFilm)+" "+ReponseAleatoire.connaisQuestion2();
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
				if (s_genre != "" || s_recent)
				{
					film = chercherFilmAvecMemeCaracteristiques();
					titreFilm = film.titre();
					if (s_genre != "")
						return ReponseAleatoire.proposeLeFilmMemeCritere(titreFilm)+". Tu le connais celui la ?";
					else return ReponseAleatoire.proposeLeFilmMemeEpoque(titreFilm)+". Tu l'as vu lui ?";
				}
				else if (s_realisateur != null) 
				{
					titreFilm = tirageAleatoire(ParseurAllocine.chercherFilmDePersonne(s_realisateur));
					film = RechercheAllocine.film(titreFilm);
					return ReponseAleatoire.proposeLeFilm(titreFilm)+". Tu le connais celui la ?";
				}
				else 
				{
					titreFilm = tirageAleatoire(ParseurAllocine.recupererFilms());
					film = RechercheAllocine.film(titreFilm);
					return ReponseAleatoire.proposeLeFilm(titreFilm)+". Tu le connais celui la ?";
				}
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
				b_film = true;
				film = RechercheAllocine.film(titreFilm);
				return "Qu'est ce que tu veux savoir ?";
			}
			else if (Reconnaissance.ouiOuNon(messageCorrige) == 0)
			{
				//L'utilisateur veut pas en sevoir plus
				prochaineEtape = Etape.DEBUT;
				effacerPreference();
				return "Si tu as besoin d'autre chose, n'hésite pas...";
			}
			else 
			{
				return "Tu veux des infos sur "+titreFilm+" ?";
			}
			
		case AUTRE_CHOSE :
			
			if (Reconnaissance.ouiOuNon(messageCorrige)==1)
			{
				prochaineEtape = Etape.DEBUT;
				effacerPreference();
				return "Dis moi tout.";
			}
			else
			{
				prochaineEtape = Etape.DEBUT;
				effacerPreference();
				return "Ok très bien. Je suis là si tu as besoin de moi.";
			}
			
		default:
			logger.error("Etape non reconnue");
			prochaineEtape = Etape.DEBUT;
			effacerPreference();
			return "Que veux tu ?";
		}
	}

	/**
	 *  Cas spéciaux, hors des étapes
	 * @return null si aucun cas ne correspond, la réponse sinon
	 */
	private String casSpeciaux()
	{
		if(messageCorrige.contains("c est pas faux"))
		{
			return "Qu'est-ce que t'as pas compris?";
		}
		if(messageCorrige.contains("dimitri"))
		{
			return "Dimitri c'est le plus fort";
		}
		if(messageCorrige.contains("un cine"))
		{
			return "Je ne saurais pas te conseiller de cinéma, je ne suis pas conçu pour, par contre je peux te conseiller un film si tu veux";
		}
		return null;
	}

	private void effacerPreference()
	{
		b_genre=false; b_film=false; b_realisateur=false; b_recent=false;
		s_genre=""; s_realisateur=""; s_recent=false;
	}

	/**
	 *  Donner un film au hasard dans une liste de film
	 * @param films
	 * @return
	 */
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
	
	public String depart(String messageCorrige)
	{
		if (Reconnaissance.genre(messageCorrige) != null)
		{
			//L'utilisateur cherche directement un film avec un genre précis
			prochaineEtape = Etape.PROPOSITION;
			b_genre = true;
			s_genre = Reconnaissance.genre(messageCorrige);
			return ReponseAleatoire.questionAnnee();
		}
		if (Reconnaissance.avisFilm(messageOrigine) != null) // Si il ne contient qu'une demande d'avis sur un film qu'on a trouvÃ©
		{
			film = Reconnaissance.avisFilm(messageOrigine);
			titreFilm = film.titre();
			b_film = true;
			prochaineEtape = Etape.AVIS;
			return ReponseAleatoire.jeConnaisFilm()+" Que veux tu savoir ?";
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
		else if (RechercheAllocine.film(messageOrigine) != null)
		{
			film = RechercheAllocine.film(messageOrigine);
			if (messageOrigine.toLowerCase().compareTo(film.titre().toLowerCase())==0)
			{
				//L'utilisateur a rentré le titre exacte d'un film
				titreFilm = film.titre();
				b_film = true;
				prochaineEtape = Etape.AVIS;
				return ReponseAleatoire.jeConnaisFilm()+" Que veux tu savoir ?";
			}
			else
			{
				film = null;
				prochaineEtape = Etape.DEBUT;
				return ReponseAleatoire.queVeuxTu();
			}
		}
		else // Si rien n'est reconnu
		{
			prochaineEtape = Etape.DEBUT;
			return ReponseAleatoire.queVeuxTu();
		}
	}
	
	public void reset()
	{
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
		recherche = null;
		messageCorrige = "";
		messageOrigine = "";
	}
}
