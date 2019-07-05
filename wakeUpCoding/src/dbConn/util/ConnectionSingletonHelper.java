package dbConn.util;
import java.sql.*;
import java.util.*;

/*
	ConnectionHelper Ŭ������ ����
	�Ź� ����̹� �ε�,..
	Connection ����
	
	������ �ϳ��� ���μ������� �ϳ��� ����
	�����ϸ� ���ٵ�....
	
	�ذ��� > �����ڿ�(�̱���)
*/
public class ConnectionSingletonHelper {
	// �ϳ��� ���μ������� �������� ����� �� �ִ� �����ڿ�(static)
	
	private static Connection conn;
	private ConnectionSingletonHelper() {}
	
	public static Connection getConnection() {

		//if(conn != null) return conn; 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(

					"jdbc:oracle:thin:@localhost:1521:XE", "kingsmile", "oracle");

			
		} catch (Exception e) {e.printStackTrace();} finally {return conn;}
	} // getConnection(String dsn) end
	
	public static void close() throws SQLException {
		if(conn != null) 
			if(!conn.isClosed()) conn.close();
		
	} // close() end
}
