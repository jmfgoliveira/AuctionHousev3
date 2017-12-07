package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
    		
    	Socket clientsocket = null;
    	PrintStream output = null;
    	
    	while(true){
    		try {
				clientsocket = new Socket("localhost", 65501);
				output = new PrintStream(clientsocket.getOutputStream());
				output.println("are you alive?");
				System.out.println("Sent message: are you alive");
				clientsocket.close();
				TimeUnit.SECONDS.sleep(15);
			} catch (ConnectException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	try {
    		Runtime runtime = Runtime.getRuntime();
    		Process process = runtime.exec("src/main/resources/firewall.txt");
    	}catch(Exception e) {}
    	SpringApplication.run(Application.class, args); 
    }
    
}