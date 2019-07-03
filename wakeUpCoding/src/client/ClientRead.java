package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientRead extends Thread{
   private Socket s;
   private DataInputStream dis;
   private StringTokenizer st;
   ClientAction ca;
   public ClientRead(Socket s,ClientAction ca) {
      this.s = s;
      this.ca = ca;
      ca.setTitle("세종이에요");
   }
   @Override
   public void run() {
      while(true) {
         try {
            String msg = dis.readUTF();
            
            applyMsg(msg);
         } catch (Exception e) {
         }
      }
      
      
   }
   
   public void applyMsg(String msg) {
      st = new StringTokenizer(msg, "/");
      String act = st.nextToken(); // 행동
      String act2 = st.nextToken();
      	if(act.equals("NewUser")){
      		ca.uList.add(act2);
      		ca.userList.setListData(ca.uList);
    	  
    	  
      
      }else if(act.equals("Chatting")) {
    	  String message = st.nextToken();
			ca.chatArea.append(act + " : " + message + "\n");

      }
   }
}