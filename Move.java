import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Move {
	private int size = 6;
	private Object[][] board = new Object[size][size];
	private List<List<Point>> allBlackLists = new ArrayList<>();
	private List<List<Point>> allWhiteLists = new ArrayList<>();
	private int blackStones = 50;
	private int whiteStones = 50;

	public Move() {
		for (Object[] row : board)
			Arrays.fill(row, "+");
	}

	// klasa testowna do main
	public void getTab() {
		System.out.println("  0    1    2    3    4    5");
		System.out.println("0 " + board[0][0] + " -- " + board[0][1] + " -- " + board[0][2] + " -- " + board[0][3]
				+ " -- " + board[0][4] + " -- " + board[0][5]);
		System.out.println("  |    |    |    |    |    |");
		System.out.println("1 " + board[1][0] + " -- " + board[1][1] + " -- " + board[1][2] + " -- " + board[1][3]
				+ " -- " + board[1][4] + " -- " + board[1][5]);
		System.out.println("  |    |    |    |    |    |");
		System.out.println("2 " + board[2][0] + " -- " + board[2][1] + " -- " + board[2][2] + " -- " + board[2][3]
				+ " -- " + board[2][4] + " -- " + board[2][5]);
		System.out.println("  |    |    |    |    |    |");
		System.out.println("3 " + board[3][0] + " -- " + board[3][1] + " -- " + board[3][2] + " -- " + board[3][3]
				+ " -- " + board[3][4] + " -- " + board[3][5]);
		System.out.println("  |    |    |    |    |    |");
		System.out.println("4 " + board[4][0] + " -- " + board[4][1] + " -- " + board[4][2] + " -- " + board[4][3]
				+ " -- " + board[4][4] + " -- " + board[4][5]);
		System.out.println("  |    |    |    |    |    |");
		System.out.println("5 " + board[5][0] + " -- " + board[5][1] + " -- " + board[5][2] + " -- " + board[5][3]
				+ " -- " + board[5][4] + " -- " + board[5][5]);
	}

	public void game() {
		int row;
		int column;
		int i = 1;
		Scanner sc = new Scanner(System.in);
		String player = "CZARNY";

		while (blackStones != 0 || whiteStones != 0) {
			Integer blackPlayer = 1;
			Integer whitePlayer = 0;
			System.out.println("Biale kamienie = " + whiteStones + " Czarne kamienie = " + blackStones);
			System.out.println("Gracz " + player + ".");
			System.out.println("Ruch nr " + i + ".");
			System.out.print("Podaj rzad: ");
			row = sc.nextInt();
			System.out.print("Podaj kolumne: ");
			column = sc.nextInt();
			if (isEmpty(row, column)) {
				if (i % 2 != 0) {
					move(row, column, blackPlayer);
					checkBreath(row, column, whitePlayer);
					player = "BIALY";
					blackStones--;
				} else {
					move(row, column, whitePlayer);
					checkBreath(row, column, blackPlayer);
					player = "CZARNY";
					whiteStones--;
				}
				i++;
			} else {
				System.out.println("Pole jest zajete.");
			}
			getTab();
		}
	}

	public void move(int row, int column, Integer player) {
		if (board[row][column] == "+") {
			board[row][column] = player;
			if (player == 1) {
				if (countFriends(row, column, player) == 0) {
					newChain(row, column, player);
				} else {
					connectChains(row, column, player);
				}
			} else {
				if (countFriends(row, column, player) == 0) {
					newChain(row, column, player);
				} else {
					connectChains(row, column, player);
				}
			}
		} else {
			System.out.println("Zajete!");
		}
	}

	public void newChain(int row, int column, Integer player) {
		List<List<Point>> outsideList;
		String color;
		if (player == 1) {
			outsideList = allBlackLists;
			color = "BLACK";
		} else {
			outsideList = allWhiteLists;
			color = "WHITE";
		}
		List<Point> insideList = new LinkedList<>();
		insideList.add(new Point(row, column));
		outsideList.add(insideList);
		System.out.println("Nowy " + color + " chain = " + insideList);
	}

	public void connectChains(int row, int column, Integer player) {
		Point[] friends = friendsLocation(row, column, player);
		if (countFriends(row, column, player) == 1) {
			oneFriendChain(row, column, player, friends);
		} else {
			manyFriendsChain(row, column, player, friends);
		}
	}

	public void oneFriendChain(int row, int column, Integer player, Point[] friend) {
		List<List<Point>> friendsList;
		if (player == 1) {
			friendsList = allBlackLists;
		} else {
			friendsList = allWhiteLists;
		}
		for (List<Point> aFriendsList : friendsList) {
			for (int i = 0; i < aFriendsList.size(); i++) {
				if (aFriendsList.get(i).getX() == friend[0].getX() && aFriendsList.get(i).getY() == friend[0].getY()) {
					aFriendsList.add(new Point(row, column));
					System.out.println("Dodano punkt x=" + row + " y=" + column + " do " + aFriendsList);
				}
			}
		}
	}

	public void manyFriendsChain(int row, int column, Integer player, Point[] friend) {
		List<List<Point>> friendsList;
		if (player == 1) {
			friendsList = allBlackLists;
		} else {
			friendsList = allWhiteLists;
		}
		List<Point> masterList = new ArrayList<>(); // nowa lista, do ktorej
													// migruja wszystkie punkty
		int numberOfFriends = countFriends(row, column, player); // liczba
																	// sasiadow
																	// tego
																	// samego
																	// koloru
		for (List<Point> aFriendsList : friendsList) {
			for (int i = 0; i < aFriendsList.size(); i++) {
				if (aFriendsList.get(i).getX() == friend[0].getX() && aFriendsList.get(i).getY() == friend[0].getY()) {
					masterList = aFriendsList;
					masterList.add(new Point(row, column));
				}
			}
		}
		for (int j = 0; j < numberOfFriends; j++) {
			for (List<Point> aFriendsList : friendsList) {
				for (int i = 0; i < aFriendsList.size(); i++) {
					if (aFriendsList.get(i).getX() == friend[j].getX()
							&& aFriendsList.get(i).getY() == friend[j].getY()) {
						if (!masterList.containsAll(aFriendsList)) {
							masterList.addAll(aFriendsList);
							aFriendsList.clear();
							System.out.println(aFriendsList);
						}
					}
				}
			}
		}
		System.out.println("masterList" + player + " = " + masterList);
	}

	public void checkBreath(int row, int column, Integer player) {
		int numberOfFriends = countFriends(row, column, player);
		System.out.println("Liczba wrogow = " + numberOfFriends);
		if (numberOfFriends > 0) {
			List<List<Point>> friendsList;
			Map<Integer, List<Point>> toRemoveMap = new HashMap<>();
			Point[] friends = friendsLocation(row, column, player);
			int[] empty = new int[numberOfFriends];
			if (player == 1) {
				friendsList = allBlackLists;
			} else {
				friendsList = allWhiteLists;
			}
			for (int j = 0; j < numberOfFriends; j++) { // DLA KAZDEGO
														// PRZYJACIELA
				for (List<Point> aFriendsList : friendsList) { // DLA KAZDEGO
																// ELEMENTU
																// LISTY
																// ZEWNETRZNEJ
					for (int i = 0; i < aFriendsList.size(); i++) { // DLA
																	// KAZDEGO
																	// ELEMENTU
																	// LISTY
																	// WEWNETRZNEJ
						if (aFriendsList.get(i).getX() == friends[j].getX()
								&& aFriendsList.get(i).getY() == friends[j].getY()) { // JESLI
																						// WSPOLRZEDNA
																						// X-OWA
																						// I
																						// Y-OWA
																						// ELEMENTU
							// LISTY WEWNETRZNEJ=PUNKTU PRZYJACIELA W POBLIZU
							toRemoveMap.put(j, aFriendsList); // ZAPAMIETUJE
																// LISTE
																// (CHAINA)
																// PRZYJACIELA O
																// INDEKSIE "j"
							for (int k = 0; k < aFriendsList.size(); k++) {
								empty[j] = empty[j] + countEmpty((int) aFriendsList.get(k).getX(),
										(int) aFriendsList.get(k).getY()); // ZLICZANIE
																			// WOLNYCH
																			// MIEJSC
																			// DO
																			// OKOLA
																			// KAZDEGO
																			// ELEMENTU
																			// CHAINA
							}
						}
					}
				}
				System.out.println(j + " Suma oddechow = " + empty[j]);
				if (empty[j] == 0) { // JESLI SUMA PUSTYCH MIEJSC DO OKOLA
										// CHAINA O INDEKSIE "j" JEST = 0
					System.out.println("BLACK = " + allBlackLists);
					System.out.println("WHITE = " + allWhiteLists);
					for (Point point : toRemoveMap.get(j)) { // DLA KAZDEGo
																// ELEMENTU
																// ZAPAMIETANEJ
																// LISTY O
																// INDEKSIE "j"
						board[(int) point.getX()][(int) point.getY()] = "+"; // CZYSZCZENIE
																				// MIEJSC
																				// W
																				// KTORYCH
																				// CHAIN
																				// SIE
																				// UDUSIL
					}
					friendsList.remove(toRemoveMap.get(j));
					System.out.println("BLACK = " + allBlackLists);
					System.out.println("WHITE = " + allWhiteLists);
					System.out.println("Usunieto chain index = " + j);
				}
			}
		}
	}

	public boolean isEmpty(int row, int column) {
		boolean result;
		result = board[row][column] == "+" || board[row][column] == null;
		return result;
	}

	public int countEnemies(int row, int column, Integer player) {
		int enemies = 0;
		if (column == 0) {
			if (board[row][column + 1] != player && board[row][column + 1] != "+") // down
			{
				enemies++;
			}
		}
		if (column > 0 && column < size - 1) {
			if (board[row][column + 1] != player && board[row][column + 1] != "+") // down
			{
				enemies++;
			}
			if (board[row][column - 1] != player && board[row][column - 1] != "+") // down
			{
				enemies++;
			}
		}
		if (column == size) {
			if (board[row][column - 1] != player && board[row][column - 1] != "+") // down
			{
				enemies++;
			}
		}
		if (row == 0) {
			if (board[row + 1][column] != player && board[row + 1][column] != "+") // down
			{
				enemies++;
			}
		}
		if (row > 0 && row < size - 1) {
			if (board[row + 1][column] != player && board[row + 1][column] != "+") // down
			{
				enemies++;
			}
			if (board[row - 1][column] != player && board[row - 1][column] != "+") // down
			{
				enemies++;
			}
		}
		if (row == size) {
			if (board[row - 1][column] != player && board[row - 1][column] != "+") // down
			{
				enemies++;
			}
		}
		return enemies;
	}

	public int countFriends(int row, int column, Integer player) {
		int friends = 0;
		if (column == 0) {
			if (board[row][column + 1] == player) // down
			{
				friends++;
			}
		}
		if (column > 0 && column < size - 1) {
			if (board[row][column + 1] == player) // down
			{
				friends++;
			}
			if (board[row][column - 1] == player) // down
			{
				friends++;
			}
		}
		if (column == size - 1) {
			if (board[row][column - 1] == player) // down
			{
				friends++;
			}
		}
		if (row == 0) {
			if (board[row + 1][column] == player) // down
			{
				friends++;
			}
		}
		if (row > 0 && row < size - 1) {
			if (board[row + 1][column] == player) // down
			{
				friends++;
			}
			if (board[row - 1][column] == player) // down
			{
				friends++;
			}
		}
		if (row == size - 1) {
			if (board[row - 1][column] == player) // down
			{
				friends++;
			}
		}
		return friends;
	}

	public int countEmpty(int row, int column) {
		int empty = 0;
		if (column == 0) {
			if (board[row][column + 1] == "+") // down
			{
				empty++;
			}
		}
		if (column > 0 && column < size - 1) {
			if (board[row][column + 1] == "+") // down
			{
				empty++;
			}
			if (board[row][column - 1] == "+") // down
			{
				empty++;
			}
		}
		if (column == size - 1) {
			if (board[row][column - 1] == "+") // down
			{
				empty++;
			}
		}
		if (row == 0) {
			if (board[row + 1][column] == "+") // down
			{
				empty++;
			}
		}
		if (row > 0 && row < size - 1) {
			if (board[row + 1][column] == "+") // down
			{
				empty++;
			}
			if (board[row - 1][column] == "+") // down
			{
				empty++;
			}
		}
		if (row == size - 1) {
			if (board[row - 1][column] == "+") // down
			{
				empty++;
			}
		}
		return empty;
	}

	public Point[] friendsLocation(int row, int column, Integer player) {
		Point[] friends = new Point[4];
		int i = 0;
		if (column == 0) {
			if (board[row][column + 1] == player) // down
			{
				friends[i] = new Point(row, column + 1);
				i++;
			}
		}
		if (column > 0 && column < size - 1) {
			if (board[row][column + 1] == player) // down
			{
				friends[i] = new Point(row, column + 1);
				i++;
			}
			if (board[row][column - 1] == player) // down
			{
				friends[i] = new Point(row, column - 1);
				i++;
			}
		}
		if (column == size - 1) {
			if (board[row][column - 1] == player) // down
			{
				friends[i] = new Point(row, column - 1);
				i++;
			}
		}
		if (row == 0) {
			if (board[row + 1][column] == player) // down
			{
				friends[i] = new Point(row + 1, column);
				i++;
			}
		}
		if (row > 0 && row < size - 1) {
			if (board[row + 1][column] == player) // down
			{
				friends[i] = new Point(row + 1, column);
				i++;
			}
			if (board[row - 1][column] == player) // down
			{
				friends[i] = new Point(row - 1, column);
				i++;
			}
		}
		if (row == size - 1) {
			if (board[row - 1][column] == player) // down
			{
				friends[i] = new Point(row - 1, column);
			}
		}
		return friends;
	}
}
