package ia;

public class ReponseAleatoire
{
    public static String jadore()
    {
    	String[] choix = {"C'est l'un de mes films préférés ! Je l'ai vu au moins 5 fois.", "J'aime beaucoup ce film.", "J'ai adoré ce film.", "Ce film est génial !"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jaime()
    {
    	String[] choix = {"J'ai bien aimé ce film.", "Je l'ai trouvé pas mal.", "J'avais passé un bon moment.", "J'ai vu mieux mais c'était pas mal."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jaimepas()
    {
		String[] choix = {"Je n'ai pas trop aimé.", "Ce film est pas top.", "A vrai dire j'ai vu mieux."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jedeteste()
    {
		String[] choix = {"Je n'ai pas du tout aimé.", "Je me suis ennuyé à mourir.", "Un des films les plus nuls que j'ai vu..."};	
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jeconnaispas()
    {
		String[] choix = {"Je ne connais pas.", "J'en ai entendu parlé mais je ne l'ai pas encore vu.", "Je ne connais pas du tout."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
      		
    }
    public static String question_fermee()
    {
		String[] choix = {"Tu l'as bien aimé ?", "Tu l'as trouvé bien ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_ouverte()
    {
		String[] choix = {"Pourquoi ?", "Qu'en as-tu pensé ?","Peux-tu m'en dire plus ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String proposition_tab()
    {
		String[] choix = {"Sinon je te propose le film ","Dans le meme genre, il y a aussi le film "};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String connais_question()
    {
		String[] choix = {"Tu connais toi ?","Tu l'as déjà vu toi ?", "Et toi tu l'as vu ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String connais_question2()
    {
		String[] choix = {"Tu connais ?","Tu l'as déjà vu ?", "Tu l'as vu ?", "T'as déjà vu ce film ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String incomprehension()
    {
		String[] choix = {"Je n'ai pas compris ta réponse. ","Ce n'est pas clair ... ","Tu peux reformuler ? "};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String jeconseille()
    {
		String[] choix = {"Je te conseille ce film","Regarde le, tu ne le regrettera pas !"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String jeconseillepas()
    {
		String[] choix = {"Je ne te le conseille pas ...","Tu risques de perdre ton temps si tu le regardes."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_genre()
    {
		String[] choix = {"Tu veux regarder quel genre de film ?", "Tu préfères les films d'action ?, d'aventure ?, romantique ? ...", "Quel genre de film préfères-tu ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_annee()
    {
		String[] choix = {"Ca te dérange si le film est vieux ?", "Ca te dérange si le film n'est pas très récent ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_duree()
    {
		String[] choix = {"Est-ce que ça te dérange si le film est long ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_genre_unique()
    {
		String[] choix = {"Ce film appartient à quel genre ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_annee_unique()
    {
		String[] choix = {"Il est récent ce film ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_duree_unique()
    {
		String[] choix = {"Est-ce qu'il est court ? Des fois j'ai pas trop le temps de regarder des films trop long ..."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String questuveux()
    {
		String[] choix = {"Pourquoi as-tu besoin de moi ?", "Que puis-je faire pour toi ?", "Dis moi ce que je peux faire pour t'aider ... ", "Si tu as besoin d'un conseil sur un film, dis moi. "};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String proposelefilm()
    {
		String[] choix = {"Je peux te proposer le film "};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String message_bonjour()
    {
		String[] choix = {"Bonjour", "Salut"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String aurevoir()
    {
		String[] choix = {"A bientôt", "Au revoir", "Salut"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
}
