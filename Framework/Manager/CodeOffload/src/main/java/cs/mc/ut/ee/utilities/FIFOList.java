package cs.mc.ut.ee.utilities;


import java.util.LinkedList;

class FIFOList {
    public static void main(String args[]) {
        char arr[] = {3,1,4,1,5,9,2,6,5,3,5,8,9};
        LinkedList<Integer> fifo = new LinkedList<Integer>();

        for (int i = 0; i < arr.length; i++)
            fifo.add (new Integer (arr[i]));

        System.out.print (fifo.removeFirst() + ".");
        while (! fifo.isEmpty()){
            System.out.print (fifo.removeFirst());
        }
        System.out.println();
        
    }
}