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
	
	
	
	Server server;
	public ServerGate(Socket s,Server server) throws IOException {
		this.s = s;
		this.server = server;
		
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
		st = new StringTokenizer(msg, "/");
		
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		System.out.println("act: " + act );
		System.out.println("act2   : " + act2);
		//act = "chatting/nickname/ content"
		// 구현하세요.
		if (act.equals("NewUser")) {
			
			// 신규유저에게 기존유저뿌리기
			Set<String> nicks = server.userHash.keySet();
			for (String nick : nicks) {
				sendMsg(s, act, nick);
			}
			
			// 모든유저에게 신규유저 추가
			server.userHash.put(act2, s);
			sendAllMsg(act, act2);
			
			/////////방뿌리기//////////
			Set<String> roomN = server.roomHash.keySet();
			for(String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
		} else if(act.equals("JoinRoom")) {
			String nick = st.nextToken();
			System.out.println(nick);
			String oldRoom = st.nextToken();
			System.out.println("oldRoom:" + oldRoom);
			if(!oldRoom.equals("none")) {
				server.roomHash.get(oldRoom).remove(nick);
				System.out.println("여기로 온다고?");
				sendRoomMsg("DelUser", oldRoom, nick);
//				server.roomHash.get(act2).remove(nick);
				sendRoomMsg( "DelUserList", oldRoom);
				Set<String> nicks = server.roomHash.get(oldRoom).keySet();
				for (String n : nicks) {
					sendMsg(s, "NewUser", n);
				}
			} 
				System.out.println("처음 방참여");
				sendMsg(s, "DelUser", nick);
//				server.roomHash.get(act2).remove(nick);
				sendMsg(s, "DelUserList", "hi");				
			
			// 0:act 1:roomName 2:nickname 3~:msg

			
			/////////user 뿌리기///////////
//			sendMsg(s, "DelUserList", "hi");
			
			Set<String> nicks = server.roomHash.get(act2).keySet();
			for (String n : nicks) {
				sendMsg(s, "NewUser", n);
			}
			
			server.roomHash.get(act2).put(nick, s);
			sendRoomMsg("NewUser", act2, nick); // 0:act 1:roomName 2:nickname 3~:msg
			/////////////////////
			
			sendRoomMsg("Chatting", act2, "관리자", nick+"님이 입장하였습니다.");
			
			
		} else if (act.equals("Chatting")) {
			String roomName = act2; 
			String nick = st.nextToken();
			String chat = st.nextToken();
			sendRoomMsg(act, roomName, nick, chat);
			
		}else if(act.equals("NewRoom")) {
		
			server.roomHash.put(act2, new Hashtable<String, Socket>());
			sendAllMsg(act, act2);
			
			

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
			String send = msg[0] + "/";
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
