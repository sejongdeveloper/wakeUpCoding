package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
	
	private static final String String = null;
	private ServerSocket ss;
	private Hashtable<String, Socket> userHash; // 수정가능한 부분
	
	
	
	
	
	public Server() { //연결
		userHash = new Hashtable<String, Socket>();
		
		try {
			ss = new ServerSocket(7777);
			System.out.println("서버시작");
			Thread thread = new Thread(()->{
			userHash = new Hashtable<String, Socket>();
				while(true) {
					try {
						Socket s = ss.accept();
					
						
						

						

						Thread th = new ServerGate(s, userHash);
						
						th.start();
						//끝
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}//while
			}) ;
			thread.start();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// Server() end
	
	public static void main(String[] args) {
		new Server();
	}
}
