package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class FinalBag 
{
	public HashMap<String, String> bag;
	
	public FinalBag()
	{
		bag = new HashMap<>();
	}
	
	public void Init()
	{
		String path = "finalData/prodata.txt";
		//String path = "finalData/prodatacount.txt";
		//String path = "finalData/prodata3.txt";
		//String path = "finalData/prodata5.txt";
		//String path = "finalData/prodata7.txt";
		Init(path);
	}
	private void Init(String path)
	{
		try
		{
			String eachLine = "";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			while((eachLine = readDataLine.readLine()) != null)
			{
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
	
	public String getValue(String key)
	{
		String value = null;
		if(bag.containsKey(key))
			value = bag.get(key);
		else
			return "";
		return getAny3(value);
	}
	private String getAny3(String value)
	{
		/*
		Random rn = new Random();
		int ans = rn.nextInt(10);
		System.out.println(ans);
		*/
		Random rn = new Random();
		String[] valAr = value.split(" ");
		if(valAr.length<=3)
		{
			return value;
		}
		else
		{
			String result = null;
			int count = 0;
			while(count!=3)
			{
				int anyValue = rn.nextInt(valAr.length);
				if(valAr[anyValue]!="")
				{
					if(result==null)
						result = valAr[anyValue];
					else
						result += " "+valAr[anyValue];
					valAr[anyValue] = "";
					count++;
				}	
			}
			return result;
		}
	}
	
	public String getWholeValue(String key)
	{
		return bag.get(key);
	}
}
