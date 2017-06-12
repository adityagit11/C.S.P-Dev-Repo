package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trace 
{
	//This is the Root of Trace
	private final Node root;
	
	/*
	 * This below method
	 * is constructor for Trace class*/
	public Trace()
	{
		root = new Node();
	}
	
	/*
	 * This below method
	 * returns the root of the Trace class*/
	public Node getRoot()
	{
		return root;
	}
	
	/*
	 * This below method
	 * initiates the Trace class
	 * whice uploads the dataset to the Trace
	 * from Global and Local Datasets*/
	public void Init()
	{
		/**********************************************************************/
		//Data Upload
		DataSetPath pathObj = new DataSetPath();
		String globalPath = pathObj.getGlobalDataPath();
		String localPath = pathObj.getLocalDataPath();
		push(this, globalPath);
		push(this, localPath);
	}
	private void push(Trace myTrace, String path)
	{
		try
		{
			String eachLine = "";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
				myTrace.insertAndGetNode(eachLine);
			}
			readDataLine.close();
			dataSet.close();
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
	}
	
	/*
	 * This below method
	 * inserts and queries for next node
	 * simultaneously*/
	public Node insertAndGetNode(String sentence)
	{
		String[] wordAr = sentence.split(" ");
		return insertAndGetNode(root, wordAr);
	}
	private Node insertAndGetNode(Node root, String[] wordAr)
	{
		Node currentNode = root;
		for(int i = 0;i<wordAr.length;i++)
		{
			String currentWord = wordAr[i];
			Node temp = currentNode.getMap().get(currentWord);
			if(temp==null)
			{
				temp = new Node();
				currentNode.getMap().put(currentWord, temp);
			}
			else
			{
				temp.setCount(temp.getCount() + 1);
			}
			currentNode = temp;
		}
		return currentNode;
	}

	/*
	 * This below method
	 * writes the sentence to Local Dataset
	 * only if it is not found in the Global Dataset*/
	public void writeToUserFile(String sentence)
	{
		DataSetPath pathObj = new DataSetPath();
		String localPath = pathObj.getLocalDataPath();
		try
		{
			FileWriter fw = new FileWriter(localPath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sentence + "\n");
			bw.close();
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
	}
	
	/*
	 * This below method
	 * converts the Whole predicted List
	 * to 3 most prominent based on count/ freq words
	 * and returns them in a single space
	 * seperated Strings*/
	public String convertNodeToString(Node node)
	{
		return "";
	}
	
	/*
	 * This below method
	 * returns the predicted <String> list*/
	public List<String> returnPredictedStringList(Node node)
	{
		List<String> wordList = new ArrayList<>();
		return returnPredictedStringList(node, wordList);
	}
	private List<String> returnPredictedStringList(Node node, List<String> wordList)
	{
		Map<String, Node> children = node.getMap();
		for(Map.Entry<String, Node> eachString : children.entrySet())
		{
			wordList.add(eachString.getKey());
		}
		return wordList;
	}
	
	/*
	 * This below method
	 * returns the predicted <Node> list*/
	public List<Node> returnPredictedNodeList(Node node)
	{
		List<Node> nodeList = new ArrayList<>();
		return returnPredictedNodeList(node, nodeList);
	}
	private List<Node> returnPredictedNodeList(Node node, List<Node> nodeList)
	{
		Map<String, Node> children = node.getMap();
		for(Map.Entry<String, Node> eachNode : children.entrySet())
		{
			nodeList.add(eachNode.getValue());
		}
		return nodeList;
	}
	
	/*
	 * This below method
	 * prints the whole Trace on console
	 * This method is recommended for Console
	 * and not recommended for GUI implementation*/
	public void printTrace()
	{
		printTrace(root, "");
	}
	private void printTrace(Node current, String res)
	{
		if(current.getMap().isEmpty())
			System.out.println(res);
		for(Map.Entry<String, Node> entry : current.getMap().entrySet())
		{
			if(res == "")
				printTrace(entry.getValue(), res + entry.getKey());
			else
				printTrace(entry.getValue(), res + " " + entry.getKey());
		}
	}
}
