package client;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JList;

public class ClientRead extends Thread{
   private Socket s;
   private DataInputStream dis;
   private StringTokenizer st;
   private TextArea chatArea;
   private JList<String> userList;
   private Vector<String> uList; 
   
   public ClientRead(Socket s,ClientAction ca) {
      this.s = s;
      this.chatArea = ca.chatArea;
      this.userList = ca.userList;
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
      String act = st.nextToken(); // Çàµ¿
      String act2 = st.nextToken();
      
      if(act.equals("Chatting")) {
         String message = st.nextToken();
         chatArea.append(act + " : " + message + "\n");
         
      } else if(act.equals("NewUser")){
         uList.add(msg);
         userList.setListData(uList);
         
      }
   }
}