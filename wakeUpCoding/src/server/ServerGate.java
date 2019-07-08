package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

//서버와 클라이언트 입출력(Gate)담당 클래스
public class ServerGate implements Runnable {
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	Socket s;
	Server server;
	
	//생성자 메소드
	public ServerGate(Socket s, Server server) throws IOException {
		this.s = s;
		this.server = server;

	} // ServerGate(Socket s, Server server)

	@Override
	public void run() {
		try {
			// 네트워크 읽기 객체(s.getInputStream()) 생성
			dis = new DataInputStream(s.getInputStream());

			while (true) { // 항상 읽을 수 있도록 무한로프
				String msg = dis.readUTF();
				System.out.println(msg); 
				applyMsg(msg);
			}
		} catch (IOException e) {
			System.out.println("ServerGate/Err/" + e.getMessage());
		} finally {
			try {dis.close(); s.close();} catch (IOException e) {} // 소켓관련 끊기
		}
	} // run() end

	// 클라이언트가 보내온 msg내용을 구분하여 처리
	public void applyMsg(String msg) {
		st = new StringTokenizer(msg, "/"); // msg = "NewUser/nick"
											// nextToken() -> "NewUser"
											// nextToken() -> "nick"

		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		System.out.println("act: " + act);
		System.out.println("act2   : " + act2);

		// NewUser/nick
		if (act.equals("NewUser")) { // 새로운 클라이언트(자신) 접속
			String nick = act2; // 닉네임
			
			// 동일 유저가 접속한 경우
			if(server.userHash.containsKey(nick)) {
				sendMsg(s, "Bye/suah"); return;
			}
			
			// 나한테만 대기실유저UI 추가하라고 뿌림
			Set<String> nicks = server.roomHash.get("대기방").keySet();
			for (String n : nicks) {
				sendMsg(s, act, n);
			}
			// 대기실유저에게 내 닉네임UI 추가하라고 뿌림
			server.userHash.put(nick, s);
			server.roomHash.get("대기방").put(nick, s);
			sendRoomMsg("NewUser", "대기방", nick);

			// 나한테만 기존방UI 모두 추가하라고 뿌림
			Set<String> roomN = server.roomHash.keySet();
			for (String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
		// JoinRoom/roomName/nick
		} else if (act.equals("JoinRoom")) { // 방참여
			String roomName = act2; // 현재방
			String nick = st.nextToken(); // act1:JoinRoom | act2:방이름 | nick:닉네임
			String oldRoom = st.nextToken(); // 기존방

			// 나의 클라만 유저UI 모두제거
			sendMsg(s, "DelUserList", oldRoom);

			// 기존방에 나를 제거
			server.roomHash.get(oldRoom).remove(nick);

			// 기존방 모두에게 내 닉만 UI 제거하라고 뿌림
			sendRoomMsg("DelUser", oldRoom, nick);
			
			// 기존방 모두에게 퇴장했다고 알려줌
			sendRoomMsg("Chatting", oldRoom, "관리자", nick + "님이 퇴장하였습니다.");

			// 나한테만 현재방 유저 모두를 UI 추가하라고 뿌림
			Set<String> nicks = server.roomHash.get(roomName).keySet();
			for (String n : nicks) {
				sendMsg(s, "NewUser", n);
			}

			// 현재방에 나를 등록
			server.roomHash.get(roomName).put(nick, s);

			// 현재방 모두에게 내 닉만 UI 추가하라고 뿌림
			sendRoomMsg("NewUser", roomName, nick);

			// 현재방 모두에게 내 닉이 입장했다고 뿌림
			sendRoomMsg("Chatting", roomName, "관리자", nick + "님이 입장하였습니다.");

			// 현재방으로 UI타이틀 수정
			sendMsg(s, "ChangeTitle", roomName, nick);
		
		// Chatting/roomName/nick	
		} else if (act.equals("Chatting")) { // 채팅내용
			String roomName = act2; // 방이름
			String nick = st.nextToken(); // 닉네임
			
			// 채팅내용이 있다면 해당방에 보내기
			if (st.hasMoreTokens()) {
				String chat = st.nextToken(); // 채팅내용
				
				// 해당방에 들어있는 모든 유저에게 채팅내용 뿌림
				sendRoomMsg(act, roomName, nick, chat);
			}

		// NewRoom/roomName
		} else if (act.equals("NewRoom")) { // 방생성
			String roomName = act2;
			
			// 1.같은이름의 방이있는지 찾아보기
			if (server.roomHash.get(roomName) != null) {
				// 만들고자하는 방의 이름이 동일한 방이 있을경우
				sendMsg(s, "CreateRoomfail", roomName);

			} else {
				server.roomHash.put(roomName, new Hashtable<String, Socket>());
				sendAllMsg(act, roomName);
				

			} // if end
		
		// ExitUser/nick/[roomName]
		} else if (act.equals("DelRoom")) {
			String roomName = act2;
			if(server.roomHash.get(roomName).isEmpty()) {
				server.roomHash.remove(roomName);
				Set<String> nicks = server.userHash.keySet();
				for(String nick : nicks) {
					sendMsg(server.userHash.get(nick), "DelRoom", roomName);
				}
				sendMsg(s, "Msg", roomName + "방 삭제했습니다.");
			} else {
				sendMsg(s, "Msg", "비어있는 방만 삭제가능합니다.");
			}
		} else if (act.equals("ExitUser")) { // 유저가 채팅을 종료할 경우
			String nick = act2;
			System.out.println("삭제 전");
			server.userHash.remove(nick);
			sendAllMsg("DelUser", nick);
			System.out.println("삭제 완료");
			
			// 유저가 방에 들어가서 나간 경우
			String roomName = st.nextToken();
			server.roomHash.get(roomName).remove(nick);
			sendRoomMsg("Chatting", roomName, "관리자", nick + "님이 퇴장하였습니다.");
				
		} // if end
	} // applyMsg(String msg) end

	// 모든 유저에게 보내기
	public void sendAllMsg(String... msg) {
		Set<String> nicks = server.userHash.keySet(); // 접속한 모든 닉네임 얻기
		String send = "";
		for (String n : nicks) {
			for (int i = 0; i < msg.length; i++)
				send += msg[i] + "/"; // ex1) msg[0]/msg[1]/msg[2] | ex2) NewUser/nick/
			sendMsg(server.userHash.get(n), send); // sendMsg( n[닉네임에 대한 소켓], send[예: NewUser/nick/] );
		}
	}

	// 해당방에 대한 모든 유저에게 보내기
	public void sendRoomMsg(String... msg) { // 0:act 1:roomName 2:nickname 3~:msg
		Set<String> nicks = server.roomHash.get(msg[1]).keySet();
		for (String n : nicks) {
			String send = msg[0] + "/";
			for (int i = 2; i < msg.length; i++)
				send += msg[i] + "/";
			sendMsg(server.roomHash.get(msg[1]).get(n), send); // server.roomHash.get(msg[1]).get(n) => 해당방에 속한 n닉네임의 소켓
		}
	}

	// 해당소켓 유저에게 보내기
	public void sendMsg(Socket s, String... msg) {
		try {
			dos = new DataOutputStream(s.getOutputStream());
			String send = "";
			for (int i = 0; i < msg.length; i++)
				send += msg[i] + "/";
			dos.writeUTF(send);
		} catch (IOException e) {
			System.out.println("sendMsg/Err/" + e.getMessage());
		}
	}
}
