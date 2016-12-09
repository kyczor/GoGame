
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer
{
	
	public EchoServer(int portnum)
	{
		try
		{
			server = new ServerSocket(portnum);
		}
		catch (Exception err)
		{
			System.out.println(err);
		}
	}

	public void serve()
	{
		try
		{
			int i;
			int[][] ourTab;
			Board board=new Board();
			while (true)
			{
				
				Socket client = server.accept();
				BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter w = new PrintWriter(client.getOutputStream(), true);
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream ous = new ObjectOutputStream(client.getOutputStream());

				w.println("Welcome to the Java GoServer.  Type 'bye' to close.  Select table size ( 3/9/19/29 )");
				String line;
				line = r.readLine();
				if(line.trim().equals("3") || line.trim().equals("9") || line.trim().equals("19") || line.trim().equals("29"))
				{
					i = Integer.parseInt(line);
				ourTab = board.InitTable(i); //should create a 2-dimensional table filled with 0's  (size: i)

				//	tab = new int[i][i];
				//	for( int a=0; a<i; a++)
				//	{
				//		for(int b=0; b<i; b++)
				//		{
				//			tab[a][b] = 0;// 0 empty field 
				//		}
				//	}
					//board.delivertable();
				}
				else
				{
				// TODO write a message "select a correct size".
					client.close();
				}
				
				do
				{
					ous.println(ourTab);
				
				//	line = r.readLine();
					if ( line != null )
						w.println("Got: "+ line);
				}
				while ( !line.trim().equals("bye") );
				client.close();
			}
		}
		catch (Exception err)
		{
			System.err.println(err);
		}
	}

	public static void main(String[] args)
	{
		EchoServer s = new EchoServer(9997);
		s.serve();
	}

	private ServerSocket server;
}
