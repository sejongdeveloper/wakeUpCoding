package dbConn.util;
import java.sql.*;

//if(rs != null) { try { rs.close(); } catch (Exception e) {} }
//if(stmt != null) { try { stmt.close(); } catch (Exception e) {} }
//if(conn != null) { try { conn.close(); } catch (Exception e) {} }
public class ConnectionCloseHelper {
	
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // close(Connection conn) end

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // close(Statement stmt) end
	
	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // close(PreparedStatement pstmt) end
	
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // close(ResultSet rs) end
	
	public static void close(CallableStatement cstmt) {
		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // close(ResultSet rs) end

}
