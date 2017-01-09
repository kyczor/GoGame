package Server;/*
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

import View.GUI;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A client that demands connection with the server. Connects itself with GUI.
 * It can send commands to server.
 * Also, it handles all the received commands.
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
	private boolean gaveup = false;

	/**
	 * EchoClient's constructor. Used to connect properly with GUI.
	 * @param host host of the game
	 * @param port chosen port
	 * @param gui Graphic Interface of the whole game
	 */
	public EchoClient(String host, int port, GUI gui)
	{
		this.gui = gui;

		try
		{
			s = new Socket(host, port);
			s.setSoTimeout(30000);
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

	/**
	 *
	 * @param command command sent to server.
	 * A function that sends appropriate commands to server.
	 * The commands can be of various types: MOVE, PASS, GIVEUP, ENDGAME, HUMAN_SIZE (which means that user choosed to play with another person and picked one of the available sizes), BOT_SIZE (which is similar to HUMAN_SIZE), ...
	 */
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
				if(!gaveup)
				{
					movemaking = false;
					oos.writeObject(command);
				}
				else
				{

				}
			}
//			if (command.type.equals("ENDGAME"))
//			{
//				oos.writeObject(command);
//				System.out.println("FAZA 1");
//				Platform.runLater(() -> {
//					Alert alert = new Alert(Alert.AlertType.INFORMATION);
//					alert.setTitle("Info");
//					alert.setHeaderText("The game has ended");
//					alert.setContentText("You have been disconnected.");
//					alert.showAndWait();
//				});
//				end = true;
//
//			}
			if (command.type.equals("GIVEUP"))
			{
				movemaking = true;
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("You gave up");
					alert.setHeaderText("The game has ended.\nYou LOST.");
					alert.setContentText("You have been disconnected.");
					alert.showAndWait();
				});
				oos.writeObject(command);
				movemaking = false;
				end = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Handles all received commands. If the command type is GIVEUP or ENDGAME - it simply closes all sockets and writes a message that explains how the game has ended.
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
//					case "ENDGAME":
//						movemaking = false;
//						Platform.runLater(() -> {
//							Alert alert = new Alert(Alert.AlertType.INFORMATION);
//							alert.setTitle("Info");
//							alert.setHeaderText("The game has ended");
//							alert.setContentText("You have been disconnected.");
//							alert.showAndWait();    //creates a pop up window which blocks any "game actions" until it is closed
//						});
//						end = true;
//						break;
					case "GIVEUP":
						movemaking = false;
						gaveup = true;
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