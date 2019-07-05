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
			// 네트워크 읽기 객체(s.getInputStream()) 생성
			dis = new DataInputStream(s.getInputStream());
			 
			while (true) { // 항상 읽을 수 있도록 무한로프
				String msg = dis.readUTF();
				applyMsg(msg);
			}
		} catch (IOException e) {} 
		finally {try {dis.close(); s.close();} catch (IOException e) {}}
	}

	// 클라이언트가 보내온 msg내용을 구분하여 처리
	public void applyMsg(String msg) throws IOException {
		st = new StringTokenizer(msg, "/"); // msg = "NewUser/nick"
											// nextToken() -> "NewUser"
		                                    // nextToken() -> "nick"
		
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		System.out.println("act: " + act );
		System.out.println("act2   : " + act2);
		
		if (act.equals("NewUser")) { // 새로운 클라이언트(자신) 접속
			
			// 나한테만 기존유저UI 추가하라고 뿌림
			Set<String> nicks = server.userHash.keySet();
			for (String nick : nicks) {
				sendMsg(s, act, nick);
			}
			
			// 모든유저에게 내 닉네임UI 추가하라고 뿌림
			server.userHash.put(act2, s);
			sendAllMsg(act, act2);
			
			// 나한테만 기존방UI 모두 추가하라고 뿌림
			Set<String> roomN = server.roomHash.keySet();
			for(String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
		} else if(act.equals("JoinRoom")) { // 방참여 
			String nick = st.nextToken(); // act1:JoinRoom | act2:방이름 | nick:닉네임
			String oldRoom = st.nextToken(); // 기존방
			
			//나의 클라만 유저UI 모두제거
			sendMsg(s, "DelUserList", oldRoom);
			
			//기존방이 있는 상태에서 방참여를 한 경우
			if(!oldRoom.equals("none")) {
				//기존방에 나를 제거
				server.roomHash.get(oldRoom).remove(nick);
				
				//기존방 모두에게 내 닉만 UI 제거하라고 뿌림
				sendRoomMsg("DelUser", oldRoom, nick);

			} 
			
			//나한테만 현재방 유저 모두를 UI 추가하라고 뿌림
			Set<String> nicks = server.roomHash.get(act2).keySet();
			for (String n : nicks) {
				sendMsg(s, "NewUser", n);
			}
			
			//현재방에 나를 등록
			server.roomHash.get(act2).put(nick, s);
			
			//현재방 모두에게 내 닉만 UI 추가하라고 뿌림
			sendRoomMsg("NewUser", act2, nick); 
			
			//현재방 모두에게 내 닉이 입장했다고 뿌림
			sendRoomMsg("Chatting", act2, "관리자", nick+"님이 입장하였습니다.");			
			
			//현재방으로 UI타이틀 수정
			sendMsg(s, "ChangeTitle", act2, nick);
			
		} else if (act.equals("Chatting")) { // 채팅내용
			String roomName = act2; // 방이름
			String nick = st.nextToken(); // 닉네임
			if(st.hasMoreTokens()) {
				String chat = st.nextToken(); // 채팅내용
				// 해당방에 들어있는 모든 유저에게 채팅내용 뿌림
				sendRoomMsg(act, roomName, nick, chat); 
			}
			
			
		}else if(act.equals("NewRoom")) { // 방생성
			// 방이름 HashTable에 추가(put)
			// server.roomHash.put(방이름, new Hashtable<닉네임, Socket>());
			server.roomHash.put(act2, new Hashtable<String, Socket>());
			
			//모든 유저에게 새로운 방이름 UI에 추가하라고 뿌림
			sendAllMsg(act, act2);	
		}else if(act.equals("ExitUser")) {
			String nick = act2;
			server.userHash.remove(nick);
			sendAllMsg("DelUser", nick);
			
			if(st.hasMoreTokens()) {
				String roomName = st.nextToken();
				server.roomHash.get(roomName).remove(nick);
				sendRoomMsg("Chatting", roomName, "관리자", nick+"님이 퇴장하였습니다.");
			}
		}

	}// applyMsg(String msg) end
	
	// 모든 유저에게 보내기
	public void sendAllMsg(String ... msg) {
		Set<String> nicks = server.userHash.keySet(); // 접속한 모든 닉네임 얻기
		String send = "";
		for(String n : nicks) {
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/"; // ex1) msg[0]/msg[1]/msg[2] | ex2) NewUser/nick/
			sendMsg(server.userHash.get(n), send); // sendMsg( n[닉네임에 대한 소켓], send[예: NewUser/nick/] );
		}
	}
	
	// 해당방에 대한 모든 유저에게 보내기
	public void sendRoomMsg(String ... msg) {	// 0:act 1:roomName 2:nickname 3~:msg
		Set<String> nicks =server.roomHash.get(msg[1]).keySet();
		for(String n : nicks) {
			String send = msg[0] + "/";
			for (int i = 2; i < msg.length; i++) send += msg[i] + "/";
			sendMsg( server.roomHash.get(msg[1]).get(n), send); //server.roomHash.get(msg[1]).get(n) => 해당방에 속한 n닉네임의 소켓
		}
	}
	
	// 해당소켓 유저에게 보내기
	public void sendMsg(Socket s, String ...msg) {
		try {
			dos = new DataOutputStream(s.getOutputStream());
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i]+ "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
}
