package client;

import java.net.Socket;

public class ClientRead extends Thread{
	private Socket s;
	
	public ClientRead(Socket s) {
		this.s = s;
	}
	@Override
	public void run() {
		// 구현하세요.
		while(true) {
			
		}
		
	}
	// 서버 90%
	public void applyMsg() {
		
	}
}
