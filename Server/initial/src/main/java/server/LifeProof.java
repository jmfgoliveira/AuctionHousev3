package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;

public class LifeProof extends TimerTask{

	private ServerSocket socket;
	
	
	public LifeProof(ServerSocket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		DataInputStream input;
    	Socket clientSocket = null;
    	try {
    		System.out.println("aaa");
    		clientSocket = this.socket.accept();
    		input = new DataInputStream(clientSocket.getInputStream());
			System.out.println("Received: " + input.readLine());
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
