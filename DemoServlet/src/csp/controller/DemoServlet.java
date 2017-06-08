package csp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csp.model.Node;
import csp.model.Trie;


public class DemoServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    protected List<String> myList;
    protected Trie myTrie;
	
    public DemoServlet() 
    {
        super();
        myTrie = new Trie();
        myTrie.Init(); //Intialise on Global Data Set
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		String strLine = "";
		for(int i = 0;i<myList.size();i++)
		{
			if(i==0)
				strLine = myList.get(i);
			else
				strLine = strLine + " " + myList.get(i);
		}
		response.getWriter().write(strLine);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/plain");
		String command = request.getParameter("inputText");
		Node temp = myTrie.insertAndGetNode(command);
		if(temp.children.isEmpty()!=true)
			myList = myTrie.returnPredictedList(temp);
		else
			myTrie.writeToUserFile(command);
	}
}
