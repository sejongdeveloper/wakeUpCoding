package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


public class ServerGate extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	public ServerGate(Socket s) {
		
	}
	
	@Override
	public void run() {
		try {
			dis = new DataInputStream(s.getInputStream());			
		
			while(true) {
				String msg = dis.readUTF();
				applyMsg(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void applyMsg(String msg) {
		//��ū���� �˾Ƽ� ��������. 
		st = new StringTokenizer(msg, "/");
		String xxx = st.nextToken(); // �ൿ
		String xxx2 = st.nextToken();
		
		// �����ϼ���.
		if(xxx.equals("�ൿ1")) {
		
		} else if(xxx.equals("�ൿ2")){
			
		}
	}
	
	// Ŭ���̾�Ʈ ������
	public void sendMsg(String msg) throws IOException {
		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(msg);
	}
}
