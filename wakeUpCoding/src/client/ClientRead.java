package client;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientRead extends Thread{
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	
	public ClientRead(Socket s) {
		this.s = s;
	}
	@Override
	public void run() {
		// �����ϼ���.
		while(true) {
			
		}
		
	}
	// ���� 90%
	public void applyMsg(String msg) {
		//��ū���� �˾Ƽ� ��������. 
		st = new StringTokenizer(msg, "/");
		String act = st.nextToken(); // �ൿ
		String act2 = st.nextToken();
		
		// �����ϼ���.
		if(act.equals("�ൿ1")) {
		
			
			
		} else if(act.equals("�ൿ2")){
			
		}
	}
}
