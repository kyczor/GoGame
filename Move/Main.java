public class Main {
	public static void main(String[] args) {
		try {
			Move move = new Move();
			move.getTab();
			move.game();
		} catch (ArrayException e) {
			System.out.println(e.getMessage());
			// break;
		}
	}
}