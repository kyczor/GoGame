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

import java.io.*;
import java.net.*;
/**
 * A client that demands connection with the server. In the future it's going to contain the whole GUI.
 * When a player sends a "bye"-message, it shuts itself down.
 * @author Karola
 * @see ServerClient - connects clients with server
 */
public class EchoClient
{
	/**
	 * Main Client method.
	 * Connects to the game's server. Repeatedly reads objects (table) sent by server and (TODO GUI) displays it on the player's screen.
	 * After a player makes a move - sends a string with appropriate message to server.
	 * When the message is "bye", the client closes itself.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Socket s = null;
		try
		{
			s = new Socket("127.0.0.1", 9995);
			System.out.println("Socket created");
			BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter w = new PrintWriter(s.getOutputStream(), true);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			
			Object obj = ois.readObject();
			if(obj instanceof String)
			{
				String msg = (String)obj;
				System.out.println(msg);
			}
			
			boolean end = false;
			
			do
			{
				obj = ois.readObject();
				if(obj instanceof String)
				{
					String msg = (String)obj;
					System.out.println(msg);
					
					String ret = console.readLine();
					if(ret.equals("bye"))
						end = true;
					
					oos.writeObject(ret);
					oos.flush();
				}
				else if(obj instanceof Board)
				{
					Board board = (Board)obj;
					for(int[] et: board.tab)
					{
						for(int e:et){
							System.out.print(e);
						}
						System.out.println("");	
					}
					String ret = console.readLine();
					oos.writeObject(ret);
					oos.flush();
				}
			}
			while (!end);
			
			s.close();
		}
		catch (Exception err)
		{
			System.err.println(err);
		}
	}
}

