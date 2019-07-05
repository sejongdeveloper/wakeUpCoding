package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import client.Client;
import client.join.Join;
import dbConn.util.DBControll;

public class LoginAction extends LoginUI implements ActionListener,KeyListener{

	private String id;
	
	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
		pwdField.addKeyListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10) {
			c.sendMsg("NewUser", nick); // ������ �� ������

		}
		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}
