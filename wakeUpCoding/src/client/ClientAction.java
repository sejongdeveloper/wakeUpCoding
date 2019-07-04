package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener{
	Client c;
	public ClientAction(Client c) {
		this.c = c;
		
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			String roomName = "proto";
			c.sendMsg("JoinRoom",roomName,c.nick);
			JOptionPane.showMessageDialog(null, "채팅방 입장");
		} else if (e.getSource() == btnNewRoom) {
			JOptionPane.showMessageDialog(null, "채팅방 생성");
//			c.createRoom();
		} else if (e.getSource() == btnEnter) {
			String roomName = "proto";
			c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
			JOptionPane.showMessageDialog(null, "전송");
			
			
			chatField.setText(""); // 입력필드 초기화
			
		}
	}
}
