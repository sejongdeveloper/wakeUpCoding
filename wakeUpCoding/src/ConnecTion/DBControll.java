package ConnecTion;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.join.JoinAction;
import client.login.LoginAction;
import dbConn.util.ConnectionCloseHelper;
import dbConn.util.ConnectionSingletonHelper;

public class DBControll {// controll
	private String id;
	private String pwd;
	private String nick;
	private JoinAction ja;
	private JoinAction la;
	
	
	Connection conn = null;
	public PreparedStatement pstmt;
	public Statement stmt;
	public ResultSet rs;
	public DBControll(JoinAction ja) {
		this.ja = ja;
		
		conn = ConnectionSingletonHelper.getConnection();
	}

	public DBControll(LoginAction ja) {
		this.la = la;
		conn = ConnectionSingletonHelper.getConnection();
	}

	public void close() {
		try {
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// close

	public void insert() {

		id = ja.idField.getText().trim();
		pwd = ja.pwdField.getText().trim();
		nick = ja.nickField.getText().trim();
		System.out.println("ID : " + id + "\tNickName : " + nick);

		try {
			pstmt = conn.prepareStatement("insert into UserDB values(?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, nick);
			pstmt.executeUpdate();
			System.out.println("ID생성 완료");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionCloseHelper.close(pstmt);
		}

	}	
	public String select() {
	
		System.out.println("tNickName : " + nick);
		try {
			pstmt = conn.prepareStatement("Select nick from UserDB where id =? and pwd =?");
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