package org.edelweiss.cspspark.model;

import java.util.HashMap;
import java.util.Map;

public class Node 
{
	/*
	 * This Node class
	 * will have 2 member variables at this stage
	 * 
	 * 1. A bag (MAP) of childrens correlated with String - Node
	 * 
	 * 2. A word frequency counter*/
	private Map<String, Node> children;
	
	private int count;
	
	public Node()
	{
		children = new HashMap<String, Node>();
		
		count = 0;
	}
	
	public Map<String, Node> getMap()
	{
		return this.children;
	}
	
	public int getCount()
	{
		return this.count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
}
