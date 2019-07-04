package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

public class ServerGate extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	
	Hashtable<String, Socket> userHash;
	public ServerGate(Socket s,Hashtable<String, Socket> userHash) throws IOException {
		this.s = s;
		this.userHash = userHash;
		
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
	public void applyMsg(String msg) throws IOException {
		//토큰으로 알아서 나누세요. 첫글은대문자
		st = new StringTokenizer(msg, "/");
		
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		System.out.println("act: " + act );
		System.out.println("act2   : " + act2);
		//act = "chatting/nickname/ content"
		// 구현하세요.
		if (act.equals("Chatting")) {
			String roomName = act2; // 나중에 방이름으로 처리해야함
			String nick = st.nextToken();
			String chat = st.nextToken();
			sendAllMsg(act, roomName, nick, chat);
			
		} else if (act.equals("NewUser")) {
			String olds = "";
			if(!userHash.isEmpty()) {
				Set<String> nicks = userHash.keySet();
				for(String n : nicks) {
					olds += "/" + n;
				}
				sendMsg("OldUser" + olds, s);
			}	
			
			userHash.put(act2, s);
			System.out.println("닉네임 : " + act2 + "==>" + olds);
			sendAllMsg(act, act2);
		}

	}// 종료
	
	public void sendAllMsg(String ... str) {
		Set<String> nicks = userHash.keySet();
		for(String n : nicks) {
			try {
				String send = "";
				for (int i = 0; i < str.length; i++) {
					send += str[i] + "/";
				}
				sendMsg(send, userHash.get(n));
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void sendRoomMsg(Hashtable<String, Hashtable<String, Socket>> roomHash, String roomName, String ... str) {
		Set<String> nicks = roomHash.get(roomName).keySet();
		for(String n : nicks) {
			try {
				String send = "";
				for (int i = 0; i < str.length; i++) {
					send += str[i] + "/";
				}
				sendMsg(send, roomHash.get(roomName).get(n));
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	

	// 클라이언트 보내기
	public void sendMsg(String msg) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
		
	}
	
	public void sendMsg(String msg, Socket s) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
