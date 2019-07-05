package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


import dbConn.util.DBControll;

public class JoinAction extends JoinUI implements ActionListener{
	
	public JoinAction() {
		btnNew.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnNew) {
	         System.out.println("회원가입 버튼 클릭");
	         DBControll dbc = new DBControll(this);
	         dbc.insert();
	         
	         dispose(); 
//	         Client c = new Client(dbc.id);
//	         System.out.println("test2");
//	         c.sendMsg("NewUser", dbc.id ,dbc.pwd ,dbc.nick);
	         
	            
	      }
		JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	}
}
