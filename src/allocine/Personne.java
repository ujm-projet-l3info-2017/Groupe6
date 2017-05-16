package allocine;

import java.util.List;

import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.FilmographyInfos;
import com.moviejukebox.allocine.model.Participance;
import com.moviejukebox.allocine.model.PersonInfos;
import com.moviejukebox.allocine.model.ShortPerson;

public class Personne
{
	private ShortPerson personne;
	private PersonInfos p;
	private FilmographyInfos filmographie;
	private List<Participance> participance;
	
	public Personne(ShortPerson personne)
	{
		this.personne = personne;
		String code = String.valueOf(personne.getCode());
		try
		{
			p = new RechercheAllocine("").a.getPersonInfos(code);
			filmographie = new RechercheAllocine("").a.getPersonFilmography(code);
			participance = filmographie.getParticipances();
		}
		catch (AllocineException e)
		{
			System.out.println("Les informations sur cette personne n'ont pas été trouvées");
		}
	}
	
	public String film()
	{
		return participance.get(0).getTitle();
	}
	
	
}
