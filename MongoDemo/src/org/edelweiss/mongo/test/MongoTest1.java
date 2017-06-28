package org.edelweiss.mongo.test;

import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class MongoTest1 
{
	public static MongoClient mongoClient;
	public static MongoDatabase database;
	public static MongoCollection<Document> collection;
	
	static
	{
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("test");
		collection = database.getCollection("wolvie");
	}
	
	public static Document convertStringToDocument(String sentence)
	{
		//Sentence format: word->value1::count1 value2::count2 value3::count3
		String[] wordValueAr = sentence.split("->");
		String word = wordValueAr[0];
		String[] valueCountAr = wordValueAr[1].split(" ");
		int LENGTH = valueCountAr.length;
		String[] valueAr = new String[LENGTH];
		String[] countAr = new String[LENGTH];
		for(int i = 0;i<valueCountAr.length;i++)
		{
			valueAr[i] = valueCountAr[i].split("::")[0];
			countAr[i] = valueCountAr[i].split("::")[1];
		}
		return convertStringToDocument(word, valueAr, countAr, LENGTH);
	}
	private static Document convertStringToDocument(String word, String[] valueAr, String[] countAr, int LENGTH)
	{
		Document doc = new Document().append("_id", word);
		for(int i = 0;i<LENGTH;i++)
		{
			doc.append(valueAr[i], Integer.parseInt(countAr[i]));
		}
		return doc;
	}
	
	public static void insertIntoCollection(Document db)
	{
		collection.insertOne(db);
	}
	
	/*
	 * This below method
	 * updates the collection via the given
	 * @INPUT sentence
	 * Case1: User presses space, a post request is made sentence is empty
	 * Case2: "I" and space, sentence is "I" and we return a document for this sentence*/
	public static Document searchWordReturnDocument(String sentence)
	{
		String[] userWholeQueryAr = sentence.split(" ");
		if(userWholeQueryAr.length>0)
		{
			String lastWord = userWholeQueryAr[userWholeQueryAr.length-1];
			
			Document queryDoc = new Document("_id", lastWord);
			Document projectionDoc = new Document("_id", 0);
			Document valueCountPairs = collection.find(queryDoc).projection(projectionDoc).first();
			return valueCountPairs;
		}
		else
			return null;
	}
	
	public static String convertDocumentToJson(Document doc)
	{
		return doc.toJson();
	}
	
	/*
	 * This below method
	 * updates the collection via the given 
	 * @INPUT sentence
	 * Case1: User presses space, a post request is made sentence is empty
	 * Case2: "I" and presses space, sentence is "I" and there is nothing to learn
	 * Case3: "I will" and presses space, So for "I" we increment the "will" count
	 * Case4: "I will take" and presses space, So we only consider "will take"
	 * 		   and update count of "take" by 1*/
	public static void updateCollection(String Sentence)
	{
		String[] wordAr = Sentence.split(" ");
		int wordArLength = wordAr.length;
		if(wordArLength>1)
		{
			String penultimate = wordAr[wordArLength-2];
			String ultimate = wordAr[wordArLength-1];
			
			Document fieldToUpdateDoc = new Document(ultimate, 1);
			Document projectionDoc = new Document("$inc", fieldToUpdateDoc);
			Document queryDoc = new Document("_id", penultimate);
			
			collection.updateOne(queryDoc, projectionDoc);
		}
	}
	
	public static void printDocuments(FindIterable<Document> documents)
	{
		for(Document each : documents)
		{
			System.out.println(each.toJson());
		}
		System.out.println();
	}
	
	
	public static void main(String args[])
	{
		String[] ar = {"descriptor->of::2 but::1",
						"conformed->to::5 less::1",
						"snail->mail::12 food::2 here::1"};
		for(int i = 0; i <ar.length; i++)
			insertIntoCollection(convertStringToDocument(ar[i]));
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter command: ");
		String command = input.nextLine();
		while(command.equalsIgnoreCase("stop") != true)
		{
			//search next word
			Document doc = searchWordReturnDocument(command);
			if(doc != null)
			{
				String result = convertDocumentToJson(doc);
				System.out.println(result);
			}
			//update collection
			updateCollection(command);
			
			System.out.println("***************Whole Collection***************");
			FindIterable<Document> documents = collection.find();
			printDocuments(documents);
			System.out.println("**********************************************");

			command = input.nextLine();
		}
		
		input.close();
		
		/*
		//To insert
		String input = "conformed->to::5 less::1";
		Document inputDoc = convertStringToDocument(input);
		insertIntoCollection(inputDoc);
		*/
		
		/*
		//To search
		FindIterable<Document> documents = collection.find();
		printDocuments(documents);
		Document doc = searchWordReturnDocument("I will be conformed");
		String result = convertDocumentToJson(doc);
		System.out.println(result);
		*/
		
		/*
		//To update
		FindIterable<Document> documents = collection.find();
		printDocuments(documents);
		updateCollection("I will be conformed to");
		printDocuments(documents);
		*/
		
		mongoClient.close();
	}
}
