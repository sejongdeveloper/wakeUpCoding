package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	
	private ServerSocket ss;
	
	//대기실 유저
	Hashtable<String, Socket> userHash; // Hashtable<닉네임, Socket> userHash;
	Hashtable<String, Socket> noneHash; // Hashtable<닉네임, Socket> userHash;
	
	// 방유저
	Hashtable<String, Hashtable<String, Socket>> roomHash; // Hashtable<방이름, Hashtable<닉네임, Socket>> roomHash;
	
	public Server() { 
		// 객체 생성 (new 연산자)
		userHash = new Hashtable<String, Socket>();
		noneHash = new Hashtable<String, Socket>();
		roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		
		// 서버가 항상 클라이언트를 받을 수 있도록 대기
		Thread ready = new Thread(()->{ // 익명구현객체 구현
			try {
				ss = new ServerSocket(7777);
				System.out.println("서버시작");
				
				while(true) { // 무한루프
						Socket s = ss.accept(); // 새로운 클라이언트 접속하면 서로 연결할 소켓생성
						
						Thread serverGate = new ServerGate(s, this); // 클라이언트와 서버 네트워크 통신 쓰레드
						serverGate.start();
												
				}// while end
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		ready.start();
			
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // 서버시작
	}
}
