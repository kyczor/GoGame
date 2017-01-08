package Server;

import Model.ArrayException;
import Model.Board;
import Model.Status;

public interface Functions {
	void setData(int y, int x, Status player, Board board);
	boolean game() throws ArrayException;
	Board getBoard();
}
