package models;

import models.Board;
import models.Status;
import play.libs.F.Callback;
import play.mvc.WebSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.util.Collections;

/**
 * Creates two sockets for each player. Connects two players.
 * @author Karola
 *
 */
public class ServerClient 
{
	public Status color;
	private WebSocket.Out<JsonNode> out;
	private BlockingDeque<Command> deque = new LinkedBlockingDeque<>();
	
	/**
	 * Passes the stream
	 * @param socket Socket
	 * @throws IOException handled by Exception class
	 */
	public ServerClient(WebSocket.In<JsonNode> _in,
            WebSocket.Out<JsonNode> _out) throws IOException
	{
		out = _out;
		_in.onMessage(new Callback<JsonNode>() {
			
			@Override
			public void invoke(JsonNode jsonNode) throws Throwable {
				try{
				System.out.println("Received command: " + jsonNode.get("type").textValue());
				deque.add(new Command(jsonNode));
				System.out.println("Command forwarded");
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 * @param o object to be written as an object output stream
	 * @throws IOException IOException handled by Exception class
	 */
	public void Write(Command o) throws IOException
	{
		out.write(o.toJson());
	}


	/**
	 * 
	 * @return Reads object from the object input stream
	 * @throws ClassNotFoundException exception with no detail message
	 * @throws IOException IOException handled by Exception class
	 */
	public Command Read() throws ClassNotFoundException, IOException
	{
		Command command = null;
		while(command == null){
			try {
				Thread.sleep(500);
				command = deque.pollFirst(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Command read");
		return command;
	}
	
}
