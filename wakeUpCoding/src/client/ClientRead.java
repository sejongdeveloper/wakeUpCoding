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

		ca.setTitle("�����̿���");
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
		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();

		if (act.equals("Chatting")) {
			String message = st.nextToken();
			ca.chatArea.append(act + " : " + message + "\n");

		} else if (act.equals("NewUser")) {
			ca.uList.add(act2);
			ca.userList.setListData(ca.uList);
		} else if (act.equals("OldUser")) {
			System.out.println("act2:" + act2);
			String[] nicks = act2.split("/");
			for(String n : nicks) {
				System.out.println("n : " + n);
				ca.uList.add(n);
			}
			ca.userList.setListData(ca.uList);		
		}
	}
}