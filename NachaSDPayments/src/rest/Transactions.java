package rest;

public class Transactions implements java.io.Serializable {

	private int id;
	private int account;
	private String date;
	private Double amount;
	private String status;
	
	public Transactions() {
	}

	public Transactions(int account) {
		this.account = account;
	}

	public Transactions(int id, int account, String date, Double amount, String status) {
		this.id = id;
		this.account = account;
		this.date = date;
		this.amount = amount;
		this.status = status;
	}

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public int getAccount() { return this.account; }
	public void setAccount(int account) { this.account = account; }
	
	public String getDate() { return this.date; }
	public void setDate(String date) { this.date = date; }
	
	public Double getAmount() { return this.amount; }
	public void setAmount(Double amount) { this.amount = amount; }
	
	public String getStatus(){ return this.status; }
	public void setStatus(String status){ this.status = status; }
}
