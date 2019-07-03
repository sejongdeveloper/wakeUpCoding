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
			System.out.println("�α��ι�ư Ŭ��");
			id = idField.getText().trim();
//			client.showChatUi();
			
			JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�.");
			dispose(); 
			Client c = new Client();
			////////////////////////////////
			String nick = String.valueOf((int)(Math.random()*100)+1);
			
			/////////////////////////
			c.nick = nick; // �� ���
			c.sendMsg("NewUser/" + nick); // ������ �� ������
			
		} else if (e.getSource() == btnNew) {
			JoinUI joinUi = new JoinUI();
		}
		
	}
	
}
