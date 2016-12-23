//try{

public class Move implements Functions {

	private int x, y;
	whosefield color;
	private Board board;

	/**
	 * A function that reads all the needed data
	 * 
	 * @param x
	 *            column of move
	 * @param y
	 *            row of move
	 * @param color
	 *            enum - which player did the move
	 * @param board
	 *            actual board
	 */

	public void getData(int x, int y, whosefield color, Board board) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 *
	 * @return true if the chosen field was empty
	 * @throws ArrayException
	 */

	public boolean game() {
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

	/**
	 *
	 * @return board
	 */

	public Board getBoard() {
		return board;
	}

	/**
	 * A function that removes enemy pawns (killed)
	 * 
	 * @param x
	 *            column
	 * @param y
	 *            row
	 * @param enemy
	 *            enemy colour
	 * @param friend
	 *            current player's colour
	 */

	public void removePlayer(int x, int y, whosefield enemy, whosefield friend) {
		if (x == 0 || y == 0) {
			cornerCase(x, y, enemy, friend);
		} else {
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
	}

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
