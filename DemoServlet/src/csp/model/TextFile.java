package csp.model;

public class TextFile 
{
	private String GlobalFile = "E:\\JavaTech\\Edelweiss\\DemoServlet\\textRepo\\SentenceDataSet.txt";
	
	private String UserFile = "E:\\JavaTech\\Edelweiss\\DemoServlet\\textRepo\\UserDefinedSentenceDataSet.txt";
	
	public String getGlobalFile()
	{
		return GlobalFile;
	}
	
	public String getUserFile()
	{
		return UserFile;
	}
}
