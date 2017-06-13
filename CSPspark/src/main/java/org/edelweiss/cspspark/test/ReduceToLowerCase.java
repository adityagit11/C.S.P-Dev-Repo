package org.edelweiss.cspspark.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReduceToLowerCase 
{
	//file paths are empty
	public static String FilePathToRead = "E:\\JavaTech\\DataRepo\\GlobalSentenceDataSet.txt";
	public static String FilePathToWrite = "E:\\JavaTech\\DataRepo\\LocalSentenceDataSet.txt";
	public static void main(String[] args)
	{
		try
		{
			String eachLine = "";
			FileReader dataset = new FileReader(FilePathToRead);
			BufferedReader readDataLine = new BufferedReader(dataset);
			while((eachLine = readDataLine.readLine()) != null)
			{
				write(eachLine);
			}
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
	public static void write(String sentence)
	{
		sentence = sentence.toLowerCase();
		try
		{
			FileWriter fw = new FileWriter(FilePathToWrite, true);
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
}
