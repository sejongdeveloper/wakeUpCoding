package ConnecTion;
import java.sql.*;
public class ConnectT {
	
	 
	    public static void main(String[] args) throws ClassNotFoundException, SQLException {
	        Class.forName("oracle.jdbc.OracleDriver");
	        String url = "jdbc:oracle:thin:@10.10.10.171:1521:XE";
	        String uid = "magic";
	        String pwd = "oracle";
	        
	        Connection conn =DriverManager.getConnection(url,uid,pwd);
	        System.out.println("test");
//	        Statement stmt = conn.createStatement("CREATE TABLE na (no number , name varchar2(20) , job varchar2(20) , bunho number)");
	        Statement stmt = conn.createStatement();
	        System.out.println("test");
	        //회원 관리자 테이블 
	        //primary key -admin ,pwd nao null ,닉네임  not null unique
	        //관리자 테이블 아이디 admin , 비번 oracle , 닉네임 운영자
	        //접속날짜 테이블 아이디 frogin key , 날짜 sysdate
	        //로그인 회원테이블 조회하여 구현 , 관리자면 서버시작
	        //회원 가입 - 회원 테이블 등록하여 구현
	        
	        stmt.executeUpdate("CREATE TABLE wakeup (no number , name varchar2(20) , job varchar2(20) , bunho number)");        
	        System.out.println("완료");
	        stmt.close();
	        conn.close();
	        
	        
	        
	        
	 
	    }//main
	 
	}//class
