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
    			"Ce film est g�nial !"};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaime()
    {
    	String[] choix = {
    			"J'ai bien aim� ce film.",
    			"Je l'ai trouv� pas mal.",
    			"J'avais pass� un bon moment.",
    			"J'ai vu mieux mais c'�tait pas mal."};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaimePas()
    {
		String[] choix = {
				"Je n'ai pas trop aim�.",
				"Ce film est pas top.",
				"A vrai dire j'ai vu mieux."};

    	return aleatoireTableau(choix);
    }
    public static String jeDeteste()
    {
		String[] choix = {
				"Je n'ai pas du tout aim�.",
				"Je me suis ennuy� � mourir.",
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
				"Dans le meme genre, il y a aussi le film "};

    	return aleatoireTableau(choix);  	    
    }
    public static String connaisQuestion()
    {
		String[] choix = {
				"Tu connais toi ?",
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
				"Je ne comprends pas ce que tu dis.."};

    	return aleatoireTableau(choix);	    
    }
    public static String jeConseille()
    {
		String[] choix = {
				"Je te conseille ce film",
				"Regarde le, tu ne le regrettera pas !"};

    	return aleatoireTableau(choix);  
    }
    public static String jeConseillePas()
    {
		String[] choix = {
				"Je ne te le conseille pas ...",
				"Tu risques de perdre ton temps si tu le regardes."};

    	return aleatoireTableau(choix); 
    }
    public static String questionGenre()
    {
		String[] choix = {
				"Tu veux regarder quel genre de film ?",
				"Tu pr�f�res les films d'action ?, d'aventure ?, romantique ? ...",
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
		String[] choix = {"Est-ce qu'il est court ? Des fois j'ai pas trop le temps de regarder des films trop long ..."};

    	return aleatoireTableau(choix);     	    
    }
    public static String queVeuxTu()
    {
		String[] choix = {	
				"Pourquoi as-tu besoin de moi ?",
				"Besoin d'un conseil ?",
				"Que puis-je faire pour toi ?",
				"Dis moi ce que je peux faire pour t'aider ... ",
				"Si tu as besoin d'un conseil sur un film, dis moi. ",
				"Souhaites-tu que je te donne mon avis sur un film ou que je t'en en propose un ?"};

    	return aleatoireTableau(choix);	    
    }
    public static String aurevoir()
    {
		String[] choix = {"A bient�t", "Au revoir", "Salut"};

    	return aleatoireTableau(choix);    	    
    }
	public static String proposeLeFilm()
	{
		String[] choix = {
				"Je te propose le film ",
				"J'ai ce film � te proposer :",
				"Je te conseil ce film :",
				"Je peux te proposer le film "};

    	return aleatoireTableau(choix);
	}

	public static String jeConnaisFilm()
	{
		String[] choix = {
				"Je connais ce film !",
				"Oui, je connais ce film l�.",
				};

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
				"Des pr�f�rences sur un r�alisateur ou un acteur ?"};

    	return aleatoireTableau(choix);
	}

	public static String qui()
	{
		String[] choix = {
				"Qui donc ?",
				"Qui ?"};

    	return aleatoireTableau(choix);
	}
}
