package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class JoinAction extends JoinUI implements ActionListener{
	
	public JoinAction() {
		btnNew.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnNew) {
	         System.out.println("ȸ������ ��ư Ŭ��");
	         System.out.println(idField.getText());
	         DBControll dbc = new DBControll();
	         dbc.insert();
	         
	         dispose(); 
//	         Client c = new Client(dbc.id);
//	         System.out.println("test2");
//	         c.sendMsg("NewUser", dbc.id ,dbc.pwd ,dbc.nick);
	         
	            
	      }
		JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
	}
}
