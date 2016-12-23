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

	/**
	 * Constructor.
	 * @param arr array of enum fields
	 */
	public Board(whosefield[][] arr)
	{
		this.i = arr.length;
		tab = new ArrayList<>();
		for (int i = 0; i < arr.length; i++)
		{
			tab.add(new ArrayList<>());
			for (int j = 0; j < arr[i].length; j++)
			{
				tab.get(i).add(arr[i][j]);// 0 = empty field
			}
		}
	}

	public int getSize()
	{
		return i;
	}

	/**
	 *
	 * @param col color of pawn
	 * @param x column (of pawn)
	 * @param y row (of pawn)
	 * @return true if there is an empty field, false if the field is occupied
	 */
	public boolean putPawn(whosefield col, int x, int y)
	{
		if (getField(x, y) == whosefield.empty)
		{
			tab.get(x).set(y, col);
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param x column
	 * @param y row
	 * @return returns enum of whose field it is
	 */
	public whosefield getField(int x, int y)
	{
		return tab.get(x).get(y);
	}

	/**
	 *
	 * @param oos output stream to be sent
	 * @throws IOException
	 */
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

	/**
	 *
	 * @param ois input stream that is being read
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
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

	/**
	 *
	 * @return converts array of arrays into array
	 */
	public whosefield[][] toArray()
	{
		whosefield[][] r = new whosefield[i][i];
		for (int a = 0; a < i; a++)
		{
			for (int b = 0; b < i; b++)
			{
				r[a][b] = getField(a,b);
			}
		}
		return r;
	}

}
