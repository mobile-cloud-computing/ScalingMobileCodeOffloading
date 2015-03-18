package edu.ut.mobile.network;



import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;


public class NetworkManagerClient {
    int portnum;
    Socket mysocket = null;
    InputStream in = null;
    OutputStream out = null;
    
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    
    byte []serverAddress =new byte[4];
    CloudController callingparent = null;
    long startTime = 0;

    public NetworkManagerClient(byte[] serverAddress, int port) {

        this.serverAddress = serverAddress;
        portnum = port;
    }

    public void setNmf(CloudController callingparent) {
        this.callingparent = callingparent;
    }


    public boolean connect(){  
        mysocket = new Socket();
        try {
            mysocket.connect(new InetSocketAddress(Inet4Address.getByAddress(serverAddress), portnum), NetInfo.waitTime);

            startTime = System.currentTimeMillis();
            in = mysocket.getInputStream();
            
            out = mysocket.getOutputStream();  

            //oos = new ObjectOutputStream(out);
            oos = new ObjectOutputStream(new BufferedOutputStream(out));
            oos.flush(); 

            //ois = new ObjectInputStream(in); 
            ois =new ObjectInputStream(new BufferedInputStream(in));
                 
            
            return true;
        } catch (IOException ex) {
            callingparent.setResult(null, null);
            return false;
        }
    }



    public void send(String functionName, Class[] paramTypes, Object[] funcArgValues, Object state, Class stateDType){
        try{
            new Sending(new Pack(functionName, stateDType, state, funcArgValues, paramTypes)).send();
        }catch(Exception ex){
            callingparent.setResult(null, null);
        }
    }



    class Sending implements  Runnable{
        Pack MyPack = null;
        ResultPack result = null;

        public Sending(Pack MyPack) {
            this.MyPack = MyPack;
        }


        public void send(){
            Thread t = new Thread(this);
            t.start();
        }

        
        public void run() {
            try {

                oos.writeObject( MyPack );
                oos.flush();

                result = (ResultPack) ois.readObject();
                
                /*this is for collecting times across the infrastructure
                 *remember that the times of the servers must be synchronized with a central authority, otherwise the measurements are unreliable. 
                
                List<String> timestamps = result.getTimeStamps();
                
                
                String [] time1 = timestamps.get(0).split(",");
                String [] time2 = timestamps.get(3).split(",");
                
                String [] time3 = timestamps.get(1).split(",");
                String [] time4 = timestamps.get(2).split(",");
                
                double processTime = (Double.parseDouble(time4[0]) - Double.parseDouble(time3[0]));
                
                
                System.out.println("Total time of the request:, " + (Double.parseDouble(time2[0]) - Double.parseDouble(time1[0])) + "/" + "processing time: " + processTime );*/
                
                if (result==null)
                	System.out.println("Result is null");
                else
                	System.out.println("Total time of the request:," + (System.currentTimeMillis() - startTime));

                
                if((System.currentTimeMillis() - startTime) < NetInfo.waitTime){
                    if(result == null)
                        callingparent.setResult(null, null);
                    else
                        callingparent.setResult(result.getresult(), result.getstate());
                }

                oos.close(); 
                ois.close();

                in.close();
                out.close();

                mysocket.close();

                oos = null;
                ois = null;

                in = null;
                out = null;
                mysocket = null;

            } catch (IOException ex) {
                callingparent.setResult(null, null);
            } catch (ClassNotFoundException ex) {
                callingparent.setResult(null, null);
            }
        }

    }


}
