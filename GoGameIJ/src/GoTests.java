import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class GoTests {

	// check that place is empty
	// @Test
	// public void testicsEmpty() {
	// Board board = new Board(5);
	// board.tab.get(2).set(2, whosefield.black)
	// }

	// check that we have empty table while creating.
	@Test
	public void testisEmpty() {
		Board board = new Board(5);
		board.tab.get(0).set(0, whosefield.black);
		assertNotSame(whosefield.empty, board.getField(0, 0));
	}

	@Test
	public void testRemovePawnWithEnemyAround() {
		Board board = new Board(5);
		Move move = new Move();
		board.tab.get(1).set(0, whosefield.black);
		board.tab.get(2).set(1, whosefield.black);
		board.tab.get(0).set(1, whosefield.black);
		board.tab.get(1).set(1, whosefield.white);
		move.getData(1, 2, whosefield.black, board);
		org.junit.Assert.assertSame(board.tab.get(0).get(0), whosefield.empty);
	}

	@Test
	public void testPutPawnInSelectedPlace() {
		Board board = new Board(9);
		Move move = new Move();
		move.getData(2, 2, whosefield.black, board);
		move.game();
		board = move.getBoard();
		org.junit.Assert.assertSame(board.tab.get(2).get(2), whosefield.black);
	}

	@Test
	public void testCornerCase() {
		Board board = new Board(9);
		Move move = new Move();
		board.tab.get(0).set(0, whosefield.white);
		board.tab.get(0).set(1, whosefield.black);
		move.getData(1, 0, whosefield.black, board);
		move.game();
		board = move.getBoard();
		// assertNotSame(whosefield.black, board.getField(0, 0));
		org.junit.Assert.assertSame(board.getField(0, 0), whosefield.empty);
	}
}
