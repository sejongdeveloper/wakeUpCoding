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
	        //ȸ�� ������ ���̺� 
	        //primary key -admin ,pwd nao null ,�г���  not null unique
	        //������ ���̺� ���̵� admin , ��� oracle , �г��� ���
	        //���ӳ�¥ ���̺� ���̵� frogin key , ��¥ sysdate
	        //�α��� ȸ�����̺� ��ȸ�Ͽ� ���� , �����ڸ� ��������
	        //ȸ�� ���� - ȸ�� ���̺� ����Ͽ� ����
	        
	        stmt.executeUpdate("CREATE TABLE wakeup (no number , name varchar2(20) , job varchar2(20) , bunho number)");        
	        System.out.println("�Ϸ�");
	        stmt.close();
	        conn.close();
	        
	        
	        
	        
	 
	    }//main
	 
	}//class
