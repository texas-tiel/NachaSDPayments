package rest;

import java.sql.Date;

public class DelayedPayment implements java.io.Serializable {
	
	private String accountNum;
	private Date paymentDate;
	private Double delayedPaymentAmount;
	private int numOfPayments;
	
	public String getAccountNum() {return accountNum;}
	public void setAccountNum(String an) {accountNum = an;}
	
	public Date getPaymentDate() {return paymentDate;}
	public void setPaymentDate(Date pd) {paymentDate = pd;}

	
	public Double getDelayedPaymentAmount() {return delayedPaymentAmount;}
	public void setDelayedPaymentAmount(Double dpa) {delayedPaymentAmount = dpa;}
	
	public int getNumOfPayments() {return numOfPayments;}
	public void setNumOfPayments(int nop) {numOfPayments = nop;}


}
