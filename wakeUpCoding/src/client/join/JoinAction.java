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
		
	    if (e.getSource() == btnNew) { // ȸ������ ��ư �������
	    	DBControll dbc = new DBControll(this);
	    	try {
	    		dbc.insert();
	    	} catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, "��ĭ�� �ְų� ���̵� �̹� �����մϴ�.");
				return;
			}
	    	JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
	    	dispose(); 
	    }
	}
}
