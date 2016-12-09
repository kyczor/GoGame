
public class Board {
	
	int[][] tab;
//	int i;
	
	public int[][]InitTable(int i) {
		tab = new int[i][i];
		for( int a=0; a<i; a++)
		{
			for(int b=0; b<i; b++)
			{
				tab[a][b] = 0;// 0 empty field 
			}
		}
		 return tab; 
	}
	

	
	
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
	
	
	
