package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class ClientRead extends Thread {
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	ClientAction ca;

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
		} catch (Exception e) {}

	}

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
			System.out.println("ACT2:" + act2);
			ca.rList.add(act2);
			ca.roomList.setListData(ca.rList);

		}else if (act.equals("OldRoom")) { // 기존 들어온 유저 UI리스트 추가
			ca.rList.add(act2);
			while(st.hasMoreTokens()) {
				ca.rList.add(st.nextToken());
			}ca.roomList.setListData(ca.rList);	

		}else if (act.equals("CreateRoomfail")) {// 만들지못했을때
			JOptionPane.showMessageDialog(null, "방만들기 실패", "알림", JOptionPane.ERROR_MESSAGE);

		} else if (act.equals("DelUserList")) { // 새로 들어온 유저 UI리스트 추가
			ca.uList.clear();
			System.out.println("남은사람"+act2);
			ca.userList.setListData(ca.uList);
			
		// UI제목 변경
		} else if (act.equals("ChangeTitle")) { 
			String nick = st.nextToken();
			ca.setTitle("닉네임:" + nick + "     방이름: " + act2);
		} else if (act.equals("Bye")) {
			JOptionPane.showMessageDialog(null, "이미 접속중인 유저입니다");
			ca.dispose();
			System.exit(0);
		}
	}
}