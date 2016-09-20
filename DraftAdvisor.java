import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class DraftAdvisor {
	public static void main(String[] args) {

        Connection con = null;
        Statement pst = null;
        ResultSet rs = null;
        
        ArrayList<String> attributes = new ArrayList<String>();

        String url = "jdbc:mysql://localhost:3306/drafting";
        String user = "root";
        String password = "supersecret";

        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
//            rs = pst.executeQuery("SELECT MAX(G) as G FROM skaters");
            rs = pst.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'goalies' OR TABLE_NAME = 'skaters'");

            while (rs.next()) {
                
                attributes.add(rs.getString(1));
//                System.out
            }
            
            // ask the user which attributes they want to use
            
            // ask the user for number of players on a team, number of goalies
            
            // locate the top values for each of these attributes
            
            // create a team comprised of each of these max attributes
            
            // ask the user for which players are already on their team
            
            // ask the user for which players are already on other teams
            
            // start suggesting players
            
            // ask the user to make a selection
            
            // ask the user if it is their turn yet
            
            // if it isn't, ask which player has been selected
            
            // if it is suggest players
            
            // repeat

        } catch (SQLException ex) {

            //myOrder.addSugar(2);    
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