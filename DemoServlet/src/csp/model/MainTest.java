package csp.model;

import java.util.List;
import java.util.Scanner;

public class MainTest 
{
	public static void main(String args[])
	{
		Trie myTrie = new Trie();
		myTrie.Init();
		Scanner input = new Scanner(System.in);
		String command = "";
		List<String> myList = null;
		while(command.equalsIgnoreCase("stop")!=true)
		{
			System.out.println("*****************************************");
			System.out.println("Enter sequence:");
			command = input.nextLine();
			if(command.equalsIgnoreCase("PrintAll"))
				myTrie.printWholeTrie();
			else
			{
				Node temp = myTrie.insertAndGetNode(command);
				if(temp.children.isEmpty()!=true)
				{
					System.out.println("---------------Suggestions---------------");
					myList = myTrie.returnPredictedList(temp);
					for(int i = 0;i<myList.size();i++)
						System.out.println(myList.get(i));
				}
				else
					myTrie.writeToUserFile(command);
			}
		}
		input.close();
	}
}
//my extension is 3634
//dharma prasad - 8264