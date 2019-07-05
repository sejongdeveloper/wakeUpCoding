package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.Client;
import client.join.JoinUI;

public class LoginAction extends LoginUI implements ActionListener,KeyListener{

	private String id;
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
		pwdField.addKeyListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnLogin) {
			System.out.println("�α��ι�ư Ŭ��");
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10) {
			c.sendMsg("NewUser", nick); // ������ �� ������

		}
		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}
