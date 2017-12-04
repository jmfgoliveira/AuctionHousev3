package server;



public class User {

	private String name;
	private String password; //isto tem de sair
	private String email;
	private int userId;
	
	public User(int id, String name, String email) {
		this.userId = id;
		this.name = name;
		this.email = email;
	}
	
	public User(int id, String name, String password, String email) {
		this.userId = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	//Getters And Setters
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getUserId() {
		return this.userId;
	}
}
