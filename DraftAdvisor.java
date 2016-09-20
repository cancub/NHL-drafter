import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DraftAdvisor {
	public static void main(String[] args) {

        Connection con = null;
        Statement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/drafting";
        String user = "root";
        String password = "supersecret";

        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
//            rs = pst.executeQuery("SELECT MAX(G) as G FROM skaters");
            rs = pst.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'goalies'");

            while (rs.next()) {
                
                System.out.println(rs.getString(1));
//                System.out
            }

        } catch (SQLException ex) {
        
            Logger lgr = Logger.getLogger(DraftAdvisor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            
            try {
                
                if (rs != null) {
                    rs.close();
                }
                
                if (pst != null) {
                    pst.close();
                }
                
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
                Logger lgr = Logger.getLogger(DraftAdvisor.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}