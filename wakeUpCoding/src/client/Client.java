package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private DataOutputStream dos;
	private Socket s;
	public String nick;

	public Client(String nick) {
		this.nick = nick;
<<<<<<< HEAD
		ClientAction ca = new ClientAction(this);
=======
		ClientAction ca = new ClientAction(this);	
>>>>>>> JaeJun
		try {
			s = new Socket("localhost", 7777); // ¿ø·¡´Â ¼­¹öIP´Â º¯µ¿ÀÌ ÀÚÁÖ ÀÏ¾î³ªÁö ¾ÊÀ½
			Thread read = new ClientRead(s, ca);
			read.start();
			dos = new DataOutputStream(s.getOutputStream()); // ³×Æ®¿öÅ© Ãâ·Â °´Ã¼(s.getOutputStream()) »ý¼º
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ msg ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	public void sendMsg(String ...msg) {
		try {
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
	
<<<<<<< HEAD
} // Client µÕ
=======
	// ï¿½ï¿½ï¿½ï¿½ï¿½
//	public Hashtable<String, Hashtable<String,Socket>> newRoom() {
//		String roomname = JOptionPane.showInputDialog("ï¿½ï¿½ ï¿½Ì¸ï¿½");
//		sendMsg("NewRoom/ " + roomname);// ï¿½Þ¼ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ï¿ï¿½ ï¿½ï¿½ï¿½Ì¸ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ø´ï¿½.
//		roomHash.put(roomname, new Hashtable<String, Socket>());
//		return roomHash;
//	}
	
	// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	public void joinRoom() {
		
	}
	
	// ï¿½æ³ªï¿½ï¿½ï¿½ï¿½
	public void leaveRoom() {
		
	}
	
}
>>>>>>> JaeJun
