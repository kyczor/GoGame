package Model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board implements  Logic , java.io.Serializable {

	private int size;
	private Status[][] board;
	private List<List<Point>> allBlackLists =new ArrayList<>();
	private List<List<Point>> allWhiteLists = new ArrayList<>();
	private int empty;
	private int friends;
	private Point[] friendsLocation;

	public Board(int size) {
		this.size = size;
		board = new Status[size][size];
		for (Object[] row : board)
			Arrays.fill(row, Status.empty);
	}
	
	/**
	 * @return size of board
	 * */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * @return number of surrounding empty places
	 * */
	@Override
	public int getEmptyNumber() {
		return empty;
	}

	/**
	 * set number of surrounding empty places 
	 * */
	@Override
	public void setEmptyNumber(int value) {
		empty = value;
	}

	/**
	 * @return number of surrounding friends
	 * */
	@Override
	public int getFriendsNumber() {
		return friends;
	}

	/**
	 * set number of surrounding friends
	 * */
	@Override
	public void setFriendsNumber(int value) {
		friends = value;
	}
	
	
	/**
	 * @return board[y][x] 
	 * */
	@Override
	public Status getField(int y, int x) {
		return board[y][x];
	}

	/**
	 * place player's pawn color at position y, x 
	 * */
	@Override
	public void move(int y, int x, Status color) {
		board[y][x] = color;
	}

	/**
	 * create new chain of size 1 when pawn hasn't friends around or connect chains when pawn has friends
	 * */
	@Override
	public void chains(int y, int x, Status color) {
		friends = 0;
		lookAround(y, x, color, false);
		if (friends == 0) {
			newChain(y, x, color);
		} else {
			connectChains(y, x, color);
		}
	}

	/**
	 * create new chain (insideList) and add it to certain outsideList depending on the pawn's color
	 * */
	@Override
	public void newChain(int y, int x, Status color) {
		List<List<Point>> outsideList;
		if (color == Status.black) {
			outsideList = allBlackLists;
		} else {
			outsideList = allWhiteLists;
		}
		List<Point> insideList = new LinkedList<>();
		insideList.add(new Point(y, x));
		outsideList.add(insideList);
	}
	
	/**
	 * connect chains depending on the quantity of surrounding friends
	 * */
	@Override
	public void connectChains(int y, int x, Status color) {
		friends = 0;
		friendsLocation = new Point[4];
		lookAround(y, x, color, false);
		if (friends == 1) {
			oneFriendChain(y, x, color, friendsLocation);
		} else {
			manyFriendsChain(y, x, color, friendsLocation);
		}
	}

	/**
	 * connect one chain with another with the same pawn's color
	 * */	
	@Override
	public void oneFriendChain(int y, int x, Status color, Point[] friend) {
		List<List<Point>> friendsList;
		if (color == Status.black) {
			friendsList = allBlackLists;
		} else {
			friendsList = allWhiteLists;
		}
		for (List<Point> aFriendsList : friendsList) {
			for (int i = 0; i < aFriendsList.size(); i++) {
				if (aFriendsList.get(i).getX() == friend[0].getX() && aFriendsList.get(i).getY() == friend[0].getY()) {
					aFriendsList.add(new Point(y, x));
				}
			}
		}
	}

	/**
	 * connect one chain with two or more having the same pawn's color, create new list (masterList) to merge all points and remove old list
	 * */	
	@Override
	public void manyFriendsChain(int y, int x, Status color, Point[] friend) {
		friends = 0;
		lookAround(y, x, color, false);
		List<List<Point>> friendsList;
		List<Point> listToRemove = new ArrayList<>();
		if (color == Status.black) {
			friendsList = allBlackLists;
		} else {
			friendsList = allWhiteLists;
		}
		List<Point> masterList = new ArrayList<>(); 
		for (List<Point> aFriendsList : friendsList) {
			for (int i = 0; i < aFriendsList.size(); i++) {
				if (aFriendsList.get(i).getX() == friend[0].getX() && aFriendsList.get(i).getY() == friend[0].getY()) {
					masterList = aFriendsList;
					masterList.add(new Point(y, x));
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
	}

	/**
	 * at position y, x for certain player check if there are friendly pawns. 
	 * If so, find their chains (lists), count breath for the whole chain.
	 * If parameter remove is true, each chain with 0 breaths is deleted.
	 * @return hasBreath = true if breaths are higher than 1 to prevent suicide.
	 * */
	@Override
	public boolean checkBreath(int y, int x, Status color, boolean remove) {
		boolean hasBreath = false;
		friends = 0;
		friendsLocation = new Point[4];
		lookAround(y, x, color, false);
		if (friends > 0) {
			List<List<Point>> friendsList;
			Map<Integer, List<Point>> toRemoveMap = new HashMap<>();
			int[] sumEmpty = new int[friends];
			if (color == Status.black) {
				friendsList = allBlackLists;
			} else {
				friendsList = allWhiteLists;
			}
			int numberOfFriends = friends;
			for (int j = 0; j < numberOfFriends; j++) { //for all friends
				for (List<Point> aFriendsList : friendsList) { //for each element external list
					for (int i = 0; i < aFriendsList.size(); i++) {//for each element of list of internal 
						if (aFriendsList.get(i).getX() == friendsLocation[j].getX()
								&& aFriendsList.get(i).getY() == friendsLocation[j].getY()) {//coordinate X and Y element list of internal=point friend near
							toRemoveMap.put(j, aFriendsList); //remember list(chain) friend at index 'j'
							for (int k = 0; k < aFriendsList.size(); k++) {
								empty = 0;
								lookAround((int) aFriendsList.get(k).getX(), (int) aFriendsList.get(k).getY(), color,
										true); //empty fields are checked and selected how status.checked
								sumEmpty[j] = sumEmpty[j] + empty; //couting free field around every element chain	
							}
							cleanEmpty();
						}
					}
				}
				if (remove) {
					if (sumEmpty[j] == 0) { //sum empty filed around chain at index 'j'=0
						removeList(j, toRemoveMap, friendsList);
					}
				}
					if (sumEmpty[j] > 1) {
						hasBreath = true;
					}
			}
		}
		return hasBreath;
	}

	/**
	 * remove list 
	 * */
	@Override
	public void removeList(int index, Map<Integer, List<Point>> map, List<List<Point>> list) {
		for (Point point : map.get(index)) { //for every element memorized list at list 'j'
			board[(int) point.getX()][(int) point.getY()] = Status.empty; // after the suffocation chain
		}
		list.remove(map.get(index));
	}
	
	/**
	 * change status of field from checked to empty.
	 * */
	@Override
	public void cleanEmpty() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == Status.checked) {
					board[i][j] = Status.empty;
				}
			}
		}
	}

	/**
	 * check if a place where we want to put a pawn is empty
	 * */
	@Override
	public boolean isEmpty(int y, int x) {
		boolean result;
		result = board[y][x] == Status.empty || board[y][x] == null;
		return result;
	}

	/**
	 * check if the move is allowed, because of KO, or not allowed meaning suicide.
	 * @return allow
	 * */
	@Override
	public boolean ko(int y, int x, Status color) {
		boolean allow = false;
		if (x > 0 && x < size - 1 && y > 0 && y < size - 1) {
			friends = 0;
			lookAround(y, x + 1, color, false);
			if (friends == 3) {
				allow = true;
			} else {
				friends = 0;
				lookAround(y, x - 1, color, false);
				if (friends == 3) {
					allow = true;
				} else {
					friends = 0;
					lookAround(y + 1, x, color, false);
					if (friends == 3) {
						allow = true;
					} else {
						friends = 0;
						lookAround(y - 1, x, color, false);
						if (friends == 3) {
							allow = true;
						}
					}
				}
			}
		}
		return allow;
	}
	
