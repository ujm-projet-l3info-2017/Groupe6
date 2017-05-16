package ia;

import java.util.Random;

public class ReponseAleatoire
{
	private static String aleatoireTableau(String[] tableau)
	{
		Random rand = new Random();
		int nbAleatoire = rand.nextInt(tableau.length);
		return tableau[nbAleatoire];
	}
	
    public static String jadore()
    {
    	String[] choix = {
    			"C'est l'un de mes films pr�f�r�s ! Je l'ai vu au moins 5 fois.",
    			"J'aime beaucoup ce film.",
    			"J'ai ador� ce film.",
    			"Ce film est g�nial !",
    			"Ce film est un indispensable.",
    			"Oui ce film est vraiment g�nial !"};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaime()
    {
    	String[] choix = {
    			"J'ai bien aim� ce film.",
    			"Je l'ai trouv� pas mal.",
    			"J'ai pass� un bon moment.",
    			"J'ai aim�.",
    			"Je le reverrai sans soucis.",
    			"J'ai vu mieux mais c'�tait pas mal."};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaimePas()
    {
		String[] choix = {
				"Je n'ai pas trop aim�.",
				"A mon avis, ce film n'est pas top.",
				"A vrai dire j'ai vu mieux.",
				"Je ne l'aime pas.",
				"C'est un film plut�t inint�r�ssant."};

    	return aleatoireTableau(choix);
    }
    public static String jeDeteste()
    {
		String[] choix = {
				"Je n'ai pas du tout aim�.",
				"Je me suis ennuy� � mourir.",
				"Ce film est ennuyeux.",
				"Quel mauvais film !",
				"Je l'ai d�test�.",
				"Je te d�conseille ce film.",
				"Un des films les plus nuls que j'ai vu..."};	

    	return aleatoireTableau(choix);
    }
   
    public static String jeConnaisPas()
    {
		String[] choix = {
				"Je ne connais pas.",
				"J'en ai entendu parl� mais je ne l'ai pas encore vu.",
				"Je ne connais pas du tout."};

    	return aleatoireTableau(choix);	
    }
    
    public static String questionFermee()
    {
		String[] choix = {
				"Comment l'as-tu trouv� ?",
				"Tu l'as bien aim� ?",
				"Tu l'as trouv� bien ?"};

    	return aleatoireTableau(choix); 	    
    }
    public static String questionOuverte()
    {
		String[] choix = {
				"Pourquoi ?",
				"Qu'en as-tu pens� ?",
				"Peux-tu m'en dire plus ?"};

    	return aleatoireTableau(choix);   	    
    }
    public static String propositionTab()
    {
		String[] choix = {
				"Sinon je te propose le film ",
				"Je peux aussi te proposer le film ",
				"Dans le meme genre, il y a aussi le film "};

    	return aleatoireTableau(choix);  	    
    }
    public static String connaisQuestion()
    {
		String[] choix = {
				"Tu le connais toi ?",
				"Tu l'as d�j� vu toi ?",
				"Et toi tu l'as vu ?"};

    	return aleatoireTableau(choix);    
    }
    public static String connaisQuestion2()
    {
		String[] choix = {
				"Tu connais ?",
				"Tu l'as d�j� vu ?",
				"Tu l'as vu ?",
				"T'as d�j� vu ce film ?"};

    	return aleatoireTableau(choix);
    }
    public static String incomprehension()
    {
		String[] choix = {
				"Je n'ai pas compris ta r�ponse. ",
				"Ce n'est pas clair ... ",
				"Tu peux reformuler ? ",
				"Je ne comprends pas ce que tu dis ..."};

    	return aleatoireTableau(choix);	    
    }
    public static String jeConseille()
    {
		String[] choix = {
				"Je te conseille ce film.",
				"Je suis s�r que tu va aimer.",
				"Je le regarderais � ta place.",
				"Regarde le, tu ne le regretteras pas !"};

    	return aleatoireTableau(choix);  
    }
    public static String jeConseillePas()
    {
		String[] choix = {
				"Je ne te le conseille pas ...",
				"Je ne le regarderais pas � ta place.",
				"Tu risques de perdre ton temps si tu le regardes."};

    	return aleatoireTableau(choix); 
    }
    public static String questionGenre()
    {
		String[] choix = {
				"Tu veux regarder quel genre de film ?",
				"Il y a un genre de film en particulier que tu voudrais voir ?",
				"Quel genre de film pr�f�res-tu ?",
				"As-tu un genre favori ?",
				"Quel genre de film ?"};

    	return aleatoireTableau(choix);    	    
    }
    public static String questionAnnee()
    {
		String[] choix = {
				"Ca te d�range si le film est vieux ?",
				"Ca te d�range si le film n'est pas tr�s r�cent ?",
				"Pref�res-tu un film r�cent ?"};

    	return aleatoireTableau(choix);  	    
    }
    public static String questionDuree()
    {
		String[] choix = {"Est-ce que �a te d�range si le film est long ?"};

    	return aleatoireTableau(choix);	    
    }
    public static String questionGenreUnique()
    {
		String[] choix = {"Ce film appartient � quel genre ?"};

    	return aleatoireTableau(choix);    
    }
    public static String questionAnneeUnique()
    {
		String[] choix = {"Il est r�cent ce film ?"};

    	return aleatoireTableau(choix);  
    }
    public static String questionDureeUnique()
    {
		String[] choix = {"Est-ce qu'il est court ? Des fois je n'ai pas vraiment le temps de regarder des films trop long ..."};

    	return aleatoireTableau(choix);     	    
    }
    public static String queVeuxTu()
    {
		String[] choix = {	
				"Pourquoi as-tu besoin de moi ?",
				"Que puis-je faire pour toi ?",
				"Dis moi ce que je peux faire pour t'aider.",
				"Si tu as besoin d'un conseil sur un film, dis moi. ",
				"Souhaites-tu que je te donne mon avis sur un film ou que je t'en en propose un ?"};

    	return aleatoireTableau(choix);	    
    }
    public static String bonjour()
    {
    	String[] choix = {
    			"Bonjour",
    			"Salut",
    			"Hey",
    			"Content de te parler"};
    	
    	return aleatoireTableau(choix);
    }
    public static String aurevoir()
    {
		String[] choix = {"A bient�t", "Au revoir", "Salut"};

    	return aleatoireTableau(choix);    	    
    }
	public static String proposeLeFilm(String titre)
	{
		String[] choix = {
				"Je te propose le film "+titre+".",
				"Le film "+titre+" est vraiment bien.",
				titre+" est un film que j'ai beaucoup aim�.",
				"Je peux te proposer le film "+titre};

    	return aleatoireTableau(choix);
	}
	
	public static String proposeLeFilmMemeCritere(String titre)
	{
		String[] choix = {
				"Dans le m�me genre il y a le film "+titre+".",
				"Le film "+titre+" est plus ou moins dans le m�me genre.",
				titre+" est un peu dans le m�me genre."};

    	return aleatoireTableau(choix);
	}
	

	public static String proposeLeFilmMemeEpoque(String titre)
	{
		String[] choix = {
				"Le film "+titre+" est sorti il y a pas longtemps, il devrait te plaire.",
				"Il y a aussi "+titre+" qui est sorti r�cemment."};

    	return aleatoireTableau(choix);
	}

	public static String jeConnaisFilm()
	{
		String[] choix = {
				"Je connais ce film.",
				"Je me souviens de ce film.",
				"J'ai vu ce film.",
				"Je vois de quel film tu parles."};

    	return aleatoireTableau(choix);
	}
	
	public static String avisSurQuelFilm()
	{
		String[] choix = {
				"Tu as besoin d'un avis sur quel film ?",
				"Tu veux mon avis sur quel film ?",
				"Tu veux que je te dise ce que je pense de quel film ?"};

    	return aleatoireTableau(choix);
	}

	public static String questionCriterePersonne()
	{
		String[] choix = {
				"Il y a un r�alisateur, un acteur que tu aimes bien ?",
				"Tu pr�f�re un r�alisateur ou un acteur particulier ?",
				"Des pr�f�rences sur un r�alisateur ou un acteur ?"};

    	return aleatoireTableau(choix);
	}

	public static String qui()
	{
		String[] choix = {
				"Qui donc ?",
				"De qui parles-tu ?",
				"Qui ?"};

    	return aleatoireTableau(choix);
	}

	public static String jaimeMaisPasPresse()
	{
		String[] choix = {
				"La presse ne lui a pas donn� de bonnes notes, moi je l'ai bien aim�, mais ce n'est que mon avis...",
				"La presse n'en a pas fait une bonne critique mais je l'ai quand m�me aim�.",
				"Je pense que c'est un bon film, m�me si la presse n'est pas d'accord."};

    	return aleatoireTableau(choix);
	}

}
