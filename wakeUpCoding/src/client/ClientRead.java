package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientRead extends Thread {
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	ClientAction ca;

	public ClientRead(Socket s, ClientAction ca) {
		this.s = s;
		this.ca = ca;

		ca.setTitle("세종이에요");
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
		} else if (act.equals("OldUser")) { // 기존 들어온 유저 UI리스트 추가
			ca.uList.add(act2);
			while(st.hasMoreTokens()) {
				ca.uList.add(st.nextToken());
			}
			ca.userList.setListData(ca.uList);		


		} else if (act.equals("NewRoom")) {
			ca.rList.add(act2);
			ca.roomList.setListData(ca.rList);
		}else if (act.equals("OldRoom")) { // 기존 들어온 유저 UI리스트 추가
			ca.rList.add(act2);
			while(st.hasMoreTokens()) {
				ca.rList.add(st.nextToken());
			}
			ca.roomList.setListData(ca.rList);	

		}
	}
}
