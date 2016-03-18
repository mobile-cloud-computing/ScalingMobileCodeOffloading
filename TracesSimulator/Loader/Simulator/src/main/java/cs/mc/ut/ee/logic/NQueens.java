package cs.mc.ut.ee.logic;

import java.lang.reflect.Method; 
import edu.ut.mobile.network.CloudRemotable;
import java.util.Vector;



public class NQueens extends CloudRemotable  {
 
    int[] x;
 
    public NQueens(int N) {
        x = new int[N];
    }
 
    public boolean canPlaceQueen(int r, int c) {

        for (int i = 0; i < r; i++) {
            if (x[i] == c || (i - r) == (x[i] - c) ||(i - r) == (c - x[i]))
            {
                return false;
            }
        }
        return true;
    }
 
    public void printQueens(int[] x) {
        int N = x.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (x[i] == j) {
                    //System.out.print("Q ");
                } else {
                    //System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    } 
 
    public void placeNqueens(int r, int n) {
        for (int c = 0; c < n; c++) {
            if (canPlaceQueen(r, c)) {
                x[r] = c;
                if (r == n - 1) {
                    //printQueens(x);
                } else {
                    placeNqueens(r + 1, n);
                }
            }
        }
    }
 
    
    public void localcallplaceNqueens() {
        placeNqueens(0, x.length);
    }
 
   

    public void callplaceNqueens() {
	Method toExecute; 
	Class<?>[] paramTypes = null; 
	Object[] paramValues = null; 
	
	try{
		toExecute = this.getClass().getDeclaredMethod("localcallplaceNqueens", paramTypes);
		Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
		if(results != null){
			copyState(results.get(1));
		}else{
			localcallplaceNqueens();
		}
	}  catch (SecurityException se){
	} catch (NoSuchMethodException ns){
	}catch (Throwable th){
	}
	
	}

void copyState(Object state){
	NQueens localstate = (NQueens) state;
	this.x = localstate.x;
}
}


