package client;

import java.net.Socket;

public class Client {
	private Socket s;
	
	public Client() {
		ClientAction ca = new ClientAction(this);
		
		try {
			s = new Socket("localhost", 7777);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread th = new ClientRead(s, ca);
		
		th.start();
		
		
	} // Client() end
	
	// 서버에 msg 보내기
	public void sendMsg(String msg) {
		
	}
	
	// 방생성
	public void createRoom() {
		
	}
	
	// 방참여
	public void joinRoom() {
		
	}
	
	// 방나가기
	public void leaveRoom() {
		
	}
	
	
	public static void main(String[] args) {
		new Client();
	}
}
