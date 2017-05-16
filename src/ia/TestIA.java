package ia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestIA
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(TestIA.class.getName());

	public static void main(String[] args)
	{
		//Initialisation de l'IA de la conversation
		ConversationIA convIA = new ConversationIA("Dimitri");
		String message = "Salut";
		String reponse;
		
		System.out.println("Test de l'IA, commencez la conversation:");

	    try
	    {
	    	//Initialisation du flux entrant
	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	       
	        //Boucle de la conversation
	        for(;;)
			{
	        	System.out.print("(Moi) : ");
				message = bufferRead.readLine();
				reponse = convIA.traitementMessage(message);
				System.out.println("(IA) : "+reponse);
			}
	    }
	    catch(IOException e)
	    {
	        logger.catching(e);
	    }
		
	}

}
