package org.edelweiss.csp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Bag 
{
public HashMap<String, String> bag;
	
	public Bag()
	{
		bag = new HashMap<>();
	}
	
	/*
	 * This below method
	 * is used by Controller and Co-Java Application*/
	public void Init()
	{
		// For dataset prodata7.txt use getRValues() method only
		// For dataset prodatacount.txt use getPredictedValues() method only
		
		//String path = "E:\\JavaTech\\Edelweiss\\CSPv3Web\\finalData\\prodata.txt";
		//String path = "E:\\JavaTech\\Edelweiss\\CSPv3Web\\finalData\\prodatacount.txt";
		//String path = "E:\\JavaTech\\Edelweiss\\CSPv3Web\\finalData\\prodata3.txt";
		//String path = "E:\\JavaTech\\Edelweiss\\CSPv3Web\\finalData\\prodata5.txt";
		String path = "E:\\JavaTech\\Edelweiss\\CSPv3Web\\finalData\\prodata7.txt";
		
		
		//String path = "finalData/prodata.txt";
		//String path = "finalData/prodatacount.txt";
		//String path = "finalData/prodata3.txt";
		//String path = "finalData/prodata5.txt";
		//String path = "finalData/prodata7.txt";
		
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
	
	/*
	 * This below method
	 * is accessed by REST API only*/
	public void Init(String path)
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
	
	/*
	 * This below method
	 * returns the most prominent values
	 * to the user's previous word*/
	public String getPredictedValues(String key)
	{
		if(bag.containsKey(key))
		{
			String alpha="", beta="", gamma="";
			int max = 0;
			
			String[] valueAr = bag.get(key).split(" ");
			
			for(int i = valueAr.length-1;i>=0;i--)
			{
				String[] wordFreq = valueAr[i].split("::");
				int val = Integer.parseInt(wordFreq[1]);
				if(val>=max)
				{
					gamma = beta;
					beta = alpha;
					alpha = wordFreq[0];
					max = val;
				}
			}
			return alpha+" "+beta+" "+gamma;
		}
		else
		{
			//write to local dataset
			return "";
		}
		
	}
	
	/*
	 * This below method
	 * returns any number of random Strings
	 * but made of most prominent words used after the key */
	public String getRValues(String key)
	{
		String value = null;
		if(bag.containsKey(key))
			value = bag.get(key);
		else
			return "";
		return getR3Values(value);
	}
	private String getR3Values(String value)
	{
		/*
		 * Generates random 3 values
		 * from a set of 7 most prominent words
		 * which are selected for global datasets*/
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
	
	/*
	 * This below method
	 * returns the whole Value of the key entered by the user*/
	public String getWholeValue(String key)
	{
		return bag.get(key);
	}
}
