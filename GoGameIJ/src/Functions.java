public interface Functions {
	void getData(int x, int y, whosefield color, Board board);

	boolean game();

	Board getBoard();

	void removePlayer(int x, int y, whosefield enemy, whosefield friend);

	void cornerCase(int x, int y, whosefield enemy, whosefield friend);

}
