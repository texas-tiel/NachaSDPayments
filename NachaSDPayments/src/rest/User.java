package rest;

import java.sql.Date;

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
	private String zipcode;
	private Date dateofbirth;
	private String firstname;
	private String lastname;
	private Double salary;
	private int creditscore;
	
	public int getId() { return id; }
	public void setId(int i) { id = i; }
	
	public String getUsername(){ return username; }
	public void setUsername(String u){ username = u; }
	
	public String getPassword(){ return password; }
	public void setPassword(String p){ password = p; }
	
	public String getSsn(){ return ssn; }
	public void setSsn(String n){ ssn = n; }
	
	public String getZipcode() { return zipcode; }
	public void setZipcode(String z) {zipcode =z; }
 	
	public Date getDateofbirth() {return dateofbirth;}
	public void setDateofbirth(Date dob) {dateofbirth = dob;}
	
	public String getFirstname() {return firstname;}
	public void setFirstname(String fn) {firstname = fn; }
	
	public String getLastname() {return lastname;}
	public void setLastname(String ln) {lastname = ln;}
	
	public Double getSalary() {return salary;}
	public void setSalary(Double s) {salary = s;}
	
	public int getCreditscore() {return creditscore;}
	public void setCreditscore(int cs) {creditscore = cs;}
	
	
}
