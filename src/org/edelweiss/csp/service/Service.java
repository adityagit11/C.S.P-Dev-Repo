package org.edelweiss.csp.service;

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

import org.edelweiss.csp.model.Bag;

//->http://localhost:8080/CSPv3Web/REST/CALL

@WebListener
@SuppressWarnings("serial")
@Path("/CALL")
public class Service extends HttpServlet implements ServletContextListener
{
	@javax.ws.rs.core.Context 
	private ServletContext context;
	
	private Bag csp;
	private String result;
	
	@Override
	public void contextInitialized(ServletContextEvent event) 
	{
		System.out.println("Uploading.... CSPv3.0");
		
		//String path = "/Res/DataSet/prodata.txt";
		//String path = "/Res/DataSet/prodata3.txt";
		//String path = "/Res/DataSet/prodata5.txt";
		String path = "/Res/DataSet/prodata7.txt";
		//String path = "/Res/DataSet/prodatacount.txt";

		/*
		 * ******IMPORTANT NOTICE************
		 * To use dataset prodata7.txt 
		 * you should always use getRValues() method only
		 * 
		 * To use dataset prodatacount.txt
		 * you shoudl always use getPredictedValues() method only*/
		
		context = event.getServletContext();
		String datasetPath = context.getRealPath(path);
		
		csp = new Bag();
		csp.Init(datasetPath);
		context.setAttribute("csp", csp);
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String postRequest(String command, @Context HttpServletRequest request,
			@Context HttpServletResponse response)
	{
		/*
		 * There are 2 forms of String command (or user can give input)
		 * POSTMAN WAY: String command = "will" or "i will be"
		 * HTML5 INPUT way: String command = "input=will" or "input=i will be"
		 * */
		command = command.toLowerCase();
		command = command.split("=")[1];
		command = command.split(" ")[command.split(" ").length-1];
		
		csp = (Bag)context.getAttribute("csp");
		result = csp.getRValues(command);
		context.setAttribute("result", result);
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRequest(@Context HttpServletRequest request,
			@Context HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		
		result = (String)context.getAttribute("result");
		return result;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		//context is destroyed
		//write to user file
	}
}
