package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Client;
import client.join.Join;
import dbConn.util.DBControll;

public class LoginAction extends LoginUI implements ActionListener{

	private String id;
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnLogin) { // 로그인
			
			id = idField.getText().trim();
			DBControll db = new DBControll(this);
			String nick = db.select();
			
			if(nick == null) { JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해주세요"); return;}
			
			dispose(); 
			Client c = new Client(nick);
			c.sendMsg("NewUser", nick); // 서버에 닉 보내기
			
		} else if (e.getSource() == btnNew) {
			new Join();
		}
		
	}
	
}
