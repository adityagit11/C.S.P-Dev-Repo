package org.edelweiss.csp.test;

import org.edelweiss.csp.model.Bag;
import org.edelweiss.csp.model.ProBag;
import org.edelweiss.csp.model.SuperBag;

public class Test1 
{
	public static void main(String args[])
	{
		//Create a data.txt file
		
		/*
		Bag csp = new Bag();
		csp.Init();
		csp.writeKeyValueToFile();
		System.out.println("hey");
		*/
		
		//Step 1: we have to create a new dataset: prodata.txt
		//make a new class ProBag.java
		
		//ProBag probag = new ProBag();
		//probag.filter1();
		//probag.Init();
		//probag.writeToDataFile();
		
		
		//Step 4: Make a super bag and upload data.txt dataset to HashMap
		SuperBag csp = new SuperBag();
		csp.Init();
		System.out.println(csp.getValue("grill"));
	}
}
