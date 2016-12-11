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
	private Socket s = null;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	/**
	 * Reads the output stream and makes it an input stream to another class
	 * @param socket Socket
	 * @throws IOException handled by Exception class
	 */
	public ServerClient(Socket socket) throws IOException
	{
		s = socket;
		oos = new ObjectOutputStream(s.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(s.getInputStream());
	}

	/**
	 * 
	 * @param o object to be written as an object output stream
	 * @throws IOException IOException handled by Exception class
	 */
	public void Write(Object o) throws IOException
	{
		oos.writeObject(o);
		oos.flush();
	}
	/**
	 * 
	 * @return Reads object from the object input stream
	 * @throws ClassNotFoundException exception with no detail message
	 * @throws IOException IOException handled by Exception class
	 */
	public Object Read() throws ClassNotFoundException, IOException
	{
		return ois.readObject();
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
			return (String)Read();
		return null;
	}
	
}
