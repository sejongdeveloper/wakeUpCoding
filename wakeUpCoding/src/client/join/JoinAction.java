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
	         int result = dbc.insert(); 
	         if(result != 1) {
	        	 JOptionPane.showMessageDialog(null, "빈칸이 있거나 아이디가 이미 존재합니다.");
	        	 return;
	         }
	         dispose(); 
	    }
		JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	}
}
