package org.edelweiss.csp.test;

import java.util.Scanner;

import org.edelweiss.csp.model.Bag;

public class Test 
{
	public static void main(String args[])
	{
		Bag csp = new Bag();
		csp.Init();
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter command!");
		String command = input.nextLine();
		while(command.equals("stop")!=true)
		{
			System.out.println(csp.getValue(command));
			System.out.println("Enter command!");
			command = input.nextLine();
		}
		input.close();
	}
}
