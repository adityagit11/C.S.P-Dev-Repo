package org.edelweiss.csp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.edelweiss.csp.model.*;

@Path("/call")
public class TraceService 
{
	private Trace trace;
	private Node temp;
	private List<String> myList;
	private String res;
	
	public TraceService()
	{
		trace = new Trace();
		trace.Init();
		res = "";
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String postRequest(String command, @Context HttpServletRequest request)
	{
		temp = trace.insertAndGetNode(command);
		if(temp.getMap().isEmpty() != true)
		{
			myList = trace.returnPredictedStringList(temp);
			for(int i = 0;i<myList.size();i++)
			{
				if(i == 0)
					res = res + myList.get(i);
				else
					res = res + " " + myList.get(i);
			}
			HttpSession session = request.getSession();
			session.setAttribute("csp", res);
		}
		else
		{
			trace.writeToUserFile(command);
		}
		return res;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRequest(@Context HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		res = (String)session.getAttribute("csp");
		return res;
	}
}
