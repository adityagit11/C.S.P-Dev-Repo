package TestDay14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Bag
{
	public Map<String, String> bag;
	
	public Bag()
	{
		bag = new HashMap<>();
	}
	
	public void Init()
	{
		try
		{
			String eachLine = "";
			String path = "dataset/GlobalSentenceDataSet2.txt";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			int count = 0;
			while((eachLine = readDataLine.readLine()) != null)
			{
				submitSentence(eachLine);
				if(count == 100000)
					break;
				count++;
				System.out.println(count);
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
	
	public void submitSentence(String sentence)
	{
		String[] wordAr = sentence.split(" ");
		for(int i = 0;i<wordAr.length-1;i++)
			submitKeyValue(wordAr[i], wordAr[i+1]);
	}
	
	public void submitKeyValue(String key, String value)
	{
		if(bag.containsKey(key))
		{
			String trace = bag.get(key);
			trace += " " + value;
			bag.remove(key);
			bag.put(key, trace);
		}
		else
			bag.put(key, value);
	}
	
	public String getValue(String key)
	{
		if(bag.containsKey(key))
			return bag.get(key);
		else
			return "Not Found!";
	}
	
	public void writeKeyValueToFile()
	{
		int count = 0;
		try
		{
			String path = "dataset/Key-ValueDataSet";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Map.Entry<String, String> eachEntry : bag.entrySet())
			{
				bw.write(eachEntry.getKey()+" -> "+eachEntry.getValue()+" \n");
				/*
				count++;
				System.out.println(count);
				*/
			}
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
	
	public void submitTrace()
	{
		for(Map.Entry<String, String> eachEntry : bag.entrySet())
			submitTrace(eachEntry.getKey(), eachEntry.getValue());
	}
	private void submitTrace(String key, String trace)
	{
		int maxCount = 5;
		String[] ar = trace.split(" ");
		HashMap<String, Integer> myHash = new HashMap<>();
		for(int i = 0;i<ar.length;i++)
		{
			if(myHash.containsKey(ar[i]))
			{
				myHash.replace(ar[i], myHash.get(ar[i])+1);
			}
			else
			{
				myHash.put(ar[i], 1);
			}
		}
		int max = -1;
		for(Map.Entry<String, Integer> eachEntry : myHash.entrySet())
		{
			if(eachEntry.getValue()>max)
				max = eachEntry.getValue();
		}
		String[] wordAr = new String[max];
		for(Map.Entry<String, Integer> eachEntry : myHash.entrySet())
		{
			wordAr[eachEntry.getValue()-1] = eachEntry.getKey();
		}
		int count = 0;
		trace = "";
		for(int i = wordAr.length-1;i>=0;i--)
		{
			if(count==maxCount)
				break;
			if(wordAr[i]!=null)
			{
				trace = trace + " " + wordAr[i];
				count++;
			}
		}
		bag.replace(key, trace);
	}
}

public class Test2 
{
	public static void main(String args[])
	{
		/*
		Date rightNow = new Date();
		long alpha = rightNow.getTime();
		Bag myBag = new Bag();
		myBag.Init();
		Date rightNow2 = new Date();
		long beta = rightNow2.getTime();
		System.out.println(beta-alpha);
		*/
		
		/*
		Bag bag = new Bag();
		bag.Init();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter command");
		String val = input.nextLine();
		while(val.equalsIgnoreCase("stop")!=true)
		{
			System.out.println(bag.getValue(val));
			System.out.println("Enter command");
			val = input.nextLine();
		}
		*/
		
		Bag csp = new Bag();
		csp.Init();
		csp.submitTrace();
		csp.writeKeyValueToFile();
		Scanner input = new Scanner(System.in);
		String val = input.nextLine();
		while(val.equalsIgnoreCase("stop") != true)
		{
			System.out.println(csp.getValue(val));
			System.out.println("Enter command");
			val = input.nextLine();
		}
		input.close();
	}
}
