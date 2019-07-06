package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Client;

import client.join.Join;
import dbConn.util.DBControll;




	public LoginAction() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		

		if (e.getSource() == btnLogin) { // �α���			

			DBControll db = new DBControll(this);
			String nick = db.select();
			
			// �α��� ������ ���
			if(nick == null) { JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���"); return;}
			

			dispose(); // UI ����
			Client c = new Client(nick); // Ŭ���̾�Ʈ ����
			c.sendMsg("NewUser", nick); // ������ �� ������
			
		} else if (e.getSource() == btnNew) { // ȸ������
			new Join();
		}
		
	} // actionPerformed(ActionEvent e) end

	
} // LoginAction end
