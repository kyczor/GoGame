/*
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.ServerSocket;

public class CLIENTTT 
{
    public static void main(String argv[]) throws Exception 
    {
        String oldtable; //table from the server
        String modifiedtable; //new table after a player's move
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 4444);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        oldtable = inFromServer.readLine();
        modifiedtable = inFromUser.readLine();
        outToServer.writeBytes(modifiedtable + '\n');
        
        
        System.out.println(modifiedtable);
        clientSocket.close();
    }
}
*/

import javafx.application.Platform;

import java.io.*;
import java.net.*;

/**
 * A client that demands connection with the server. In the future it's going to contain the whole GUI.
 * When a player sends a "bye"-message, it shuts itself down.
 *
 * @author Karola
 * @see ServerClient - connects clients with server
 */
public class EchoClient implements Runnable
{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private volatile boolean end;
	private GUI gui;
	private boolean movemaking = false;

	public EchoClient(String host, int port, GUI gui)
	{
		this.gui = gui;
		Socket s;
		try
		{
			s = new Socket(host, port);
			System.out.println("Socket created");
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			ois.readObject();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		new Thread(this).start();
	}


	public void SendCommand(Command command)
	{
		try
		{
			oos.writeObject(command);
			if (command.type == "MOVE")
			{
				movemaking = false;
			} else if (command.type == "GIVEUP")
			{
				movemaking = false; // a client can no longer make moves
			} else if (command.type == "PASS")
			{
				movemaking = false;
			} else if (command.type == "COUNTP")
			{
			} else if (command.type == "ENDGAME")
			{
				movemaking = false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Main Client method.
	 * Connects to the game's server. Repeatedly reads objects (table) sent by server and (TODO GUI) displays it on the player's screen.
	 * After a player makes a move - sends a string with appropriate message to server.
	 * When the message is "bye", the client closes itself.
	 */
	@Override
	public void run()
	{
		while (!end)
		{
			try
			{
				Thread.sleep(1000);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{

				Command c = (Command) ois.readObject();
				switch (c.type)
				{
					case "MOVE":
						movemaking = true;
						Platform.runLater(() -> {
							gui.drawBoard(c.board);
						});
						break;
					case "B":
						Platform.runLater(() -> {
							gui.drawBoard(c.board);
						});
						break;
					case "W":
						Platform.runLater(() -> {
							gui.drawBoard(c.board);
						});
						break;
					default:
						break;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}


//		super.start();
//
//		Socket s = null;
//		try
//		{
//			s = new Socket("127.0.0.1", 9995);
//			System.out.println("Socket created");
//			BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			PrintWriter w = new PrintWriter(s.getOutputStream(), true);
//			oos = new ObjectOutputStream(s.getOutputStream());
//			oos.flush();
//			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//			console = new BufferedReader(new InputStreamReader(System.in));
//
//
//			Object obj = ois.readObject();
//			if(obj instanceof String)
//			{
//				String msg = (String)obj;
//				System.out.println(msg);
//			}
//
//			end = false;
//
//			do
//			{
//				obj = ois.readObject();
//				if(obj instanceof String)
//				{
//					String msg = (String)obj;
//					if(msg.equals("Board"))
//					{
//						Board board = new Board();
//					board.readFromStream(ois);
//						board.Print();
//						SendAnswer(console.readLine());
//					}
//					else if(msg.equals("bye"))
//					{
//						System.out.println("Your opponent has left.");
//						end = true;
//					}
//					else if(msg.equals("size"))
//					{
//						System.out.println("3/9/19/29");
//						String ret = console.readLine();
//						Integer size = Integer.parseInt(ret);
//						//TODO: validate input and try/catch parseInt
//						SendAnswer(size.toString());
//					}
//					else
//					{
//						System.out.println(msg);
//						SendAnswer(console.readLine());
//					}
//				}
//				else if (obj instanceof NewMove)
//				{
//
//				}
//			}
//			while (!end);
//
//			s.close();
//		}
//		catch (Exception err)
//		{
//			System.err.println(err);
//		}
	}
}
