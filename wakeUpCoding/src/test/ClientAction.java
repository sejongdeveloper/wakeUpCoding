package test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientAction extends ClientUI {
	String roomName = "";
	String oldName = "none";
	
	public ClientAction() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setTitle("�г���: aa" + "     ���̸�: ��������");
//		chatUser.setText("ff");
	}
	
	public static void main(String[] args) {
		new ClientAction();
	}
}



