package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener{
	Client c;
	String roomName = "";
	public ClientAction(Client c) {
		this.c = c;
		
		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "관리자", c.nick+"님이 퇴장하였습니다.");
			}
			roomName = roomList.getSelectedValue();
			chatArea.setText("");
			c.sendMsg("JoinRoom",roomName,c.nick);
			JOptionPane.showMessageDialog(null, "채팅방 입장");
		} else if (e.getSource() == btnNewRoom) {
			String roomname = JOptionPane.showInputDialog("방 이름");
			c.sendMsg("NewRoom/" + roomname);// 메세지를 통하여 방이름을 보내준다.
//			c.newRoom();
		} else if (e.getSource() == btnEnter) {
			c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
//			JOptionPane.showMessageDialog(null, "전송");
			
			
			chatField.setText(""); // 입력필드 초기화
			
		}
	}
}
