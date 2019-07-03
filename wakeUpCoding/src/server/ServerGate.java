package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class ServerGate extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;

	private String Nick = "";

	public ServerGate(Socket s) {

	}

	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream());

			while (true) {
				String msg = dis.readUTF();
				applyMsg(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 시작
	public void applyMsg(String msg) {
		//토큰으로 알아서 나누세요. 첫글은대문자
		st = new StringTokenizer(msg, "/");
		Hashtable userHash = new Hashtable<String, Socket>();
		userHash.put(Nick,s);
		
		String Chatting = st.nextToken(); // 행동
		String Chatting2 = st.nextToken();
		System.out.println("Chatting: " + Chatting );
		System.out.println("Chatting2   : " + Chatting2);
		
		// 구현하세요.
		if(Chatting.equals("Note")) {
		st = new StringTokenizer(Chatting2 ,"/");
		
		String User = st.nextToken();
		String Note = st.nextToken();
		System.out.println("받는사람  : " + User);
		System.out.println("보낼 내용  : "  +  Note);
		
		for (int i = 0; i < userHash.size(); i++) {
			
			sendMsg sm = (sendMsg)userHash.elementAt(i);
			
			if(sm.nick.equals(User)) {
				sm.sendM("Note" + Nick +"/"+ Note);
			}
			else if(Chatting2.equals("Chatting2")){							
					sm.sendM("Chatting/" + Nick +"/"+ msg);
					
			}		
		}//if end
		}//for
	}// 종료

	// 클라이언트 보내기
	public void sendMsg(String msg) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
