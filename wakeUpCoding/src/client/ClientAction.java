package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
=======
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
>>>>>>> JaeJun

import javax.swing.JOptionPane;

public class ClientAction extends ClientUI implements ActionListener, KeyListener {
	Client c;
	String roomName = "";
	String oldName = "none";
<<<<<<< HEAD
	
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
		setTitle("닉네임:" + c.nick + "     방이름: 대기실");
=======

	public ClientAction(Client c) {
		this.c = c;

		btnEnter.addActionListener(this);
		btnJoin.addActionListener(this);
		btnNewRoom.addActionListener(this);
		chatField.addKeyListener(this);
		setTitle("닉네임:" + c.nick + "     방이름: 대기실");

>>>>>>> JaeJun
	}

	@Override
	public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
		
		if (e.getSource() == btnJoin) { // 방참여
			//채팅방 없을경우
			if(roomList == null || rList.isEmpty()) {
=======
		if (e.getSource() == btnJoin) {
			// 채팅방 없을경우
			if (roomList == null || rList.isEmpty()) {
>>>>>>> JaeJun
				JOptionPane.showMessageDialog(null, "채팅방이 없습니다.");
				return;
			}

<<<<<<< HEAD
			// 
=======
>>>>>>> JaeJun
			if (!roomName.isEmpty()) {
				c.sendMsg("Chatting", roomName, "관리자", c.nick + "님이 퇴장하였습니다.");
			}
			
			// 방리스트UI에 선택한 방이름 저장
			roomName = roomList.getSelectedValue();
<<<<<<< HEAD
			
			// 방참여할때 기존방과 현재방이 같을 경우
			if(roomName.equals(oldName)) {
				JOptionPane.showMessageDialog(null, "참여중인 채팅방입니다.");
				return;
			}		
			
			chatArea.setText(""); // 채팅UI 초기화
			c.sendMsg("JoinRoom",roomName,c.nick,oldName); // 서버에 방참여에 대한 정보 전송
			oldName = roomName; // 서버에 전송 이후 기존방에 현재방을 저장
=======
			chatArea.setText("");
			c.sendMsg("JoinRoom", roomName, c.nick, oldName);
			oldName = roomName;
>>>>>>> JaeJun
			JOptionPane.showMessageDialog(null, "채팅방 입장");
			
		} else if (e.getSource() == btnNewRoom) { // 방생성
			String roomname = JOptionPane.showInputDialog("방 이름");
			if(roomname == null || roomname.isEmpty()) return;
			if(roomname.equalsIgnoreCase("none")) {
				JOptionPane.showMessageDialog(null, "다른 방이름을 입력해주세요.");
				return;
			}
			c.sendMsg("NewRoom/" + roomname);// 메세지를 통하여 방이름을 보내준다.
		} else if (e.getSource() == btnEnter) {
<<<<<<< HEAD
			if(!roomName.isEmpty()) c.sendMsg("Chatting", roomName, c.nick ,chatField.getText().trim());
=======
			c.sendMsg("Chatting", roomName, c.nick, chatField.getText().trim());
//			JOptionPane.showMessageDialog(null, "전송")keypress enter;

>>>>>>> JaeJun
			chatField.setText(""); // 입력필드 초기화

		}

	}

	public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
		c.sendMsg("Chatting", roomName, c.nick, chatField.getText().trim());
				
		chatField.setText(""); // 입력필드 초기화

			
<<<<<<< HEAD
		} // if end
	} // actionPerformed(ActionEvent e) end
} // ClientAction end
=======
			}
		}


	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
>>>>>>> JaeJun
