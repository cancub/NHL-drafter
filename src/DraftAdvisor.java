import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
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
    private HashMap<String, Float> idealGoalieStats = new HashMap<String, Float>();

    private ArrayList<String> skaterAttributes = new ArrayList<String>();
    private HashMap<String, Float> idealSkaterStats = new HashMap<String, Float>();
    
    private ArrayList<String> playersTaken = new ArrayList<String>();
    
    int numberKeepers;
    int numberGoalieKeepers;
	
	
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
	
	public ArrayList<String> getStats(String tableName, String playerName){
		String command = new String();
		
		if (tableName.equals("skaters")){
			command = "SELECT " + String.join(", ", idealSkaterStats.keySet()) + 
					" FROM " + tableName + " WHERE name = \'" + playerName + "\'";
		} else {
			command = "SELECT " + String.join(", ", idealGoalieStats.keySet()) + 
					" FROM " + tableName + " WHERE name = \'" + playerName + "\'";
		}
		
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
	
	public void collectAvailableKeepers() {
		
		String keeperName = new String();
		
		ArrayList<String> resultsList = new ArrayList<String>();
		
		
		Scanner user_input = new Scanner( System.in );
    	
    	// get the keeper info
    	
    	System.out.println("Enter the number of keepers:");
    	numberKeepers = Integer.parseInt(user_input.next());
    	System.out.println("Enter the maximum number of goalie keepers:");
    	numberGoalieKeepers = Integer.parseInt(user_input.next());
    	
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
        			resultsList = getSimilarGoalies(keeperName);
        			
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
        			
        			resultsList = getStats("skaters",keeperSelection);
        			
        			// the key for the hashmap is the player's name
        		
        			HashMap<String, Float> newSkater = new HashMap<String, Float>();
        			int i = 0;
        			for (String item:idealSkaterStats.keySet()){
        				newSkater.put(item, Float.valueOf(resultsList.get(i)));
        				i++;
        			}
        			availableSkaterKeepers.put(keeperSelection,newSkater);
        		} else {
        			resultsList = getStats("goalies",keeperSelection);
        			
        			// the key for the hashmap is the player's name
        		
        			HashMap<String, Float> newGoalie = new HashMap<String, Float>();
        			int i = 0;
        			for (String item:idealGoalieStats.keySet()){
        				newGoalie.put(item, Float.valueOf(resultsList.get(i)));
        				i++;
        			}
        			availableGoalieKeepers.put(keeperSelection,newGoalie);
        		}
        	}
        	System.out.println("Finished adding " + keeperSelection + 
        			". Enter next player.");
    	}
    	
    	user_input.close();
    	// Find the skaters and goalies that should be kept from the given list
	}
	
	public void selectKeepers() {
		ArrayList<String> playerStats = new ArrayList<String>();
		HashMap<String, Float> aggregateStats = new HashMap<String, Float>();
		
		// initialize the aggregates for player stats
		for (String stat: ){
			
		}
		
		// initialize the aggregates for player stats
		for (String stat: ){
			
		}
		
		String bestPlayer = new String();
		float minimumDistance = 1000000;
		
		
		while(chosenGoalies.size() + chosenSkaters.size() < numberKeepers) {
			float testDistance = 0;
			float aggregateStat = 0;
			
			// find the sum for each stat on the team thus far
			
			
			
			for (String playerName: availableSkaterKeepers.keySet()){
				// get a player whose stats should be referenced
				
				// find the distance between the aggregate of this stat for ideal
				// team and the aggregate of this stat for my team with this new player
				// (include stats even for the other type of player, i.e. goalie stats
				// for a skater)
				
				// square the distance value and add it to the total
				
				// divide the sum of the squares by the total number of stats being checked
				
				// take the square root of the sum
				
				// compare with the previously observed minimum, taking this player
				// as the new minimum if it is lower
			}
			
			// repeat the above steps with goalies if the maximum number of goalies has
			// not been selected
			
			// suggest the final minimum and see if the user is ok with it
			
			// if they are ok, add the player to the list of chosen skaters/goalies
			// as well as the list of players that are taken
			
			// if they are not okay with this player, remove from the pool of players
			// and continue to find the next best player
		}
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
            	} else if (myAdvisor.idealGoalieStats.keySet().contains(attr)) {
            		System.out.println(attr + " has already been added");
            	} else {
            		if (attr.equals("GAA")) {
            			resultFloat = myAdvisor.getMin("goalies", attr);
            		} else {
            			resultFloat = myAdvisor.getMax("goalies", attr);
            		}
            		myAdvisor.idealGoalieStats.put(attr, resultFloat);
//            		System.out.println(rs.getString(1));
            		System.out.println(attr + " added to selected stat categories");
            	}
            }
            
            System.out.println();
            
            System.out.println("Selected goalie stats and best values:");
            
            for (String currentKey : myAdvisor.idealGoalieStats.keySet()) {
            	System.out.println(currentKey + ": " + 
            			myAdvisor.idealGoalieStats.get(currentKey));
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
            		myAdvisor.idealSkaterStats.put(attr, resultFloat);
            		System.out.println(attr + " added to selected stat categories");
            	}
            }
            
            System.out.println();
            
            System.out.println("Selected skater stats and best values:");
            
            for (String currentKey : myAdvisor.idealSkaterStats.keySet()) {
            	System.out.println(currentKey + ": " + myAdvisor.idealSkaterStats.get(currentKey));
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
            for (String currentKey: myAdvisor.idealSkaterStats.keySet()){
            	myAdvisor.idealSkaterStats.put(currentKey, 
            			myAdvisor.idealSkaterStats.get(currentKey) * 
            			(overallSlots - goalieSlots));
            }
            
            for (String currentKey: myAdvisor.idealGoalieStats.keySet()) {
            	if (Arrays.asList("GP","GS","W","SO").contains(currentKey)) {
            		myAdvisor.idealGoalieStats.put(currentKey, 
            				myAdvisor.idealGoalieStats.get(currentKey) * goalieSlots);
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