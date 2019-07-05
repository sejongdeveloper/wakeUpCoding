package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Client;
import ConnecTion.DBControll;
import client.join.Join;
import client.join.JoinUI;


public class LoginAction extends LoginUI implements ActionListener{
	
	private String id;
	private String pwd;
	
	
	
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		
		if (e.getSource() == btnLogin) {
			System.out.println("�α��ι�ư Ŭ��");
			id = idField.getText().trim();
			pwd = pwdField.getText().trim();
			////////////////////////////////
			DBControll db= new DBControll(this);
			String nick = db.select();
			dispose(); 
			
			/////////////////////////
			Client c = new Client(nick);
			c.sendMsg("NewUser", nick); // ������ �� ������
			
		} else if (e.getSource() == btnNew) {
			System.out.println("ȸ������ ��ư Ŭ��");	
			
			dispose(); 
			new Join();
		}
		
	}

	
}
