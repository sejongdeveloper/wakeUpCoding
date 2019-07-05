package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ConnecTion.DBControll;
import client.Client;
import client.login.Login;



public class JoinAction extends JoinUI implements ActionListener{
	private Join j;
	public JoinAction(Join j) {
		this.j = j;
		btnNew.addActionListener(this);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNew) {			
			DBControll dbc = new DBControll(this);
			dbc.insert();
			
			dispose(); 
//			Client c = new Client(dbc.id);
//			c.sendMsg("NewUser", pwd);
//			c.sendMsg("NewUser", nick);
				
		}
	JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	new Login();
	}
}
