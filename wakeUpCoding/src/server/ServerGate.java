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
	
	Hashtable<String, Socket> userHash;
	public ServerGate(Socket s,Hashtable<String, Socket> userHash) {
		this.userHash = userHash;
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

	// ����
	public void applyMsg(String msg) throws IOException {
		//��ū���� �˾Ƽ� ��������. ù�����빮��
		st = new StringTokenizer(msg, "/");
		
		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();
		System.out.println("act: " + act );
		System.out.println("act2   : " + act2);
		//act = "chatting/nickname/ content"
		// �����ϼ���.
		if (act.equals("Chatting")) {
			String nick = act2; // ���߿� ���̸����� ó���ؾ���
			String message = st.nextToken();
			sendAllMsg(act, nick, message);
			
		}

	}// ����
	
	public void sendAllMsg(String Chatting, String nick, String msg) {
		Set<String> nicks = userHash.keySet();
		for(String n : nicks) {
			userHash.get(n);
		}
	}
	

	// Ŭ���̾�Ʈ ������
	public void sendMsg(String msg) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
