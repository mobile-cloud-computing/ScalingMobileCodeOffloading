/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package cs.mc.ut.ee.logic;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;


public class MiniMaxRemote extends CloudRemotable {

	int chess[];
	int maxValue=1000;
	int minValue=-1000;
	
	
	public MiniMaxRemote(){
		
	}
		
	
	
	public void setChessStatus(int [][] chessBoard){
		
		
		chess = new int[64];
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				chess[j*8+i]=chessBoard[i][j];
	}
	
	
	public int[] getPossibleMove(int x, int y, int[] board)
	{
		int pos = y*8+x;
		int n = 0;
		int a[] = new int[64];
		int[][] king = {{0,-1},{0,1},
						{1,0},{-1,0},
						{1,-1},{1,1},
						{-1,1},{-1,-1}};
		
		int[][] queen = {{0,-1},{0,1},
						 {1,0},{-1,0},
						 {1,-1},{1,1},
						 {-1,1},{-1,-1}};
		
		int[][] rook = {{0,-1},{0,1},
						{1,0},{-1,0}};

		int[][] bishop = {{1,-1},{1,1},
						  {-1,1},{-1,-1}};

		int[][] knight = {{1,2},{1,-2},
						  {-1,2},{-1,-2},
						  {2,1},{2,-1},
						  {-2,1},{-2,-1}};
		
		
		switch(Math.abs(board[pos]))
		{
			case 1://King
				for (int move=0;move<4;move++)
				{
					int i=x+king[move][0],j=y+king[move][1];
					if ((i>=0)&&(i<8)&&(j>=0)&&(j<8))						
						if((board[j*8+i]==0)||(board[j*8+i]*board[pos]<0))
						{
							a[n]=j*8+i;
							n++;
						}
	
				}
			//===
				
				
				break;
				
			case 2://Queen
				for (int move=0;move<8;move++)	
					for (int i=x+queen[move][0],j=y+queen[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=queen[move][0], j+=queen[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				
				break;
			case 3://Rook
				for (int move=0;move<4;move++)	
					for (int i=x+rook[move][0],j=y+rook[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=rook[move][0], j+=rook[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				break;
			case 4://Bishop
				for (int move=0;move<4;move++)	
					for (int i=x+bishop[move][0],j=y+bishop[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=bishop[move][0], j+=bishop[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				break;
			case 5://Knight
				for (int move=0;move<8;move++)
				{
					int i=x+knight[move][0],j=y+knight[move][1];
					if ((i>=0)&&(i<8)&&(j>=0)&&(j<8))						
						if((board[j*8+i]==0)||(board[j*8+i]*board[pos]<0))
						{
							a[n]=j*8+i;
							n++;
						}
	
				}
				break;
			case 6://Pawn
				if (board[pos]<0)
				{
					
					int i=x+1,j=y;
					
					if (j==8) break;
	
					if(board[j*8+i]==0)
					{
						a[n]=j*8+i;
						n++;
					}
					
					//add 1/2
					if( (j-1>=0)&&(board[(j-1)*8+i]*board[pos]<0))
					{
						a[n]=j*8+i-1;
						n++;
					}
					if ((j+1<8)&&(board[j*8+i+1]*board[pos]<0))
					{
						a[n]=j*8+i+1;
						n++;
					}
					
				}
				else
				{
					int i=x-1,j=y;
					if (j<0) break;
					if(board[j*8+i]==0)
					{
						a[n]=j*8+i;
						n++;
					}
					
					//add 1/2
					if( (j-1>=0)&&(board[(j-1)*8+i]*board[pos]<0))
					{
						a[n]=j*8+i-1;
						n++;
					}
					if ((j+1<8)&&(board[j*8+i+1]*board[pos]<0))
					{
						a[n]=j*8+i+1;
						n++;
					}
					
				}
					
				
				
				break;
					
		}
		int[] re = new int[n];
		
		for (int i=0;i<n;i++)
			re[i]=a[i];
		return re;
	}
	
	
	public float[] minimax(int chess[], int depth, boolean player){
		Method toExecute;
		Class<?>[] paramTypes = {int[].class, int.class, boolean.class};
		Object[] paramValues = {chess, depth, player};
		float[] result = new float[3]; 
		
		try{
			
			toExecute = this.getClass().getDeclaredMethod("localminimax", paramTypes);
			Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
			if(results != null){
				result = (float[])results.get(0);
				copyState(results.get(1));

			}else{
				result = localminimax(chess, depth, player);

			}
			
		}catch (SecurityException se){
		
		}catch (NoSuchMethodException ns){
		
		}catch (Throwable th){
		}
		
		
		return result;
	}
	
	
	void copyState(Object state){
		MiniMaxRemote localstate = (MiniMaxRemote) state;
	}
	
	
	
	public float[] localminimax(int chess[], int depth, boolean player)
	{
		float[] value= new float[3];
		
		try{
		 
		if (depth==0) {
						value[0]=0;
						value[1]=0;
						value[2]=0;
						return value;	
		}
	
		//value[0] = minValue;
		value[0] = -10000;
			for (int i=0;i<8;i++)
				for (int j=0;j<8;j++)	
				{
					int pos = j*8+i;
					if (((chess[pos]>=0)&&(!player))||((chess[pos]<=0)&&(player))) continue;
					int move[] = getPossibleMove(i , j , chess);
					int x = chess[pos];
		
					
					for (int k=0;k<move.length;k++)
		
					
					chess[pos]=0;
					
					for (int k=0;k<move.length;k++)
					{
						int y = chess[move[k]];
						float score=0;
						switch (y)
						{
							case 1: score = 100.0f;
							break;
							case 2: score = 10.0f;
							break;
							case 3: score = 5.5f;
							break;
							case 4: score = 3.5f;
							break;
							case 5: score =1.0f;
							break;
							default: score =0.0f;
						}
						Random r = new Random();
						float ans =r.nextInt(500)/1000.0f+score - localminimax(chess, depth-1, !player)[0]; 
						if (ans>value[0]) {
											value[0] = ans;
											value[1] = pos;
											value[2] = move[k];
						}
					}
					chess[pos] =x;
						
					
				}
		
		}catch(NullPointerException e) {
			System.out.println("Exception is: " + e.getMessage() + ", " + e.getCause() + "," + e.getLocalizedMessage() );
			e.printStackTrace();
		}
		
		return value;
	}
	
	public float[] getSteps(int [][] chessBoard, int depth){
		
		setChessStatus(chessBoard);
		
		float[] step = null; 
		
		
		boolean swap = false;
		
			
		step = minimax(chess,depth,swap);
		
		chess[(int)step[2]]=chess[(int)step[1]];
		chess[(int)step[1]]=0;
		swap = !swap;
		
		
		return step;
	}

	
	
}
