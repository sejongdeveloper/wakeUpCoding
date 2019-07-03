package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	
	
	public Client() {
		new ClientAction(this);	
		try {
			s = new Socket("localhost", 7777);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// 서버에 msg 보내기
	public void sendMsg(String msg) {
		//토큰으로 알아서 나누세요. 
		st = new StringTokenizer(msg, "/");
		String xxx = st.nextToken(); // 행동
		String xxx2 = st.nextToken();
		
		// 구현하세요.
		if(xxx.equals("행동1")) {
		
		} else if(xxx.equals("행동2")){
			
		}
	}
	
	// 방생성
	public void createRoom() {
		
	}
	
	// 방참여
	public void joinRoom() {
		
	}
	
	// 방나가기
	public void leaveRoom() {
		
	}
	
	
	public static void main(String[] args) {
		new Client();
	}
}
