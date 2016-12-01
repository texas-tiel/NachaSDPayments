package rest;

import java.util.List;

public class UpdateDTO {
	private String message;
	private List<Transactions> trans;

	public UpdateDTO(String m, List<Transactions> t){
		message = m;
		trans = t;
	}
	
	public String getMessage(){ return message; }
	public void setMessage(String m){ message = m; }
	
	public List<Transactions> getTrans(){ return trans; }
	public void setAccount(List<Transactions> t){ trans = t; }
}
