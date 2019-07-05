package client.login;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.PasswordView;

public class LoginUI extends JFrame {
	JPanel pTotal, p1, p2, p3;
	JTextField idField, pwdField;
	JButton btnNew, btnLogin;
	
	public LoginUI() {
		pTotal = new JPanel(new GridLayout(3, 0, 10, 10));
		pTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1.add(new JLabel("��  ��  ��"));
		idField = new JTextField(10);
		p1.add(idField);
		pTotal.add(p1);
		
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2.add(new JLabel("��й�ȣ"));
		pwdField = new JPasswordField(10);
		System.out.println(pwdField.getText());
		p2.add(pwdField);
		pTotal.add(p2);

		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p3.add(btnNew = new JButton("ȸ������"));
		p3.add(btnLogin = new JButton("�α���"));
		pTotal.add(p3);
		
		add(pTotal);
		setBounds(150,80,300,200);
		setVisible(true);
	}
}
