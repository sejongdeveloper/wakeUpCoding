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
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("��������");
			Thread thread = new Thread(()->{
				while(true) {
					try {
						Socket s = ss.accept();
					
						//���� (userHash put())
						Thread th = new ServerGate(s, this);
						th.start();
						//��
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}// while end
			});
			thread.start();
			
			
			
		} catch (IOException e) { e.printStackTrace(); } // try end
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // ��������
	}
}
