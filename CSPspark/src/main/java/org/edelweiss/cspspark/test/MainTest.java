package org.edelweiss.cspspark.test;

import java.util.List;
import java.util.Scanner;

import org.edelweiss.cspspark.model.Node;
import org.edelweiss.cspspark.model.Trace;

public class MainTest 
{
	public static void main(String args[])
	{
		Trace trace = new Trace();
		trace.Init();
		Scanner input = new Scanner(System.in);
		String command = "";
		List<String> myList = null;
		while(command.equalsIgnoreCase("stop") != true)
		{
			System.out.println("*****************************************");
			System.out.println("Enter sequence: ");
			command = input.nextLine();
			if(command.equalsIgnoreCase("PrintAll"))
				trace.printTrace();
			else
			{
				Node temp = trace.insertAndGetNode(command);
				if(temp.getMap().isEmpty() != true)
				{
					System.out.println("---------------Suggestions---------------");
					myList = trace.returnPredictedStringList(temp);
					for(int i = 0;i<myList.size();i++)
						System.out.println(myList.get(i));
				}
				else
					trace.writeToUserFile(command);
			}
		}
		input.close();
	}
}
