package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Client;
import client.join.JoinUI;

public class LoginAction extends LoginUI implements ActionListener{

	private String id;
	private Login l;
	
	public LoginAction(Login l) {
		this.l = l;
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnLogin) {
			System.out.println("로그인버튼 클릭");
			id = idField.getText().trim();
//			client.showChatUi();
			
			JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
			dispose(); 
			Client c = new Client();
			////////////////////////////////
			String nick = String.valueOf((int)(Math.random()*100)+1);
			
			/////////////////////////
			c.setNick(nick); // 닉 등록
			c.sendMsg("NewUser/" + nick); // 서버에 닉 보내기
			
		} else if (e.getSource() == btnNew) {
			JoinUI joinUi = new JoinUI();
		}
		
	}
	
}
