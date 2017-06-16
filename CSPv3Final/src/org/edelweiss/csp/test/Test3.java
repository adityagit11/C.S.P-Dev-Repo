package org.edelweiss.csp.test;

import java.util.Random;
import java.util.Scanner;

import org.edelweiss.csp.model.FinalBag;

public class Test3 
{
	public static void main(String args[])
	{
		FinalBag csp = new FinalBag();
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
