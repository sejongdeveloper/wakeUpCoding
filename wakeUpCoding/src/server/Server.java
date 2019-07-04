package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

public class Server {
	
	private ServerSocket ss;
	Hashtable<String, Socket> userHash; // 수정가능한 부분
	Hashtable<String, Hashtable<String, Socket>> roomHash; 
	
	
	public Server() { //연결
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
				}//while
			});
			thread.start();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// Server() end
	
	public static void main(String[] args) {
		new Server();
	}
}
