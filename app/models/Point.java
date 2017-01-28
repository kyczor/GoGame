package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Point 
{
	int x;
	int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public Point(JsonNode jsonNode)
	{
		x = jsonNode.get("x").asInt();
		y = jsonNode.get("y").asInt();
	}
	
	public JsonNode toJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonNode = mapper.createObjectNode();
		
		jsonNode.put("x", x);
		jsonNode.put("y", y);
		
		return jsonNode;
	}
}
