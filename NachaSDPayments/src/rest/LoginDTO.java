package rest;

public class LoginDTO {
	private String username;
	private String password;
	
	public String getUsername(){ return username; }
	public void setUsername(String s){ username = s; }
	
	public String getPassword(){ return password; }
	public void setPassword(String p){ password = p; }
}
