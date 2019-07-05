package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener, KeyListener {
	Client c;
	String roomName = "";
	String oldName = "none";

	public ClientAction(Client c) {
		this.c = c;

		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		chatField.addKeyListener(this);
		setTitle("�г���:" + c.nick + "     ���̸�: ����");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			// ä�ù� �������
			if (roomList == null || rList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ä�ù��� �����ϴ�.");
				return;
			}

			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "������", c.nick + "���� �����Ͽ����ϴ�.");
			}
			roomName = roomList.getSelectedValue();
			chatArea.setText("");
			c.sendMsg("JoinRoom", roomName, c.nick, oldName);
			oldName = roomName;
			JOptionPane.showMessageDialog(null, "ä�ù� ����");
		} else if (e.getSource() == btnNewRoom) {
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			c.sendMsg("NewRoom/" + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
//			c.newRoom();
		} else if (e.getSource() == btnEnter) {
			c.sendMsg("Chatting", roomName, c.nick, chatField.getText().trim());
//			JOptionPane.showMessageDialog(null, "����")keypress enter;

			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ

		}

	}

	public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
		c.sendMsg("Chatting", roomName, c.nick, chatField.getText().trim());
				
		chatField.setText(""); // �Է��ʵ� �ʱ�ȭ

			
			}
		}


	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
