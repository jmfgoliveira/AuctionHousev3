package server;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.mysql.cj.jdbc.PreparedStatement;

public class DatabaseLoader {
	
	private int currentuser;
	
	private static String url = "jdbc:mysql://localhost:3306/auction_house";
	private static String username = "root";
	private static String password = "toor";
	private Connection connection;
	
	private String allusersQuery = "select * from User";
	private String allproductsQuery = "select * from Product";
	private String allauctionsQuery = "select * from Auction";
	
	
	public DatabaseLoader(){
		
	}
	
	public void connectDB() throws ClassNotFoundException, SQLException
	{
		System.out.println("Connecting database...");
		Class.forName("com.mysql.cj.jdbc.Driver");  
		connection=DriverManager.getConnection(url,username,password); 
		System.out.println("Database connected!");
	}
	
	public ArrayList<User> loadUsers() throws SQLException
	{
		ArrayList<User> users = new ArrayList<User>();
		String name, email;
		int id;
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(allusersQuery); 
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) 
        {
        	id=rs.getInt("id");
            name = rs.getString("name");
            email = rs.getString("email");
            User u = new User(id, name, email);
            users.add(u);
        }
        
        if (rs!= null)
	    { 
	    	rs.close(); 
	    }
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		System.out.println("finished loading users");
		return users;
	}
	
	public ArrayList<Product> loadProducts() throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();
		String name, description;
		int id, price, quantity, owner;
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(allproductsQuery); 
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) 
        {
        	id=rs.getInt("id");
            name = rs.getString("name");
            price=rs.getInt("price");
            quantity=rs.getInt("quantity");
            owner=rs.getInt("owner_id");
            description = rs.getString("description");
            Product p = new Product(id, name, price, quantity, owner, description);
            products.add(p);
        }
        
        if (rs!= null)
	    { 
	    	rs.close(); 
	    }
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		System.out.println("finished loading products");
		return products;
		
	}
	
	public ArrayList<Auction> loadAuctions() throws SQLException
	{
		ArrayList<Auction> auctions = new ArrayList<Auction>();
		int id, product_id, seller_id, buyer_id;
		String state;
		Date date;
		
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(allauctionsQuery); 
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) 
        {
        	id=rs.getInt("id");
        	product_id=rs.getInt("product_id");
            seller_id=rs.getInt("seller_id");
            buyer_id=rs.getInt("buyer_id");
            date=rs.getDate("end_date");
            state = rs.getString("auction_state");
            Auction a = new Auction(id, product_id, seller_id, buyer_id, date, state);
            auctions.add(a);
        }
        
        if (rs!= null)
	    { 
	    	rs.close(); 
	    }
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		System.out.println("finished loading products");
		return auctions;
		
	}

	
	private String passwordHash(String password, String salt) 
	{
		String sha256hex = DigestUtils.sha256Hex(password + salt);
		return sha256hex;
	}

	
	
	public boolean insertUser(String name, String email, String password) throws SQLException 
	{
	
		if(name == null || email == null || password == null || !email.contains("@")) {
			System.out.println("Input invalido");
			return false;
		}
		
		String emailhash = DigestUtils.sha256Hex(email);
		if(!checkUserExists(emailhash)){
	
			String sql = "INSERT INTO User(name, email, password, salt) "
				+ "VALUES(?, ?, ?, ?);";
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);

			SecureRandom random = new SecureRandom();
		    byte bytes[] = new byte[32];
		    random.nextBytes(bytes);
		    
		    String salt = Base64.getEncoder().encodeToString(bytes);
		    String passhash = passwordHash(password, salt);
		     
			stmt.setString(1, name);
			stmt.setString(2, emailhash);
			stmt.setString(3, passhash);
			stmt.setString(4, salt);			
	
			int num = stmt.executeUpdate();
			
	        
		    if (stmt != null)
		    { 
		    	stmt.close(); 
		    }
			
			System.out.println(num + " user(s) inserted.");
			return true;
		}else{
			return false;
		}
	}	

	private boolean checkUserExists(String email) throws SQLException {
		
		String sql = "select email from User where email=?";
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
		stmt.setString(1, email);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()){
			return true;
		}
		return false;		
	}

	
	public boolean login(String email, String password) throws SQLException {
		
		if(email == null || !email.contains("@")) {
			return false;
		}
		
		String emailhash = DigestUtils.sha256Hex(email);
		
		if(!checkUserExists(emailhash)){
			return false;
		}
		
		boolean login = false;
		
		String sql = "SELECT id, salt, password FROM User WHERE email = ?";
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
		
		stmt.setString(1, emailhash);
		ResultSet rs = stmt.executeQuery();
		rs.next();
    	String salt = rs.getString("salt");
    	String pwd = rs.getString("password");
    	
    	currentuser=rs.getInt("id");
    	
		if(pwd.equals(passwordHash(password, salt))) {
			login = true;
			String sql2 = "INSERT INTO Login(email, login_date)" + "VALUES(?, ?);";
			PreparedStatement stmt2 = (PreparedStatement) connection.prepareStatement(sql2);

			stmt2.setString(1,emailhash);
			stmt2.setString(2,LocalDateTime.now().toString());
			System.out.println("login ok: " + currentuser);
		}
		else{
			System.out.println("Login not ok");
		}
		
		if (rs!= null)
	    { 
	    	rs.close(); 
	    }
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		
		return login;
		
	}

	public void commentProduct (int user_id, int product_id, String comment) throws SQLException{
		
		//ver se o user está logged in
		
		Date date = new Date();
		String str = new SimpleDateFormat("dd-MM-yyyy").format(date);
		
		String sql = "INSERT INTO Comments(user_id, product_id, comment, date)" + 
		"VALUES(?, ?, ?, ?);";
		
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);

		stmt.setInt(1,user_id);
		stmt.setInt(2,product_id);
		stmt.setString(3,comment);
		stmt.setString(4,str);
		
		stmt.executeUpdate();
		
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		
	}
	
	
	public void buyProduct (int id_buyer, int product_id) throws SQLException
	{
		
		
		//ver se o buyer está logged in
		
		Date date = new Date();
		String str = new SimpleDateFormat("dd-MM-yyyy").format(date);
		
		String sql = "SELECT * FROM Auction WHERE product_id=? ORDER BY price ASC"; 
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
		stmt.setInt(1, product_id);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()){
			//TODO test date
			int auction_id = rs.getInt("id");
			sql = "DELETE FROM Auction WHERE id=?"; 
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, auction_id);
			stmt.executeUpdate();
			System.out.println("Purchase ok");
			

			sql = "INSERT INTO Purchases(seller_id, buyer_id, product_id, price, date, state) " + 
			"VALUES(?, ?, ?, ?, ?, ?);";

			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, rs.getInt("seller_id"));
			stmt.setInt(2, currentuser);
			stmt.setInt(3, product_id);
			stmt.setInt(4, rs.getInt("price"));
			stmt.setString(5, str);
			stmt.setString(6, "Delivering");
			stmt.executeUpdate();
			
			if (rs!= null)
		    { 
		    	rs.close(); 
		    }
	        
		    if (stmt != null)
		    { 
		    	stmt.close(); 
		    }
			
		}
	}
	
	public void sellProduct(int id_seller, int product_id, int price) throws SQLException
	{
		
		String sql = "INSERT INTO Auction(seller_id, product_id, price, end_date, state)" +
				 "VALUES (?,?,?,?,?)";
		
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
		
		Date date = new Date();
		date.setMonth(date.getMonth() + 2);
		String str = new SimpleDateFormat("dd-MM-yyyy").format(date);
		
		stmt.setInt(1, id_seller);
		stmt.setInt(2, product_id);
		stmt.setInt(3, price);
		stmt.setString(4, str);
		stmt.setString(5, "Pending");


		stmt.executeUpdate();
        
	    if (stmt != null)
	    { 
	    	stmt.close(); 
	    }
		
		System.out.println("Auction Created");
		
	}
	
	public void closeConn() throws SQLException 
	{
		
		if(connection!=null)
		{
			connection.close();
		}
		
	}
	
}
