package client.login;

public calss LoginAction extends LoginUI() {
		btnNew.addActionListener(this);
		btnLogin.addActionListener(this);
	

	@Override
	public void actionPerformed (ActionEvent e) {
		

		if (e.getSource() == btnLogin) { // 로그인			

			DBControll db = new DBControll(this);
			String nick = db.select();
			
			// 로그인 실패할 경우
		}if(nick == null) { JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해주세요"); return;}
			

			dispose(); // UI 종료
			Client c = new Client(nick); // 클라이언트 접속
			c.sendMsg("NewUser", nick); // 서버에 닉 보내기
			
		else if (e.getSource() == btnNew) { // 회원가입
			new Join();
		}
		
	} // actionPerformed(ActionEvent e) end
	

} // LoginAction end
