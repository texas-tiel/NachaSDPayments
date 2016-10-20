package rest;

import java.sql.Date;

public class FormInfo implements java.io.Serializable{

	private String account;
	private Date date;
	private double amount;

	public String getAccount(){ return account; }
	public void setAccount(String a){ account = a; }
	
	public Date getDate(){ return date; }
	public void setDate(Date d){ date = d; }
	
	public double getAmount(){ return amount; }
	public void setAmount(double a){ amount = a; }
}
