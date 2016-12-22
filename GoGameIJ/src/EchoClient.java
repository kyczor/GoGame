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
import javafx.scene.control.Alert;

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
	Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private volatile boolean end;
	private GUI gui;
	private boolean movemaking = false;

	public EchoClient(String host, int port, GUI gui)
	{
		this.gui = gui;

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
			if (command.type.equals("HUMAN_SIZE") || command.type.equals("BOT_SIZE"))
			{
				oos.writeObject(command);
			}
			else if (movemaking)
			{
				movemaking = false;
				oos.writeObject(command);
			}
			if (command.type.equals("ENDGAME"))
			{
				s.close();
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Look, an Information Dialog");
					alert.setContentText("I have a great message for you!");

					alert.showAndWait();
				});
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
			//	Thread.sleep(1000);
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
	}
}