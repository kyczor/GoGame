package client;

public class MoveClient {

	Pawn[][] board;

	public MoveClient() {
		board = new Pawn[19][19];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				board[i][j] = null;
			}
		}
	}

	// when we can make correct move
	boolean checkIfMoveIsCorrect(int x, int y) {

		if (checkIfMoveIsOutsideOfBoard(x, y) || checkIfFieldIsOccupied(x, y)) {
			return false;
		}
		if (checkIfPawnCommitsSuicide(x, y) == true) {
			return false;
		}
		return true;
	}

	// pawn is outside Board
	public boolean checkIfMoveIsOutsideOfBoard(int x, int y) {
		if (x < 19 && y < 19) {
			return false;
		}
		return true;
	}

	// check whether the space is occupied
	public boolean checkIfFieldIsOccupied(int x, int y) {
		if (board[x][y] == null) {
			return false;
		}
		return true;
	}

	// pawn commits suicide when is in centre opponents
	public boolean checkIfPawnCommitsSuicide(int x, int y) {
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x + 1][y].isAlive() == board[x][y + 1]
				.isAlive() == board[x][y - 1].isAlive()) {
			if (checkIfPawnsKillYourselfAndOpponent(x, y)) {
				return false;
			}
			return true;
		}
		return false;
	}

	// pawn kill yourself and opponent
	public boolean checkIfPawnsKillYourselfAndOpponent(int x, int y) {
		if (board[x][y + 2].isAlive() == board[x + 1][y + 1].isAlive() == board[x - 1][y + 1].isAlive()) {
			return true;
		}
		if (board[x][y - 2].isAlive() == board[x + 1][y - 1].isAlive() == board[x - 1][y - 1].isAlive()) {
			return true;
		}
		if (board[x - 2][y].isAlive() == board[x - 1][y - 1].isAlive() == board[x - 1][y + 1].isAlive()) {
			return true;
		}
		if (board[x + 2][y].isAlive() == board[x + 1][y - 1].isAlive() == board[x + 1][y + 1].isAlive()) {
			return true;
		}
		return false;
	}

}
