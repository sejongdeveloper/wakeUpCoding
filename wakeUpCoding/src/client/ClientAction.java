package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.login.LoginAction;

public class ClientAction extends ClientUI implements ActionListener{
	Client c;
	String roomName = "";
	public ClientAction(Client c) {
		this.c = c;
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		setTitle(LoginAction.nick);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "������", c.nick+"���� �����Ͽ����ϴ�.");
			}
			roomName = roomList.getSelectedValue();
			chatArea.setText("");
			c.sendMsg("JoinRoom",roomName,c.nick);
			JOptionPane.showMessageDialog(null, "ä�ù� ����");
		} else if (e.getSource() == btnNewRoom) {
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			c.sendMsg("NewRoom/" + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
//			c.newRoom();
		} else if (e.getSource() == btnEnter) {
			c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
//			JOptionPane.showMessageDialog(null, "����");
			
			
			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ
			
		}
	}
}
