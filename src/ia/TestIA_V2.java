package ia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestIA_V2
{

	public static void main(String[] args)
	{
		ConversationIA_V2 convIA = new ConversationIA_V2("Jacquie");

		String message = "Salut";
		String reponse;
		
		System.out.println("Test de l'IA V2, commencez la conversation:");

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
