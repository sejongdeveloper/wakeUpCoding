package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private DataOutputStream dos;
	private Socket s;
	public String nick;
	
	public String room;

	public Client() {
		ClientAction ca = new ClientAction(this);	
		try {
			s = new Socket("localhost", 7777);
			Thread read = new ClientRead(s, ca);
			read.start();
			dos = new DataOutputStream(s.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// 서버에 msg 보내기
	public void sendMsg(String ...msg) {
		try {
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	// 방생성
//	public Hashtable<String, Hashtable<String,Socket>> newRoom() {
//		String roomname = JOptionPane.showInputDialog("방 이름");
//		sendMsg("NewRoom/ " + roomname);// 메세지를 통하여 방이름을 보내준다.
//		roomHash.put(roomname, new Hashtable<String, Socket>());
//		return roomHash;
//	}
	
	// 방참여
	public void joinRoom() {
		
	}
	
	// 방나가기
	public void leaveRoom() {
		
	}
	
}
