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
	
	///
	
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		
		if (e.getSource() == btnLogin) {
			System.out.println("로그인버튼 클릭");
			id = idField.getText().trim();
			pwd = pwdField.getText().trim();
			////////////////////////////////
			DBControll db= new DBControll(this);
			String nick = db.select();
			dispose(); 
			
			/////////////////////////
			Client c = new Client(nick);
			c.sendMsg("NewUser", nick); // 서버에 닉 보내기
			
		} else if (e.getSource() == btnNew) {
			System.out.println("회원가입 버튼 클릭");	
			
			dispose(); 
			new Join();
		}
		
	}

	
}
