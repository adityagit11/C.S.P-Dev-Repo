package TestDay14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Node
{
	public String data;
	public Node next;
	public String repo;
	
	public Node(String data)
	{
		this.data = data;
		next = null;
		repo = "";
	}
}

class List
{
	private Node head;
	
	public List()
	{
		head = null;
	}
	
	public Node getHead()
	{
		return head;
	}
	
	public void Init()
	{
		try
		{
			String eachLine = "";
			String path = "dataset/GlobalSentenceDataSet.txt";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
				submitSentence(eachLine);
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
	
	public void insertNode(String data)
	{
		Node newNode = new Node(data);
		if(head==null)
			head = newNode;
		else
		{
			newNode.next = head;
			head = newNode;
		}
	}
	
	public void printList()
	{
		Node temp = head;
		while(temp!=null)
		{
			System.out.print(temp.data + " ->");
			temp = temp.next;
		}
		System.out.println();
	}
	
	public void submit(String key, String value)
	{
		Node temp = head;
		while(temp!=null&&temp.data.equalsIgnoreCase(key)!=true)
			temp = temp.next;
		if(temp==null)
		{
			insertNode(key);
			head.repo += " " + value;
		}
		else
			temp.repo += " " + value;
	}
	
	public void submitSentence(String sentence)
	{
		String[] wordAr = sentence.split(" ");
		for(int i = 0;i<wordAr.length-1;i++)
		{
			submit(wordAr[i], wordAr[i+1]);
		}
	}
	
	public void printRepo(String data)
	{
		Node temp = head;
		while(temp!=null&&temp.data.equalsIgnoreCase(data)!=true)
			temp = temp.next;
		if(temp==null)
			System.out.println("Node Not Found");
		else
			System.out.println(temp.repo);
	}
}

public class Test1 
{

	public static void main(String[] args) 
	{
		List myList = new List();
		myList.Init();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter command");
		String command = input.nextLine();
		while(command.equalsIgnoreCase("stop")!=true)
		{
			switch (command) 
			{
			case "a": 
				System.out.println("Enter sentence");
				String sentence = input.nextLine();
				myList.submitSentence(sentence);
				break;
			case "b":
				myList.printList();
				break;
			case "c":
				System.out.println("Enter Data");
				String data = input.nextLine();
				myList.printRepo(data);
				break;
			default:
				break;
			}
			System.out.println("Enter command");
			command = input.nextLine();
		}
		input.close();
	}

}
