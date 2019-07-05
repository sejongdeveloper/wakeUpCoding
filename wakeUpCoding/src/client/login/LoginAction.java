package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Client;

import client.join.Join;
import dbConn.util.DBControll;



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
		
		if (e.getSource() == btnLogin) { // �α���
			
			id = idField.getText().trim();

			DBControll db = new DBControll(this);
			String nick = db.select();
			
			if(nick == null) { JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���"); return;}
			
			dispose(); 

			Client c = new Client(nick);
			c.sendMsg("NewUser", nick); // ������ �� ������
			
		} else if (e.getSource() == btnNew) {


			new Join();
		}
		
	}

	
}
