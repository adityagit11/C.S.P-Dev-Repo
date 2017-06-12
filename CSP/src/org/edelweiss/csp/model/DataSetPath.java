package org.edelweiss.csp.model;

public class DataSetPath 
{
	private String GlobalDataPath;
	
	private String LocalDataPath;
	
	public DataSetPath()
	{
		GlobalDataPath = "DataRepo/GlobalSentenceDataSet.txt";
		
		LocalDataPath = "DataRepo/LocalSentenceDataSet.txt";
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
