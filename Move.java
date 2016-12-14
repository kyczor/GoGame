import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Move {
	private Object[][] tab = new Object[19][19];
	private String blackPlayer = "Black";
	private String whitePlayer = "White";
	private List<Point> listBlack = new LinkedList<Point>();
	private List<Point> listWhite = new LinkedList<Point>();
	private List<List<Point>> allBlackLists = new ArrayList<>();
	private List<List<Point>> allWhitekLists = new ArrayList<>();

	Move() {

	}

	public void getTab() {
		System.out.println(Arrays.deepToString(tab));
	}

	public void game() {
		int row;
		int column;
		int i = 0;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Enter row: ");
			row = sc.nextInt();
			System.out.print("Enter column: ");
			column = sc.nextInt();
			if (i % 2 == 0) {
				if (isEmpty(row, column)) {
					move(blackPlayer, row, column);
				}
			} else {
				if (isEmpty(row, column)) {
					move(whitePlayer, row, column);
				}
			}
			i++;
			System.out.println("Ruch nr " + i);
		}
	}

	public void move(String player, int row, int column) {
		if (tab[row][column] == null) {
			tab[row][column] = player;
			Point result;

			if (player == "Black") {
				if (!checkIsChain(row, column, player)) {
					List<Point> blackChain = new LinkedList<Point>();
					blackChain.add(new Point(row, column));
					allBlackLists.add(blackChain);

				} else {
					for (int i = 0; i < allBlackLists.size(); i++) {
						for (int j = 0; j < allBlackLists.get(i).size(); j++) {
							/*
							 * if (allBlackLists.get(i).get(j).getX() == &&
							 * allBlackLists.get(i).get(j).getY() == ) {
							 * 
							 * }
							 */
						}

					}
				}
			} else {
				listWhite.add(new Point(row, column));
			}

		} else {
			System.out.println("Zajete!");
		}
	}

	public boolean isEmpty(int row, int column) {
		boolean result;
		if (tab[row][column] != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public int countEnemy(int row, int column, String player) {
		int enemy = 0;
		if (tab[row + 1][column] != null || tab[row + 1][column] != player) // down
		{
			enemy++;
		}
		if (tab[row - 1][column] != null || tab[row - 1][column] != player) // up
		{
			enemy++;
		}
		if (tab[row][column + 1] != null || tab[row][column + 1] != player) // right
		{
			enemy++;
		}
		if (tab[row][column - 1] != null || tab[row][column - 1] != player) // left
		{
			enemy++;
		}
		return enemy;
	}

	public int countFriendly(int row, int column, String player) {
		int friendly = 0;
		if (tab[row + 1][column] == player) // down
		{
			friendly++;
		}
		if (tab[row - 1][column] == player) // up
		{
			friendly++;
		}
		if (tab[row][column + 1] == player) // right
		{
			friendly++;
		}
		if (tab[row][column - 1] == player) // left
		{
			friendly++;
		}
		return friendly;
	}

	public int countEmpty(int row, int column) {
		int empty = 0;
		if (tab[row + 1][column] == null) // down
		{
			empty++;
		}
		if (tab[row - 1][column] == null) // up
		{
			empty++;
		}
		if (tab[row][column + 1] == null) // right
		{
			empty++;
		}
		if (tab[row][column - 1] == null) // left
		{
			empty++;
		}
		return empty;
	}

	public boolean checkIsChain(int row, int column, String player) {
		boolean result = false;
		if ((tab[row + 1][column] == player) || // down
				(tab[row - 1][column] == player) || // up
				(tab[row][column + 1] == player) || // right
				(tab[row][column - 1] == player) // left
		) {
			result = true;
		}

		return result;
	}
	/*
	 * public Point friendlyLocation (int row, int column, String player) {
	 * 
	 * }
	 */
}
