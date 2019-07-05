package client.join;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbConn.util.ConnectionSingletonHelper;

public class DBControll extends JoinUI{// controll
   public String id;
   public String pwd;
   public String nick;
   
   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;

   public DBControll() {
      conn = ConnectionSingletonHelper.getConnection();
      insert();
   }

   public void close() { 
      try {
         
         rs.close();
         pstmt.close();
         conn.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }//close
   public void insert() {   
      System.out.println("insert()");
      id = idField.getText().trim();
      pwd = pwdField.getText().trim();
      nick = nickField.getText().trim();
      System.out.println(id + ", " + pwd + ", " + nick);
      
      PreparedStatement pstmt;
      try {
         pstmt = conn.prepareStatement("insert into UserDB values(?,?,?)");
      pstmt.setString(1, id);
      pstmt.setString(2, pwd);
      pstmt.setString(3, nick);
      pstmt.executeUpdate();
      System.out.println("ID»ý¼º");
      
      } catch (SQLException e) {
         e.printStackTrace();
      } 

   }
}// class close