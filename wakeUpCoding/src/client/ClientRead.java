package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class ClientRead extends Thread {
	private DataInputStream dis;
	private StringTokenizer st;
	private Socket s;
	ClientAction ca;

	// 생성자 메소드
	public ClientRead(Socket s, ClientAction ca) {
		this.s = s;
		this.ca = ca;

	} 

	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream()); // 네트워크 읽기 객체(s.getInputStream()) 생성
			while (true) { // 서버에서 오는 데이터 항상 읽을 수 있도록 무한루프
				String msg = dis.readUTF();
				System.out.println(msg);
				applyMsg(msg);
			}
		} catch (IOException e) { 
			System.out.println("ClientRead/Err/" + e.getMessage());
		}
	} // run() end

	public void applyMsg(String msg) {
		st = new StringTokenizer(msg, "/");
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		
		// 채팅내용
		if (act.equals("Chatting")) { 
			String message = st.nextToken();
			ca.chatArea.append(act2 + " : " + message + "\n");
			
		// 새로 들어온 유저 UI리스트 추가
		} else if (act.equals("NewUser")) { 
			ca.uList.add(act2);
			ca.userList.setListData(ca.uList);
			
		// 받아온 닉네임 UI에서 제거
		} else if (act.equals("DelUser")) { 
			ca.uList.remove(act2);
			ca.userList.setListData(ca.uList);
		
		// 방생성
		} else if (act.equals("NewRoom")) { 
			ca.rList.add(act2);
			ca.roomList.setListData(ca.rList);
		
		// 방 만들지못했을때
		}else if (act.equals("CreateRoomfail")) {
			JOptionPane.showMessageDialog(null, "방만들기 실패", "알림", JOptionPane.ERROR_MESSAGE);

		// 유저UI 모두 제거
		} else if (act.equals("DelUserList")) { 
			ca.uList.clear();
			ca.userList.setListData(ca.uList);
			
		// UI제목 변경
		} else if (act.equals("ChangeTitle")) { 
			String nick = st.nextToken();
			ca.setTitle("BitTalk  닉네임:" + nick + "     방이름: " + act2);
			ca.chatUser.setText(act2 + " 접속자");
		
		// 동일 유저로 접속한 경우 종료
		} else if (act.equals("Bye")) {
			JOptionPane.showMessageDialog(null, "이미 접속중인 유저입니다");
			ca.dispose();
			System.exit(0);
		} else if (act.equals("Msg")) {
			JOptionPane.showMessageDialog(null, act2);
		} else if (act.equals("DelRoom")) {
			ca.rList.remove(act2);
			ca.roomList.setListData(ca.rList);
		}
	} // applyMsg(String msg) end
} // ClientRead end