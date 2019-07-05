package client.join;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinUI extends JFrame{
	JPanel pTotal, p1, p2, p3, p4;
	JTextField idField, pwdField, nickField;
	JButton btnNew;
	


	public JoinUI() {
		
		pTotal = new JPanel(new GridLayout(4, 0, 10, 10));
		pTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1.add(new JLabel("아  이  디"));
		idField = new JTextField(10);
		p1.add(idField);
		pTotal.add(p1);
		
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2.add(new JLabel("비밀번호"));
		pwdField = new JTextField(10);
		p2.add(pwdField);
		pTotal.add(p2);
		
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p3.add(new JLabel("닉  네  임"));
		nickField = new JTextField(10);
		p3.add(nickField);
		pTotal.add(p3);
		
		p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p4.add(btnNew = new JButton("회원가입"));
		pTotal.add(p4);

		add(pTotal);
		setBounds(150,80,300,250);
		setVisible(true);
		
	}
}
