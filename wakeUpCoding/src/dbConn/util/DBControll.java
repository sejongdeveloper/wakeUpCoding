package dbConn.util;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import client.join.JoinAction;
import client.login.LoginAction;

public class DBControll {// controll
	private String id;
	private String pwd;
	private String nick;
	private JoinAction ja;
	private LoginAction la;
	int result;

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//로그인 매개값, DB연결 싱글톤
	public DBControll(LoginAction la) {
		this.la = la;  
		conn = ConnectionSingletonHelper.getConnection();
	}
	
	//회원가입 매개값, DB연결 싱글톤
	public DBControll(JoinAction ja) {
		this.ja = ja;
		conn = ConnectionSingletonHelper.getConnection();
	}
	
	//DB관련 종료
	public void close() {
		ConnectionCloseHelper.close(rs);
		ConnectionCloseHelper.close(pstmt);
		ConnectionCloseHelper.close(conn);
		
	}// close

	//회원가입
	public void insert() throws SQLException {
		id = ja.idField.getText().trim();
		pwd = ja.pwdField.getText().trim();
		nick = ja.nickField.getText().trim();
		System.out.println(id + ", " + pwd + ", " + nick);
		
		pstmt = conn.prepareStatement("insert into UserDB values(?,?,?)");
		pstmt.setString(1, id);
		pstmt.setString(2, pwd);
		pstmt.setString(3, nick);
		result = pstmt.executeUpdate();
		System.out.println("ID생성");		
	} // insert() end
	
	//로그인
	public String select() {

		id = la.idField.getText().trim();
		pwd = la.pwdField.getText().trim();
		
		try {
			pstmt = conn.prepareStatement("Select nick from UserDB where id = ? and pwd = ?"); 
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				nick = rs.getString("nick");
				System.out.println("닉네임:" + nick);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return nick;

	} // select() end
}// class close