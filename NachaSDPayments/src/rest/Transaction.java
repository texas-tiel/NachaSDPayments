package rest;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;
	private int account;
	private Date date;
	private double amount;

	public Transaction(){}
   
	public Transaction(int n, Date d, double a){
       account = n;
       date = d;
       amount = a;
    }

    public int getAccount() { return account; }
    public void setAccount(int n) { account = n; }
    public Date getDate() { return date; }
    public void setDate(Date d) { date = d; }
    public double getAmount() { return amount; }
    public void setAmount(double a) { amount = a; }
}
