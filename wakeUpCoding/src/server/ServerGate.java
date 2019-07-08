package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

//������ Ŭ���̾�Ʈ �����(Gate)��� Ŭ����
public class ServerGate extends Thread {
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	Socket s;
	Server server;
	
	//������ �޼ҵ�
	public ServerGate(Socket s, Server server) throws IOException {
		this.s = s;
		this.server = server;

	} // ServerGate(Socket s, Server server)

	@Override
	public void run() {
		try {
			// ��Ʈ��ũ �б� ��ü(s.getInputStream()) ����
			dis = new DataInputStream(s.getInputStream());

			while (true) { // �׻� ���� �� �ֵ��� ���ѷ���
				String msg = dis.readUTF();
				System.out.println(msg); 
				applyMsg(msg);
			}
		} catch (IOException e) {
			System.out.println("ServerGate/Err/" + e.getMessage());
		} finally {
			try {dis.close(); s.close();} catch (IOException e) {} // ���ϰ��� ����
		}
	} // run() end

	// Ŭ���̾�Ʈ�� ������ msg������ �����Ͽ� ó��
	public void applyMsg(String msg) {
		st = new StringTokenizer(msg, "/"); // msg = "NewUser/nick"
											// nextToken() -> "NewUser"
											// nextToken() -> "nick"

		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();
		System.out.println("act: " + act);
		System.out.println("act2   : " + act2);

		// NewUser/nick
		if (act.equals("NewUser")) { // ���ο� Ŭ���̾�Ʈ(�ڽ�) ����
			String nick = act2; // �г���
			
			// ���� ������ ������ ���
			if(server.userHash.containsKey(nick)) {
				sendMsg(s, "Bye/suah"); return;
			}
			
			// �����׸� ��������UI �߰��϶�� �Ѹ�
			Set<String> nicks = server.noneHash.keySet();
			for (String n : nicks) {
				sendMsg(s, act, n);
			}
			// ������������ �� �г���UI �߰��϶�� �Ѹ�
			server.userHash.put(nick, s);
			server.noneHash.put(nick, s);
			sendAllMsg("none", nick);

			// �����׸� ������UI ��� �߰��϶�� �Ѹ�
			Set<String> roomN = server.roomHash.keySet();
			for (String name : roomN) {
				sendMsg(s, "NewRoom", name);
			}
			
		// JoinRoom/roomName/nick
		} else if (act.equals("JoinRoom")) { // ������
			String roomName = act2; // �����
			String nick = st.nextToken(); // act1:JoinRoom | act2:���̸� | nick:�г���
			String oldRoom = st.nextToken(); // ������

			// ���� Ŭ�� ����UI �������
			sendMsg(s, "DelUserList", oldRoom);

			// �������� �ִ� ���¿��� �������� �� ���
			if (!oldRoom.equals("none")) {
				// �����濡 ���� ����
				server.roomHash.get(oldRoom).remove(nick);

				// ������ ��ο��� �� �и� UI �����϶�� �Ѹ�
				sendRoomMsg("DelUser", oldRoom, nick);
				
				// ������ ��ο��� �����ߴٰ� �˷���
				sendRoomMsg("Chatting", oldRoom, "������", nick + "���� �����Ͽ����ϴ�.");

			} else {
				server.noneHash.remove(nick);
				for(String n : server.noneHash.keySet()) {
					sendMsg(server.noneHash.get(n), "DelUser", nick);
					sendMsg(server.noneHash.get(n), "Chatting", "������", nick + "���� �����Ͽ����ϴ�.");
				}
				
			}

			// �����׸� ����� ���� ��θ� UI �߰��϶�� �Ѹ�
			Set<String> nicks = server.roomHash.get(roomName).keySet();
			for (String n : nicks) {
				sendMsg(s, "NewUser", n);
			}

			// ����濡 ���� ���
			server.roomHash.get(roomName).put(nick, s);

			// ����� ��ο��� �� �и� UI �߰��϶�� �Ѹ�
			sendRoomMsg("NewUser", roomName, nick);

			// ����� ��ο��� �� ���� �����ߴٰ� �Ѹ�
			sendRoomMsg("Chatting", roomName, "������", nick + "���� �����Ͽ����ϴ�.");

			// ��������� UIŸ��Ʋ ����
			sendMsg(s, "ChangeTitle", roomName, nick);
		
		// Chatting/roomName/nick	
		} else if (act.equals("Chatting")) { // ä�ó���
			String roomName = act2; // ���̸�
			String nick = st.nextToken(); // �г���
			
			// ä�ó����� �ִٸ� �ش�濡 ������
			if (st.hasMoreTokens()) {
				String chat = st.nextToken(); // ä�ó���
				
				// ���� ä���� ���
				if(roomName.equals("none")) {
					Set<String> nicks = server.noneHash.keySet();
					for(String n : nicks) {
						sendMsg(server.noneHash.get(n), act, nick, chat);
					}
				} else {
					// �ش�濡 ����ִ� ��� �������� ä�ó��� �Ѹ�
					sendRoomMsg(act, roomName, nick, chat);					
				}
			}

		// NewRoom/roomName
		} else if (act.equals("NewRoom")) { // �����
			String roomName = act2;
			
			// 1.�����̸��� �����ִ��� ã�ƺ���
			if (server.roomHash.get(roomName) != null) {
				// ��������ϴ� ���� �̸��� ������ ���� �������
				sendMsg(s, "CreateRoomfail", roomName);

			} else {
				server.roomHash.put(roomName, new Hashtable<String, Socket>());
				sendAllMsg(act, roomName);
				

			} // if end
		
		// ExitUser/nick/[roomName]
		} else if (act.equals("ExitUser")) { // ������ ä���� ������ ���
			String nick = act2;
			System.out.println("���� ��");
			server.userHash.remove(nick);
			sendAllMsg("DelUser", nick);
			if(server.noneHash.containsKey(nick)) server.noneHash.remove(nick);
			System.out.println("���� �Ϸ�");
			
			// ������ �濡 ���� ���� ���
			String roomName = st.nextToken();
			if (!roomName.equals("none")) {
				server.roomHash.get(roomName).remove(nick);
				sendRoomMsg("Chatting", roomName, "������", nick + "���� �����Ͽ����ϴ�.");
				
			} 
		} // if end
	} // applyMsg(String msg) end

	// ��� �������� ������
	public void sendAllMsg(String... msg) {
		Set<String> nicks = server.userHash.keySet(); // ������ ��� �г��� ���
		if(msg[0].equals("none")) {
			nicks = server.noneHash.keySet();
			msg[0] = "NewUser";
		}
		String send = "";
		for (String n : nicks) {
			for (int i = 0; i < msg.length; i++)
				send += msg[i] + "/"; // ex1) msg[0]/msg[1]/msg[2] | ex2) NewUser/nick/
			sendMsg(server.userHash.get(n), send); // sendMsg( n[�г��ӿ� ���� ����], send[��: NewUser/nick/] );
		}
	}

	// �ش�濡 ���� ��� �������� ������
	public void sendRoomMsg(String... msg) { // 0:act 1:roomName 2:nickname 3~:msg
		Set<String> nicks = server.roomHash.get(msg[1]).keySet();
		for (String n : nicks) {
			String send = msg[0] + "/";
			for (int i = 2; i < msg.length; i++)
				send += msg[i] + "/";
			sendMsg(server.roomHash.get(msg[1]).get(n), send); // server.roomHash.get(msg[1]).get(n) => �ش�濡 ���� n�г����� ����
		}
	}

	// �ش���� �������� ������
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
