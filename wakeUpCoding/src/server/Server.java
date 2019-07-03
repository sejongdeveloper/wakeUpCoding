package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	private ServerSocket ss;
	private Hashtable<String, Socket> userHash; // 수정가능한 부분
	
	public Server() {
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버시작");
			Thread thread = new Thread(()->{
				while(true) {
					try {
						Socket s = ss.accept();
						ServerGate sg = new ServerGate(s); //읽는
						//구현 (userHash put())
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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
