package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
    	
    	
    	DatabaseLoader dbloader = new DatabaseLoader();
    	
    	try {
			dbloader.connectDB();
			dbloader.insertUser("carlos", "carlos@gmail.com", "password");
			dbloader.login("carlos@gmail.com", "password");
			dbloader.sellProduct(1004, 1, 100);
			dbloader.sellProduct(1004, 1, 50);
			dbloader.sellProduct(1004, 1, 50);
			dbloader.sellProduct(1004, 1, 200);
			dbloader.buyProduct(1004, 1);
			dbloader.closeConn();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	//SpringApplication.run(Application.class, args); 	
		
    }
    
    
    private static boolean available(String host,int port) {
	    System.out.println("--------------Testing port " + port);
	    Socket s = null;
	    try {
	        s = new Socket(host, port);

	        // If the code makes it this far without an exception it means
	        // something is using the port and has responded.
	        System.out.println("--------------Port " + port + " is not available");
	        return false;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " is available");
	        return true;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should handle this error." , e);
	            }
	        }
	    }
	}


	/**
	 * 
	 * @return Localhost real adress
	 */
	private static InetAddress localhostAddress() {
		try {
			try {
				Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
				while (e.hasMoreElements()) {
					NetworkInterface n = e.nextElement();
					Enumeration<InetAddress> ee = n.getInetAddresses();
					while (ee.hasMoreElements()) {
						InetAddress i = ee.nextElement();
						if (i instanceof Inet4Address && !i.isLoopbackAddress())
							return i;
					}
				}
			} catch (SocketException e) {
				// do nothing
			}
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}