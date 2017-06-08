package csp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie 
{
	/*
	 * This below member variable
	 * is the default root node of Trie
	 * declared as the private member variable*/
	private final Node root;
	
	/*
	 * This below method 
	 * is the constructor of Trie class*/
	public Trie()
	{
		root = new Node();
	}
	
	/*
	 * This below method
	 * returns the root node of the Trie*/
	public Node getRoot()
	{
		return root;
	}
	
	/*
	 * This below method
	 * Initializes the Trie - Uploads Dataset into the Trie*/
	public void Init()
	{
		/********************************************************************/
		//Data Upload
		TextFile textFileObj = new TextFile();
		String GloablSentenceDataSetPath = textFileObj.getGlobalFile();
		try
		{
			FileReader dataset = new FileReader(GloablSentenceDataSetPath);
			BufferedReader readDataLine = null;
			String strLine = "";
			readDataLine = new BufferedReader(dataset);
			while((strLine = readDataLine.readLine()) != null)
			{
				this.insertAndGetNode(strLine);
			}
			
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
	}
	
	/*
	 * This below method
	 * Prints the whole Trie on the console
	 * Not recommented for actual GUI project*/
	public void printWholeTrie()
	{
		printWholeTrie(root, "");
	}
	private void printWholeTrie(Node current, String res)
	{
		if(current.children.isEmpty())
			System.out.println(res);
		for(Map.Entry<String, Node> entry : current.children.entrySet())
		{
			if(res=="")
				printWholeTrie(entry.getValue(), res + entry.getKey());
			else
				printWholeTrie(entry.getValue(), res + " " + entry.getKey());
		}
	}
	
	/*
	 * This below method
	 * inserts and predicts simultaneously
	 * It takes one parameter - String type Sentence
	 * the user entered and predicts the next word for the sentence.
	 * If the sentence entered is new then it only stores the sentece
	 * for future querying*/
	public Node insertAndGetNode(String sentence)
	{
		String[] wordAr = sentence.split(" ");
		return insertAndGetNode(root, wordAr);
	}
	private Node insertAndGetNode(Node root, String[] wordAr)
	{
		Node current = root;
		for(int i = 0;i<wordAr.length;i++)
		{
			Node temp = current.children.get(wordAr[i]);
			if(temp==null)
			{
				temp = new Node();
				current.children.put(wordAr[i], temp);
			}
			current = temp;
		}
		return current;
	}
	
	/*
	 * This below method
	 * returns the list of predicted words for
	 * the entered sentences
	 * It takes one param which is node and returns the 
	 * List of strings stored in the hash map of node*/
	public List<String> returnPredictedList(Node node)
	{
		List<String> wordList = new ArrayList<>();
		return returnPredictedList(node, wordList);
	}
	private List<String> returnPredictedList(Node node, List<String> wordList)
	{
		Map<String, Node> children = node.children;
		for(Map.Entry<String, Node> eachString : children.entrySet())
		{
			wordList.add(eachString.getKey());
		}
		return wordList;
	}

	/*
	 * This below method
	 * adds the sentence to a User text repository
	 * only if the sentence is not already present 
	 * in the global data set*/
	public void writeToUserFile(String sentence)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		TextFile textFileObj = new TextFile();
		String UserSentenceDataSetPath = textFileObj.getUserFile();
		try
		{
			fw = new FileWriter(UserSentenceDataSetPath, true);
			bw = new BufferedWriter(fw);
			bw.write(sentence + "\n");
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
