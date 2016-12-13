package client;

public class AllowedMove {

	Pawn[][] board;

	public AllowedMove() {
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
		if (checkIfPawnCommitsSuicide(x, y) == true || checkIfPawnIsInTheCentreOnTheCorners(x, y) == true
				|| checkIfPawnIsInTheCentreOnTheSides(x, y) == true) {// KO NIE
																		// JEST
																		// ZROBIONE
																		// bo
																		// nie
																		// wiem
																		// jak
																		// cofnac
																		// sie
																		// do
																		// ruchu
																		// poprzednika

			return false;
		}
		return true;
	}

	// pawn is outside board
	public boolean checkIfMoveIsOutsideOfBoard(int x, int y) {
		if ((x < 19 && y < 19) || (x >= 0 && y >= 0)) {// dopisany drugi warunek
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

	// pawn commits suicide when is in centre opponents (in centre board)
	public boolean checkIfPawnCommitsSuicide(int x, int y) {
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x + 1][y].isAlive() == board[x][y + 1]
				.isAlive() == board[x][y - 1].isAlive()) {
			// check pawn can spank opposition
			if (checkIfPawnsCanSpank(x, y)) {// mozliwosc zabojstwa bo pionki sa
												// rownomiernie ustawione
				return false;
			}
			return true;
		}
		return false;
	}

	// pawn kill yourself and opponent
	public boolean checkIfPawnsCanSpank(int x, int y) { // sprawdzenie 3 kulek
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

	// pawn commits suicide when in centre opponents (on the corners board)
	public boolean checkIfPawnIsInTheCentreOnTheCorners(int x, int y) {// samobojstwo
																		// na
																		// rogach
		if (board[x][y].isAlive() != board[x + 1][y].isAlive() == board[x][y - 1].isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x][y - 1].isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x][y + 1].isAlive() == board[x + 1][y].isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x][y + 1].isAlive()) {
			return true;
		}
		return false;
	}

	// pawn commits suicide when in centre opponents (on the sides board)
	public boolean checkIfPawnIsInTheCentreOnTheSides(int x, int y) {// samobojstwo
																		// na
																		// bokach
		if (board[x][y].isAlive() != board[x][y + 1].isAlive() == board[x + 1][y].isAlive() == board[x][y - 1]
				.isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x][y + 1].isAlive() == board[x + 1][y]
				.isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x][y + 1].isAlive() == board[x - 1][y].isAlive() == board[x][y - 1]
				.isAlive()) {
			return true;
		}
		if (board[x][y].isAlive() != board[x - 1][y].isAlive() == board[x][y - 1].isAlive() == board[x + 1][y]
				.isAlive()) {
			return true;
		}
		return false;
	}

}
