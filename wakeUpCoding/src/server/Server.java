package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	private ServerSocket ss;
	private ExecutorService es;
	
	// ���� ����
	Hashtable<String, Socket> userHash; // Hashtable<�г���, Socket> userHash;
	// ������
	Hashtable<String, Hashtable<String, Socket>> roomHash; // Hashtable<���̸�, Hashtable<�г���, Socket>> roomHash;
	
	public Server() { 
		// ��ü ���� (new ������)
		userHash = new Hashtable<String, Socket>();
		roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		roomHash.put("����", new Hashtable<String, Socket>());
		// ������ Ǯ
		es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // cpu �ھ����ŭ �۾������� ����
		
		// ������ �׻� Ŭ���̾�Ʈ�� ���� �� �ֵ��� ���
		Runnable ready = ()->{ // ���ٽ� �͸�����ü ����
			try {
				ss = new ServerSocket(7777);
				System.out.println("��������");
				
				while(true) { // ���ѷ���
						Socket s = ss.accept(); // ���ο� Ŭ���̾�Ʈ �����ϸ� ���� ������ ���ϻ���
						
						Runnable serverGate = new ServerGate(s, this); // Ŭ���̾�Ʈ�� ���� ��Ʈ��ũ ��� ������
						es.submit(serverGate); // �۾���û
												
				}// while end
			} catch (IOException e) {
				e.printStackTrace();
				es.shutdown();
			}
		};
		es.submit(ready); // �۾���û
			
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // ��������
	}
}
