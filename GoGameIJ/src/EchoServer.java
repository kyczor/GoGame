
import java.io.IOException;
import java.net.ServerSocket;

/**
 * GAME'S SERVER (It was originally an echo server, I'm going to change its name
 * later...) Converts string message send by a player into a view of a table.
 * Then - delivers the table to the second player. If a player demands to end
 * the game (and closes its socket), the server closes the second player's
 * socket and shuts itself down.
 *
 * @author Karola
 * @see ServerClient - connects server with two clients
 */
public class EchoServer {
	private ServerSocket server;
	private ServerClient client1 = null, client2 = null;
	private Move m1 = new Move();

	/**
	 * Creates a socket (at a specific port)
	 *
	 * @param portnum
	 *            Port number
	 */
	public EchoServer(int portnum) {
		try {
			server = new ServerSocket(portnum);
		} catch (Exception err) {
			System.out.println(err);
		}
	}

	/**
	 * @param b
	 *            object that is a game board
	 * @param c
	 *            object that connects two clients with my server
	 * @throws IOException
	 *             (in case something goes wrong (ex: for some reason two
	 *             clients cannot be connected)
	 */
	void SendBoardToClient(Board b, ServerClient c) throws IOException {
		// c.Write(b.AsString()); // as string
		// c.Write(b); // as object Board <--- moze juz dziala
		c.WriteAsBoard(b);
	}

	/**
	 * Creates new Board object that is (for the time being (testing)) a 3x3
	 * table filled with 0's. Starts the game and sets a first player. Sends
	 * appropriate messages. Changes "active player" after every move. Handles
	 * every move
	 */
	public void serve() {
		int x = -1, y = -1;
		System.out.println("Server started");
		Board board = new Board(5);
		try {
			if (client1 == null)
				client1 = new ServerClient(server.accept());
			Command command = client1.Read();
			if (command.type.equals("HUMAN_SIZE")) {
				board = command.board;
				client1.Write(new Command("B", board)); // sends an empty board
														// to both players
			}
			if (client2 == null)
				client2 = new ServerClient(server.accept());
			client2.Read();
			// connects two clients
			System.out.println("Two Clients connected");

			Thread.sleep(2000);
			client1.color = whosefield.black;
			client2.color = whosefield.white;
			client2.Write(new Command("W", board));

			ServerClient actualPlayer = client1;
			boolean end = false;
			while (!end) {
				while (true) {
					actualPlayer.Write(new Command("MOVE", board));
					Command c = actualPlayer.Read();
					m1.getData(c.getX(), c.getY(), actualPlayer.color, board);
					m1.game();
					if (c.type.equals("MOVE") && board.putPawn(actualPlayer.color, c.getX(), c.getY())) {
						actualPlayer.Write(new Command(actualPlayer.color == whosefield.white ? "W" : "B", board));

						break;
					}

				}

				if (actualPlayer == client1)
					actualPlayer = client2;
				else
					actualPlayer = client1;
			}
		} catch (Exception e) {
			System.out.println("Cannot connect two clients");
		}
		System.out.println("Server stoped");

		/*
		 * while (true) { Socket client = server.accept(); BufferedReader r =
		 * new BufferedReader(new InputStreamReader(client.getInputStream()));
		 * PrintWriter w = new PrintWriter(client.getOutputStream(), true);
		 * //ObjectInputStream ois = new
		 * ObjectInputStream(client.getInputStream()); //ObjectOutputStream ous
		 * = new ObjectOutputStream(client.getOutputStream());
		 * 
		 * w.
		 * println("Welcome to the Java GoServer.  Type 'bye' to close.  Select table size ( 3/9/19/29 )"
		 * ); String line; do{ line = r.readLine(); System.out.println(line);
		 * }while(!line.equals("bye")); line = r.readLine();
		 * if(line.trim().equals("3") || line.trim().equals("9") ||
		 * line.trim().equals("19") || line.trim().equals("29")) { i =
		 * Integer.parseInt(line); ourTab = board.InitTable(i); //should create
		 * a 2-dimensional table filled with 0's (size: i)
		 * 
		 * } else { System.out.println("client close"); client.close(); }
		 * 
		 * do { //ous.println(ourTab); for(int[] et: ourTab) for(int e:et)
		 * w.print(e); if ( line != null ) w.println("Got: "+ line); line =
		 * r.readLine(); if(line == "") {
		 * 
		 * }
		 * 
		 * } while ( !line.trim().equals("bye") ); client.close(); } } catch
		 * (Exception err) { System.err.println(err); }
		 */
	}

	/**
	 * Main Server method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		EchoServer s = new EchoServer(9000);
		s.serve();
	}

}
