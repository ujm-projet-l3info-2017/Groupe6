package facebook;

import java.util.ArrayList;

public interface Vocabulaire{
	
	static ArrayList<String> expressions = new ArrayList<String>();
	
	public int getLength();
	
	public String aleatoire();
}
