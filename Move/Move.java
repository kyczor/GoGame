import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

//try{

public class Move implements Functions {

	// ArrayException exception = new ArrayException();
	public int size = 6;
	private Object[][] board = new Object[size][size];
	public List<List<Point>> allBlackLists = new ArrayList<>();
	private List<List<Point>> allWhiteLists = new ArrayList<>();
	private int blackStones = 50;
	private int whiteStones = 50;
	private int empty;
	private int friends;
	public Point[] friendsLocation;

	public Move() {
		for (Object[] row : board)
			Arrays.fill(row, "+");
	}

	// testowe
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

	public void game() throws ArrayException {
		int row = 0;
		int column = 0;
		int i = 1;
		boolean checkKo = false;
		Integer blackPlayer = 1;
		Integer whitePlayer = 0;
		Scanner sc = new Scanner(System.in);
		String player = "BLACK";
		System.out.println("When you will game with bot  press 1, when you will play with human press 2");
		int input = sc.nextInt();
		while (blackStones != 0 || whiteStones != 0) {
			boolean failure = true;

			if (i % 2 != 0) {
				while (failure) {
					// System.out.println("White stones = " + whiteStones +
					// "
					// Black stones = " + blackStones);
					System.out.println("Player " + player);
					System.out.println("Move nr " + i);
					System.out.println("Give row: ");
					row = sc.nextInt();
					if (row > size)
						throw new ArrayException("Pawn outside board");
					// throw new ArrayIndexOutOfBoundsException();
					// exception.sendMessage();
					System.out.println("Give column: ");
					column = sc.nextInt();
					if (column > size)
						throw new ArrayException(" Pawn outside board");
					// throw new ArrayIndexOutOfBoundsException();
					// exception.sendMessage();
					empty = 0;
					lookAround(row, column, null, false);
					if (isEmpty(row, column)) {
						if (empty > 0) {
							move(row, column, blackPlayer);
							checkBreath(row, column, whitePlayer);
							player = "WHITE";
							blackStones--;
							failure = false;
						} else {
							if ((ko(row, column, blackPlayer)) && (checkKo == false)) {
								// checkKo = true;
								// System.out.println("W\n\n\n");
								move(row, column, blackPlayer);
								checkBreath(row, column, whitePlayer);
								player = "WHITE";
								blackStones--;
								blackStones--;
								failure = false;
								checkKo = true;

							} else {
								// checkKo=false;
								System.out.println("Illegal move");
								failure = true;
							}
						}
					} else {
						System.out.println("Field is occupied.");
					}
				}
			} else {
				while (failure) {
					// System.out.println("White stones = " + whiteStones +
					// "
					// Black stones = " + blackStones);

					if (input == 1) {
						System.out.println("Bot " + player + ".");
						System.out.println("Move nr " + i + ".");

						Random rand1 = new Random();
						Random rand2 = new Random();
						row = rand1.nextInt(size) - 1;

						column = rand2.nextInt(size) - 1;
						System.out.println("field : " + row + "  " + column);
					}

					else if (input == 2) {
						System.out.println("Player " + player + ".");
						System.out.println("Move nr " + i + ".");
						System.out.println("Give row: ");
						row = sc.nextInt();
						System.out.println("Give column: ");
						column = sc.nextInt();
					}

					else
						throw new ArrayException("You do not press 1 or 2. Fail");

					empty = 0;
					lookAround(row, column, null, false);
					if (isEmpty(row, column)) {
						if (empty > 0) {
							move(row, column, whitePlayer);
							checkBreath(row, column, blackPlayer);
							player = "BLACK";
							whiteStones--;
							failure = false;
						} else {
							if ((ko(row, column, whitePlayer)) && (checkKo == false)) {
								move(row, column, whitePlayer);
								checkBreath(row, column, blackPlayer);
								player = "BLACK";
								blackStones--;
								failure = false;
								checkKo = true;
							} else {
								System.out.println("Illegal move");
								failure = true;
							}
						}
					} else {
						System.out.println("Field is occupied.");
					}
				}
			}
			i++;
			getTab();
		}
	}

