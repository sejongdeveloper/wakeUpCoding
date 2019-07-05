package client.login;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.join.JoinAction;
import dbConn.util.ConnectionCloseHelper;
import dbConn.util.ConnectionSingletonHelper;

public class DBControll {// controll
	private String id;
	private String pwd;
	private String nick;
	private JoinAction ja;
	private LoginAction la;

	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public DBControll(LoginAction la) {
		this.la = la;  
		conn = ConnectionSingletonHelper.getConnection();
	}
	public DBControll(JoinAction ja) {
		this.ja = ja;
		conn = ConnectionSingletonHelper.getConnection();
	}

	public void close() {
		try {

			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// close

	public void insert() {
		System.out.println("insert()");
		id = ja.idField.getText().trim();
		pwd = ja.pwdField.getText().trim();
		nick = ja.nickField.getText().trim();
		System.out.println(id + ", " + pwd + ", " + nick);

		try {
			pstmt = conn.prepareStatement("insert into UserDB values(?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, nick);
			pstmt.executeUpdate();
			System.out.println("ID생성");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//로그인
	public String select() {

		id = la.idField.getText().trim();
		pwd = la.pwdField.getText().trim();
		
		try {
			pstmt = conn.prepareStatement("Select nick from UserDB where id = ? and pwd = ?"); 
			pstmt.setString(1, la.idField.getText().trim());
			pstmt.setString(2, la.pwdField.getText().trim());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				nick = rs.getString("nick");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionCloseHelper.close(pstmt);
		}
		
		return nick;

	}
}// class close