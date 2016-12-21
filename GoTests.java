import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GoTests {

	// two different places have not the same value of empty and friends
	@Test
	public void testLookAround() {
		Move move1 = new Move();
		Move move2 = new Move();
		move1.lookAround(2, 0, null, false);
		move2.lookAround(2, 2, null, false);
		assertNotSame(move1, move2);
	}

	// check that place is empty
	@Test
	public void testisEmpty() {
		Move move = new Move();
		move.isEmpty(1, 2);
		assertTrue(move.isEmpty(1, 2));
	}

	// check that we have 2 players
	@Test
	public void testMove() {
		Move move1 = new Move();
		Move move2 = new Move();
		move1.move(1, 2, 1);
		move2.move(2, 3, 0);
		assertNotEquals(move1, move2);
	}

	// check that we create new chain and it is not null
	@Test
	public void testnewChain() {
		Move move = new Move();
		move.newChain(2, 2, 1);
		assertNotNull(move);
	}

	@Test
	public void testConnectChains() {
		Move move1 = new Move();
		Move move2 = new Move();
		move1.connectChains(1, 1, 0);
		move2.connectChains(1, 0, 1);
		assertNotEquals(move1, move2);
	}

	/*
	 * @Test public void testCheckBreath() { Move move1 = new Move(); Move move2
	 * = new Move();
	 * 
	 * move1.checkBreath(1, 0, 1); move2.checkBreath(3, 0, 1); assertSame(move1,
	 * move2); }
	 */

	@Test
	public void testKo() {
		Move move = new Move();
		move.ko(1, 1, 0);
		assertFalse(move.ko(1, 1, 0));
	}

}
