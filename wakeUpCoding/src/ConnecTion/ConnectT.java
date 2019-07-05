package ConnecTion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectT {
	
	 
	    public static void main(String[] args) throws ClassNotFoundException, SQLException {
	        Class.forName("oracle.jdbc.OracleDriver");
	        String url = "jdbc:oracle:thin:@10.10.10.171:1521:XE";
	        String uid = "magic";
	        String pwd = "oracle";
	        
	        Connection conn =DriverManager.getConnection(url,uid,pwd);
	        Statement stmt = conn.createStatement();
	        
       stmt.executeUpdate("create table UserDB (id varchar2(20) CONSTRAINT id_pk PRIMARY KEY,pwd varchar2(20) not null, nick varchar2(18) not null)");        
	        System.out.println("생성 완료");
	        stmt.close();
	        conn.close();
	        
	        
	        
	        
	 
	    }//main
	 
	}//class
