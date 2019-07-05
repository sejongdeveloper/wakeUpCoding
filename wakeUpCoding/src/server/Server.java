package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	
	private ServerSocket ss;
	//대기실 유저
	Hashtable<String, Socket> userHash; // Hashtable<닉네임, Socket> userHash;
	
	// 방유저
	Hashtable<String, Hashtable<String, Socket>> roomHash; // Hashtable<방이름, Hashtable<닉네임, Socket>> roomHash;
	
	public Server() { 
		// 객체 생성 (new 연산자)
		userHash = new Hashtable<String, Socket>();
		roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버시작");
			Thread thread = new Thread(()->{
				while(true) {
					try {
						Socket s = ss.accept();
					
						//구현 (userHash put())
						Thread th = new ServerGate(s, this);
						th.start();
						//끝
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}// while end
			});
			thread.start();
			
			
			
		} catch (IOException e) { e.printStackTrace(); } // try end
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // 서버시작
	}
}
