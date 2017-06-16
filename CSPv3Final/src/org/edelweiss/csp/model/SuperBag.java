package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SuperBag 
{
	HashMap<String, String> bag;
	
	public SuperBag()
	{
		bag = new HashMap<>();
	}
	
	public void Init()
	{
		try
		{
			String path = "tempDataRepo/data.txt";
			int count = 0;
			String eachLine = "";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
				count++;
				System.out.println("Inserting: "+count);
				String[] keyValue = eachLine.split("->");
				bag.put(keyValue[0], keyValue[1]);
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
	
	public void submitTrace()
	{
		for(Map.Entry<String, String> eachEntry : bag.entrySet())
			submitTrace(eachEntry.getKey(), eachEntry.getValue());
	}
	private void submitTrace(String key, String trace)
	{
		//int maxCount = 20;
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
			/*
			if(wordAr[eachEntry.getValue()-1]!=null)
				wordAr[eachEntry.getValue()-1] += " "+eachEntry.getKey()+"-$-"+eachEntry.getValue();
			else
				wordAr[eachEntry.getValue()-1] = eachEntry.getKey()+"-$-"+eachEntry.getValue();
			*/
			wordAr[eachEntry.getValue()-1] = eachEntry.getKey();
		}
		trace = null;
		int count = 0;
		for(int i = wordAr.length-1;i>=0;i--)
		{
			if(count==maxCount)
				break;
			if(wordAr[i]!=null)
			{
				if(trace!=null)
					trace += " "+wordAr[i];
				else
					trace = wordAr[i];
				count++;
			}
		}
		bag.replace(key, trace);
	}
	
	public void writeToProDataFile()
	{
		int count = 0;
		for(Map.Entry<String, String> eachEntry : bag.entrySet())
		{
			count++;
			System.out.println(count+" of "+bag.size());
			writeToProDataFile(eachEntry.getKey(), eachEntry.getValue());
		}
	}
	private void writeToProDataFile(String key, String value)
	{
		try
		{
			String path = "tempDataRepo/prodata7.txt";
			//String path = "tempDataRepo/prodata.txt";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(key+"->"+value+"\n");
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
	
	public String getValue(String key)
	{
		return bag.get(key);
	}
}
