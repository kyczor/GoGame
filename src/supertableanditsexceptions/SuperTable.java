package supertableanditsexceptions;

public interface SuperTable 
{
	void CreateTable();
	/**
	 * Creates a table with empty fields before the game
	 */
	
	void NewMove() throws IllegalMoveException;
	/**
	 * @throws TheOtherPlayerDecidesException if the move is illegal
	 */
	
	void CheckIfKo() throws KoException;
	/**
	 * @throws KoException if there would be a Ko situation
	 */
	
	void EndOfGame() throws TheOtherPlayerDecidesException;
	/**
	 * When both players agree, the game ends and points are being counted
	 * @throws TheOtherPlayerDecidesException
	 */
	
	void GiveUp();
	/**
	 * A player decides to give up. The game ends and the other player wins
	 */
	
	//mozna tu dopisac wiecej, na razie tyle wymyslilam.
	
}
