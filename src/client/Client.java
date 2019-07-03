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
	
	// ������ msg ������
	public void sendMsg(String msg) {
		
	}
	
	// �����
	public void createRoom() {
		
	}
	
	// ������
	public void joinRoom() {
		
	}
	
	// �泪����
	public void leaveRoom() {
		
	}
	
	
	public static void main(String[] args) {
		new Client();
	}
}
