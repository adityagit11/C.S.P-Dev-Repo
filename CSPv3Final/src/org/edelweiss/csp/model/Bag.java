package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bag 
{
	public Map<String, String> bag;
	
	public Bag()
	{
		bag = new HashMap<>();
	}
	
	public void Init()
	{
		//Init("dataRepo/USblog.txt");
		Init("dataRepo/UStwitter.txt");
		//Init("dataRepo/UStwitter.txt");
	}
	public void Init(String pathToFile)
	{
		try
		{
			String eachLine = "";
			String path = pathToFile;
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			int count = 0;
			while((eachLine = readDataLine.readLine()) != null)
			{
				eachLine = eachLine.toLowerCase();
				submitSentence(eachLine);
				//only UStwitter.txt has 500000 data access
				if(count == 500000)
				{
					System.out.println(eachLine);
					break;
				}
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
		try
		{
			//String path = "tempDataRepo/USblogdata.txt";
			//String path = "tempDataRepo/USnewsdata.txt";
			String path = "tempDataRepo/UStwitterdata.txt";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Map.Entry<String, String> eachEntry : bag.entrySet())
			{
				bw.write(eachEntry.getKey()+"->"+eachEntry.getValue()+" \n");
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
	
	/*****************************************************************************/
	
	public void submitTrace()
	{
		for(Map.Entry<String, String> eachEntry : bag.entrySet())
			submitTrace(eachEntry.getKey(), eachEntry.getValue());
	}
	private void submitTrace(String key, String trace)
	{
		int maxCount = 7;
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
			if(wordAr[eachEntry.getValue()-1]!=null)
				wordAr[eachEntry.getValue()-1] += " " + eachEntry.getKey();
			else
				wordAr[eachEntry.getValue()-1] = eachEntry.getKey();
		}
		int count = 0;
		trace = null;
		for(int i = wordAr.length-1;i>=0;i--)
		{
			if(count==maxCount)
				break;
			if(wordAr[i]!=null)
			{
				if(trace!=null)
					trace = trace + " " + wordAr[i];
				else
					trace = wordAr[i];
				count++;
			}
		}
		bag.replace(key, trace);
	}
}