/** 
 *at position y, x, scan surrounding fields and count empty places, 
 *number of pawn's with the same colors (friends) and get these friends location.
 *@param markField used to prevent scanning the same field for empty place more than once during checkBreath procedure.
 *it changes status of field from empty to checked.
 * */
	@Override
	public void lookAround(int y, int x, Status color, boolean markField) {
		int i = 0;
		if (x == 0) {
			if (board[y][x + 1] == Status.empty) {
				if (markField) {
					board[y][x + 1] = Status.checked;
				}
				empty++;
			}
			if (board[y][x + 1] == color) {
				friends++;
			}
			if (board[y][x + 1] == color) {
				friendsLocation[i] = new Point(y, x + 1);
				i++;
			}
		}
		if (x > 0 && x < size - 1) {
			if (board[y][x + 1] == Status.empty) {
				if (markField) {
					board[y][x + 1] = Status.checked;
				}
				empty++;
			}
			if (board[y][x - 1] == Status.empty) {
				if (markField) {
					board[y][x - 1] = Status.checked;
				}
				empty++;
			}
			if (board[y][x + 1] == color) {
				friends++;
			}
			if (board[y][x - 1] == color) {
			}
			if (board[y][x + 1] == color) {
				friendsLocation[i] = new Point(y, x + 1);
				i++;
			}
			if (board[y][x - 1] == color) {
				friendsLocation[i] = new Point(y, x - 1);
				i++;
			}
		}
		if (x == size - 1) {
			if (board[y][x - 1] == Status.empty) {
				if (markField) {
					board[y][x - 1] = Status.checked;
				}
				empty++;
			}
			if (board[y][x - 1] == color) {
				friends++;
			}
			if (board[y][x - 1] == color) {
				friendsLocation[i] = new Point(y, x - 1);
				i++;
			}
		}
		if (y == 0) {
			if (board[y + 1][x] == Status.empty) {
				if (markField) {
					board[y + 1][x] = Status.checked;
				}
				empty++;
			}
			if (board[y + 1][x] == color) {
				friends++;
			}
			if (board[y + 1][x] == color) {
				friendsLocation[i] = new Point(y + 1, x);
				i++;
			}
		}
		if (y > 0 && y < size - 1) {
			if (board[y + 1][x] == Status.empty) {
				if (markField) {
					board[y + 1][x] = Status.checked;
				}
				empty++;
			}
			if (board[y - 1][x] == Status.empty) {
				if (markField) {
					board[y - 1][x] = Status.checked;
				}
				empty++;
			}
			if (board[y + 1][x] == color) {
				friends++;
			}
			if (board[y - 1][x] == color) {
				friends++;
			}
			if (board[y + 1][x] == color) {
				friendsLocation[i] = new Point(y + 1, x);
				i++;
			}
			if (board[y - 1][x] == color) {
				friendsLocation[i] = new Point(y - 1, x);
				i++;
			}
		}
		if (y == size - 1) {
			if (board[y - 1][x] == Status.empty) {
				if (markField) {
					board[y - 1][x] = Status.checked;
				}
				empty++;
			}
			if (board[y - 1][x] == color) {
				friends++;
			}
			if (board[y - 1][x] == color) {
				friendsLocation[i] = new Point(y - 1, x);
			}
		}
	}
}
