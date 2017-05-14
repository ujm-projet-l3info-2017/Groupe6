package ia;

public class ReponseAleatoire
{
    public static String jadore()
    {
    	String[] choix = {"C'est l'un de mes films pr�f�r�s ! Je l'ai vu au moins 5 fois.", "J'aime beaucoup ce film.", "J'ai ador� ce film.", "Ce film est g�nial !"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jaime()
    {
    	String[] choix = {"J'ai bien aim� ce film.", "Je l'ai trouv� pas mal.", "J'avais pass� un bon moment.", "J'ai vu mieux mais c'�tait pas mal."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jaimepas()
    {
		String[] choix = {"Je n'ai pas trop aim�.", "Ce film est pas top.", "A vrai dire j'ai vu mieux."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jedeteste()
    {
		String[] choix = {"Je n'ai pas du tout aim�.", "Je me suis ennuy� � mourir.", "Un des films les plus nuls que j'ai vu..."};	
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
    }
    public static String jeconnaispas()
    {
		String[] choix = {"Je ne connais pas.", "J'en ai entendu parl� mais je ne l'ai pas encore vu.", "Je ne connais pas du tout."};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
      		
    }
    public static String question_fermee()
    {
		String[] choix = {"Tu l'as bien aim� ?", "Tu l'as trouv� bien ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_ouverte()
    {
		String[] choix = {"Pourquoi ?", "Qu'en as-tu pens� ?","Peux-tu m'en dire plus ?"};
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
		String[] choix = {"Tu connais toi ?","Tu l'as d�j� vu toi ?", "Et toi tu l'as vu ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String connais_question2()
    {
		String[] choix = {"Tu connais ?","Tu l'as d�j� vu ?", "Tu l'as vu ?", "T'as d�j� vu ce film ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String incomprehension()
    {
		String[] choix = {"Je n'ai pas compris ta r�ponse. ","Ce n'est pas clair ... ","Tu peux reformuler ? "};
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
		String[] choix = {"Tu veux regarder quel genre de film ?", "Tu pr�f�res les films d'action ?, d'aventure ?, romantique ? ...", "Quel genre de film pr�f�res-tu ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_annee()
    {
		String[] choix = {"Ca te d�range si le film est vieux ?", "Ca te d�range si le film n'est pas tr�s r�cent ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_duree()
    {
		String[] choix = {"Est-ce que �a te d�range si le film est long ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_genre_unique()
    {
		String[] choix = {"Ce film appartient � quel genre ?"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
    public static String question_annee_unique()
    {
		String[] choix = {"Il est r�cent ce film ?"};
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
		String[] choix = {"A bient�t", "Au revoir", "Salut"};
    	int rangAlea = (int) ((Math.random()*1000)%-2+choix.length)+1;
    	return choix[rangAlea];
        	    
    }
}
