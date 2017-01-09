package Server;

import Model.Board;
import Model.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Creates two sockets for each player. Connects two players.
 * @author Karola
 *
 */
public class ServerClient 
{
	public Status color;
	private Socket s = null;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	/**
	 * Passes the stream
	 * @param socket Socket
	 * @throws IOException handled by Exception class
	 */
	public ServerClient(Socket socket) throws IOException
	{
		s = socket;
		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());
		oos.writeObject(1);
	}

	/**
	 * 
	 * @param o object to be written as an object output stream
	 * @throws IOException IOException handled by Exception class
	 */
	public void Write(Command o) throws IOException
	{
		oos.flush();
		oos.writeObject(o);
	}

	/**
	 * try to flush
	 */
	public void flush()
	{
		try
		{
			oos.flush();
		}
		catch (Exception e)
		{

		}
	}


	/**
	 * 
	 * @param b board to be written
	 * @throws IOException
	 */
	public void WriteAsBoard(Board b) throws IOException
	{
		//b.writeObject(oos);
		oos.flush();
		oos.writeObject(b);
	}
	/**
	 * 
	 * @return Reads object from the object input stream
	 * @throws ClassNotFoundException exception with no detail message
	 * @throws IOException IOException handled by Exception class
	 */
	public Command Read() throws ClassNotFoundException, IOException
	{
		return (Command) ois.readObject();
	}
	
	/**
	 * 
	 * @return Reads an object. If it's a string, parses it into string (to make it easier to compiler). If it's not - returns a null.
	 * @throws ClassNotFoundException exception with no detail message
	 * @throws IOException IOException handled by Exception class
	 */
	public String ReadAsString() throws ClassNotFoundException, IOException
	{
		Object obj = Read();
		if(obj instanceof String)
			return (String)obj;
		return null;
	}
	
}
