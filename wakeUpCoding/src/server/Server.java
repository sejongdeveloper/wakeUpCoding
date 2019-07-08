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
	
	// 대기실 유저
	Hashtable<String, Socket> userHash; // Hashtable<닉네임, Socket> userHash;
	// 방유저
	Hashtable<String, Hashtable<String, Socket>> roomHash; // Hashtable<방이름, Hashtable<닉네임, Socket>> roomHash;
	
	public Server() { 
		// 객체 생성 (new 연산자)
		userHash = new Hashtable<String, Socket>();
		roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		roomHash.put("대기방", new Hashtable<String, Socket>());
		// 스레드 풀
		es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // cpu 코어수만큼 작업스레드 생성
		
		// 서버가 항상 클라이언트를 받을 수 있도록 대기
		Runnable ready = ()->{ // 람다식 익명구현객체 구현
			try {
				ss = new ServerSocket(7777);
				System.out.println("서버시작");
				
				while(true) { // 무한루프
						Socket s = ss.accept(); // 새로운 클라이언트 접속하면 서로 연결할 소켓생성
						
						Runnable serverGate = new ServerGate(s, this); // 클라이언트와 서버 네트워크 통신 쓰레드
						es.submit(serverGate); // 작업요청
												
				}// while end
			} catch (IOException e) {
				e.printStackTrace();
				es.shutdown();
			}
		};
		es.submit(ready); // 작업요청
			
	}// Server() end
	
	public static void main(String[] args) {
		new Server(); // 서버시작
	}
}
