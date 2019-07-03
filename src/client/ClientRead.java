package client;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;

public class ClientRead extends Thread{
	private Socket s;
	private DataInputStream dis;
	private StringTokenizer st;
	private TextArea chatArea;
	private JList<String> userList;
	private Vector<String> uList; 
	
	public ClientRead(Socket s,ClientAction ca) {
		this.s = s;
		this.chatArea = ca.chatArea;
		this.userList = ca.userList;
	}
	@Override
	public void run() {
		// 구현하세요.
		while(true) {
			try {
				String msg = dis.readUTF();
				
				applyMsg(msg);
			} catch (Exception e) {
			}
		}
		
		
	}
	
	// 서버 90%
	public void applyMsg(String msg) {
		//토큰으로 알아서 나누세요. 
		st = new StringTokenizer(msg, "/");
		String act = st.nextToken(); // 행동
		String act2 = st.nextToken();
		
		// 구현하세요.
		if(act.equals("Chatting")) {
			String message = st.nextToken();
			chatArea.append(act + " : " + message + "\n");
			
		} else if(act.equals("New_User")){
			uList.add(msg);
			userList.setListData(uList);
			//asdasd
			
		}
	}
}
