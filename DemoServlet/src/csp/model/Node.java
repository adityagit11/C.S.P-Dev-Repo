package csp.model;

import java.util.HashMap;
import java.util.Map;

public class Node 
{
	public Map<String, Node> children;
	
	public Node()
	{
		children = new HashMap<>();
	}
}
