package ia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConversationIA_V2
{
	//Initialisation du log
	static final Logger logger = LogManager.getLogger(ConversationIA_V2.class.getName());
		
	private String nom;
	private Etape prochaineEtape;
	
	public ConversationIA_V2(String nom)
	{
		this.nom = nom;
		prochaineEtape = Etape.DEBUT;
		executerProchaineEtape();
	}
	
	public String traitementMessage(String message)
	{
		return executerProchaineEtape();
	}
	
	public String executerProchaineEtape()
	{
		switch(prochaineEtape)
		{
		case DEBUT:
			
			break;
		case SALUTATION:
			
			break;
		case AVIS:
			
			break;
		case PROPOSITION:
			
			break;
		default:
			return null;
		}
		return null;
	}
}
