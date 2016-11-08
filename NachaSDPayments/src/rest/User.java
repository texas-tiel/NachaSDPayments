package rest;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class User implements java.io.Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String ssn;

	public int getId() { return id; }
	public void setId(int i) { id = i; }
	
	public String getUsername(){ return username; }
	public void setUsername(String u){ username = u; }
	
	public String getPassword(){ return password; }
	public void setPassword(String p){ password = p; }
	
	public String getSsn(){ return ssn; }
	public void setSsn(String n){ ssn = n; }
}
