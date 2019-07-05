package dbConn.util;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.join.JoinAction;
import client.login.LoginAction;

public class DBControll {// controll
	private String id;
	private String pwd;
	private String nick;
	private JoinAction ja;
	private LoginAction la;

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//�α��� �Ű���, DB���� �̱���
	public DBControll(LoginAction la) {
		this.la = la;  
		conn = ConnectionSingletonHelper.getConnection();
	}
	
	//ȸ������ �Ű���, DB���� �̱���
	public DBControll(JoinAction ja) {
		this.ja = ja;
		conn = ConnectionSingletonHelper.getConnection();
	}
	
	//DB���� ����
	public void close() {
		ConnectionCloseHelper.close(rs);
		ConnectionCloseHelper.close(pstmt);
		ConnectionCloseHelper.close(conn);
		
	}// close

	//ȸ������
	public void insert() {
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
			System.out.println("ID����");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	} // insert() end
	
	//�α���
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return nick;

	} // select() end
}// class close