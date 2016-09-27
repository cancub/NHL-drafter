import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DraftAdvisor {
	
	// the hashmaps that will contain the players on the team far
	// as well as their respective stats
	private HashMap<String, HashMap<String, Double>> chosenSkaters = 
			new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, HashMap<String, Double>> chosenGoalies = 
			new HashMap<String, HashMap<String, Double>>();

    // the players that were on this team last year
    private HashMap<String, HashMap<String, Double>> availableSkaterKeepers = 
			new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, HashMap<String, Double>> availableGoalieKeepers = 
			new HashMap<String, HashMap<String, Double>>();
    
    private ArrayList<String> goalieAttributes = new ArrayList<String>();
    private HashMap<String, Double> maxGoalieStats = new HashMap<String, Double>();
    private HashMap<String, Double> minGoalieStats = new HashMap<String, Double>();

    private ArrayList<String> skaterAttributes = new ArrayList<String>();
    private HashMap<String, Double> maxSkaterStats = new HashMap<String, Double>();
    private HashMap<String, Double> minSkaterStats = new HashMap<String, Double>();
    
    private ArrayList<String> playersTaken = new ArrayList<String>();
    
    int overallSlots;
    int goalieSlots;
	
	
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
    	
//    	System.out.println(command);
        
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
	
	public Double getMax(String tableName, String columnName){
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        Double result;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
    	
    	String command = "SELECT MAX("+ columnName +
				") AS " + columnName + " FROM " + tableName;
    	
//    	System.out.println(command);
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            rs.next();
            result = Double.valueOf(rs.getString(1));
            
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
	
	public Double getMin(String tableName, String columnName){
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        Double result;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
    	
    	String command = "SELECT MIN("+ columnName +
				") AS " + columnName + " FROM " + tableName;
    	
//    	System.out.println(command);
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            rs.next();
            result = Double.valueOf(rs.getString(1));
            
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
	
	public ArrayList<String> getSimilarPlayers(String guess){
		
		String skaterCommand = new String("SELECT name FROM skaters WHERE name LIKE \'%" + 
				guess + "%\'");
		
		String goalieCommand = new String("SELECT name FROM goalies WHERE name LIKE \'%" + 
				guess + "%\'");
		
//		System.out.println(command);
		
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
            rs = pst.executeQuery(skaterCommand);
            
            while (rs.next()){
            	results.add(rs.getString(1));
            }
            
            rs.close();
            
            rs = pst.executeQuery(goalieCommand);
            
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
			command = "SELECT " + String.join(", ", maxSkaterStats.keySet()) + 
					" FROM " + tableName + " WHERE name = \'" + playerName + "\'";
		} else {
			command = "SELECT " + String.join(", ", maxGoalieStats.keySet()) + 
					" FROM " + tableName + " WHERE name = \'" + playerName + "\'";
		}
		
//		System.out.println(command);
		
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
	
	public void collectAvailableKeepers(String goaliesFilename, String skatersFilename) {
		
//		String keeperName = new String();
		
		ArrayList<String> resultsList = new ArrayList<String>();
		
		
//		Scanner user_input = new Scanner( System.in );
    	
    	// collect the goalie keepers
    	try {
			File file = new File(goaliesFilename);
		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String goalie;
		    while((goalie = br.readLine()) != null){
		    	resultsList = getStats("goalies",goalie);
    			
    			// the key for the hashmap is the player's name
    		
    			HashMap<String, Double> newGoalie = new HashMap<String, Double>();
    			int i = 0;
    			for (String item:maxGoalieStats.keySet()){
    				newGoalie.put(item, Double.valueOf(resultsList.get(i)));
    				i++;
    			}
    			availableGoalieKeepers.put(goalie,newGoalie);
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) {
			System.out.println("Exception thrown  :" + e);
		}
    	
    	// collect the skater keepers
    	try {
			File file = new File(skatersFilename);
		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String skater;
		    while((skater = br.readLine()) != null){
		    	resultsList = getStats("skaters",skater);
    			
    			// the key for the hashmap is the player's name
    		
    			HashMap<String, Double> newSkater = new HashMap<String, Double>();
    			int i = 0;
    			for (String item:maxSkaterStats.keySet()){
    				newSkater.put(item, Double.valueOf(resultsList.get(i)));
    				i++;
    			}
    			availableSkaterKeepers.put(skater,newSkater);
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) {
			System.out.println("Exception thrown  :" + e);
		}
    	
//    	// find the available players to choose keepers from
//    	// get part of name and search for this player in the DB
//    	System.out.println("Enter part of available player names " +
//    	"(type \"done\" when finished):");
//    	while (true) {
//    		keeperName = user_input.next();
//    		String keeperSelection = new String("NA");
//    		String keeperType = new String();
//    		
//        	if (keeperName.equals("done")) {
//        		break;
//        	} else {
//        		// check if the player is in the skater list
//        		
//        		resultsList = getSimilarSkaters(keeperName);
//        		
//        		for (String keeperOption: resultsList){
//        			System.out.println(keeperOption + "? (y/n)");
//        			if (user_input.next().equals("y")) {
//        				keeperSelection = keeperOption;
//        				keeperType = "skater";
//        				break;
//        			}
//        		}
//        		
//        		// check if the player is in the goalie list if it wasn't found in
//        		// the skaters list
//        		if (keeperSelection.equals("NA")) {
//        			resultsList = getSimilarGoalies(keeperName);
//        			
//            		for (String keeperOption: resultsList){
//            			System.out.println(keeperOption + "? (y/n)");
//            			if (user_input.next().equals("y")) {
//            				keeperSelection = keeperOption;
//            				keeperType = "goalie";
//            				break;
//            			}
//            		}
//        		}
//        		
//        		// let the user know if this player was not found at all
//        		if (keeperSelection.equals("NA")) {
//        			System.out.println("Could not locate player. Try again.");
//        			continue;
//        		}
//        		
//        		// with the proper keeper found, add their attributes to the list
//        		
//        		if (keeperType.equals("skater")) {
//        			// build a string containing all the desired stats for skaters
//        			
//        			resultsList = getStats("skaters",keeperSelection);
//        			
//        			// the key for the hashmap is the player's name
//        		
//        			HashMap<String, Double> newSkater = new HashMap<String, Double>();
//        			int i = 0;
//        			for (String item:maxSkaterStats.keySet()){
//        				newSkater.put(item, Double.valueOf(resultsList.get(i)));
//        				i++;
//        			}
//        			availableSkaterKeepers.put(keeperSelection,newSkater);
//        		} else {
//        			resultsList = getStats("goalies",keeperSelection);
//        			
//        			// the key for the hashmap is the player's name
//        		
//        			HashMap<String, Double> newGoalie = new HashMap<String, Double>();
//        			int i = 0;
//        			for (String item:maxGoalieStats.keySet()){
//        				newGoalie.put(item, Double.valueOf(resultsList.get(i)));
//        				i++;
//        			}
//        			availableGoalieKeepers.put(keeperSelection,newGoalie);
//        		}
//        	}
//        	System.out.println("Finished adding " + keeperSelection + 
//        			". Enter next player.");
//    	}
//    	
//    	user_input.close();
    	// Find the skaters and goalies that should be kept from the given list
	}
	
	public double percentOfBest(String stat, double value) {
		// this function looks at the stat in question and determines
		// where the given value falls along the line of best (1.0) to worst (0.0)
		
		// for most stats, it's a simple matter of finding where the stats lie
		// between min and max and can thus the result can be found by
		// result = (value - min) / (max - min)
		// the only exception is GAA, because the reverse is true (i.e., you want to
		// have a minimum value), so we take 1 - the result
		
		double result;
			
		if (maxGoalieStats.keySet().contains(stat)) {
			// this is a goalie stat
			value -= minGoalieStats.get(stat);
			result = value / (maxGoalieStats.get(stat) - minGoalieStats.get(stat));
			
			// flip the percent for GAA
			if (stat.equals("GAA")) {
				result = 1 - result;
			}			
		} else if (maxSkaterStats.keySet().contains(stat)){
			value -= minSkaterStats.get(stat);
			result = value / (maxSkaterStats.get(stat) - minSkaterStats.get(stat));
		} else {
			System.out.println(stat + " is not a recognized stat");
			result = Double.NaN;
		}
		
		return result;
	}
	
	public double getGP(String tableName, String playerName) {
		String command = new String("SELECT GP FROM " + tableName + " WHERE name = \'" + 
				playerName + "\'");
		
//		System.out.println(command);
		
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        double result;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            rs = pst.executeQuery(command);
            
            rs.next();
            result = Double.valueOf(rs.getString(1));
            
        } catch (SQLException ex) {

            //myOrder.addSugar(2);    
            Logger lgr = Logger.getLogger(DraftAdvisor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
            result = Double.NaN;

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
        
        if (result < 0) {
        	result = 0;
        }
        
        return result;
	}
	
	public void selectKeepers() {
		// aggregate stats holds the current team total stats at each iteration
		HashMap<String, Double> aggregateStats = new HashMap<String, Double>();
		Scanner user_input = new Scanner( System.in );
		
		// i will be used to keep track of whether or not the aggregate value should
		// be replaced with the first chosen player's stat (i = 0) or if the current
		// value should be added to by the next chosen player (i > 0)
		int i;		
		
		String bestPlayer = new String();
		String bestPlayerType = new String();
		double bestPlayerGP;
		double gamesPlayed;
		double maximumPercent;
		
		// some players will be vetoed for whatever reason by the user
		ArrayList<String> avoidedPlayers = new ArrayList<String>();
		
		double testPercent;
		double aggregateStat;
		
//		get the keeper info
    	
    	System.out.println("Enter the number of keepers:");
    	int numberKeepers = Integer.parseInt(user_input.next());
    	System.out.println("Enter the maximum number of goalie keepers:");
    	int numberGoalieKeepers = Integer.parseInt(user_input.next());
		
		while(chosenGoalies.size() + chosenSkaters.size() < numberKeepers) {
			testPercent = 0;
			bestPlayerGP = 0;
			maximumPercent = Double.MIN_VALUE;
			
			// initialize the aggregates for skater stats with the least impressive values
			for (String stat: maxSkaterStats.keySet()){
				aggregateStats.put(stat, minSkaterStats.get(stat));
			}

			// initialize the aggregates for goalie stats with the least impressive values
			for (String stat: maxGoalieStats.keySet()){
				if (stat.equals("GAA")){
					aggregateStats.put(stat, maxGoalieStats.get(stat));
				} else {
					aggregateStats.put(stat, minGoalieStats.get(stat));
				}
			}
			
			i = 0;
			
			// find the sum for each skater stat on the team thus far
			for (String skaterName: chosenSkaters.keySet()) {
				// get a skater
				gamesPlayed = getGP("skaters", skaterName);
				for (String stat: chosenSkaters.get(skaterName).keySet()){
					// add the stats from the chosen skaters to the aggregate stats
					
					if (i == 0){
						// this is the first chosen player being added, so replace
						// the initial value with their stat
						aggregateStats.put(stat,chosenSkaters.get(skaterName).get(stat) /
								(gamesPlayed / 82));
					} else {
						// the initial value has been replaced, so add to the current value
						aggregateStats.put(stat, aggregateStats.get(stat) + 
								chosenSkaters.get(skaterName).get(stat) / 
								(gamesPlayed / 82));
					}
				}
				
				i++;
			}
			
			i = 0;
			
			// repeat for goalies
			for (String goalieName: chosenGoalies.keySet()) {
				for (String stat: chosenGoalies.get(goalieName).keySet()){
					if (i == 0) {
						aggregateStats.put(stat,chosenGoalies.get(goalieName).get(stat));
					} else {
						aggregateStats.put(stat, aggregateStats.get(stat) + 
								chosenGoalies.get(goalieName).get(stat));
					}
				}
				i++;
			}
			
			
			
			for (String playerName: availableSkaterKeepers.keySet()){
				// get a player whose stats should be referenced
				
				gamesPlayed = getGP("skaters", playerName);
				
				if (chosenSkaters.keySet().contains(playerName) || 
						avoidedPlayers.contains(playerName)) {
					continue;
				}
				
				for (String stat: aggregateStats.keySet()) {
					// figure out how the skater's stats affect the overall team average
					if (availableSkaterKeepers.get(playerName).keySet().contains(stat)) {
						// if this stat is a skater stat, see how adding this new player
						// would affect the average
						
						// get the prorated stat for this skater
						aggregateStat = 
								availableSkaterKeepers.get(playerName).get(stat) / 
								(gamesPlayed/82);
						
						if (chosenSkaters.size() > 0) {
							// add this player to the current total for this stat and then
							// get the average if there is at least one skater that has
							// already been chosen
							aggregateStat = (aggregateStats.get(stat) + aggregateStat) /
								(chosenSkaters.size() + 1); 
						}
					} else {
						aggregateStat = aggregateStats.get(stat);
						if (chosenGoalies.size() > 1) {
							aggregateStat /= chosenGoalies.size();
						}
					}
				
					// square the percent value and add it to the total
					testPercent += Math.pow(percentOfBest(stat, aggregateStat),2);
				}
				
				// divide the sum of the squares by the total number of stats being checked
				testPercent /= aggregateStats.size();
				
				// take the square root of the sum
				testPercent = Math.sqrt(testPercent);
				
				// compare with the previously observed minimum, taking this player
				// as the new minimum if it is lower
				if (testPercent > maximumPercent) {
					bestPlayerType = "skater";
					bestPlayer = playerName;
					maximumPercent = testPercent;
					bestPlayerGP = gamesPlayed;
				}
			}
			
			// repeat the above steps with goalies if the maximum number of goalies has
			// not been selected
			if (chosenGoalies.size() < numberGoalieKeepers){
				for (String playerName: availableGoalieKeepers.keySet()){
					// get a goalie whose stats should be referenced
					
					if (chosenGoalies.keySet().contains(playerName) || 
							avoidedPlayers.contains(playerName)) {
						continue;
					}
					
					for (String stat: aggregateStats.keySet()) {
						if (availableGoalieKeepers.get(playerName).keySet().contains(stat)) {
							
							if (chosenGoalies.size() == 0) {
								aggregateStat = availableGoalieKeepers.get(playerName).get(stat);
							} else {
								aggregateStat = (aggregateStats.get(stat) + 
									availableGoalieKeepers.get(playerName).get(stat)) /
									(chosenGoalies.size() + 1); 
							}
						} else {
							aggregateStat = aggregateStats.get(stat);
							if (chosenSkaters.size() > 1) {
								aggregateStat /=  chosenSkaters.size();
							}
						}
					
						testPercent += Math.pow(percentOfBest(stat, aggregateStat),2);
					}
					
					testPercent /= aggregateStats.size();
					
					testPercent = Math.sqrt(testPercent);
					
					if (testPercent > maximumPercent) {
						bestPlayerType = "goalie";
						bestPlayer = playerName;
						maximumPercent = testPercent;
						bestPlayerGP = getGP("goalies",playerName);
					}
				}
			}
			
			// suggest the final maximum and see if the user is ok with it
			
			System.out.println("The best option at the moment: " + bestPlayer + 
					", value = " + maximumPercent + ", GP =" + bestPlayerGP);			
			System.out.println("Add this player to keepers? (y/n)");
			
			if (user_input.next().equals("y")) {
				// add the player to the list of chosen skaters/goalies
				// as well as the list of players that are taken
				if (bestPlayerType.equals("skater")){
					chosenSkaters.put(bestPlayer, availableSkaterKeepers.get(bestPlayer));
				} else {
					chosenGoalies.put(bestPlayer, availableGoalieKeepers.get(bestPlayer));
				}
				avoidedPlayers.clear();
				playersTaken.add(bestPlayer);
			} else {
				System.out.println("Would you like to suggest a player? (y/n)");
				if (user_input.next().equals("y")) {
					boolean playerChosen = false;
					String guess;
					System.out.println("Enter player:");
					guess = user_input.next();
					for (String player: availableSkaterKeepers.keySet()){
						if (chosenSkaters.keySet().contains(player)){
							continue;
						}
						
						if (player.contains(guess)) {
							System.out.println("Do you want " + player + "? (y/n)");
							if (user_input.next().equals("y")) {
								chosenSkaters.put(player, 
										availableSkaterKeepers.get(player));
								playerChosen = true;
								playersTaken.add(player);
								break;
							}
						}
					}
					
					if (!playerChosen) {
						for (String player: availableGoalieKeepers.keySet()){
							if (chosenGoalies.keySet().contains(player)){
								continue;
							}
							
							if (player.contains(guess)) {
								System.out.println("Do you want " + player + "? (y/n)");
								if (user_input.next().equals("y")) {
									chosenGoalies.put(player, 
											availableGoalieKeepers.get(player));
									playersTaken.add(player);
									playerChosen = true;
									break;
								}
							}
						}
					}
					
					if (!playerChosen) {
						System.out.println("Could not locate player. Continuing.");
					}
				} else {
					// if they are not okay with this player, remove from the pool 
					// of players and continue to find the next best player
					System.out.println("Avoid this player for this round? (y/n)");
					if (user_input.next().equals("y")) {
						avoidedPlayers.add(bestPlayer);
					}
				}
			}
		}
		user_input.close();
	}
	
	public void getSkaterStatsFromFile(String filename) {
		try {
			File file = new File(filename);
		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String attr;
		    while((attr = br.readLine()) != null){
		    	maxSkaterStats.put(attr, getMax("skaters", attr));
	    		minSkaterStats.put(attr, getMin("skaters", attr));
	    		System.out.println(attr + " added to selected skater stat categories");
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) {
			System.out.println("Exception thrown  :" + e);
		}
	}
	
	public void getGoalieStatsFromFile(String filename) {
		try {
			File file = new File(filename);
		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String attr;
		    while((attr = br.readLine()) != null){
		    	maxGoalieStats.put(attr, getMax("goalies", attr));
	    		minGoalieStats.put(attr, getMin("goalies", attr));
	    		System.out.println(attr + " added to selected skater stat categories");
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) {
			System.out.println("Exception thrown  :" + e);
		}
	}
	
	public HashMap<String, HashMap<String, Double>> getAllPlayers(String tableName) {
		HashMap<String, HashMap<String, Double>> results = 
				new HashMap<String, HashMap<String, Double>>();
		
		String command = new String();
		
		if (tableName.equals("skaters")){
			command = "SELECT name, " + String.join(", ", maxSkaterStats.keySet()) + 
					" FROM " + tableName;
		} else {
			command = "SELECT name, " + String.join(", ", maxGoalieStats.keySet()) + 
					" FROM " + tableName;
		}
		
//		System.out.println(command);
		
		Connection con = null;
		Statement pst = null;
        ResultSet rs = null;
        
        //  used for working with mysql
    	String url = "jdbc:mysql://localhost:3306/drafting?autoReconnect=true&useSSL=false";
    	String user = "root";
    	String password = "brickgolfinsidebuilding";
        
        try {
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.createStatement();
            
            // retrieve the possible stats for players and goalies from MYSQL
            rs = pst.executeQuery(command);
            
            int i;
            
            if (tableName.equals("skaters")) {
	            while (rs.next()){
	            	i = 2;
	            	HashMap<String, Double> tempPlayer = new HashMap<String, Double>();
	            	for (String stat: maxSkaterStats.keySet()) {
	            		tempPlayer.put(stat, Double.valueOf(rs.getString(i)));
	            		i++;
	            	}
	            	results.put(rs.getString(1), tempPlayer);
	            }
            } else {
            	while (rs.next()){
            		i = 2;
            		HashMap<String, Double> tempPlayer = new HashMap<String, Double>();
	            	for (String stat: maxGoalieStats.keySet()) {
	            		tempPlayer.put(stat, Double.valueOf(rs.getString(i)));
	            		i++;
	            	}
	            	results.put(rs.getString(1), tempPlayer);
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
	
	public void getNextPlayer() {
		// aggregate stats holds the current team total stats at each iteration
		HashMap<String, Double> aggregateStats = new HashMap<String, Double>();
		Scanner user_input = new Scanner( System.in );
		
		HashMap<String, HashMap<String, Double>> allSkaters = getAllPlayers("skaters");
		HashMap<String, HashMap<String, Double>> allGoalies = getAllPlayers("goalies");
		
		// i will be used to keep track of whether or not the aggregate value should
		// be replaced with the first chosen player's stat (i = 0) or if the current
		// value should be added to by the next chosen player (i > 0)
		int i;		
		
		String bestPlayer = new String();
		String bestPlayerType = new String();
		double bestPlayerGP;
		double gamesPlayed;
		double maximumPercent;
		
		// some players will be vetoed for whatever reason by the user
		ArrayList<String> avoidedPlayers = new ArrayList<String>();
		
		double testPercent;
		double aggregateStat;
		
		while(chosenGoalies.size() + chosenSkaters.size() < overallSlots) {
			testPercent = 0;
			bestPlayerGP = 0;
			maximumPercent = Double.MIN_VALUE;
			
			// initialize the aggregates for skater stats with the least impressive values
			for (String stat: maxSkaterStats.keySet()){
				aggregateStats.put(stat, minSkaterStats.get(stat));
			}

			// initialize the aggregates for goalie stats with the least impressive values
			for (String stat: maxGoalieStats.keySet()){
				if (stat.equals("GAA")){
					aggregateStats.put(stat, maxGoalieStats.get(stat));
				} else {
					aggregateStats.put(stat, minGoalieStats.get(stat));
				}
			}
			
			i = 0;
			
			// find the sum for each skater stat on the team thus far
			for (String skaterName: chosenSkaters.keySet()) {
				// get a skater
				gamesPlayed = getGP("skaters", skaterName);
				for (String stat: chosenSkaters.get(skaterName).keySet()){
					// add the stats from the chosen skaters to the aggregate stats
					
					if (i == 0){
						// this is the first chosen player being added, so replace
						// the initial value with their stat
						aggregateStats.put(stat,chosenSkaters.get(skaterName).get(stat) /
								(gamesPlayed / 82));
					} else {
						// the initial value has been replaced, so add to the current value
						aggregateStats.put(stat, aggregateStats.get(stat) + 
								chosenSkaters.get(skaterName).get(stat) / 
								(gamesPlayed / 82));
					}
				}
				
				i++;
			}
			
			i = 0;
			
			// repeat for goalies
			for (String goalieName: chosenGoalies.keySet()) {
				for (String stat: chosenGoalies.get(goalieName).keySet()){
					if (i == 0) {
						aggregateStats.put(stat,chosenGoalies.get(goalieName).get(stat));
					} else {
						aggregateStats.put(stat, aggregateStats.get(stat) + 
								chosenGoalies.get(goalieName).get(stat));
					}
				}
				i++;
			}
			
			
			
			for (String playerName: allSkaters.keySet()){
				// get a player whose stats should be referenced
				
				if (chosenSkaters.keySet().contains(playerName) || 
						avoidedPlayers.contains(playerName)) {
					continue;
				}
				
				gamesPlayed = getGP("skaters", playerName);
				
				
				
				
				for (String stat: aggregateStats.keySet()) {
					// figure out how the skater's stats affect the overall team average
					if (allSkaters.get(playerName).keySet().contains(stat)) {
						// if this stat is a skater stat, see how adding this new player
						// would affect the average
						
						// get the prorated stat for this skater
						aggregateStat = 
								allSkaters.get(playerName).get(stat) / 
								(gamesPlayed/82);
						
						if (chosenSkaters.size() > 0) {
							// add this player to the current total for this stat and then
							// get the average if there is at least one skater that has
							// already been chosen
							aggregateStat = (aggregateStats.get(stat) + aggregateStat) /
								(chosenSkaters.size() + 1); 
						}
					} else {
						aggregateStat = aggregateStats.get(stat);
						if (chosenGoalies.size() > 1) {
							aggregateStat /= chosenGoalies.size();
						}
					}
					
					System.out.format("%.3f ", percentOfBest(stat, aggregateStat));
				
					// square the percent value and add it to the total
					testPercent += Math.pow(percentOfBest(stat, aggregateStat),2);
				}
				
				// divide the sum of the squares by the total number of stats being checked
				testPercent /= aggregateStats.size();
				
				// take the square root of the sum
				testPercent = Math.sqrt(testPercent);
				
				System.out.format("= %.2f  ",testPercent);
				System.out.println(playerName);
				
				// compare with the previously observed minimum, taking this player
				// as the new minimum if it is lower
				if (testPercent > maximumPercent) {
					bestPlayerType = "skater";
					bestPlayer = playerName;
					maximumPercent = testPercent;
					bestPlayerGP = gamesPlayed;
				}
			}
			
			// repeat the above steps with goalies if the maximum number of goalies has
			// not been selected
			if (chosenGoalies.size() < goalieSlots){
				for (String playerName: allGoalies.keySet()){
					// get a goalie whose stats should be referenced
					
					if (chosenGoalies.keySet().contains(playerName) || 
							avoidedPlayers.contains(playerName)) {
						continue;
					}
					
					for (String stat: aggregateStats.keySet()) {
						if (allGoalies.get(playerName).keySet().contains(stat)) {
							
							if (chosenGoalies.size() == 0) {
								aggregateStat = allGoalies.get(playerName).get(stat);
							} else {
								aggregateStat = (aggregateStats.get(stat) + 
									allGoalies.get(playerName).get(stat)) /
									(chosenGoalies.size() + 1); 
							}
						} else {
							aggregateStat = aggregateStats.get(stat);
							if (chosenSkaters.size() > 1) {
								aggregateStat /=  chosenSkaters.size();
							}
						}
						
						System.out.format("%.3f ", percentOfBest(stat, aggregateStat));
					
						testPercent += Math.pow(percentOfBest(stat, aggregateStat),2);
					}
					
					testPercent /= aggregateStats.size();
					
					testPercent = Math.sqrt(testPercent);
					
					System.out.format("= %.3f ",testPercent);
					System.out.println(playerName);
					
					if (testPercent > maximumPercent) {
						bestPlayerType = "goalie";
						bestPlayer = playerName;
						maximumPercent = testPercent;
						bestPlayerGP = getGP("goalies",playerName);
					}
				}
			}
			
			// suggest the final maximum and see if the user is ok with it
			
			System.out.println("The best option at the moment: " + bestPlayer + 
					", value = " + maximumPercent + ", GP =" + bestPlayerGP);			
			System.out.println("Add this player to keepers? (y/n)");
			
			if (user_input.next().equals("y")) {
				// add the player to the list of chosen skaters/goalies
				// as well as the list of players that are taken
				if (bestPlayerType.equals("skater")){
					chosenSkaters.put(bestPlayer, allSkaters.get(bestPlayer));
				} else {
					chosenGoalies.put(bestPlayer, allGoalies.get(bestPlayer));
				}
				avoidedPlayers.clear();
				playersTaken.add(bestPlayer);
			} else {
				System.out.println("Would you like to suggest a player? (y/n)");
				if (user_input.next().equals("y")) {
					boolean playerChosen = false;
					String guess;
					System.out.println("Enter player:");
					guess = user_input.next();
					for (String player: allSkaters.keySet()){
						if (playersTaken.contains(player)){
							continue;
						}
						
						if (player.contains(guess)) {
							System.out.println("Do you want " + player + "? (y/n)");
							if (user_input.next().equals("y")) {
								chosenSkaters.put(player, allSkaters.get(player));
								playerChosen = true;
								playersTaken.add(player);
								break;
							}
						}
					}
					
					if (!playerChosen) {
						for (String player: allGoalies.keySet()){
							if (chosenGoalies.keySet().contains(player)){
								continue;
							}
							
							if (player.contains(guess)) {
								System.out.println("Do you want " + player + "? (y/n)");
								if (user_input.next().equals("y")) {
									chosenGoalies.put(player,allGoalies.get(player));
									playersTaken.add(player);
									playerChosen = true;
									break;
								}
							}
						}
					}
					
					if (!playerChosen) {
						System.out.println("Could not locate player or they are " + 
								" taken. Continuing.");
					}
				} else {
					// if they are not okay with this player, remove from the pool 
					// of players and continue to find the next best player
					System.out.println("Avoid this player for this round? (y/n)");
					if (user_input.next().equals("y")) {
						avoidedPlayers.add(bestPlayer);
					}
				}
			}
		}
		user_input.close();
	}

	public static void main(String[] args) {
		
		DraftAdvisor myAdvisor = new DraftAdvisor();

		ArrayList<String> resultsList = null;
		
		String attr;


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

            
            // ask the user which attributes they want to use for goalies
            
            System.out.println("Is there a file for goalie stats that" +
            		" this league uses? (y/n)");
            
            if (user_input.next().equals("y")){
            	System.out.println("Enter the filename:");
//            	myAdvisor.getGoalieStatsFromFile(user_input.next());
            	myAdvisor.getGoalieStatsFromFile("goalie_stats.txt");
            } else {
            
	            System.out.println("Select the goalie stats your league uses (type \"done\" when finished)");
	            
	            while (true) {
	            	// get the chosen stat
	            	attr = user_input.next();
	            	
	            	// determine if it is a viable stat or if the user is done
	            	if (attr.equals("done")) {
	            		break;
	            	} else if (!myAdvisor.goalieAttributes.contains(attr)) {
	            		System.out.println("Sorry, " + attr + " is not an available stat for goalies");
	            	} else if (myAdvisor.maxGoalieStats.keySet().contains(attr)) {
	            		System.out.println(attr + " has already been added");
	            	} else {
	            		// if the stat is viable, find the minimum and maximum values
	            		// for that stat among all players
	            		myAdvisor.maxGoalieStats.put(attr, myAdvisor.getMax("goalies", attr));
	            		myAdvisor.minGoalieStats.put(attr, myAdvisor.getMin("goalies", attr));
	            		System.out.println(attr + " added to selected stat categories");
	            	}
	            }
            }
            
            System.out.println();
            
            System.out.println("Selected goalie stats and their extrema:");
            
            for (String currentKey : myAdvisor.maxGoalieStats.keySet()) {
            	System.out.println(currentKey + ": MAX = " + 
            			myAdvisor.maxGoalieStats.get(currentKey) + ", MIN = " + 
            			myAdvisor.minGoalieStats.get(currentKey));
            }
            
            System.out.println();
            
            
            // do the same for skaters
            
            System.out.println("Is there a file for skater stats that" +
            		" this league uses? (y/n)");
            
            if (user_input.next().equals("y")){
            	System.out.println("Enter the filename:");
//            	myAdvisor.getSkaterStatsFromFile(user_input.next());
            	myAdvisor.getSkaterStatsFromFile("skater_stats.txt");
            } else {
            
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
                		myAdvisor.maxSkaterStats.put(attr, myAdvisor.getMax("skaters", attr));
                		myAdvisor.minSkaterStats.put(attr, myAdvisor.getMin("skaters", attr));
                		System.out.println(attr + " added to selected stat categories");
                	}
                }
                
                System.out.println();
            }      
            
            
            System.out.println("Selected skater stats and best values:");
            
            for (String currentKey : myAdvisor.maxSkaterStats.keySet()) {
            	System.out.println(currentKey + ": MAX = " + 
            			myAdvisor.maxSkaterStats.get(currentKey) + ", MIN = " + 
            			myAdvisor.minSkaterStats.get(currentKey));
            }
            
            System.out.println();   
            
            
            // ask the user for number of players on a team, number of goalies
            
            System.out.println("Enter the maximum number of roster slots:");
            myAdvisor.overallSlots = Integer.parseInt(user_input.next());
            
            System.out.println("Suggest a number of goalie slots:");
            myAdvisor.goalieSlots = Integer.parseInt(user_input.next());
            
//            System.out.println("Is there a maxmimum number of skater slots? (y/n)");
//            if (user_input.next().equals("y")) {
//            	System.out.println("Enter the maximum number of skater slots:");
//            	int max_skater_slots = Integer.parseInt(user_input.next());
//            }
            
            // work with kepers
            
            System.out.println("Is this a keeper league? (y/n)");
            if (user_input.next().equals("y")) {
            	myAdvisor.collectAvailableKeepers("available_goalie_keepers.txt",
            			"available_skater_keepers.txt");
            	myAdvisor.selectKeepers();
            }
            
            // print out the aggregate stats
            
            // print out the goalies that were selected and their stats
            
            System.out.println("\nChosen goalie keepers:");
            for (String name: myAdvisor.chosenGoalies.keySet()) {
            	System.out.println(name);
            }
            
            System.out.println("\nTeam goalie keeper stats:");
            double totalStat;
            for (String stat: myAdvisor.maxGoalieStats.keySet()) {
            	totalStat = 0;
            	for (String player: myAdvisor.chosenGoalies.keySet()) {
            		totalStat += myAdvisor.chosenGoalies.get(player).get(stat);
            	}
            	if (stat.equals("GAA") || stat.equals("save_percent")) {
            		totalStat /= myAdvisor.chosenGoalies.size();
            	}
            	System.out.println(stat + ": " + totalStat);
            }
            
            // print out the skaters that were selected and their stats
            
            System.out.println("\nChosen skater keepers:");
            for (String name: myAdvisor.chosenSkaters.keySet()) {
            	System.out.println(name);
            }
            
            System.out.println("\nTeam skater keeper stats:");
            for (String stat: myAdvisor.maxSkaterStats.keySet()) {
            	totalStat = 0;
            	for (String player: myAdvisor.chosenSkaters.keySet()) {
            		totalStat += myAdvisor.chosenSkaters.get(player).get(stat);
            	}
            	System.out.println(stat + ": " + totalStat);
            }
            
            // ask the user for which players are already on other teams
            
            System.out.println("Is there a file that contains a list of " +
            		"players that have already been taken? (y/n)");
            if (user_input.next().equals("y")) {
            	// load the keepers from this file
            }
            
// start suggesting players
            
            while (myAdvisor.chosenSkaters.size() + 
            		myAdvisor.chosenGoalies.size() < myAdvisor.overallSlots) {
            
	            // ask the user if it is their turn yet
            	System.out.println("Is it your turn? (y/n)");
            	
            	if (user_input.next().equals("n")) {
            		// if it isn't, get the player that has been selected by the other
            		// team so that they can be removed from consideration
            		System.out.println("Enter all or part of the selected player's name:");
            		resultsList = myAdvisor.getSimilarPlayers(user_input.next());
            		for (String player: resultsList){
            			System.out.println("Was the player " + player + "? (y/n)");
            			if (user_input.next().equals("y")) {
            				myAdvisor.playersTaken.add(player);
            				break;
            			}
            		}
            	} else {
            		// obtain the next best player in relation to the current team
            		myAdvisor.getNextPlayer();
            	}
            	
            	System.out.println("Is the draft over? (y/n)");
            	if (user_input.next().equals("y")) {
            		break;
            	}
            }

        user_input.close();
    }
	
	
}