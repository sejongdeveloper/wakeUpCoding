package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener, KeyListener {
	Client c;
	String roomName = "����";
	String oldName = "����";
	public ClientAction() {}
	
	public ClientAction(Client c) {
		this.c = c;
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		btnDelRoom.addActionListener(this);
		chatField.addKeyListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				c.sendMsg("ExitUser", c.nick, roomName);
				System.exit(0);
			}
		});
		setTitle("BitTalk  �г���:" + c.nick + "     ���̸�: ����");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnJoin) { // ������
			//ä�ù� �������
			if(roomList == null || rList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ä�ù��� �����ϴ�.");
				return;
			}
			
			// �渮��ƮUI�� ������ ���̸� ����
			roomName = roomList.getSelectedValue();
			
			// �������Ҷ� ������� ������� ���� ���
			if(roomName.equals(oldName)) {
				JOptionPane.showMessageDialog(null, "�������� ä�ù��Դϴ�.");
				return;
			}		
			
			chatArea.setText(""); // ä��UI �ʱ�ȭ
			c.sendMsg("JoinRoom",roomName,c.nick,oldName); // ������ �������� ���� ���� ����
			oldName = roomName; // ������ ���� ���� �����濡 ������� ����

			JOptionPane.showMessageDialog(null, "ä�ù� ����");
			
		} else if (e.getSource() == btnNewRoom) { // �����
			chatArea.setText(""); // ä�ó��� �ʱ�ȭ
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			if(roomname == null || roomname.isEmpty()) return;
			if(roomname.equalsIgnoreCase("����")) {
				JOptionPane.showMessageDialog(null, "�ٸ� ���̸��� �Է����ּ���.");
				return;
			}
			c.sendMsg("NewRoom/" + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
			c.sendMsg("JoinRoom",roomname,c.nick,oldName);
		} else if (e.getSource() == btnEnter) {
			if(!roomName.isEmpty()) c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ

		} else if (e.getSource() == btnDelRoom) { // �����
			// �渮��ƮUI�� ������ ���̸� ����
			roomName = roomList.getSelectedValue();
			if(roomName.equals("����")) {
				JOptionPane.showMessageDialog(null, "������ ������ �� �����ϴ�.");
				return;
			}
			c.sendMsg("DelRoom", roomName);
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			c.sendMsg("Chatting", roomName, c.nick, chatField.getText().trim());				
			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

