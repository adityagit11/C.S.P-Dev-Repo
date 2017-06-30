package org.edelweiss.aditya.csp.service;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.edelweiss.aditya.csp.model.DataInvasion;
import org.edelweiss.aditya.csp.model.DocumentParse;
import org.edelweiss.aditya.csp.model.MongoPatch;

//->http://localhost:8080/CSPv4/REST/CALL

@WebListener
@SuppressWarnings("serial")
@Path("/CALL")
public class Service extends HttpServlet implements ServletContextListener
{
	@javax.ws.rs.core.Context 
	private ServletContext context;
	
	private MongoPatch mongoPatch;
	private DataInvasion alienData;
	private Document doc;
	private String result;
	private String datasetPath;
	
	/****************************************************************************************************************************/
	
	@Override
	public void contextInitialized(ServletContextEvent event) 
	{
		System.out.println("Uploading.... CSPv4.0");
		
		String localDatasetPath = "/RES/DATA/dataset.txt";
		
		context = event.getServletContext();
		datasetPath = context.getRealPath(localDatasetPath);
		
		//create database "cspdb" and collection "cspcol".
		mongoPatch = new MongoPatch();
		
		//upload data
		//alienData = new DataInvasion(mongoPatch, datasetPath);
		//alienData.Init();
		
		context.setAttribute("mongo-client", mongoPatch);
	}
	
	/****************************************************************************************************************************/
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public void postRequest(String command, @Context HttpServletRequest request,
			@Context HttpServletResponse response)
	{
		/*
		 * There are 2 forms of String command (or user can give input)
		 * POSTMAN WAY: String command = "will" or "i will be"
		 * HTML5 INPUT way: String command = "input=will" or "input=i will be"
		 * */
		command = command.toLowerCase().trim();
		command = command.split("=")[1];
		
		mongoPatch = (MongoPatch)context.getAttribute("mongo-client");
		doc = mongoPatch.processInputString(command);
		
		result = DocumentParse.parseDoc(doc);
		
		context.setAttribute("result", result);
	}
	
	/****************************************************************************************************************************/
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRequest(@Context HttpServletRequest request,
			@Context HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		
		result = (String)context.getAttribute("result");
		
		if(result != null)
			return result;
		
		return null;
	}
	
	/****************************************************************************************************************************/
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		mongoPatch = (MongoPatch)context.getAttribute("mongo-client");
		mongoPatch.closeClient();
	}
	
}
