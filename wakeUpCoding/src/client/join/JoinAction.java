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
		
	    if (e.getSource() == btnNew) { // ȸ������ ��ư �������
	         DBControll dbc = new DBControll(this);
	         dbc.insert(); 	         
	         dispose(); 
	    }
		JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
	}
}
