package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private DataOutputStream dos;
	private Socket s;
	public String nick;

	public Client(String nick) {
		this.nick = nick;
		ClientAction ca = new ClientAction(this);
		try {
			s = new Socket("localhost", 7777); // 원래는 서버IP는 변동이 자주 일어나지 않음
			Thread read = new ClientRead(s, ca);
			read.start();
			dos = new DataOutputStream(s.getOutputStream()); // 네트워크 출력 객체(s.getOutputStream()) 생성
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // Client() end
	
	// 서버에 msg 보내기
	public void sendMsg(String ...msg) { // 원래는 ClientRead로 가야하나 이렇게도 뺄수 있다고 실험함
		try {
			String send = "";
			for (int i = 0; i < msg.length; i++) send += msg[i] + "/";
			dos.writeUTF(send);
		} catch (IOException e) {e.printStackTrace();}
	}
	
} // Client end
