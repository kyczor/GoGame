import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class can create a new table.
 *
 * @author Karola
 * @see EchoServer
 */
public class Board implements Serializable
{

	ArrayList<ArrayList<whosefield>> tab;
	int i;

	/**
	 * @param i size of the table defined by user
	 * @return returns updated table (ixi) filled with 0's
	 */
	public Board(int i)
	{
		tab = new ArrayList<>();
		this.i = i;
		for (int a = 0; a < i; a++)
		{
			tab.add(new ArrayList<>());
			for (int b = 0; b < i; b++)
			{
				tab.get(a).add(whosefield.empty);// 0 = empty field
			}
		}
	}

	public int getSize()
	{
		return i;
	}

	public boolean putPawn(whosefield col, int x, int y)
	{
		if (getField(x, y) == whosefield.empty)
		{
			tab.get(x).set(y, col);
			return true;
		}
		return false;
	}

	public whosefield getField(int x, int y)
	{
		return tab.get(x).get(y);
	}


	private void writeObject(ObjectOutputStream oos)
			throws IOException
	{
		oos.writeObject(i);
		for (int a = 0; a < i; a++)
		{
			for (int b = 0; b < i; b++)
			{
				oos.writeObject(getField(a,b).toInt());// 0 = empty field
			}
		}
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException
	{
		i = (Integer) ois.readObject();
		tab = new ArrayList<>();
		for (int a = 0; a < i; a++)
		{
			tab.add(new ArrayList<>());
			for (int b = 0; b < i; b++)
			{
				tab.get(a).add(whosefield.fromInt((Integer)ois.readObject()));// 0 = empty field
			}
		}

	}
}
