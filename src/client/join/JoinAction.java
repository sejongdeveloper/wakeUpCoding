package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class JoinAction extends JoinUI implements ActionListener{
	private Join j;
	public JoinAction(Join j) {
		this.j = j;
		btnNew.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
	}
}
