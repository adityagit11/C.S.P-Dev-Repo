package org.edelweiss.cspspark.service;

import static spark.Spark.*;

import java.util.List;

import org.edelweiss.cspspark.model.Node;
import org.edelweiss.cspspark.model.Trace;

import spark.Request;
import spark.Response;
import spark.Route;

public class TraceServiceSpark 
{
	private static Trace trace;
	private static Node temp;
	private static List<String> myList;
	private static String result;
	
	public static void main(String[] args)
	{

		//This is required for initialising Trace
		put("/cspspark/put", new Route(){

			public Object handle(Request req, Response res) 
			{
				trace = new Trace();
				trace.Init();
				req.session().attribute("trace-obj", trace);
				return "Successfully Connected";
			}
						
		});
		
		post("/cspspark/post", new Route(){

			public Object handle(Request req, Response res) 
			{
				Trace trace = (Trace)req.attribute("trace-obj");
				String command = req.body();
				temp = trace.insertAndGetNode(command);
				if(temp.getMap().isEmpty() != true)
				{
					myList = trace.returnPredictedStringList(temp);
					for(int i = 0;i<myList.size();i++)
					{
						if(i==0)
							result = result + myList.get(i);
						else
							result = result + " " + myList.get(i);
					}
					req.session().attribute("result-string", result);
				}
				else
					trace.writeToUserFile(command);
				return "Success POST";
			}
			
		});
		
		get("/cspspark/get", new Route(){

			public Object handle(Request req, Response res) 
			{
				result = req.session().attribute("result-string");
				return result;
			}
			
		});
	}
}
