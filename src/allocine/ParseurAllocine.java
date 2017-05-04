package allocine;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseurAllocine 
{	
	
	public static void testURL(String url)
	{
		try 
		{
			Document doc = Jsoup.connect(url).get();
			
			Elements film = doc.select("a");
			Elements films = film.select(".meta-title-link");
			for (int i=0; i<films.size();i++)
			{
				System.out.println(films.get(i).text());
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
