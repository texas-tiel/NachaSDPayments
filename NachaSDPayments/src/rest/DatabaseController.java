package rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
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
	    	String query = "FROM rest.User WHERE username='"+user.getUsername()+"' AND password= crypt('" + user.getPassword() + "', password)";
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
	        
	        String account; 
	        for(int i = 0; i < history.size(); i++){
	        	account = history.get(i).getAccount();
	        	for(int j = 0; j < account.length()-4; j++)
	        		account = account.substring(0, j) + "x" + account.substring(j+1, account.length());
	        	history.get(i).setAccount(account);
	        }
	        
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
    @Path("/pending")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transactions> updatePending(int id) {
    	
    	//CODE GOES HERE
    	String fileName = "receipt.txt";
    	
    	String fileInput = null;
	    
    	try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((fileInput = bufferedReader.readLine()) != null) {
            	int updateId = Integer.parseInt(fileInput.substring(0,15));
            	int updateCode = Integer.parseInt(fileInput.substring(16,17));
            	
            	System.out.println(updateId);
            	System.out.println(updateCode);
            	
            	Session session = HibernateUtil.getSessionFactory().openSession();
        	    Transaction tx = null;
        	    try{
        	    	tx = session.beginTransaction();
        	    	String sql = "";
        	    	if(updateCode==0){
        	    		sql = "UPDATE TRANSACTIONS SET STATUS='Failed' WHERE ID="+updateId+";";
        	    	}
        	    	else{
        	    		sql = "UPDATE TRANSACTIONS SET STATUS='Successful' WHERE ID="+updateId+";";
        	    	}
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
            }

            bufferedReader.close();  
            
            File fileDelete = new File("receipt.txt");
            fileDelete.delete();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	    
    	return getTransactionHistory(id);
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
	    List<Transactions> trans = new ArrayList<Transactions>();
	    String id = "";
	    try{
	    	tx = session.beginTransaction();
	        String sql = "INSERT INTO TRANSACTIONS (DATE,AMOUNT,STATUS, ACCOUNT) VALUES ('"+ form.getDate() +"', "+form.getAmount()+", 'Pending', '"+form.getAccount()+"');";
	        /*SQLQuery query = */
	        session.createSQLQuery(sql)
	        	.executeUpdate();
	        //query.setResultTransformer(Transformers.aliasToBean(Transactions.class));
	        
	        tx.commit();
	        
	        tx = session.beginTransaction();
	        sql = "SELECT t.id FROM Transactions t WHERE t.amount = " + form.getAmount() + " AND t.date ='" + form.getDate() + "' AND t.account = '" + form.getAccount() + "' AND t.status = 'Pending';";
	        SQLQuery query = session.createSQLQuery(sql);
	        query.setResultTransformer(Transformers.aliasToBean(Transactions.class));

	        trans = query.list();
	        id = trans.get(0).getId() + "";
	        
	        tx.commit();
	    }catch (HibernateException e) {
	        if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	        session.close(); 
	    }
    	String fileOutput = null;
    	String account = form.getAccount();
    	String amount = (int)(form.getAmount()*100) + "";
    	//String id = form.getId() + "";
    	
    	int length = 17-account.length();
    	for(int i=0;i<length;i++){
    		account += "_";
    	}
    	length = 10-amount.length();
    	for(int i=0;i<length;i++){
    		amount = "_" + amount;
    	}
    	length = 15-id.length();
    	for(int i=0;i<length;i++){
    		id = "0" + id;
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
    	+ id							//Trace Number
    	;
    	
    	PrintWriter writer = new PrintWriter("output.txt", "UTF-8");	//open writer
    	writer.println(fileOutput);										//write to file
    	writer.close();											//close writer
    	
    	//System.out.println(form.getAccount());
    	return true;
    }

    @POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addUser(User u) throws FileNotFoundException, UnsupportedEncodingException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		    Transaction tx = null;
		    try{
		        tx = session.beginTransaction();
		        String sql = "INSERT INTO user(USERNAME, PASSWORD, SSN) VALUES ('" + u.getUsername() + "', crypt('" + u.getPassword() + "', gen_salt('md5')), '" + u.getSsn() + "');";
			session.createSQLQuery(sql)
			    .executeUpdate();
			tx.commit();
		    }catch (HibernateException e) {
		        if (tx!=null) tx.rollback();
		        e.printStackTrace(); 
		    }finally {
		        session.close();
		    	}
		return true;
    }
}
