
import java.io.IOException;
import java.net.ServerSocket;

/**
 * SERVER Connects two clients. (Should be) Connected with Move class (in order
 * to apply all the game rules)
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
	 */
	void SendBoardToClient(Board b, ServerClient c) throws IOException {
		c.WriteAsBoard(b);
	}

	/**
	 * Creates new Board. Starts the game and sets a first player. Handles all
	 * the commands sent by clients.
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
					if (c.type.equals("MOVE")) // after the implementation of
												// "making move" is done -
												// putparn has to be commented
					{
						boolean result = m1.game();

						if (result) // udany ruch
						{
							board = m1.getBoard();
							actualPlayer.Write(new Command(actualPlayer.color == whosefield.white ? "W" : "B", board));
							break;
						} else {
							System.out.println("Spróbuj ponowanie");
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
			System.out.println("Cannot connect two clients");
		}
		System.out.println("Server stoped");

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
