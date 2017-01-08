package Model;
import Server.Functions;

public class Session implements Functions {

    private int x, y;
    Status player;
    private Board board;

    /**
     * read all the needed data
     * 
     * @param board actual board
     * @param x column of move
     * @param y row of move
     * @param player which player did the move
     */
    public void setData(int y, int x, Status player, Board board) {
        this.board = board;
        this.y = y;
        this.x = x;
        this.player = player;
    }

    /**
     * @param player who did the move
     * @throws ArrayException when can not make move
     * @return true if can make move
     */
    public boolean game() throws ArrayException{
        Status enemy;
        if (player == Status.black){
            enemy = Status.white;

        } else {
            enemy = Status.black;
        }
        boolean checkKo = false;
        board.setEmptyNumber(0);
        board.setFriendsNumber(0);
        board.lookAround(y, x, player, false);
        if (board.isEmpty(y, x)) {
            if (board.getEmptyNumber() > 0 || board.checkBreath(y, x, player, false)) {
            	board.checkBreath(y, x, player, false);
                board.move(y, x, player);
                board.chains(y, x, player);
                board.checkBreath(y, x, enemy, true);
                return true;
            } 
            else {
                if ((board.ko(y, x, player)) && (checkKo==false)) {
                    board.move(y, x, player);
                    board.checkBreath(y, x, enemy, true);
                    checkKo = true;
                    return true;
                } else {
                	//throw new ArrayException("Illegal move");
                	return false;
                }
            }
        } else
        {
        	//throw new ArrayException("Illegal move");
        	return false;
        }
    }

    /**
     * @return board
     */
    public Board getBoard() {
        return board;
    }
}
