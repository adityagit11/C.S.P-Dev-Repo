package org.edelweiss.aditya.csp.test;

import java.util.*;

import org.bson.Document;
import org.edelweiss.aditya.csp.model.DataInvasion;
import org.edelweiss.aditya.csp.model.MongoPatch;

/*
 * Now you start taking commands from the USER
 * FORMAT "STRING" STRING_LENGTH STRING.SPLIT(" ")_LENGTH
 * Case1: User presses space and a post is generated: 0 1
 * Case2: User enters "I" and presses space, No learning but value must be returned: I 1 1, WILL 4 1
 * Case3: User enters "I will" and presses space, Learn/ update for "I" and return value for "will": I WILL 6 2
 * Case4: User enters "I will kill" and presses space, Learn/ update for "will" and returns value for "kill": I WILL KILL 11 3
 * */

public class MongoTest1 
{
	public static void main(String args[])
	{
		MongoPatch newPatch = new MongoPatch();
		
		//These below codes only required for Data Insertion from TEXT file to DATABASE
		/*DataInvasion createCollection = new DataInvasion(newPatch);
		createCollection.Init();*/
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter command: ");
		String command = input.nextLine();
		while(command.equalsIgnoreCase("stop") != true)
		{
			Document doc = newPatch.processInputString(command);
			if(doc != null)
			{
				String result = doc.toJson();
				System.out.println(result);
			}
			
			System.out.println("Enter command: ");
			command = input.nextLine();
		}
		input.close();
		
		newPatch.closeClient();
	}
}
