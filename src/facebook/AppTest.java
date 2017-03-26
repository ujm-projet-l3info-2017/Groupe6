package facebook;

public class AppTest
{
	public static void main(String args[])
	{
		PageRazbot razbot = new PageRazbot();
		
		System.out.println(razbot.existeMessagesNonLu());
		System.out.println(razbot.nombreMessagesNonLu());
		
		System.out.println(razbot.derniereConversationNonLu().getId());
		
//		razbot.envoyerMessage("t_mid.1456287448561:f59d1b19d211d78343", "Test");
	}
}