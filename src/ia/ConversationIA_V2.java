package ia;

public class ConversationIA_V2
{
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
