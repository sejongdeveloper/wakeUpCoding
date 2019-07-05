package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener{
	Client c;
	String roomName = "";
	String oldName = "none";
	
	public ClientAction(Client c) {
		this.c = c;
		
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		
		setTitle("�г���:" + c.nick + "     ���̸�: ����");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnJoin) { // ������
			//ä�ù� �������
			if(roomList == null || rList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ä�ù��� �����ϴ�.");
				return;
			}
			
			// 
			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "������", c.nick+"���� �����Ͽ����ϴ�.");
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
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			c.sendMsg("NewRoom/" + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
			
		} else if (e.getSource() == btnEnter) { // ä������
			c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
			chatField.setText(""); // �Է��ʵ� �ʱ�ȭ
			
		} // if end
	} // actionPerformed(ActionEvent e) end
} // ClientAction end
