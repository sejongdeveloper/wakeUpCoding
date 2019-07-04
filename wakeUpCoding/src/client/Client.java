package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private DataOutputStream dos;
	private Socket s;
	public String nick;
	
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
	
	// ������ msg ������
	public void sendMsg(String msg) {
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
