package client.login;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginUI extends JFrame {
	JPanel pTotal, p1, p2, p3;
	JTextField idField, pwdField;
	JButton btnNew, btnLogin;
	
	public LoginUI() {
		pTotal = new JPanel(new GridLayout(3, 0, 10, 10));
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
		p3.add(btnNew = new JButton("회원가입"));
		p3.add(btnLogin = new JButton("로그인"));
		pTotal.add(p3);
		
		add(pTotal);
		setBounds(150,80,300,200);
		setVisible(true);
	}
}
