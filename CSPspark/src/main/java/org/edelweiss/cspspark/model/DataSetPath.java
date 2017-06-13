package org.edelweiss.cspspark.model;

public class DataSetPath 
{
	private String GlobalDataPath;
	
	private String LocalDataPath;
	
	public DataSetPath()
	{
		GlobalDataPath = "E:\\JavaTech\\Edelweiss\\CSPspark\\DataRepo\\GlobalSentenceDataSet.txt";
		
		LocalDataPath = "E:\\JavaTech\\Edelweiss\\CSPspark\\DataRepo\\LocalSentenceDataSet.txt";
	}
	
	/*
	 * Only administrator can write to this file
	 * READ only file*/
	public String getGlobalDataPath()
	{
		return GlobalDataPath;
	}
	
	public String getLocalDataPath()
	{
		return LocalDataPath;
	}
}
