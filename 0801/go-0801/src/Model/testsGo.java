package Model;

import org.junit.Test;

public class testsGo {
	
	/**
	 *check that 2 field are different
	 * */
	@Test
	public void testGetField() {
		Board board=new Board(9);
		Session session=new Session();
		session.setData(3, 4, Status.white, board);
		session.game();
		session.setData(3, 3, Status.black, board);
		session.game();
		org.junit.Assert.assertNotSame(board.getField(3, 4), board.getField(3,3));
	}
		
	/**
	 *check that "ko" work properly
	 * */
	@Test
	public void testKo() {
		boolean checkKo;
		Board board=new Board(9);
		Session session=new Session();
		session.setData(1, 0, Status.white, board);
		session.game();
		session.setData(0, 1, Status.white, board);
		session.game();
		session.setData(1, 2,Status.white, board);
		session.game();
		session.setData(2, 1, Status.white, board);
		session.game();
		session.setData(2, 0, Status.black, board);
		session.game();
		session.setData(3, 1, Status.black, board);
		session.game();
		session.setData(2, 2, Status.black, board);
		session.game();
		session.setData(1, 1, Status.black, board);
		session.game();
		checkKo=board.ko(2, 1, Status.white);	
		org.junit.Assert.assertTrue(checkKo);
	}
	
	/**
	 *check that when we  put the white stone at game,this place is not empty
	 * */
	@Test
	public void testIsEmpty() {
		boolean checkIsEmpty;
		Board board=new Board(9);
		Session session=new Session();
		session.setData(3, 4, Status.white, board);
		session.game();
		board=session.getBoard();
		checkIsEmpty=board.isEmpty(3, 4);	
		org.junit.Assert.assertFalse(checkIsEmpty);
	}
	
	/**
	 *check breath stone
	 * */
	@Test
	public void testCheckBreath() {
		boolean checkBreath;
		Board board=new Board(9);
		Session session=new Session();
		session.setData(1, 1, Status.white, board);
		session.game();
		session.setData(1, 0, Status.black, board);
		session.game();
		session.setData(0, 1, Status.black, board);
		session.game();
		session.setData(2, 1, Status.black, board);
		session.game();
		session.setData(1, 2, Status.white, board);
		session.game();
		checkBreath=board.checkBreath(1, 1, Status.white, false);
		org.junit.Assert.assertTrue(checkBreath);
	}
	
	/**
	 * check two difference status stone's
	 * **/
	@Test
	public void testStatus() {
		org.junit.Assert.assertNotEquals(Status.fromInt(0), Status.fromInt(1));
		org.junit.Assert.assertNotEquals(Status.black, Status.white);
	}	
}
