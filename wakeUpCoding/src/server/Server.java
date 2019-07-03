package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	
	private static final String String = null;
	private ServerSocket ss;
	private Hashtable<String, Socket> userHash; // ���������� �κ�
	
	
	
	
	
	public Server() { //����
		
		
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("��������");
			Thread thread = new Thread(()->{
			userHash = new Hashtable<String, Socket>();
				while(true) {
					try {
						Socket s = ss.accept();
					
						//����
						
						//���� (userHash put())						
						Thread th = new ServerGate(s, userHash);
						
						th.start();
						//��
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}//while
			}) ;
			thread.start();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
