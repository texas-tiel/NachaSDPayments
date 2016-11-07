package rest;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class Transactions implements java.io.Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String account;
	private Date date;
	private Double amount;
	private String status;
	
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public String getAccount() { return this.account; }
	public void setAccount(String account) { this.account = account; }
	
	public Date getDate() { return this.date; }
	public void setDate(Date date) { this.date = date; }
	
	public Double getAmount() { return this.amount; }
	public void setAmount(Double amount) { this.amount = amount; }
	
	public String getStatus(){ return this.status; }
	public void setStatus(String status){ this.status = status; }
}
