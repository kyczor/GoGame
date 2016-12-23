//try{

public class Move /* implements Functions */ {

	public int size = 9;

	private int x, y;
	whosefield color;
	private Board board;
	boolean isFieldCorrect = false;

	// laduje dane
	public void getData(int x, int y, whosefield color, Board board) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.color = color;
		// listToBoard();
	}

	// rozpoczyna sprawdzanie warunkow
	public boolean game() throws ArrayException {
		whosefield playerColor = color;
		int col = x;
		int row = y;
		whosefield enemyColor = color == color.black ? color.white : color.black;
		if (board.getField(col, row) == color.empty) {
			board.tab.get(x).set(y, playerColor);
			removePlayer(x, y, enemyColor, playerColor);
			return true;
		}
		return false;
	}

	// zwraca tablice
	public Board getBoard() {
		return board;
	}

	// sprawdza warunki okrazenia gracza
	public void removePlayer(int x, int y, whosefield enemy, whosefield friend) {
		cornerCase(x, y, enemy, friend);

		if (board.getField(x, y + 1) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x, y + 2) == friend
					&& board.getField(x - 1, y + 1) == friend && board.getField(x + 1, y + 1) == friend)
				board.tab.get(x).set(y + 1, color.empty);
		}
		if (board.getField(x, y - 1) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x, y - 2) == friend
					&& board.getField(x - 1, y - 1) == friend && board.getField(x + 1, y - 1) == friend)
				board.tab.get(x).set(y - 1, color.empty);
		}
		if (board.getField(x + 1, y) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x + 2, y) == friend
					&& board.getField(x + 1, y - 1) == friend && board.getField(x + 1, y + 1) == friend)
				board.tab.get(x + 1).set(y, color.empty);
		}
		if (board.getField(x - 1, y) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x - 2, y) == friend
					&& board.getField(x - 1, y - 1) == friend && board.getField(x - 1, y + 1) == friend)
				board.tab.get(x - 1).set(y, color.empty);
		}
	}

	// sprawdza warunki brzegowe
	public void cornerCase(int x, int y, whosefield enemy, whosefield friend) {
		int height = board.tab.get(0).size();
		int width = board.tab.size();

		if (x == 1 && y == 0) {
			if (board.getField(0, 0) == enemy && board.getField(0, 1) == friend) {
				board.tab.get(0).set(0, color.empty);
			}
		} else if (x == 0 && y == 1) {
			if (board.getField(0, 0) == enemy && board.getField(1, 0) == friend) {
				board.tab.get(0).set(0, color.empty);
			}
		}

		if (x == (width - 1) && y == 0) {
			if (board.getField(width, 0) == enemy && board.getField(width, 1) == friend) {
				board.tab.get(width).set(0, color.empty);
			}
		} else if (x == width && y == 1) {
			if (board.getField(width, 0) == enemy && board.getField(width - 1, 0) == friend) {
				board.tab.get(width).set(0, color.empty);
			}
		}

		if (x == 0 && y == (height - 1)) {
			if (board.getField(0, height) == enemy && board.getField(1, height) == friend) {
				board.tab.get(0).set(height, color.empty);
			}
		} else if (x == 1 && y == height) {
			if (board.getField(0, height) == enemy && board.getField(0, height - 1) == friend) {
				board.tab.get(0).set(height, color.empty);
			}
		}

		if (x == board.tab.size() && y == (board.tab.get(board.tab.size()).size() - 1)) {
			if (board.tab.get(width).get(height) == enemy && board.tab.get(width - 1).get(height) == friend) {
				board.tab.get(width).set(height, color.empty);
			}
		} else if (x == (board.tab.size() - 1) && y == board.tab.get(0).size()) {
			if (board.tab.get(width).get(height) == enemy && board.tab.get(width).get(height - 1) == friend) {
				board.tab.get(width).set(height, color.empty);
			}
		}
	}

}