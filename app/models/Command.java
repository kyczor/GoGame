package models;

import models.Board;
import models.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by Karola on 2016-12-21.
 */

/**
 * This class is supposed to pass specific commands from client to server and the other way around.
 */
public class Command implements java.io.Serializable {
	String type = "";
	Board board;
	int x = -1, y = -1;

	/**
	 * @return x (column)
	 */
	public int getX()
	{return x;}

	/**
	 * @return y (row)
	 */
	public int getY()
	{return y;}
	/**
	 * Constructor
	 * @param type type of command
	 * @param board actual board
	 */
	public Command(String type, Board board) {
		this.type = type;
		this.board = board;
	}

	/**
	 * (Overloaded) Constructor
	 * @param type type of command (MOVE)
	 * @param x column of chosen move
	 * @param y row of chosen move
	 */
	public Command(String type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
		board = new Board(1);
	}

	/**
	 * @return type of command
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return actual board
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * @param oos output stream to be sent
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos)
			throws IOException {
		oos.writeObject(type);
		oos.writeObject(board.getSize());
		for (int a = 0; a < board.getSize(); a++)
		{
			for (int b = 0; b < board.getSize(); b++)
			{
				oos.writeObject(board.getField(a,b).toInt());// 0 = empty field
			}
		}
		oos.writeObject(x);
		oos.writeObject(y);
	}

	/**
	 *
	 * @param ois input stream that is being read
	 * @throws ClassNotFoundException exception1
	 * @throws IOException exception2
	 */
	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		type = (String) ois.readObject();
		board = new Board((Integer) ois.readObject());
		for (int a = 0; a < board.getSize(); a++)
		{
			for (int b = 0; b < board.getSize(); b++)
			{
				//board.putPawn(Status.fromInt((Integer) ois.readObject()),a,b);//.get(a).add(whosefield.fromInt((Integer)ois.readObject()));// 0 = empty field
				if (board.isEmpty(a, b))
				{
					board.move(a, b, Status.fromInt((Integer) ois.readObject()));
				}
			}
		}
		x = (Integer) ois.readObject();
		y = (Integer) ois.readObject();

	}
	
	public Command(JsonNode jsonNode)
	{
		type = jsonNode.get("type").textValue();
		if(type.equals("MOVE"))
		{
			x = jsonNode.get("x").asInt();
			y = jsonNode.get("y").asInt();
		}
		board = jsonNode.get("board") == null ? null : new Board(jsonNode.get("board"));
	}
	public JsonNode toJson(){

		//TODO: fill json
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonNode = mapper.createObjectNode();
		
		try{
			
//		jsonNode.put("key", "value");
		jsonNode.put("type", type);
		jsonNode.put("x", x);
		jsonNode.put("y", y);
		if (board != null )jsonNode.put("board", board.toJson());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		
		return jsonNode;
	}

}
