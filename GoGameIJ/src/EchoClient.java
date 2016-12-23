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
			s.setSoTimeout(100);
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
			} else if (movemaking)
			{
				movemaking = false;
				oos.writeObject(command);
			}
			if (command.type.equals("ENDGAME"))
			{

				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Info");
					alert.setHeaderText("The game has ended");
					alert.setContentText("You have been disconnected.");
					alert.showAndWait();
				});
				end = true;

			}
			if (command.type.equals("GIVEUP"))
			{
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("You gave up");
					alert.setHeaderText("The game has ended.\nYou LOST.");
					alert.setContentText("You have been disconnected.");
					alert.showAndWait();
				});
				end = true;
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
					case "ENDGAME":

						Platform.runLater(() -> {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Info");
							alert.setHeaderText("The game has ended");
							alert.setContentText("You have been disconnected.");
							alert.showAndWait();
						});
						end = true;
						break;
					case "GIVEUP":
						Platform.runLater(() -> {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Your opponent gave up");
							alert.setHeaderText("The game has ended. You WON!!!!");
							alert.setContentText("You have been disconnected.");
							alert.showAndWait();
						});
						end = true;
						break;

					default:
						break;
				}
			} catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		try
		{
			s.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}