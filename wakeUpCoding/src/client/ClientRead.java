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

		ca.setTitle(ca.c.nick);
	}

	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream());
			while (true) {
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

		if (act.equals("Chatting")) {
			String message = st.nextToken();
			ca.chatArea.append(act2 + " : " + message + "\n");
			
		} else if (act.equals("NewUser")) { // 새로 들어온 유저 UI리스트 추가
			ca.uList.add(act2);
			ca.userList.setListData(ca.uList);
		} else if (act.equals("DelUser")) { // 새로 들어온 유저 UI리스트 추가
			System.out.println("선벡 "+ ca.uList);
			ca.uList.remove(act2);
			System.out.println(act2);
			System.out.println("후벡" + ca.uList);
			ca.userList.setListData(ca.uList);
		} else if (act.equals("NewRoom")) {
			ca.rList.add(act2);
			ca.roomList.setListData(ca.rList);
		} else if (act.equals("DelUserList")) { // 새로 들어온 유저 UI리스트 추가
			System.out.println("실행시작");
			System.out.println(act2);
			ca.uList.clear();
			System.out.println("남은사람"+act2);
			ca.userList.setListData(ca.uList);
		}
	
}
}