	public void move(int row, int column, Integer player) {
		if (board[row][column] == "+") {
			board[row][column] = player;
			friends = 0;
			lookAround(row, column, player, false);
			if (friends == 0) {
				newChain(row, column, player);
			} else {
				connectChains(row, column, player);
			}
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
		System.out.println("New " + color + " chain = " + insideList);
	}

	public void connectChains(int row, int column, Integer player) {
		friends = 0;
		friendsLocation = new Point[4];
		lookAround(row, column, player, false);
		if (friends == 1) {
			oneFriendChain(row, column, player, friendsLocation);
		} else {
			manyFriendsChain(row, column, player, friendsLocation);
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
					System.out.println("Add a point x=" + row + " y=" + column + " to " + aFriendsList);
				}
			}
		}
	}

	public void manyFriendsChain(int row, int column, Integer player, Point[] friend) {
		friends = 0;
		lookAround(row, column, player, false);
		List<List<Point>> friendsList;
		List<Point> listToRemove = new ArrayList<>();
		if (player == 1) {
			friendsList = allBlackLists;
		} else {
			friendsList = allWhiteLists;
		}
		List<Point> masterList = new ArrayList<>(); // NOWA LISTA, DO KTOREJ
													// MIGRUJA WSZYSTKIE PUNKTY
		for (List<Point> aFriendsList : friendsList) {
			for (int i = 0; i < aFriendsList.size(); i++) {
				if (aFriendsList.get(i).getX() == friend[0].getX() && aFriendsList.get(i).getY() == friend[0].getY()) {
					masterList = aFriendsList;
					masterList.add(new Point(row, column));
				}
			}
		}
		for (int j = 0; j < friends; j++) {
			for (List<Point> aFriendsList : friendsList) {
				for (int i = 0; i < aFriendsList.size(); i++) {
					if (aFriendsList.get(i).getX() == friend[j].getX()
							&& aFriendsList.get(i).getY() == friend[j].getY()) {
						if (!masterList.containsAll(aFriendsList)) {
							masterList.addAll(aFriendsList);
							listToRemove = aFriendsList;
						}
					}
				}
			}
		}
		friendsList.remove(listToRemove);
		System.out.println("masterList" + player + " = " + masterList);
	}

	public void checkBreath(int row, int column, Integer player) {
		friends = 0;
		friendsLocation = new Point[4];
		lookAround(row, column, player, false);
		System.out.println("the number of enemies = " + friends);
		if (friends > 0) {
			List<List<Point>> friendsList;
			Map<Integer, List<Point>> toRemoveMap = new HashMap<>();
			int[] sumEmpty = new int[friends];
			if (player == 1) {
				friendsList = allBlackLists;
			} else {
				friendsList = allWhiteLists;
			}
			for (int j = 0; j < friends; j++) { // DLA KAZDEGO PRZYJACIELA
				for (List<Point> aFriendsList : friendsList) { // DLA KAZDEGO
																// ELEMENTU
																// LISTY
																// ZEWNETRZNEJ
					for (int i = 0; i < aFriendsList.size(); i++) { // DLA
																	// KAZDEGO
																	// ELEMENTU
																	// LISTY
																	// WEWNETRZNEJ
						if (aFriendsList.get(i).getX() == friendsLocation[j].getX()
								&& aFriendsList.get(i).getY() == friendsLocation[j].getY()) { // JESLI
																								// WSPOLRZEDNA
																								// X-OWA
																								// I
																								// Y-OWA
																								// ELEMENTU
																								// LISTY
																								// WEWNETRZNEJ
																								// =
																								// PUNKTU
																								// PRZYJACIELA
																								// W
																								// POBLIZU
							toRemoveMap.put(j, aFriendsList); // ZAPAMIETUJE
																// LISTE
																// (CHAINA)
																// PRZYJACIELA O
																// INDEKSIE "j"
							for (int k = 0; k < aFriendsList.size(); k++) {
								empty = 0;
								lookAround((int) aFriendsList.get(k).getX(), (int) aFriendsList.get(k).getY(), null,
										true); // TRUE = PUSTE MIEJSCA JUZ
												// SPRAWDZONE ZAZNACZONE SA "#"
								sumEmpty[j] = sumEmpty[j] + empty; // ZLICZANIE
																	// WOLNYCH
																	// MIEJSC DO
																	// OKOLA
																	// KAZDEGO
																	// ELEMENTU
																	// CHAINA
							}
							cleanEmpty();
						}
					}
				}
				System.out.println(j + " The sum of breaths = " + sumEmpty[j]);
				if (sumEmpty[j] == 0) { // JESLI SUMA PUSTYCH MIEJSC DO OKOLA
										// CHAINA O INDEKSIE "j" JEST = 0
					removeList(j, toRemoveMap, friendsList);
				}
			}
		}
	}

	public void removeList(int index, Map<Integer, List<Point>> map, List<List<Point>> list) {
		System.out.println("BLACK = " + allBlackLists);
		System.out.println("WHITE = " + allWhiteLists);
		for (Point point : map.get(index)) { // DLA KAZDEGO ELEMENTU
												// ZAPAMIETANEJ LISTY O INDEKSIE
												// "j"
			board[(int) point.getX()][(int) point.getY()] = "+"; // CZYSZCZENIE
																	// MIEJSC Z
																	// "#" W
																	// KTORYCH
																	// CHAIN SIE
																	// UDUSIL
		}
		list.remove(map.get(index));
		System.out.println("BLACK = " + allBlackLists);
		System.out.println("WHITE = " + allWhiteLists);
		System.out.println("Delet chain index = " + index);
	}

	public void cleanEmpty() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == "#") {
					board[i][j] = "+";
				}
			}
		}
	}

	public boolean isEmpty(int row, int column) {
		boolean result;
		result = board[row][column] == "+" || board[row][column] == null;
		return result;
	}

	@Override
	public boolean ko(int row, int column, Integer player) {
		boolean allow = false;
		if (column > 0 && column < size - 1 && row > 0 && row < size - 1) {
			friends = 0;
			lookAround(row, column + 1, player, false);
			System.out.println("column + 1 friends = " + friends);
			if (friends == 3) {
				allow = true;
			} else {
				friends = 0;
				lookAround(row, column - 1, player, false);
				System.out.println("column - 1 friends = " + friends);
				if (friends == 3) {
					allow = true;
				} else {
					friends = 0;
					lookAround(row + 1, column, player, false);
					System.out.println("row + 1 friends = " + friends);
					if (friends == 3) {
						allow = true;
					} else {
						friends = 0;
						lookAround(row - 1, column, player, false);
						System.out.println("row - 1 friends = " + friends);
						if (friends == 3) {
							allow = true;
						}
					}
				}
			}
		}
		return allow;
	}

	public void lookAround(int row, int column, Integer player, boolean markField) { // markField
																						// -
																						// POTRZEBNE
																						// DO
																						// SPRAWDZANIA
																						// WOLNYCH
																						// MIEJSC
																						// WOKOL
																						// CHAINA,
																						// ZEBY
																						// POMINAC
																						// JUZ
																						// SPRAWDZONE
																						// POLE
		int i = 0;
		if (column == 0) {
			if (board[row][column + 1] == "+") {
				if (markField) {
					board[row][column + 1] = "#";
				}
				empty++;
			}
			if (board[row][column + 1] == player) {
				friends++;
			}
			if (board[row][column + 1] == player) {
				friendsLocation[i] = new Point(row, column + 1);
				i++;
			}
		}
		if (column > 0 && column < size - 1) {
			if (board[row][column + 1] == "+") {
				if (markField) {
					board[row][column + 1] = "#";
				}
				empty++;
			}
			if (board[row][column - 1] == "+") {
				if (markField) {
					board[row][column - 1] = "#";
				}
				empty++;
			}
			if (board[row][column + 1] == player) {
				friends++;
			}
			if (board[row][column - 1] == player) {
				friends++;
			}
			if (board[row][column + 1] == player) {
				friendsLocation[i] = new Point(row, column + 1);
				i++;
			}
			if (board[row][column - 1] == player) {
				friendsLocation[i] = new Point(row, column - 1);
				i++;
			}
		}
		if (column == size - 1) {
			if (board[row][column - 1] == "+") {
				if (markField) {
					board[row][column - 1] = "#";
				}
				empty++;
			}
			if (board[row][column - 1] == player) {
				friends++;
			}
			if (board[row][column - 1] == player) {
				friendsLocation[i] = new Point(row, column - 1);
				i++;
			}
		}
		if (row == 0) {
			if (board[row + 1][column] == "+") {
				if (markField) {
					board[row + 1][column] = "#";
				}
				empty++;
			}
			if (board[row + 1][column] == player) {
				friends++;
			}
			if (board[row + 1][column] == player) {
				friendsLocation[i] = new Point(row + 1, column);
				i++;
			}
		}
		if (row > 0 && row < size - 1) {
			if (board[row + 1][column] == "+") {
				if (markField) {
					board[row + 1][column] = "#";
				}
				empty++;
			}
			if (board[row - 1][column] == "+") {
				if (markField) {
					board[row - 1][column] = "#";
				}
				empty++;
			}
			if (board[row + 1][column] == player) {
				friends++;
			}
			if (board[row - 1][column] == player) {
				friends++;
			}
			if (board[row + 1][column] == player) {
				friendsLocation[i] = new Point(row + 1, column);
				i++;
			}
			if (board[row - 1][column] == player) {
				friendsLocation[i] = new Point(row - 1, column);
				i++;
			}
		}
		if (row == size - 1) {
			if (board[row - 1][column] == "+") {
				if (markField) {
					board[row - 1][column] = "#";
				}
				empty++;
			}
			if (board[row - 1][column] == player) {
				friends++;
			}
			if (board[row - 1][column] == player) {
				friendsLocation[i] = new Point(row - 1, column);
			}
		}
	}
}
