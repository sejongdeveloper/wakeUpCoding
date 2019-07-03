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
			JOptionPane.showMessageDialog(null, "채팅방 입장");	
			c.joinRoom();
		} else if (e.getSource() == btnNewRoom) {
			JOptionPane.showMessageDialog(null, "채팅방 생성");
			c.createRoom();
		} else if (e.getSource() == btnEnter) {
			JOptionPane.showMessageDialog(null, "엔터");
			
		}
	}
}
