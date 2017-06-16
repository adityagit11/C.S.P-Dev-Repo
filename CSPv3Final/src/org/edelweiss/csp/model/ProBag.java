package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProBag
{
	//step 1: Remove those lines in data.txt whose key's data is not in ascii
	//		  and also those lines whose value array element number < 3
	//		  and also remove those lines whose keyValueAr.length>2
	public void filter1()
	{
		String dataset = "tempDataRepo/UStwitterdata.txt";
		filter1(dataset);
	}
	private void filter1(String pathToFile)
	{
		try
		{
			int count = 0;
			String eachLine = "";
			String path = pathToFile;
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
				count++;
				System.out.println(count);
				String[] keyValueAr = eachLine.split("->");
				String[] valueAr = keyValueAr[1].split(" ");
				if(keyValueAr.length==2&&valueAr.length>=3&&whetherAscii(keyValueAr[0]))
					writeToProData(eachLine);
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
	private boolean whetherAscii(String key)
	{
		for(int i = 0;i<key.length();i++)
			if(key.charAt(i)<97||key.charAt(i)>122)
				return false;
		return true;
	}
	private void writeToProData(String sentence)
	{
		try
		{
			String path = "tempDataRepo/UStwitterprodata.txt";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sentence+"\n");
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
	
	/********************************************************************/
	
	public HashMap<String, String> bag;
	
	public ProBag()
	{
		bag = new HashMap<>();
	}
	
	//step 2: Init the ProBag.java file which reads the prodata.txt
	//		  and inserts each sentence as key-value pair into bag
	public void Init()
	{
		Init("tempDataRepo/USblogprodata.txt");
		Init("tempDataRepo/USnewsprodata.txt");
		Init("tempDataRepo/UStwitterprodata.txt");
	}
	private void Init(String pathToFile)
	{
		try
		{
			String eachLine = "";
			String path = pathToFile;
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
				System.out.println("reading: "+pathToFile);
				String[] keyValue = eachLine.split("->");
				pushData(keyValue[0], keyValue[1]);
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
	private void pushData(String key, String value)
	{
		if(bag.containsKey(key))
		{
			bag.replace(key, bag.get(key)+" "+value);
		}
		else
			bag.put(key, value);
	}
	/*
	 * Step3: Write to the data.txt from USblogprodata.txt, USnewsprodata.txt
	 * and UStwitterprodata.txt*/
	public void writeToDataFile()
	{
		int count = 0;
		for(Map.Entry<String, String> eachEntry : bag.entrySet())
		{
			count++;
			System.out.println(count+" of "+bag.size());
			writeToDataFile(eachEntry.getKey(), eachEntry.getValue());
		}
	}
	private void writeToDataFile(String key, String value)
	{
		try
		{
			String path = "tempDataRepo/data.txt";
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
