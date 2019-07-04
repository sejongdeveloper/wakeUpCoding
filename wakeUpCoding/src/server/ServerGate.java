package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class ServerGate extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	
	
	
//	Hashtable<String, Socket> userHash;
	Server server;
//	public ServerGate(Socket s,Hashtable<String, Socket> userHash, Hashtable<String, Hashtable<String, Socket>> roomHash) throws IOException {
	public ServerGate(Socket s,Server server) throws IOException {
		this.s = s;
		this.server = server;
//		this.userHash = userHash;
		
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
			String nick = act2; // 나중에 방이름으로 처리해야함
			String message = st.nextToken();
			System.out.println("nick:" + act2);
			System.out.println("message:" + message);
			sendAllMsg(act, nick, message);
			
		} else if (act.equals("NewUser")) {
			String olds = "";
			if(!server.userHash.isEmpty()) {
				Set<String> nicks = server.userHash.keySet();
				for(String n : nicks) {
					olds += "/" + n;
				}
				sendMsg("OldUser" + olds, s);
			}	
			
			server.userHash.put(act2, s);
			System.out.println("닉네임 : " + act2 + "==>" + olds);
			sendAllMsg(act, act2, null);

		}else if(act.equals("NewRoom")) {
//			sendMsg("NewRoom/" + act2);
			String oldrooms = "";
			if(!server.roomHash.isEmpty()) {
				Set<String> rooms = server.roomHash.keySet();
				for(String room : rooms) {
					oldrooms += "/" + room;
				}
				sendMsg("OldRoom" + oldrooms, s);
			}	
			
			server.roomHash.put(act2,server.userHash);
			System.out.println("닉네임 : " + act2 + "==>" + oldrooms);
			sendAllMsg(act, act2, null);
			
			

		}

	}// 종료
	
	public void sendAllMsg(String act, String nick, String msg) {
		Set<String> nicks = server.userHash.keySet();
		for(String n : nicks) {
			try {
				sendMsg(act + "/"+ nick + "/"+ msg, server.userHash.get(n));
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
