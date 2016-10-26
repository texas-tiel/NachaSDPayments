package rest;

public class Account implements java.io.Serializable {

	private int id;
	private int userId;
	private int bankNum;
	private String accountNum;
	
	public int getId() { return id; }
	public void setId(int i) { id = i; }
	
	public int getUserId() { return userId; }
	public void setUserId(int i) { userId = i; }
	
	public int getBankNum(){ return bankNum; }
	public void setBankNum(int b){ bankNum = b; }

	public String getAccountNum() { return accountNum; }
	public void setAccountNum(String a) { accountNum = a; }
}
