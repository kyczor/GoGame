//try{

public class Move /* implements Functions */ {

	// ArrayException exception = new ArrayException();
	public int size = 9;

	private int x, y;
	whosefield color;
	private Board board;
	boolean isFieldCorrect = false;

	public Move() {
		// for (Object[] row : board)
		// Arrays.fill(row, "+");
	}

	public void getData(int x, int y, whosefield color, Board board) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.color = color;
		// listToBoard();
	}

	public boolean game() throws ArrayException {
		whosefield playerColor = color;
		int col = x;
		int row = y;
		whosefield enemyColor = color == color.black ? color.white : color.black;
		board.tab.get(x).set(y, playerColor);
		removePlayer(x, y, enemyColor, playerColor);
		return true;
	}

	public Board getBoard() {
		return board;
	}

	public void removePlayer(int x, int y, whosefield enemy, whosefield friend) {
		if (board.getField(x, y + 1) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x, y + 2) == friend
					&& board.getField(x - 1, y + 1) == friend && board.getField(x + 1, y + 1) == friend)
				board.putPawn(whosefield.empty, x, y + 1);
		}
		if (board.getField(x, y - 1) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x, y - 2) == friend
					&& board.getField(x - 1, y - 1) == friend && board.getField(x + 1, y - 1) == friend)
				board.putPawn(whosefield.empty, x, y - 1);
		}
		if (board.getField(x + 1, y) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x + 2, y) == friend
					&& board.getField(x + 1, y - 1) == friend && board.getField(x + 1, y + 1) == friend)
				board.putPawn(whosefield.empty, x + 1, y);
		}
		if (board.getField(x - 1, y) == enemy) {
			if (board.getField(x, y) == friend && board.getField(x - 2, y) == friend
					&& board.getField(x - 1, y - 1) == friend && board.getField(x - 1, y + 1) == friend)
				board.putPawn(whosefield.empty, x - 1, y);
		}
	}
}
//
// public void move(int row, int column, Integer player) {
// if (board[row][column] == "+") {
// board[row][column] = player;
// friends = 0;
// lookAround(row, column, player, false);
// if (friends == 0) {
// newChain(row, column, player);
// } else {
// connectChains(row, column, player);
// }
// }
// }
//
// public void newChain(int row, int column, Integer player) {
// List<List<Point>> outsideList;
// String color;
// if (player == 1) {
// outsideList = allBlackLists;
// color = "BLACK";
// } else {
// outsideList = allWhiteLists;
// color = "WHITE";
// }
// List<Point> insideList = new LinkedList<>();
// insideList.add(new Point(row, column));
// outsideList.add(insideList);
// // System.out.println("New " + color + " chain = " + insideList);
//
// }
//
// public void connectChains(int row, int column, Integer player) {
// friends = 0;
// friendsLocation = new Point[4];
// lookAround(row, column, player, false);
// if (friends == 1) {
// oneFriendChain(row, column, player, friendsLocation);
// } else {
// manyFriendsChain(row, column, player, friendsLocation);
// }
// }
//
// public void oneFriendChain(int row, int column, Integer player, Point[]
// friend) {
// List<List<Point>> friendsList;
// if (player == 1) {
// friendsList = allBlackLists;
// } else {
// friendsList = allWhiteLists;
// }
// for (List<Point> aFriendsList : friendsList) {
// for (int i = 0; i < aFriendsList.size(); i++) {
// if (aFriendsList.get(i).getX() == friend[0].getX() &&
// aFriendsList.get(i).getY() == friend[0].getY()) {
// aFriendsList.add(new Point(row, column));
// // System.out.println("Add a point x=" + row + " y=" +
// // column + " to " + aFriendsList);
// }
// }
// }
// }
//
// public void manyFriendsChain(int row, int column, Integer player, Point[]
// friend) {
// friends = 0;
// lookAround(row, column, player, false);
// List<List<Point>> friendsList;
// List<Point> listToRemove = new ArrayList<>();
// if (player == 1) {
// friendsList = allBlackLists;
// } else {
// friendsList = allWhiteLists;
// }
// List<Point> masterList = new ArrayList<>(); // NOWA LISTA, DO KTOREJ
// // MIGRUJA WSZYSTKIE PUNKTY
// for (List<Point> aFriendsList : friendsList) {
// for (int i = 0; i < aFriendsList.size(); i++) {
// if (aFriendsList.get(i).getX() == friend[0].getX() &&
// aFriendsList.get(i).getY() == friend[0].getY()) {
// masterList = aFriendsList;
// masterList.add(new Point(row, column));
// }
// }
// }
// for (int j = 0; j < friends; j++) {
// for (List<Point> aFriendsList : friendsList) {
// for (int i = 0; i < aFriendsList.size(); i++) {
// if (aFriendsList.get(i).getX() == friend[j].getX()
// && aFriendsList.get(i).getY() == friend[j].getY()) {
// if (!masterList.containsAll(aFriendsList)) {
// masterList.addAll(aFriendsList);
// listToRemove = aFriendsList;
// }
// }
// }
// }
// }
// friendsList.remove(listToRemove);
// // System.out.println("masterList" + player + " = " + masterList);
// }
//
// public void checkBreath(int row, int column, Integer player) {
// friends = 0;
// friendsLocation = new Point[4];
// lookAround(row, column, player, false);
// // System.out.println("the number of enemies = " + friends);
// if (friends > 0) {
// List<List<Point>> friendsList;
// Map<Integer, List<Point>> toRemoveMap = new HashMap<>();
// int[] sumEmpty = new int[friends];
// if (player == 1) {
// friendsList = allBlackLists;
// } else {
// friendsList = allWhiteLists;
// }
// for (int j = 0; j < friends; j++) { // DLA KAZDEGO PRZYJACIELA
// for (List<Point> aFriendsList : friendsList) { // DLA KAZDEGO
// // ELEMENTU
// // LISTY
// // ZEWNETRZNEJ
// for (int i = 0; i < aFriendsList.size(); i++) { // DLA
// // KAZDEGO
// // ELEMENTU
// // LISTY
// // WEWNETRZNEJ
// if (aFriendsList.get(i).getX() == friendsLocation[j].getX()
// && aFriendsList.get(i).getY() == friendsLocation[j].getY()) { // JESLI
// // WSPOLRZEDNA
// // X-OWA
// // I
// // Y-OWA
// // ELEMENTU
// // LISTY
// // WEWNETRZNEJ
// // =
// // PUNKTU
// // PRZYJACIELA
// // W
// // POBLIZU
// toRemoveMap.put(j, aFriendsList); // ZAPAMIETUJE
// // LISTE
// // (CHAINA)
// // PRZYJACIELA O
// // INDEKSIE "j"
// for (int k = 0; k < aFriendsList.size(); k++) {
// empty = 0;
// lookAround((int) aFriendsList.get(k).getX(), (int)
// aFriendsList.get(k).getY(), null,
// true); // TRUE = PUSTE MIEJSCA JUZ
// // SPRAWDZONE ZAZNACZONE SA "#"
// sumEmpty[j] = sumEmpty[j] + empty; // ZLICZANIE
// // WOLNYCH
// // MIEJSC DO
// // OKOLA
// // KAZDEGO
// // ELEMENTU
// // CHAINA
// }
// cleanEmpty();
// }
// }
// }
// // System.out.println(j + " The sum of breaths = " +
// // sumEmpty[j]);
// if (sumEmpty[j] == 0) { // JESLI SUMA PUSTYCH MIEJSC DO OKOLA
// // CHAINA O INDEKSIE "j" JEST = 0
// removeList(j, toRemoveMap, friendsList);
// }
// }
// }
// }
//
// public void removeList(int index, Map<Integer, List<Point>> map,
// List<List<Point>> list) {
// // System.out.println("BLACK = " + allBlackLists);
// // System.out.println("WHITE = " + allWhiteLists);
// for (Point point : map.get(index)) { // DLA KAZDEGO ELEMENTU
// // ZAPAMIETANEJ LISTY O INDEKSIE
// // "j"
// board[(int) point.getX()][(int) point.getY()] = "+"; // CZYSZCZENIE
// // MIEJSC Z
// // "#" W
// // KTORYCH
// // CHAIN SIE
// // UDUSIL
// }
// list.remove(map.get(index));
// // System.out.println("BLACK = " + allBlackLists);
// // System.out.println("WHITE = " + allWhiteLists);
// // System.out.println("Delet chain index = " + index);
// }
//
// public void cleanEmpty() {
// for (int i = 0; i < size; i++) {
// for (int j = 0; j < size; j++) {
// if (board[i][j] == "#") {
// board[i][j] = "+";
// }
// }
// }
// }
//
// public boolean isEmpty(int row, int column) {
// boolean result;
// result = board[row][column] == "+" || board[row][column] == null;
// return result;
// }
//
// @Override
// public boolean ko(int row, int column, Integer player) {
// boolean allow = false;
// if (column > 0 && column < size - 1 && row > 0 && row < size - 1) {
// friends = 0;
// lookAround(row, column + 1, player, false);
// System.out.println("column + 1 friends = " + friends);
// if (friends == 3) {
// allow = true;
// } else {
// friends = 0;
// lookAround(row, column - 1, player, false);
// System.out.println("column - 1 friends = " + friends);
// if (friends == 3) {
// allow = true;
// } else {
// friends = 0;
// lookAround(row + 1, column, player, false);
// System.out.println("row + 1 friends = " + friends);
// if (friends == 3) {
// allow = true;
// } else {
// friends = 0;
// lookAround(row - 1, column, player, false);
// System.out.println("row - 1 friends = " + friends);
// if (friends == 3) {
// allow = true;
// }
// }
// }
// }
// }
// return allow;
// }
//
// public void lookAround(int row, int column, Integer player, boolean
// markField) { // markField
// // -
// // POTRZEBNE
// // DO
// // SPRAWDZANIA
// // WOLNYCH
// // MIEJSC
// // WOKOL
// // CHAINA,
// // ZEBY
// // POMINAC
// // JUZ
// // SPRAWDZONE
// // POLE
// int i = 0;
// if (column == 0) {
// if (board[row][column + 1] == "+") {
// if (markField) {
// board[row][column + 1] = "#";
// }
// empty++;
// }
// if (board[row][column + 1] == player) {
// friends++;
// }
// if (board[row][column + 1] == player) {
// friendsLocation[i] = new Point(row, column + 1);
// i++;
// }
// }
// if (column > 0 && column < size - 1) {
// if (board[row][column + 1] == "+") {
// if (markField) {
// board[row][column + 1] = "#";
// }
// empty++;
// }
// if (board[row][column - 1] == "+") {
// if (markField) {
// board[row][column - 1] = "#";
// }
// empty++;
// }
// if (board[row][column + 1] == player) {
// friends++;
// }
// if (board[row][column - 1] == player) {
// friends++;
// }
// if (board[row][column + 1] == player) {
// friendsLocation[i] = new Point(row, column + 1);
// i++;
// }
// if (board[row][column - 1] == player) {
// friendsLocation[i] = new Point(row, column - 1);
// i++;
// }
// }
// if (column == size - 1) {
// if (board[row][column - 1] == "+") {
// if (markField) {
// board[row][column - 1] = "#";
// }
// empty++;
// }
// if (board[row][column - 1] == player) {
// friends++;
// }
// if (board[row][column - 1] == player) {
// friendsLocation[i] = new Point(row, column - 1);
// i++;
// }
// }
// if (row == 0) {
// if (board[row + 1][column] == "+") {
// if (markField) {
// board[row + 1][column] = "#";
// }
// empty++;
// }
// if (board[row + 1][column] == player) {
// friends++;
// }
// if (board[row + 1][column] == player) {
// friendsLocation[i] = new Point(row + 1, column);
// i++;
// }
// }
// if (row > 0 && row < size - 1) {
// if (board[row + 1][column] == "+") {
// if (markField) {
// board[row + 1][column] = "#";
// }
// empty++;
// }
// if (board[row - 1][column] == "+") {
// if (markField) {
// board[row - 1][column] = "#";
// }
// empty++;
// }
// if (board[row + 1][column] == player) {
// friends++;
// }
// if (board[row - 1][column] == player) {
// friends++;
// }
// if (board[row + 1][column] == player) {
// friendsLocation[i] = new Point(row + 1, column);
// i++;
// }
// if (board[row - 1][column] == player) {
// friendsLocation[i] = new Point(row - 1, column);
// i++;
// }
// }
// if (row == size - 1) {
// if (board[row - 1][column] == "+") {
// if (markField) {
// board[row - 1][column] = "#";
// }
// empty++;
// }
// if (board[row - 1][column] == player) {
// friends++;
// }
// if (board[row - 1][column] == player) {
// friendsLocation[i] = new Point(row - 1, column);
// }
// }
// }
// }
