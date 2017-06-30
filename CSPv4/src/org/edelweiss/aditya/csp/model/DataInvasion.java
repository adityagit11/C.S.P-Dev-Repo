package org.edelweiss.aditya.csp.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.bson.Document;

public class DataInvasion 
{
	public String datasetPath;
	//public final String path = "C:\\Users\\adityaas\\workspace\\CSPv4\\WebContent\\RES\\DATA\\dataset.txt";
	public MongoPatch mongoPatch;
	public Document doc;
	
	public DataInvasion(MongoPatch mongoPatch, String datasetPath)
	{
		this.mongoPatch = mongoPatch;
		this.datasetPath = datasetPath;
	}
	
	/****************************************************************************************************************************/
	
	public void Init()
	{
		try
		{
			String eachLine = "";
			FileReader dataSet = new FileReader(datasetPath);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			
			System.out.println("Uploading data....");
			
			while((eachLine = readDataLine.readLine()) != null)
			{
				doc = mongoPatch.convertStringToDocument(eachLine);
				mongoPatch.insertIntoCollection(doc);
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
	
	/****************************************************************************************************************************/
	
	/*
	//This below method converts prodatacount to dataset upto 10 value-count pairs
	public static void Init()
	{
		int MAX = 10;
		try
		{
			String eachLine = "";
			FileReader dataSet = new FileReader(path);
			BufferedReader readDataLine = new BufferedReader(dataSet);
			int count = 0;
			
			FileWriter fw = new FileWriter("data/dataset.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			while((eachLine = readDataLine.readLine()) != null)
			{
				String result = "";
				String[] keyValue = eachLine.split("->");
				String[] valueCount = keyValue[1].split(" ");
				
				result += keyValue[0] + "->";
				
				for(int i = 0;i<MAX&&i<valueCount.length;i++)
				{
					result+= valueCount[i] + " ";
				}
				
				result = result.trim();
				bw.write(result + "\n");
				//doc = mongoPatch.convertStringToDocument(eachLine);
				//mongoPatch.insertIntoCollection(doc);
				count++;
				System.out.println(count);
			}
			
			bw.close();
			fw.close();
			
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
	*/
}
