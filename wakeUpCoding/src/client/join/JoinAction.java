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
		
	    if (e.getSource() == btnNew) { // 회원가입 버튼 누른경우
	         DBControll dbc = new DBControll(this);
	         dbc.insert(); 	         
	         dispose(); 
	    }
		JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	}
}
