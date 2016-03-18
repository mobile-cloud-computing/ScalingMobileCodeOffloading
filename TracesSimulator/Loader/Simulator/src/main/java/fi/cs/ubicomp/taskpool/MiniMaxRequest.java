package fi.cs.ubicomp.taskpool;

import cs.mc.ut.ee.logic.MiniMaxRemote;



/*
 * author Huber Flores
 */

public class MiniMaxRequest implements Runnable {
	
	
	private final String TAG = MiniMaxRequest.class.getCanonicalName();
	
	MiniMaxRemote client;
	
	
	
	public MiniMaxRequest(){
		
	}

	public void run() {
		// Method invocation of the algorithm to execute remotely
		
		
		//BubbleSort
		
		//client = new BubbleSort(0);
		//client.sortFunction();
		
		
		client = new MiniMaxRemote();
		
		/**
		 * Initial board
		 */
		/*int[][] board = {{-3, -5, -4, -2, -1, -4, -5, -3},
				{-6, -6, -6, -6, -6, -6, -6, -6},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{6, 6, 6, 6, 6, 6, 6, 6},
				{3, 5, 4, 2, 1, 4, 5, 3}
				};
		*/
		
		/**
		 * Game in progress
		 */
		
		int [][] board = {{0, -3, -4, -2, -1, -4, 0, -3}, 
				{0, 0, -6, -6, -6, 0, -6, -6}, 
				{-6, -6, 0, 0, 0, 0, 0, -5}, 
				{-5, 0, 0, 5, 0, 0, 0, 0}, 
				{0, 0, 6, 0, 6, -6, 0, 0}, 
				{6, 6, 0, 6, 0, 6, 0, 6}, 
				{0, 0, 0, 0, 0, 0, 6, 0}, 
				{3, 0, 0, 2, 1, 4, 5, 3}
				};
		
		int [] chess = new int[64];
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				chess[j*8+i]=board[i][j];
		

		
		float [] steps = client.minimax(chess, 2, false); //changed to 2 from 4

		
		System.out.println("Executed " + TAG);
		
		
	}
	

	

}
