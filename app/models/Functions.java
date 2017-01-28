package models;

import models.Board;
import models.Status;

public interface Functions {
	void setData(int y, int x, Status player, Board board);
	boolean game();
	Board getBoard();
}
