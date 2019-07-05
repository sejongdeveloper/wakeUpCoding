package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Client;
import client.join.JoinUI;

public class LoginAction extends LoginUI implements ActionListener{

	private String id;
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnLogin) { // �α���
			id = idField.getText().trim();
			dispose(); 
			////////////////////////////////
			String nick = String.valueOf((int)(Math.random()*100)+1);
			/////////////////////////
			Client c = new Client(nick);
			c.sendMsg("NewUser", nick); // ������ �� ������
			
		} else if (e.getSource() == btnNew) {
			JoinUI joinUi = new JoinUI();
		}
		
	}
	
}
