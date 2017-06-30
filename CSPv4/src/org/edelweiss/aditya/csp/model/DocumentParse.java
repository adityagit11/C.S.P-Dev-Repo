package org.edelweiss.aditya.csp.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

//MAIN OBJECTIVE: To extract string from this:
//Document{{be=1, have=1, not=1, never=1, get=1, you=1, do=1, always=1}}

/*
 * ALGORITHM MODEL: 4 CLASS: 3 
 * Date: 6/30/2017
 * Author: Aditya Singh
 * Designation: Intern
 * Organisation: Edelweiss
 * */

public class DocumentParse 
{
	
	/****************************************************************************************************************************/
	
	public static String parseDoc(Document doc)
	{
		if(doc==null)
			return null;
		
		String docString = doc.toString();
		String extractString = "";
		for(int i = 10;i<docString.length()-2;i++)
			extractString += docString.charAt(i);
		
		String[] valueCountAr = extractString.split(", ");
		
		String alphaString = null, betaString = null, gammaString = null;
		int alphaNum = 0, betaNum = 0, gammaNum = 0;
		
		HashMap<String, Integer> myHash = new HashMap<>();
		
		for(int i = 0;i<valueCountAr.length;i++)
		{
			String[] valueCountTemp = valueCountAr[i].split("=");
			myHash.put(valueCountTemp[0], Integer.parseInt(valueCountTemp[1]));
		}
		
		for(Map.Entry<String, Integer> each : myHash.entrySet())
		{
			if(each.getValue()>alphaNum)
			{
				gammaString = betaString;
				betaString = alphaString;
				alphaString = each.getKey();
				
				gammaNum = betaNum;
				betaNum = alphaNum;
				alphaNum = each.getValue();
			}
			else if(each.getValue()>betaNum)
			{
				gammaString = betaString;
				betaString = each.getKey();
				
				gammaNum = betaNum;
				betaNum = each.getValue();
			}
			else if(each.getValue() > gammaNum)
			{
				gammaString = each.getKey();
				
				gammaNum = each.getValue();
			}
		}
		
		extractString = alphaString + " " + betaString + " " + gammaString;
		return extractString;
	}
	
	/****************************************************************************************************************************/
	
}
