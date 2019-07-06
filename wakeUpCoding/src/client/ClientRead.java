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

	// ������ �޼ҵ�
	public ClientRead(Socket s, ClientAction ca) {
		this.s = s;
		this.ca = ca;

	} 

	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream()); // ��Ʈ��ũ �б� ��ü(s.getInputStream()) ����
			while (true) { // �������� ���� ������ �׻� ���� �� �ֵ��� ���ѷ���
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
		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();
		
		// ä�ó���
		if (act.equals("Chatting")) { 
			String message = st.nextToken();
			ca.chatArea.append(act2 + " : " + message + "\n");
			
		// ���� ���� ���� UI����Ʈ �߰�
		} else if (act.equals("NewUser")) { 
			ca.uList.add(act2);
			ca.userList.setListData(ca.uList);
			
		// �޾ƿ� �г��� UI���� ����
		} else if (act.equals("DelUser")) { 
			ca.uList.remove(act2);
			ca.userList.setListData(ca.uList);
		
		// �����
		} else if (act.equals("NewRoom")) { 
			ca.rList.add(act2);
			ca.roomList.setListData(ca.rList);
			
		// ���� ���� ���� UI����Ʈ �߰�
		}else if (act.equals("OldRoom")) { 
			ca.rList.add(act2);
			while(st.hasMoreTokens()) {
				ca.rList.add(st.nextToken());
			}
			ca.roomList.setListData(ca.rList);	
		
		// �� ��������������
		}else if (act.equals("CreateRoomfail")) {
			JOptionPane.showMessageDialog(null, "�游��� ����", "�˸�", JOptionPane.ERROR_MESSAGE);

		// ����UI ��� ����
		} else if (act.equals("DelUserList")) { 
			ca.uList.clear();
			ca.userList.setListData(ca.uList);
			
		// UI���� ����
		} else if (act.equals("ChangeTitle")) { 
			String nick = st.nextToken();
			ca.setTitle("�г���:" + nick + "     ���̸�: " + act2);
		
		// ���� ������ ������ ��� ����
		} else if (act.equals("Bye")) {
			JOptionPane.showMessageDialog(null, "�̹� �������� �����Դϴ�");
			ca.dispose();
			System.exit(0);
		}
	} // applyMsg(String msg) end
} // ClientRead end