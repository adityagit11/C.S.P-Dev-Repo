package csp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	
    public DemoServlet() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		response.setCharacterEncoding("UTF-8"); 
		String strLine = "";
		
		try
		{
			FileReader dataset = new FileReader("E:\\JavaTech\\Edelweiss\\DemoServlet\\WebContent\\tut.txt");
			BufferedReader readDataLine = null;
			readDataLine = new BufferedReader(dataset);
			strLine = readDataLine.readLine();
			/*
			 * Let's close the hatch*/
			readDataLine.close();
			dataset.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Unable to find the file");
		}
		catch(IOException e)
		{
			System.err.println("Unable to read the file");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		response.getWriter().write(strLine);       // Write response body.
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		String command = request.getParameter("inputText");
		Trie myTrie = new Trie();
		myTrie.Init();
		List<String> myList = null;
		Node temp = myTrie.insertAndGetNode(command);
		//Read the data from tut.txt
		if(temp.children.isEmpty()!=true)
		{
			myList = myTrie.returnPredictedList(temp);
			BufferedWriter bw = null;
			FileWriter fw = null;
			try
			{
				fw = new FileWriter("E:\\JavaTech\\Edelweiss\\DemoServlet\\WebContent\\tut.txt");
				bw = new BufferedWriter(fw);
				for(int i = 0;i<myList.size();i++)
				{
					bw.write(myList.get(i) + " ");
				}
				bw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			myTrie.writeToUserFile(command);
	}
}
