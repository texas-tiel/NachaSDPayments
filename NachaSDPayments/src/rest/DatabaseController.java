package rest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

@Path("/data") //specifies the routing information for the HTTP calls to this class
public class DatabaseController {
	
	@POST
    @Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int verifyUserLogin(LoginDTO user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    int id = 0;
	    
	    try{
	    	tx = session.beginTransaction();
	    	String query = "FROM rest.User WHERE username='"+user.getUsername()+"' AND password='"+user.getPassword()+"'";
	        List trans = session.createQuery(query).list(); 
	        User temp = null;
	        
	        for (Iterator iterator1 = trans.iterator(); iterator1.hasNext();){
	           temp = (User) iterator1.next();
	        }
	        
	        if(temp != null)
	        	id = temp.getId();
	        
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }

	    return id;
	}
	
    @POST
    @Path("/history")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transactions> getTransactionHistory(int id) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<Transactions> history = new ArrayList<Transactions>();
	    try{
	    	tx = session.beginTransaction();
	        String sql = "SELECT * FROM Transactions t where account IN (SELECT account_num from Account WHERE user_id = " + id + ") ORDER BY t.date desc;";
	        SQLQuery query = session.createSQLQuery(sql);
	        query.setResultTransformer(Transformers.aliasToBean(Transactions.class));

	        history = query.list();
	        
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
	    
    	return history;
    }
    
    @POST
    @Path("/accountinfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankAccountDTO> getBankAccounts(int id){
    	Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    List<BankAccountDTO> accounts = new ArrayList<BankAccountDTO>();
	    
	    try{
	    	tx = session.beginTransaction();
	        String sql = "SELECT a.id, a.account_num as account, b.bank_name as bankname FROM Account a INNER JOIN Bank b ON a.bank_num = b.id WHERE a.user_id = " + id + ";";
	        SQLQuery query = session.createSQLQuery(sql);
	        query.setResultTransformer(Transformers.aliasToBean(BankAccountDTO.class));

	        accounts = query.list();
	        
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
	    
    	return accounts;
    }
    
    @POST //specifies which type of HTTP call the method accepts (get, post, put, delete)
    @Path("/newtran") //declares the routing information for the HTTP call to this method
    @Consumes(MediaType.APPLICATION_JSON) //declares the format of the data it receives as a parameter
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public boolean processNewTransaction(FormInfoDTO form) throws FileNotFoundException, UnsupportedEncodingException {
    	//Processing for NACHA file
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    try{
	    	tx = session.beginTransaction();
	        String sql = "INSERT INTO TRANSACTIONS (DATE,AMOUNT,STATUS, ACCOUNT) VALUES ('"+ form.getDate() +"', "+form.getAmount()+", 'Pending', '"+form.getAccount()+"');";
	        /*SQLQuery query = */
	        session.createSQLQuery(sql)
	        	.executeUpdate();
	        //query.setResultTransformer(Transformers.aliasToBean(Transactions.class));
	        
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
    	/*String fileOutput = null;
    	String account = form.getAccount();
    	String amount = String.format ("%d", form.getAmount()*100);
    	
    	int length = 17-account.length();
    	for(int i=0;i<length;i++){
    		account += "_";
    	}
    	length = 10-amount.length();
    	for(int i=0;i<length;i++){
    		amount = "_" + amount;
    	}
    	
    	fileOutput = 
    	  "6" 							//Record Type Code
    	+ "__"							//Transaction Code
    	+ "________"					//Receiving DFI Identification
    	+ "_"							//Check Digit
    	+ account						//DFI Account Number, 17 digits, left justify
    	+ amount						//Amount, in dollars and cents  "00000000" + "00"
    	+ "_______________"				//Individual Identification Number
    	+ "___________" + "___________"	//Name, last, first, left justify
    	+ "__"							//Discretionary Data
    	+ "_"							//Addenda Record Indicator
    	+ "_______________"				//Trace Number
    	;
    	
    	PrintWriter writer = new PrintWriter("output.txt", "UTF-8");	//open writer
    	writer.println(fileOutput);										//write to file
    	writer.close();	*/											//close writer
    	
    	//System.out.println(form.getAccount());
    	return true;
    }
}