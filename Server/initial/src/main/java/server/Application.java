package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	
	private static final int WAITING_RANGE = 5; //in seconds
	public static ConcurrentHashMap<String, Token> usersLoggedIn = new ConcurrentHashMap<String, Token>();
	
    public static void main(String[] args) throws IOException {
    	
    	
    	ServerSocket backup_socket;
	    try 
	    {
	    	Timer timer = new Timer();
	    	backup_socket = new ServerSocket(65501);
	    	LifeProof lifeproof = new LifeProof(backup_socket);
	    	timer = new Timer(/*isDaemon*/ true);
			timer.schedule(lifeproof, /*delay*/1  * 1000, /*period*/ WAITING_RANGE * 1000); 
        }
        catch (IOException e) {
        	System.out.println(e);
        }
    	
    	DatabaseLoader dbloader = new DatabaseLoader();
    	
    	try {
			dbloader.connectDB();
			dbloader.insertUser("miguelito123", "miguelito123@gmail.com", "password");
			dbloader.login("carlos@gmail.com", "password");
			dbloader.sellProduct("carlos@gmail.com", "Tablet", 100);
			dbloader.sellProduct("carlos@gmail.com", "Tablet", 50);
			dbloader.commentProduct(1004, 1, "Very Good Product");
			dbloader.closeConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
	    Runtime runtime = Runtime.getRuntime();
	    Process process = runtime.exec("src/main/resources/firewall.txt");
    	}catch(Exception e) {}
    	SpringApplication.run(Application.class, args); 	
		
    }
    
}