import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DraftAdvisor {
	
	// the hashmaps that will contain the players on the team far
	// as well as their respective stats
	private HashMap<String, HashMap<String, Float>> chosenSkaters = 
			new HashMap<String, HashMap<String, Float>>();
	private HashMap<String, HashMap<String, Float>> chosenGoalies = 
			new HashMap<String, HashMap<String, Float>>();

    // the players that were on this team last year
    private HashMap<String, HashMap<String, Float>> availableSkaterKeepers = 
			new HashMap<String, HashMap<String, Float>>();
	private HashMap<String, HashMap<String, Float>> availableGoalieKeepers = 
			new HashMap<String, HashMap<String, Float>>();
    
    private ArrayList<String> goalieAttributes = new ArrayList<String>();
    private HashMap<String, Float> goalieAttributesUsed = new HashMap<String, Float>();

    private ArrayList<String> skaterAttributes = new ArrayList<String>();
    private HashMap<String, Float> skaterAttributesUsed = new HashMap<String, Float>();
    
    private ArrayList<String> playersTaken = new ArrayList<String>();
	
	
	public ArrayList<String> getColumnTitles(String tableName){
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        ArrayList<String> results = new ArrayList<String>();
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
    	
    	String command = "SELECT COLUMN_NAME FROM INFORMATION_"+ 
    			"SCHEMA.COLUMNS WHERE TABLE_NAME = \'" + tableName + "\'";
    	
    	System.out.println(command);
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            while (rs.next()){
            	results.add(rs.getString(1));
            }
            
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
        
        return results;
	}
	
	public Float getMax(String tableName, String columnName){
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        Float result;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
    	
    	String command = "SELECT MAX("+ columnName +
				") AS " + columnName + " FROM " + tableName;
    	
    	System.out.println(command);
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            rs.next();
            result = Float.valueOf(rs.getString(1));
            
        } catch (SQLException ex) {

            //myOrder.addSugar(2);    
            Logger lgr = Logger.getLogger(DraftAdvisor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            result = null;

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
        
        return result;
	}
	
	public Float getMin(String tableName, String columnName){
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        Float result;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
    	
    	String command = "SELECT MIN("+ columnName +
				") AS " + columnName + " FROM " + tableName;
    	
    	System.out.println(command);
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            rs.next();
            result = Float.valueOf(rs.getString(1));
            
        } catch (SQLException ex) {

            //myOrder.addSugar(2);    
            Logger lgr = Logger.getLogger(DraftAdvisor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            result = null;

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
        
        return result;
	}
	
	public ArrayList<String> getSimilarSkaters(String guess){
		
		String command = new String("SELECT name FROM skaters WHERE name LIKE \'%" + 
				guess + "%\'");
		
		System.out.println(command);
		
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        ArrayList<String> results = new ArrayList<String>();
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            while (rs.next()){
            	results.add(rs.getString(1));
            }
            
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
        
        return results;
	}
	
	public ArrayList<String> getStats(String tableName, String playerName, Set<String> statNames){
		
		String command = "SELECT " + String.join(", ", statNames) + " FROM " + tableName + 
				" WHERE name = \'" + playerName + "\'";
		System.out.println(command);
		
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        ArrayList<String> results = new ArrayList<String>();
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            while (rs.next()){
            	for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		results.add(rs.getString(i));
            	}
            }
            
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
        
        return results;
	}
	
	public ArrayList<String> getSimilarGoalies(String guess){
		String command = new String("SELECT name FROM goalies WHERE name LIKE \'%" + 
				guess + "%\'");
		
		System.out.println(command);
		
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        ArrayList<String> results = new ArrayList<String>();
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            rs = pst.executeQuery(command);
            
            while (rs.next()){
            	results.add(rs.getString(1));
            }
            
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
        
        return results;
	}
	
	public static void collectAvailableKeepers() {
		
		String keeperName = new String();
		
		ArrayList<String> resultsList = new ArrayList<String>();
		
		
		Scanner user_input = new Scanner( System.in );
    	
    	// get the keeper info
    	
    	System.out.println("Enter the number of keepers:");
    	int numberKeepers = Integer.parseInt(user_input.next());
    	System.out.println("Enter the maximum number of goalie keepers:");
    	int numberGoalieKeepers = Integer.parseInt(user_input.next());
    	
    	// find the available players to choose keepers from
    	// get part of name and search for this player in the DB
    	System.out.println("Enter part of available player names " +
    	"(type \"done\" when finished):");
    	while (true) {
    		keeperName = user_input.next();
    		String keeperSelection = new String("NA");
    		String keeperType = new String();
    		
        	if (keeperName.equals("done")) {
        		break;
        	} else {
        		// check if the player is in the skater list
        		
        		resultsList = getSimilarSkaters(keeperName);
        		
        		for (String keeperOption: resultsList){
        			System.out.println(keeperOption + "? (y/n)");
        			if (user_input.next().equals("y")) {
        				keeperSelection = keeperOption;
        				keeperType = "skater";
        				break;
        			}
        		}
        		
        		// check if the player is in the goalie list if it wasn't found in
        		// the skaters list
        		if (keeperSelection.equals("NA")) {
        			resultsList = myAdvisor.getSimilarGoalies(keeperName);
        			
            		for (String keeperOption: resultsList){
            			System.out.println(keeperOption + "? (y/n)");
            			if (user_input.next().equals("y")) {
            				keeperSelection = keeperOption;
            				keeperType = "goalie";
            				break;
            			}
            		}
        		}
        		
        		// let the user know if this player was not found at all
        		if (keeperSelection.equals("NA")) {
        			System.out.println("Could not locate player. Try again.");
        			continue;
        		}
        		
        		// with the proper keeper found, add their attributes to the list
        		
        		if (keeperType.equals("skater")) {
        			// build a string containing all the desired stats for skaters
        			
        			resultsList = myAdvisor.getStats("skaters",keeperSelection,
        					skaterAttributesUsed.keySet());
        			
        			// the key for the hashmap is the player's name
        		
        			HashMap<String, Float> newSkater = new HashMap<String, Float>();
        			int i = 0;
        			for (String item:skaterAttributesUsed.keySet()){
        				newSkater.put(item, Float.valueOf(resultsList.get(i)));
        				i++;
        			}
        			myAdvisor.availableSkaterKeepers.put(keeperSelection,newSkater);
        		} else {
        			resultsList = myAdvisor.getStats("goalies",keeperSelection,
        					goalieAttributesUsed.keySet());
        			
        			// the key for the hashmap is the player's name
        		
        			HashMap<String, Float> newGoalie = new HashMap<String, Float>();
        			int i = 0;
        			for (String item:goalieAttributesUsed.keySet()){
        				newGoalie.put(item, Float.valueOf(resultsList.get(i)));
        				i++;
        			}
        			myAdvisor.availableGoalieKeepers.put(keeperSelection,newGoalie);
        		}
        	}
        	System.out.println("Finished adding " + keeperSelection + 
        			". Enter next player.");
    	}
    	
    	user_input.close();
    	// Find the skaters and goalies that should be kept from the given list
	}

	public static void main(String[] args) {
		
		DraftAdvisor myAdvisor = new DraftAdvisor();

		ArrayList<String> resultsList = null;
		Float resultFloat;

        String attr = new String();

        Scanner user_input = new Scanner( System.in );
            
            // retrieve the possible stats for goalies from MYSQL
            resultsList = myAdvisor.getColumnTitles("goalies");
            
            for (String result: resultsList){
            	
            	if (!result.equals("name") && !result.equals("team")){
                	myAdvisor.goalieAttributes.add(result);
            	}
            }
            
            // retrieve the possible stats for skaters from MYSQL
            resultsList = myAdvisor.getColumnTitles("skaters");
            
            for (String result: resultsList) {
                
            	if (!result.equals("name") && !result.equals("team")){
                	myAdvisor.skaterAttributes.add(result);
            	}
            }
            
            System.out.printf("Available goalie stats:");
            for (String stat: myAdvisor.goalieAttributes) {
            	System.out.print(" " + stat);
            }
            
            System.out.println();

            
            // ask the user which attributes they want to use
            
            System.out.println("Select the goalie stats your league uses (type \"done\" when finished)");
            
            while (true) {
            	attr = user_input.next();
            	if (attr.equals("done")) {
            		break;
            	} else if (!myAdvisor.goalieAttributes.contains(attr)) {
            		System.out.println("Sorry, " + attr + " is not an available stat for goalies");
            	} else if (myAdvisor.goalieAttributesUsed.keySet().contains(attr)) {
            		System.out.println(attr + " has already been added");
            	} else {
            		if (attr.equals("GAA")) {
            			resultFloat = myAdvisor.getMin("goalies", attr);
            		} else {
            			resultFloat = myAdvisor.getMax("goalies", attr);
            		}
            		myAdvisor.goalieAttributesUsed.put(attr, resultFloat);
//            		System.out.println(rs.getString(1));
            		System.out.println(attr + " added to selected stat categories");
            	}
            }
            
            System.out.println();
            
            System.out.println("Selected goalie stats and best values:");
            
            for (String currentKey : myAdvisor.goalieAttributesUsed.keySet()) {
            	System.out.println(currentKey + ": " + myAdvisor.goalieAttributesUsed.get(currentKey));
            }
            
            System.out.println();
            
            
            // Do the same for skater stats
            System.out.printf("Available skater stats:");
            for (String stat: myAdvisor.skaterAttributes) {
            	System.out.print(" " + stat);
            }
            
            System.out.println();
            
            System.out.println("Select the skater stats your league uses "+
            		"(type \"done\" when finished):");
            
            while (true) {
            	attr = user_input.next();
            	if (attr.equals("done")) {
            		break;
            	} else if (!myAdvisor.skaterAttributes.contains(attr)) {
            		System.out.println("Sorry, " + attr + 
            				" is not an available stat for skaters):");
            	} else {
            		resultFloat = myAdvisor.getMax("skaters", attr);
            		myAdvisor.skaterAttributesUsed.put(attr, resultFloat);
            		System.out.println(attr + " added to selected stat categories");
            	}
            }
            
            System.out.println();
            
            System.out.println("Selected skater stats and best values:");
            
            for (String currentKey : myAdvisor.skaterAttributesUsed.keySet()) {
            	System.out.println(currentKey + ": " + myAdvisor.skaterAttributesUsed.get(currentKey));
            }
            
            System.out.println();   
            
            
            // ask the user for number of players on a team, number of goalies
            
            System.out.println("Enter the maximum number of roster slots:");
            int overallSlots = Integer.parseInt(user_input.next());
            
            System.out.println("Suggest a number of goalie slots:");
            int goalieSlots = Integer.parseInt(user_input.next());
            
//            System.out.println("Is there a maxmimum number of skater slots? (y/n)");
//            if (user_input.next().equals("y")) {
//            	System.out.println("Enter the maximum number of skater slots:");
//            	int max_skater_slots = Integer.parseInt(user_input.next());
//            }
            
            // create a team comprised of each of these max attributes, pay
            // attention to average version addition
            for (String currentKey: myAdvisor.skaterAttributesUsed.keySet()){
            	myAdvisor.skaterAttributesUsed.put(currentKey, 
            			myAdvisor.skaterAttributesUsed.get(currentKey) * (overallSlots - goalieSlots));
            }
            
            for (String currentKey: myAdvisor.goalieAttributesUsed.keySet()) {
            	if (Arrays.asList("GP","GS","W","SO").contains(currentKey)) {
            		myAdvisor.goalieAttributesUsed.put(currentKey, 
            				myAdvisor.goalieAttributesUsed.get(currentKey) * goalieSlots);
            	}
            }
            
            // work with kepers
            
            System.out.println("Is this a keeper league? (y/n)");
            if (user_input.next().equals("y")) {
            	myAdvisor.collectAvailableKeepers();
            	myAdvisor.selectKeepers();
            }
            
            // print out the players that were selected and their stats
//            for (String player: mySkaters.keySet()){
//            	System.out.println(player + ":");
//            	for (String playerStat: mySkaters.get(player).keySet()) {
//            		System.out.println("\t" + playerStat + ": " + 
//            	mySkaters.get(player).get(playerStat));
//            	}
//            }
            
            // print out the goalies that were selected and their stats
//            for (String player: myGoalies.keySet()){
//            	System.out.println(player + ":");
//            	for (String playerStat: myGoalies.get(player).keySet()) {
//            		System.out.println("\t" + playerStat + ": " + 
//            	myGoalies.get(player).get(playerStat));
//            	}
//            }
            
            // ask the user for which players are already on other teams
            
            // start suggesting players
            
            // ask the user to make a selection
            
            // ask the user if it is their turn yet
            
            // if it isn't, ask which player has been selected
            
            // if it is suggest players
            
            // repeat

        user_input.close();
    }
	
	
}