import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Karola on 2016-12-21.
 */
public class Command implements Serializable {
	String type = "";
	Board board;
	int x = -1, y = -1;

	public Command(String type, Board board) {
		this.type = type;
		this.board = board;
	}

	public Command(String type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
		board = new Board(1);
	}


	public String getType() {
		return type;
	}

	public Board getBoard() {
		return board;
	}

	public int getX(){return x;}
	public int getY(){return y;}

	private void writeObject(ObjectOutputStream oos)
			throws IOException {
		oos.writeObject(type);
		oos.writeObject(board.getSize());
		for (int a = 0; a < board.getSize(); a++)
		{
			for (int b = 0; b < board.getSize(); b++)
			{
				oos.writeObject(board.getField(a,b).toInt());// 0 = empty field
			}
		}
		oos.writeObject(x);
		oos.writeObject(y);
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		type = (String) ois.readObject();
		board = new Board((Integer) ois.readObject());
		for (int a = 0; a < board.getSize(); a++)
		{
			for (int b = 0; b < board.getSize(); b++)
			{
				board.putPawn(whosefield.fromInt((Integer)ois.readObject()),a,b);//.get(a).add(whosefield.fromInt((Integer)ois.readObject()));// 0 = empty field
			}
		}
		x = (Integer) ois.readObject();
		y = (Integer) ois.readObject();

	}

}
