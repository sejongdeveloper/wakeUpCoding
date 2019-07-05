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
			// ��Ʈ��ũ �б� ��ü(s.getInputStream()) ����
			dis = new DataInputStream(s.getInputStream());
			 
			while (true) { // �׻� ���� �� �ֵ��� ���ѷ���
				String msg = dis.readUTF();
				applyMsg(msg);
			}
		} catch (IOException e) {} 
		finally {try {dis.close(); s.close();} catch (IOException e) {}}
	}

	// Ŭ���̾�Ʈ�� ������ msg������ �����Ͽ� ó��
	public void applyMsg(String msg) throws IOException {
		st = new StringTokenizer(msg, "/"); // msg = "NewUser/nick"
											// nextToken() -> "NewUser"
		                                    // nextToken() -> "nick"
		
		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();
		System.out.println("act: " + act );
		System.out.println("act2   : " + act2);
		
		if (act.equals("NewUser")) { // ���ο� Ŭ���̾�Ʈ(�ڽ�) ����
			
			// �����׸� ��������UI �߰��϶�� �Ѹ�
			Set<String> nicks = server.userHash.keySet();
			for (String nick : nicks) {
				sendMsg(s, act, nick);
			}
			
			// ����������� �� �г���UI �߰��϶�� �Ѹ�
			server.userHash.put(act2, s);
			sendAllMsg(act, act2);
			
			// �����׸� ������UI ��� �߰��϶�� �Ѹ�
			Set<String> roomN = server.roomHash.keySet();
			for(String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
		} else if(act.equals("JoinRoom")) { // ������ 
			String nick = st.nextToken(); // act1:JoinRoom | act2:���̸� | nick:�г���
			String oldRoom = st.nextToken(); // ������
			
			//���� Ŭ�� ����UI �������
			sendMsg(s, "DelUserList", oldRoom);
			
			//�������� �ִ� ���¿��� �������� �� ���
			if(!oldRoom.equals("none")) {
				//�����濡 ���� ����
				server.roomHash.get(oldRoom).remove(nick);
				
				//������ ��ο��� �� �и� UI �����϶�� �Ѹ�
				sendRoomMsg("DelUser", oldRoom, nick);

			} 
			
			//�����׸� ����� ���� ��θ� UI �߰��϶�� �Ѹ�
			Set<String> nicks = server.roomHash.get(act2).keySet();
			for (String n : nicks) {
				sendMsg(s, "NewUser", n);
			}
			
			//����濡 ���� ���
			server.roomHash.get(act2).put(nick, s);
			
			//����� ��ο��� �� �и� UI �߰��϶�� �Ѹ�
			sendRoomMsg("NewUser", act2, nick); 
			
			//����� ��ο��� �� ���� �����ߴٰ� �Ѹ�
			sendRoomMsg("Chatting", act2, "������", nick+"���� �����Ͽ����ϴ�.");			
			
			//��������� UIŸ��Ʋ ����
			sendMsg(s, "ChangeTitle", act2, nick);
			
		} else if (act.equals("Chatting")) { // ä�ó���
			String roomName = act2; // ���̸�
			String nick = st.nextToken(); // �г���
			if(st.hasMoreTokens()) {
				String chat = st.nextToken(); // ä�ó���
				// �ش�濡 ����ִ� ��� �������� ä�ó��� �Ѹ�
				sendRoomMsg(act, roomName, nick, chat); 
			}
			
			
		}else if(act.equals("NewRoom")) { // �����
			// ���̸� HashTable�� �߰�(put)
			// server.roomHash.put(���̸�, new Hashtable<�г���, Socket>());
			server.roomHash.put(act2, new Hashtable<String, Socket>());
			
			//��� �������� ���ο� ���̸� UI�� �߰��϶�� �Ѹ�
			sendAllMsg(act, act2);	
		}else if(act.equals("ExitUser")) {
			String nick = act2;
			server.userHash.remove(nick);
			sendAllMsg("DelUser", nick);
			
			if(st.hasMoreTokens()) {
				String roomName = st.nextToken();
				server.roomHash.get(roomName).remove(nick);
				sendRoomMsg("Chatting", roomName, "������", nick+"���� �����Ͽ����ϴ�.");
			}
		}

	}// applyMsg(String msg) end
	
	// ��� �������� ������
	public void sendAllMsg(String ... msg) {
		Set<String> nicks = server.userHash.keySet(); // ������ ��� �г��� ���
		String send = "";
		for(String n : nicks) {
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/"; // ex1) msg[0]/msg[1]/msg[2] | ex2) NewUser/nick/
			sendMsg(server.userHash.get(n), send); // sendMsg( n[�г��ӿ� ���� ����], send[��: NewUser/nick/] );
		}
	}
	
	// �ش�濡 ���� ��� �������� ������
	public void sendRoomMsg(String ... msg) {	// 0:act 1:roomName 2:nickname 3~:msg
		Set<String> nicks =server.roomHash.get(msg[1]).keySet();
		for(String n : nicks) {
			String send = msg[0] + "/";
			for (int i = 2; i < msg.length; i++) send += msg[i] + "/";
			sendMsg( server.roomHash.get(msg[1]).get(n), send); //server.roomHash.get(msg[1]).get(n) => �ش�濡 ���� n�г����� ����
		}
	}
	
	// �ش���� �������� ������
	public void sendMsg(Socket s, String ...msg) {
		try {
			dos = new DataOutputStream(s.getOutputStream());
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i]+ "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
}
