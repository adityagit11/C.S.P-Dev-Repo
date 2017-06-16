package org.edelweiss.csp.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.edelweiss.csp.model.Bag;


@SuppressWarnings("serial")
@Path("/call")
public class Service extends HttpServlet
{
	private Bag csp;
	private String result;
	
	public Service()
	{
		csp = new Bag();
		csp.Init();
		result = "beta";
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String postRequest(String command, @Context HttpServletRequest request)
	{
		result = csp.getValue(command);
		HttpSession session = request.getSession();
		session.setAttribute("csp", result);
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRequest(@Context HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		result = (String)session.getAttribute("csp");
		return result;
	}
}
