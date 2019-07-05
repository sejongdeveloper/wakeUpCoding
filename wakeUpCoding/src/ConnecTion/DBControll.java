package ConnecTion;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.join.JoinAction;
import client.join.JoinUI;
import dbConn.util.ConnectionCloseHelper;
import dbConn.util.ConnectionSingletonHelper;

public class DBControll {// controll
	private String id;
	private String pwd;
	private static String nick;
	private JoinAction ja;

	Connection conn = null;
	public PreparedStatement pstmt;
	public Statement stmt;
	
	public DBControll(JoinAction ja) {
		this.ja = ja;

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

//	public static String slelct() {		
//		nick =;
//		try {
//			stmt = conn.prepareStatement("Select nick = ? from UserDB");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		return null;
//	}
//	public void select() {
//
//		nick = ja.nickField.getText().trim();
//		System.out.println("tNickName : " + nick);
//		try {
//			pstmt = conn.prepareStatement("Select nick = ? from UserDB");
//			pstmt.setString(1, ja.nickField.getText().trim());
//			pstmt.executeQuery();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectionCloseHelper.close(pstmt);
//		}
//
//	}

}// class close