package org.edelweiss.mongo.model;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

/*
 * Now you start taking commands from the USER
 * FORMAT "STRING" STRING_LENGTH STRING.SPLIT(" ")_LENGTH
 * Case1: User presses space and a post is generated: 0 1
 * Case2: User enters "I" and presses space, No learning but value must be returned: I 1 1, WILL 4 1
 * Case3: User enters "I will" and presses space, Learn/ update for "I" and return value for "will": I WILL 6 2
 * Case4: User enters "I will kill" and presses space, Learn/ update for "will" and returns value for "kill": I WILL KILL 11 3
 * */

public class MongoPatch 
{
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	private final String DatabaseName = "test";
	private final String CollectionName = "wolvie";
	
	public MongoPatch()
	{
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase(DatabaseName);
		collection = database.getCollection(CollectionName);
	}
	
	public MongoCollection<Document> getCollection()
	{
		return collection;
	}
	
	/****************************************************************************************************************************/
	
	/*
	 * This below method
	 * is for converting String to Document, and we further
	 * insert it into our collection*/
	public Document convertStringToDocument(String sentence)
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
	private Document convertStringToDocument(String word, String[] valueAr, String[] countAr, int LENGTH)
	{
		Document doc = new Document().append("_id", word);
		for(int i = 0;i<LENGTH;i++)
		{
			doc.append(valueAr[i], Integer.parseInt(countAr[i]));
		}
		return doc;
	}
	
	public void insertIntoCollection(Document db)
	{
		collection.insertOne(db);
	}
	
	/****************************************************************************************************************************/
	
	public Document processInputString(String sentence)
	{
		String[] wordAr = sentence.split(" ");
		int SENTENCE_LENGTH = sentence.length();
		int ARRAY_LENGTH = wordAr.length;
		if(SENTENCE_LENGTH==0 && ARRAY_LENGTH==1)
		{
			//USER PRESSES SPACE WITHOUT ENTERING ANY TEXT AND DO NOTHING
			System.out.println("Kindly Enter something too..");
			return Case1();
		}
		else if(SENTENCE_LENGTH>0 && ARRAY_LENGTH==1)
		{
			//USER TYPES "I" AND PRESSES SPACE WE JUST SEARCH FOR THIS WORD VALUES AND RETURN IT
			System.out.println("We don't learn anything");
			return Case2(sentence);
		}
		else if(SENTENCE_LENGTH>0 && ARRAY_LENGTH>1)
		{
			//USER TYPES "I WILL" AND PRESSES SPACE WHERE: penultimate=I and ultimate=WILL
			//We have to update for penultimate and search for ultimate
			String penultimate = wordAr[ARRAY_LENGTH-2];
			String ultimate = wordAr[ARRAY_LENGTH-1];
			return Case3(penultimate, ultimate);
		}
		return null;
	}
	private Document Case1()
	{
		return null;
	}
	private Document Case2(String ultimate)
	{
		Document result = searchDocument(ultimate);
		return result;
	}
	private Document Case3(String penultimate, String ultimate)
	{
		//Update for penultimate
		//First check whether penultimate exists
		Document penultimateDoc = new Document("_id", penultimate);
		Document searchPenultimateDoc = collection.find(penultimateDoc).first();
		if(searchPenultimateDoc!=null)
		{
			Document fieldToUpdateDoc = new Document(ultimate, 1);
			Document projectionDoc = new Document("$inc", fieldToUpdateDoc);
			collection.updateOne(penultimateDoc, projectionDoc);
		}
		else
		{
			//Penultimate doesn't exist
			collection.insertOne(penultimateDoc);
			Document fieldToUpdateDoc = new Document(ultimate, 1);
			Document projectionDoc = new Document("$inc", fieldToUpdateDoc);
			collection.updateOne(penultimateDoc, projectionDoc);
		}
		//Search for ultimate
		Document result = searchDocument(ultimate);
		return result;
	}
	private Document searchDocument(String ultimate)
	{
		Document queryDoc = new Document("_id", ultimate);
		Document projectionDoc = new Document("_id", 0);
		Document valueCountPairs = collection.find(queryDoc).projection(projectionDoc).first();
		return valueCountPairs;
	}
	
	/****************************************************************************************************************************/
	
	public void closeClient()
	{
		mongoClient.close();
	}
	
	/****************************************************************************************************************************/
}