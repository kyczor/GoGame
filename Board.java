import java.io.IOException;
import java.io.Serializable;

/**
 * This class can create a new table.
 * Also, it does everything concerning the game rules and player moves.
 * 
 * @author Karola
 * @see EchoServer
 *
 */
public class Board implements Serializable {

	int[][] tab;
//	int i;

	/**
	 * @param i size of the table defined by user
	 * @return returns updated table (ixi) filled with 0's
	 */
	public int[][] InitTable(int i) {
		tab = new int[i][i];
		for (int a = 0; a < i; a++) {
			for (int b = 0; b < i; b++) {
				tab[a][b] = 0;// 0 = empty field 
			}
		}
		return tab;
	}

	/**
	 * @param move move send to server by a client
	 * @return FOR NOW, true = end of the game, false = continue game
	 */
	public void HandleMove(String move)// change type class and add object class Move
	{
		tab[1][1] = 7;
	}

	/**
	 * @return converts tab into string
	 */
	public String AsString() {
		String s = "";
		for (int[] et : tab) {
			for (int e : et) {
				s = s + e + " ";
			}
		}
		return s;
	}

	public void readFromStream(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		int size = stream.readInt();
		InitTable(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tab[i][j] = stream.readInt();
			}
		}
	}

	public void writeToStream(java.io.ObjectOutputStream stream) throws IOException {
		stream.writeObject("Board");
		stream.writeInt(tab.length);
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				stream.writeInt(tab[i][j]);
			}
		}
	}

	public void Print() {
		for (int[] et : tab) {
			for (int e : et) {
				System.out.print(e + " ");
			}
			System.out.println("");
		}
	}
}
