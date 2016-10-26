package rest;

public class Bank implements java.io.Serializable {

	private int id;
	private String bankName;
	
	public int getId() { return id; }
	public void setId(int i) { id = i; }

	public String getBankName() { return bankName; }
	public void setBankName(String b) { bankName = b; }
}