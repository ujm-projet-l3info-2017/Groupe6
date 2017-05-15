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
    			"C'est l'un de mes films préférés ! Je l'ai vu au moins 5 fois.",
    			"J'aime beaucoup ce film.",
    			"J'ai adoré ce film.",
    			"Ce film est génial !"};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaime()
    {
    	String[] choix = {
    			"J'ai bien aimé ce film.",
    			"Je l'ai trouvé pas mal.",
    			"J'avais passé un bon moment.",
    			"J'ai vu mieux mais c'était pas mal."};
    	
    	return aleatoireTableau(choix);
    }
    public static String jaimePas()
    {
		String[] choix = {
				"Je n'ai pas trop aimé.",
				"Ce film est pas top.",
				"A vrai dire j'ai vu mieux."};

    	return aleatoireTableau(choix);
    }
    public static String jeDeteste()
    {
		String[] choix = {
				"Je n'ai pas du tout aimé.",
				"Je me suis ennuyé à mourir.",
				"Un des films les plus nuls que j'ai vu..."};	

    	return aleatoireTableau(choix);
    }
    public static String jeConnaisPas()
    {
		String[] choix = {
				"Je ne connais pas.",
				"J'en ai entendu parlé mais je ne l'ai pas encore vu.",
				"Je ne connais pas du tout."};

    	return aleatoireTableau(choix);	
    }
    public static String questionFermee()
    {
		String[] choix = {
				"Tu l'as bien aimé ?",
				"Tu l'as trouvé bien ?"};

    	return aleatoireTableau(choix); 	    
    }
    public static String questionOuverte()
    {
		String[] choix = {
				"Pourquoi ?",
				"Qu'en as-tu pensé ?",
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
				"Tu l'as déjà vu toi ?",
				"Et toi tu l'as vu ?"};

    	return aleatoireTableau(choix);    
    }
    public static String connaisQuestion2()
    {
		String[] choix = {
				"Tu connais ?",
				"Tu l'as déjà vu ?",
				"Tu l'as vu ?",
				"T'as déjà vu ce film ?"};

    	return aleatoireTableau(choix);
    }
    public static String incomprehension()
    {
		String[] choix = {
				"Je n'ai pas compris ta réponse. ",
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
				"Tu préfères les films d'action ?, d'aventure ?, romantique ? ...",
				"Quel genre de film préfères-tu ?",
				"As-tu un genre favori ?",
				"Quel genre de film ?"};

    	return aleatoireTableau(choix);    	    
    }
    public static String questionAnnee()
    {
		String[] choix = {
				"Ca te dérange si le film est vieux ?",
				"Ca te dérange si le film n'est pas très récent ?",
				"Prefères-tu un film récent ?"};

    	return aleatoireTableau(choix);  	    
    }
    public static String questionDuree()
    {
		String[] choix = {"Est-ce que ça te dérange si le film est long ?"};

    	return aleatoireTableau(choix);	    
    }
    public static String questionGenreUnique()
    {
		String[] choix = {"Ce film appartient à quel genre ?"};

    	return aleatoireTableau(choix);    
    }
    public static String questionAnneeUnique()
    {
		String[] choix = {"Il est récent ce film ?"};

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
		String[] choix = {"A bientôt", "Au revoir", "Salut"};

    	return aleatoireTableau(choix);    	    
    }
	public static String proposeLeFilm()
	{
		String[] choix = {
				"Je te propose le film ",
				"J'ai ce film à te proposer :",
				"Je te conseil ce film :",
				"Je peux te proposer le film "};

    	return aleatoireTableau(choix);
	}

	public static String jeConnaisFilm()
	{
		String[] choix = {
				"Je connais ce film !",
				"Oui, je connais ce film là.",
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
				"Il y a un réalisateur, un acteur que tu aimes bien ?",
				"Des préférences sur un réalisateur ou un acteur ?"};

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
