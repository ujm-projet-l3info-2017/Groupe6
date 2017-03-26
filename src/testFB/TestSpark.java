package testFB;

import static spark.Spark.*;

import java.util.Set;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

public class TestSpark
{
    public static void main(String[] args)
    {
//    	Spark.port(4567);
//    	Route route = (request, response) -> test(request, response);
//    	post("/webhook", route);
    }

	private static Object test(Request request, Response response)
	{
		Set<String> attributs = request.attributes();
		return "HTTP 200 OK";
	}
}
