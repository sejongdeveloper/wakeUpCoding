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
		if (act.equals("NewUser")) {
			String olds = "";
			if(!server.userHash.isEmpty()) {
				Set<String> nicks = server.userHash.keySet();
				for(String n : nicks) {
					olds += "/" + n;
				}
				sendMsg(s, "OldUser" + olds);
			}	
			
			server.userHash.put(act2, s);
			System.out.println("닉네임 : " + act2 + "==>" + olds);
			sendAllMsg(act, act2);
			
			/////////방뿌리기//////////
			
			Set<String> roomN = server.roomHash.keySet();
			for(String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
			//////////////////////////
			
		} else if(act.equals("JoinRoom")) {
			String nick = st.nextToken();
			server.roomHash.get(act2).put(nick, s);
			System.out.println(nick);
			
			/////////uesr 뿌리기///////////
			sendMsg(s, "DelUserList", "nick");
			
			Set<String> nicks = server.roomHash.get(act2).keySet();
			if(!nicks.isEmpty()) {
				String oldNick = "";
				for(String n : nicks) oldNick += n+ "/" ;
				sendMsg(s, "OldUser" , oldNick);
			}
			sendRoomMsg("NewUser", "proto", nick); // 0:act 1:roomName 2:nickname 3~:msg
			/////////////////////
			
			sendRoomMsg("Chatting", "proto", "관리자", nick+"님이 입장하였습니다.");
			
			
		} else if (act.equals("Chatting")) {
			String roomName = act2; 
			String nick = st.nextToken();
			String chat = st.nextToken();
			sendRoomMsg(act, roomName, nick, chat);
			
		}

	}// 종료
	
	public void sendAllMsg(String ... msg) {
		Set<String> nicks = server.userHash.keySet();
		for(String n : nicks) {
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/";
			sendMsg(server.userHash.get(n), send);
		}
	}
	
	public void sendRoomMsg(String ... msg) {	// 0:act 1:roomName 2:nickname 3~:msg
		Set<String> nicks =server.roomHash.get(msg[1]).keySet();
		for(String n : nicks) {
			String send = msg[0];
			for (int i = 2; i < msg.length; i++) send += msg[i] + "/";
			sendMsg( server.roomHash.get(msg[1]).get(n), send);
		}
	}
	

	// 클라이언트 보내기
//	public void sendMsg(String msg){
//
//		try {
//			dos = new DataOutputStream(s.getOutputStream());
//			dos.writeUTF(msg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void sendMsg(Socket s, String ...msg) {

		try {
			dos = new DataOutputStream(s.getOutputStream());
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i]+ "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
}
