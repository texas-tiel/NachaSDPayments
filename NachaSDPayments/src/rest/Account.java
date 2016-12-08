package rest;

import java.sql.Date;

public class Account implements java.io.Serializable {

	private int id;
	private int bankNum;
	private int userId;
	private String accountNum;
	private Double paymentDueAmount;
	private Date paymentDueDate;
	
	public int getId() { return id; }
	public void setId(int i) { id = i; }

	public int getBankNum() {return bankNum;}
	public void setBankNum(int bn) {bankNum = bn;}
	
	public int getUserId() {return userId;}
	public void setUserId(int id) {userId = id;}
	
	public String getAccountNum() { return accountNum; }
	public void setAccountNum(String a) { accountNum = a; }
	
	public Double getPaymentDueAmount() {return paymentDueAmount;}
	public void setPaymentDueAmount(Double pda) {paymentDueAmount = pda;}
	
	public Date getPaymentDueDate() {return paymentDueDate;}
	public void setPaymentDueDate(Date pdd) {paymentDueDate = pdd;}
}
