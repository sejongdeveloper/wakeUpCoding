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
			JOptionPane.showMessageDialog(null, "ä�ù� ����");	
			c.joinRoom();
		} else if (e.getSource() == btnNewRoom) {
			JOptionPane.showMessageDialog(null, "ä�ù� ����");
			c.createRoom();
		} else if (e.getSource() == btnEnter) {
			c.sendMsg("Chatting/"+c.nick +"/"+ chatField.getText().trim());
			JOptionPane.showMessageDialog(null, "����");
			
		}
	}
}
