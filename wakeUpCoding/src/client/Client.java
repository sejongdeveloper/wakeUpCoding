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
			s = new Socket("localhost", 7777); // ������ ����IP�� ������ ���� �Ͼ�� ����
			Thread read = new ClientRead(s, ca);
			read.start();
			dos = new DataOutputStream(s.getOutputStream()); // ��Ʈ��ũ ��� ��ü(s.getOutputStream()) ����
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// ������ msg ������
	public void sendMsg(String ...msg) {
		try {
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
	
<<<<<<< HEAD
} // Client ��
=======
	// �����
//	public Hashtable<String, Hashtable<String,Socket>> newRoom() {
//		String roomname = JOptionPane.showInputDialog("�� �̸�");
//		sendMsg("NewRoom/ " + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
//		roomHash.put(roomname, new Hashtable<String, Socket>());
//		return roomHash;
//	}
	
	// ������
	public void joinRoom() {
		
	}
	
	// �泪����
	public void leaveRoom() {
		
	}
	
}
>>>>>>> JaeJun
