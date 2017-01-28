package models;

import models.Board;
import models.Session;
import models.Status;
import play.mvc.WebSocket;

import java.io.IOException;
import java.net.ServerSocket;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * SERVER Connects two clients. (Should be) Connected with Session class (in order
 * to apply all the game rules)
 *
 * @author Karola
 * @see ServerClient - connects server with two clients
 */
public class EchoServer   {
	private static ServerClient client1 = null, client2 = null;
	private static Session session = new Session();



	/**
	 * Creates new Board. Starts the game and sets a first player. Handles all
	 * the commands sent by clients.
	 */
	public static void serve() {
		System.out.println("Server started");
		Board board = new Board(9);
		try {
		
			client1.color = Status.black;
			client2.color = Status.white;
			client2.Write(new Command("W", board));

			ServerClient actualPlayer = client1;
			boolean end = false;
			while (!end) {
				while (true) {
					actualPlayer.Write(new Command("MOVE", board));
					Command c = actualPlayer.Read();
					session.setData(c.getY(), c.getX(), actualPlayer.color, board);
					if (c.type.equals("MOVE")) // after the implementation of
												// "making move" is done -
												// putparn has to be commented
					{
						boolean result = session.game();

						if (result) // udany ruch
						{
							board = session.getBoard();
							actualPlayer.Write(new Command(actualPlayer.color == Status.white ? "W" : "B", board));
							break;
						} else {
							System.out.println("Try again");
						}
					} else if (c.type.equals("PASS")) {
						break; // doesn't have to do anything
					} else if (c.type.equals("ENDGAME")) {
						Command closeit = new Command("ENDGAME", board);
						client1.Write(closeit);
						client2.Write(closeit);
						break;
					} else if (c.type.equals("GIVEUP")) {
						Command lethimknow = new Command("GIVEUP", board);
						client1.Write(lethimknow);
						client2.Write(lethimknow);

						break;
					}

				}

				if (actualPlayer == client1)
					actualPlayer = client2;
				else
					actualPlayer = client1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Cannot connect two clients");
		}
		System.out.println("Server stoped");

	}


	public static void join(WebSocket.In<JsonNode> _in, WebSocket.Out<JsonNode> _out)
	{
		if(client1 == null)
		{
			try
			{
				client1 = new ServerClient(_in, _out);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		else if(client2 == null)
		{
			try
			{
				client2 = new ServerClient(_in, _out) ;
				serve();
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
}
