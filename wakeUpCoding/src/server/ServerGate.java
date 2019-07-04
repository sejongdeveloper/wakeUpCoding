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
			String roomName = act2; // ���߿� ���̸����� ó���ؾ���
			String nick = st.nextToken();
			String chat = st.nextToken();
			sendAllMsg(act, roomName, nick, chat);
			
		} else if (act.equals("NewUser")) {
			String olds = "";
			if(!server.userHash.isEmpty()) {
				Set<String> nicks = server.userHash.keySet();
				for(String n : nicks) {
					olds += "/" + n;
				}
				sendMsg("OldUser" + olds, s);
			}	
			
			server.userHash.put(act2, s);
			System.out.println("�г��� : " + act2 + "==>" + olds);
			sendAllMsg(act, act2);
			/////////��Ѹ���//////////
			server.roomHash.put("proto", new Hashtable<String, Socket>());
			server.roomHash.get("proto").put(act2, s);
			sendMsg("NewRoom/proto", s);
			
			//////////////////////////
			
		}

	}// ����
	
	public void sendAllMsg(String ... str) {
		Set<String> nicks = userHash.keySet();
		for(String n : nicks) {
			try {
				String send = "";
				for (int i = 0; i < str.length; i++) {
					send += str[i] + "/";
				}
				sendMsg(send, userHash.get(n));
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void sendRoomMsg(Hashtable<String, Hashtable<String, Socket>> roomHash, String roomName, String ... str) {
		Set<String> nicks = roomHash.get(roomName).keySet();
		for(String n : nicks) {
			try {
				String send = "";
				for (int i = 0; i < str.length; i++) {
					send += str[i] + "/";
				}
				sendMsg(send, roomHash.get(roomName).get(n));
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	

	// Ŭ���̾�Ʈ ������
	public void sendMsg(String msg) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
		
	}
	
	public void sendMsg(String msg, Socket s) throws IOException {

		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
