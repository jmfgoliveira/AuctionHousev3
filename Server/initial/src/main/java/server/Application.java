package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	
	private static final int WAITING_RANGE = 5; //in seconds
	public static ConcurrentHashMap<String, Token> usersLoggedIn = new ConcurrentHashMap<String, Token>();
	
    public static void main(String[] args) {
    	
    	
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
    	
//    	DatabaseLoader dbloader = new DatabaseLoader();
//    	
//    	try {
//			dbloader.connectDB();
//			dbloader.insertUser("carlos", "carlos@gmail.com", "password");
//			dbloader.login("carlos@gmail.com", "' OR 1=1 /* ");
//			dbloader.sellProduct(1004, 1, 100);
//			dbloader.sellProduct(1004, 1, 50);
//			dbloader.sellProduct(1004, 1, 50);
//			dbloader.sellProduct(1004, 1, 200);
//			dbloader.commentProduct(1004, 1, "Este produto é uma merda");
//			dbloader.commentProduct(1004, 1, "<script>alert('XSS');</script>");
//			dbloader.buyProduct(1004, 1);
//			dbloader.closeConn();
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
    	SpringApplication.run(Application.class, args); 	
		
    }
    
}