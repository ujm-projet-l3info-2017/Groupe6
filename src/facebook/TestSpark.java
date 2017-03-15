package facebook;

import static spark.Spark.*;

import spark.Spark;

public class TestSpark
{
    public static void main(String[] args)
    {

    	Spark.port(4567);
    	post("/test", (request, response) -> request.toString()+"Recu POST"+request.body());
//    	Spark.
    }
}