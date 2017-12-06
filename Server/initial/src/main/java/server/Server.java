package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
public class Server {

    private final AtomicLong counter = new AtomicLong();
    private static ConcurrentHashMap<String, Token> usersLoggedIn;
	private static final int SESSION_TIMEOUT = 30 * 60 * 1000;

    private DatabaseLoader dbloader = new DatabaseLoader();

    //@RequestMapping(value={"/register"}, consumes = MediaType.APPLICATION_JSON, method = RequestMethod.POST)   
    @RequestMapping(value={"/register"}, method=RequestMethod.POST)
    public String register(@RequestBody String param){
    	System.out.println("OLAAAA");
    	String name = "";
    	String email = "";
    	String password = "";
    	JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(param);
			
	    	name = StringEscapeUtils.escapeHtml((String) json.get("name"));
			email = StringEscapeUtils.escapeHtml((String) json.get("email"));
			password = StringEscapeUtils.escapeHtml((String) json.get("password"));
    	
		}catch(Exception e) { }
		
		System.out.println("NAME: " + name);
		System.out.println("EMAIL: " + email);
		System.out.println("PASS: " + password);
		
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
    public String product(@RequestParam("name") String name, @RequestParam("price") String price,
            @RequestParam("quantity") String quantity) {
  	
    	//TODO verificar input 
    		//invalid input -> return
    	
    	boolean insert = false;
    	int intPrice = 0;
    	int intQuantity = 0;
    	
    	if(isInteger(price)) {
    		 intPrice = Integer.parseInt(price);
    	}
    	else {
    		return "Price is not an Integer";
    	}
    	if(isInteger(quantity)) {
    		intQuantity = Integer.parseInt(quantity);
	   	}
	   	else {
	   		return "Quantity is not an Integer";
	   	}
    	
		try {
			dbloader.connectDB();	
		//	insert = dbloader.insertProduct(int owner_id, name, intPrice, intQuantity);
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
    
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    private boolean ValidToken(JSONObject json, String email)
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		// System.out.println("valid token: " + json);
		
		try {
			String array = (String) json.get("token");

			// System.out.println("token: " + array);
			JSONParser parser = new JSONParser();
			JSONObject token = (JSONObject) parser.parse(array);
			String randomValue = (String) token.get("randomNum");
			long tokenTime = (long) token.get("timeStamp");
			
		
			Token cToken = new Token(randomValue, tokenTime);
	
			Token sToken = usersLoggedIn.get(email);
			
			boolean valid = cToken.equals(sToken) && checkTokenTimeStamp(sToken.getTimeStamp());
			if (!valid)
				usersLoggedIn.remove(email);
			
			return valid;
			
		} catch(Exception e) {}	
		return false;
	}

	private boolean checkTokenTimeStamp(long tokenTime) {
		long serverTime = System.currentTimeMillis();
		return Math.abs(serverTime - tokenTime) < SESSION_TIMEOUT;
	}
	
}