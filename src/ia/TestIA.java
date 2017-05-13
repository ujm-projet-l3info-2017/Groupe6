package ia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class TestIA
{

	public static void main(String[] args)
	{
		Hashtable<String,ConversationIA> conversationsIA = new Hashtable<>();
		ConversationIA convIA = new ConversationIA("Jacquie");
		conversationsIA.put("a", convIA);
		String message = "Salut";
		String reponse;
		
		System.out.println("Test de l'IA, commencez la conversation:");

	    try
	    {
	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	       
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
	        e.printStackTrace();
	    }
		
	}

}
