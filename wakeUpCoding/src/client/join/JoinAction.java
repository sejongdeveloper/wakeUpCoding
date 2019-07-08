package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
	    	try {
	    		dbc.insert();
	    	} catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, "빈칸이 있거나 아이디가 이미 존재합니다.");
				return;
			}
	    	JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	    	dispose(); 
	    }
	}
}
