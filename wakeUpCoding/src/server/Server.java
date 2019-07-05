package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	
	private ServerSocket ss;
	
	//���� ����
	Hashtable<String, Socket> userHash; // Hashtable<�г���, Socket> userHash;
	
	// ������
	Hashtable<String, Hashtable<String, Socket>> roomHash; // Hashtable<���̸�, Hashtable<�г���, Socket>> roomHash;
	
	public Server() { 
		// ��ü ���� (new ������)
		userHash = new Hashtable<String, Socket>();
		roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		
			
		// ������ �׻� Ŭ���̾�Ʈ�� ���� �� �ֵ��� ���
		Thread ready = new Thread(()->{ // �͸�����ü ����
			try {
				ss = new ServerSocket(7777);
				System.out.println("��������");
				
				while(true) { // ���ѷ���
						Socket s = ss.accept(); // ���ο� Ŭ���̾�Ʈ �����ϸ� ���� ������ ���ϻ���
						
						Thread serverGate = new ServerGate(s, this); // Ŭ���̾�Ʈ�� ���� ��Ʈ��ũ ��� ������
						serverGate.start();
												
				}// while end
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		ready.start();
			
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // ��������
	}
}
