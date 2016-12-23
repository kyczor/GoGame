import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface Functions {
	// void getTab();

	boolean game() throws ArrayException;

	void move(int row, int column, Integer player);

	void newChain(int row, int column, Integer player);

	void connectChains(int row, int column, Integer player);

	void oneFriendChain(int row, int column, Integer player, Point[] friend);

	void manyFriendsChain(int row, int column, Integer player, Point[] friend);

	void checkBreath(int row, int column, Integer player);

	void removeList(int index, Map<Integer, List<Point>> map, List<List<Point>> list);

	void cleanEmpty();

	boolean isEmpty(int row, int column);

	boolean ko(int row, int column, Integer player);

	void lookAround(int row, int column, Integer player, boolean markField);

}
