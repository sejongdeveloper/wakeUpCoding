package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	
	
	public Client() {
		new ClientAction(this);	
		try {
			s = new Socket("localhost", 7777);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// ������ msg ������
	public void sendMsg(String msg) {
		//��ū���� �˾Ƽ� ��������. 
		st = new StringTokenizer(msg, "/");
		String xxx = st.nextToken(); // �ൿ
		String xxx2 = st.nextToken();
		
		// �����ϼ���.
		if(xxx.equals("�ൿ1")) {
		
		} else if(xxx.equals("�ൿ2")){
			
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
