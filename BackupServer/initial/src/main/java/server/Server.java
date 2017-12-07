package server;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class Server {

    private final AtomicLong counter = new AtomicLong();
	private static final int SESSION_TIMEOUT = 30 * 60 * 1000;
	private static SecureRandom random = new SecureRandom();
	
	private static ConcurrentHashMap<String, Token> usersLoggedIn = new ConcurrentHashMap<String, Token>();

	private static final int ERROR = 1;

    private DatabaseLoader dbloader = new DatabaseLoader();

    @RequestMapping(value={"/register"}, method=RequestMethod.POST)
    public Response register(@RequestBody String param) throws JsonProcessingException{
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
		
		
    	boolean insert = false;
    	
		try {
			dbloader.connectDB();	

			insert = dbloader.insertUser(name, email, password);

		} catch (ClassNotFoundException | SQLException e) 
		{
		}
		finally 
		{
			try {
				dbloader.closeConn();

			} catch (SQLException e) {
				return Response.status(400).build();
		    
			}
		}
    	if(insert){
    		return GenToken(email);
		}else{
			return Response.status(ERROR).build();		}
    }
    
    @RequestMapping(value={"/login"}, method=RequestMethod.POST)
    public Response login(@RequestBody String param) throws JsonProcessingException {
    	
    	String email = "";
    	String password = "";
    	JSONParser parser = new JSONParser();
    	JSONObject json;
		try {
			json = (JSONObject) parser.parse(param);
			
			email = StringEscapeUtils.escapeHtml((String) json.get("email"));
			password = StringEscapeUtils.escapeHtml((String) json.get("password"));
    	
		}catch(Exception e) { }
    		
		boolean login = true;
		
		try 
		{
			dbloader.connectDB();
			login = dbloader.login(email, password);
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
		}
		finally 
		{
			try {
				dbloader.closeConn();
			} catch (SQLException e) {
				return Response.status(400).build();
			}
		}
				
		
		if(login){
			return GenToken(email);
			
		}
		else{
			return Response.status(ERROR).build();
		}
    }
    
    @RequestMapping(value={"/sell_product"}, method=RequestMethod.POST)
    public Response sellProduct(@RequestBody String param) throws ParseException, IOException {
  	    
    	String email = "";
    	String token = "";
    	String price = "";
    	String name = "";
    	JSONParser parser = new JSONParser();
    	JSONObject json;
		try {
			json = (JSONObject) parser.parse(param);
			
			email = StringEscapeUtils.escapeHtml((String) json.get("email"));
			token = StringEscapeUtils.escapeHtml((String) json.get("token"));
			price = StringEscapeUtils.escapeHtml((String) json.get("price"));
			name = StringEscapeUtils.escapeHtml((String) json.get("name"));
    	
		}catch(Exception e) { }
		
		String randNum = extractRandNum(token);
		String ts = extractTimestamp(token);
		
		
		int intPrice = 0;
    	int intQuantity = 0;
    	
    	if(isInteger(price)) {
    		 intPrice = Integer.parseInt(price);
    	}
    	else {
    		return Response.status(ERROR).build();
    	}
    	    	
    	if(ValidateToken(randNum, ts, email)) {
    	
	    	boolean insert = false;
	    		    	
			try {
				dbloader.connectDB();
				insert = dbloader.sellProduct(email, name, intPrice);
			} catch (ClassNotFoundException | SQLException e) 
			{
				return Response.status(ERROR).build();
			}
			finally 
			{
				try {
					dbloader.closeConn();
				} catch (SQLException e) {
					return Response.status(ERROR).build();
				}
			}
			
	    	if(insert){
	    		return GenToken(email);
			}else{
				return Response.status(ERROR).build();
			}
    	}
    	return Response.status(ERROR).build();
    }
    
    @RequestMapping(value={"/buy_product"}, method=RequestMethod.POST)
    public Response buyProduct(@RequestBody String param) throws ParseException, IOException {
  	    
    	String email = "";
    	String token = "";
    	String productName = "";
    	JSONParser parser = new JSONParser();
    	JSONObject json;
		try {
			json = (JSONObject) parser.parse(param);
			
			email = StringEscapeUtils.escapeHtml((String) json.get("email"));
			token = StringEscapeUtils.escapeHtml((String) json.get("token"));
			productName = StringEscapeUtils.escapeHtml((String) json.get("productId"));
    	
		}catch(Exception e) { }
		
		String randNum = extractRandNum(token);
		String ts = extractTimestamp(token);
		  	    	
    	if(ValidateToken(randNum, ts, email)) {
    	
	    	boolean insert = false;
	    		    	
			try {
				dbloader.connectDB();
				insert = dbloader.buyProduct(email, productName);
			} catch (ClassNotFoundException | SQLException e) 
			{
				return Response.status(ERROR).build();
			}
			finally 
			{
				try {
					dbloader.closeConn();
				} catch (SQLException e) {
					return Response.status(ERROR).build();
				}
			}
			
	    	if(insert){
	    		return GenToken(email);
			}else{
				return Response.status(ERROR).build();
			}
    	}
    	return Response.status(ERROR).build();
    }
    
    @RequestMapping(value={"/get_prices"}, method=RequestMethod.POST)
    public Response getPrices() throws ParseException, IOException {
    	int[] prices = null;
    	try {
			dbloader.connectDB();

			prices = dbloader.getPrices();

			for(int i=0; i<5; i++) {
			}
		} catch (ClassNotFoundException | SQLException e) 
		{
			return Response.status(ERROR).build();
		}
		finally 
		{
			try {
				dbloader.closeConn();
			} catch (SQLException e) {
				return Response.status(ERROR).build();
			}
		}
    	return Response.ok(prices, MediaType.APPLICATION_JSON).build();	
    }
    
    @RequestMapping(value={"/get_comments"}, method=RequestMethod.POST)
    public Response getComments() throws ParseException, IOException {
    	String[] comments = null;
    	try {
			dbloader.connectDB();
			comments = dbloader.getComments();
			for(int i=0; i<5; i++) {
			}
		} catch (ClassNotFoundException | SQLException e) 
		{
			return Response.status(ERROR).build();
		}
		finally 
		{
			try {
				dbloader.closeConn();
			} catch (SQLException e) {
				return Response.status(ERROR).build();
			}
		}
    	return Response.ok(comments, MediaType.APPLICATION_JSON).build();	
    }
    
    public String extractRandNum(String token) {
    	
    	String[] tokenSplit1 = token.split("randomNum");
    	String[] tokenSplit2 = tokenSplit1[1].split(";");
    	String[] tokenSplit3 = tokenSplit2[2].split("\\\\");
    	String randomNum = tokenSplit3[0];
    	   	
    	return randomNum;
    }
    
    public String extractTimestamp(String token) {
    	
    	String[] tokenSplit1 = token.split("timeStamp");
    	String[] tokenSplit2 = tokenSplit1[1].split(":");
    	String[] tokenSplit3 = tokenSplit2[1].split("}");
    	String ts = tokenSplit3[0];
    	   	
    	return ts;
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
    
    private boolean ValidateToken(String randNum, String ts, String email) {
    	
    	if(usersLoggedIn.containsKey(email)) {
    		Token token = usersLoggedIn.get(email);
    		long tsLong = Long.parseLong(ts);
    		if(token.getRandomNum().equals(randNum) && checkTokenTimeStamp(tsLong)) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	return false;
    	
    }

	private boolean checkTokenTimeStamp(long tokenTime) {
		long serverTime = System.currentTimeMillis();
		return Math.abs(serverTime - tokenTime) < SESSION_TIMEOUT;
	}
	

	private Response GenToken(String email) throws JsonProcessingException {
		Token token = new Token(nextSessionId(), System.currentTimeMillis());
		usersLoggedIn.put(email, token);
		ObjectMapper mapper = new ObjectMapper();
		String tokenJson = null;
		tokenJson = mapper.writeValueAsString(token);
		return Response.ok(tokenJson, MediaType.APPLICATION_JSON).build();
	}
	
	private String nextSessionId() {
		String s = new BigInteger(130, random).toString(32);
		return s;
	}
	
}