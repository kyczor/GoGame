import java.io.Serializable;

/**
 * This class can create a new table.
 * Also, it does everything concerning the game rules and player moves.
 * 
 * @author Karola
 * @see EchoServer
 *
 */
public class Board implements Serializable 
{
	
	int[][] tab;
//	int i;
	
	/**
	 * 
	 * @param i size of the table defined by user
	 * @return returns new table (ixi) filled with 0's
	 */
	public int[][]InitTable(int i)
	{
		tab = new int[i][i];
		for( int a=0; a<i; a++)
		{
			for(int b=0; b<i; b++)
			{
				tab[a][b] = 0;// 0 = empty field 
			}
		}
		 return tab; 
	}
	
	/**
	 * 
	 * @param move move send to server by a client
	 * @return FOR NOW, true = end of the game, false = continue game 
	 */
	public boolean HandleMove(Object move)
	{
		if(move instanceof String)
		{
			tab[1][1] = 7;
			if(move.equals("bye"))
				return true;
		}//else if (move instanceof GiveUpClass)  <--- example
		
		return false;
	}
	
	/**
	 * 
	 * @return converts tab into string
	 */
	public String AsString()
	{
		String s = "";
		for(int[] et: tab)
		{
			for(int e:et)
			{
				s = s + e + " ";
			}
		}
		return s;
	}
	
//	public void SendTable(int )  // <----
//	{
//		
//	}
	
	
}
//	
//	  public int[][] Testing(int[][]arr){
//		    int[][]newArr=new int[arr.length][arr[0].length];
//		    for(int i=0;i<arr.length;i++)
//		    {
//		        for(int j=0;j<arr[0].length;j++)
//		        {
//		            newArr[i][j]=arr[i][arr[0].length-1-j];
//		        }
//
//		    }
//		    return newArr; // rerunning the array witch created inside this method.
//		}
//}
	
	
	
