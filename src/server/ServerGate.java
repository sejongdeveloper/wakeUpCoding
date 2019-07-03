package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


public class ServerGate extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	public ServerGate(Socket s) {
		
	}
	
	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream());			
		
			while(true) {
				String msg = dis.readUTF();
				applyMsg(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void applyMsg(String msg) {
		//토큰으로 알아서 나누세요. 
		st = new StringTokenizer(msg, "/");
		String xxx = st.nextToken(); // 행동
		String xxx2 = st.nextToken();
		
		// 구현하세요.
		if(xxx.equals("행동1")) {
		
		} else if(xxx.equals("행동2")){
			
		}
	}
	
	// 클라이언트 보내기
	public void sendMsg(String msg) throws IOException {
		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
