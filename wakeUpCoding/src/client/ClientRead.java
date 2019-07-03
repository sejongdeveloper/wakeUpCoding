package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientRead extends Thread{
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	
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
	public void applyMsg(String msg) {
		//토큰으로 알아서 나누세요. 
		st = new StringTokenizer(msg, "/");
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		
		// 구현하세요.
		if(act.equals("행동1")) {
		
			
			
		} else if(act.equals("행동2")){
			
		}
	}
}
