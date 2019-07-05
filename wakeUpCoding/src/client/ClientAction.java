package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import client.login.LoginAction;

public class ClientAction extends ClientUI implements ActionListener{
	Client c;
	String roomName = "";
	String oldName = "none";
	public ClientAction(Client c) {
		this.c = c;
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				c.sendMsg("ExitUser", c.nick, roomName);
				System.exit(0);
			}
		});
		setTitle("�г���:" + c.nick + "     ���̸�: ����");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			//ä�ù� �������
			if(roomList == null || rList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ä�ù��� �����ϴ�.");
				return;
			}
			
			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "������", c.nick+"���� �����Ͽ����ϴ�.");
			}
			roomName = roomList.getSelectedValue();
			chatArea.setText("");
			c.sendMsg("JoinRoom",roomName,c.nick,oldName);
			oldName = roomName;
			JOptionPane.showMessageDialog(null, "ä�ù� ����");
		} else if (e.getSource() == btnNewRoom) {
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			c.sendMsg("NewRoom/" + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
//			c.newRoom();
		} else if (e.getSource() == btnEnter) {
			if(!roomName.isEmpty()) c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
//			JOptionPane.showMessageDialog(null, "����");
			
			
			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ
			
		}
	}
}
