package server;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {

    private final AtomicLong counter = new AtomicLong();
    private DatabaseLoader dbloader = new DatabaseLoader();

    @RequestMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("email") String email,
            @RequestParam("password") String password) {
    	
    	//TODO verificar input 
    		//invalid input -> return
    	boolean insert = false;
    	
		try {
			dbloader.connectDB();	
			insert = dbloader.insertUser(name, email, password);
		} catch (ClassNotFoundException | SQLException e) 
		{
			return "An error has occured. User not created";
		}
		finally 
		{
			try {
				dbloader.closeConn();
			} catch (SQLException e) {
				return "An error has occured. User not created";
			}
		}
		
    	if(insert){
			return "User created";
		}else{
			return "User not created";
		}
    }
    
    @RequestMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password) {
    	
    	//TODO
    	//verificar input
		//invalid input -> return
    		
		boolean login = false;
		
		try 
		{
			dbloader.connectDB();
			login = dbloader.login(email, password);
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			return "Login failed";
		}
		finally 
		{
			try {
				dbloader.closeConn();
			} catch (SQLException e) {
				return "Login failed";
			}
		}
				
		
		if(login){
			return "Login successful";
		}else{
			return "Login failed";
		}
    }
    
    @RequestMapping("/product")
    public String product(@RequestParam("email") String email,
            @RequestParam("password") String password) {
    	System.out.println("LOGIN");
    	System.out.println("email: " + email);
    	System.out.println("password: " + password);
    	if(email.equals("ola@ola.com") && password.equals("ola"))
    		return "LOGGED IN";
        return new String("TODO");
    }
}