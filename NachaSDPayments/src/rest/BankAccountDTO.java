package rest;

public class BankAccountDTO implements java.io.Serializable{
	
	private int id;
	private String account;
	private String bankname;

	public int getId(){ return id; }
	public void setId(int i){ id = i; }
	
	public String getAccount(){ return account; }
	public void setAccount(String a){ account = a; }
	
	public String getBankname(){ return bankname; }
	public void setBankname(String b){ bankname = b; }
}

