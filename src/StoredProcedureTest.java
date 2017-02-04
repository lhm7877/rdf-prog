

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Statement;
 
/**
 *
 * @author mysqltutorial.org
 */
public class StoredProcedureTest {
 
    /**
     * Get skills by candidate id
     *
     * @param candidateId
     */
    public static void getSkills(int candidateId) {
    	
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab?characterEncoding=UTF-8";
        String dbUsername = "librarian";
        String dbPassword = "40211";

        boolean output=false;

        Statement statement = null;
        ResultSet rs=null;

        try {
            String query = "{ call get_candidate_skill(?) }";

            	Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                CallableStatement stmt = conn.prepareCall(query);
                
        // 
            stmt.setInt(1, candidateId);
 
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(String.format("%s - %s",
                        rs.getString("first_name") + " "
                        + rs.getString("last_name"),
                        rs.getString("skill")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        }
    }
 
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        getSkills(122);
    }
}