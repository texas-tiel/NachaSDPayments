package rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
	
    @POST //specifies which type of HTTP call the method accepts (get, post, put, delete)
    @Path("/history") //declares the routing information for the HTTP call to this method
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public ArrayList<Transactions> getTransactionHistory() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    ArrayList<Transactions> history = new ArrayList<Transactions>();
	    
	    try{
	    	tx = session.beginTransaction();
	    	String query = "FROM rest.Transactions order by date desc";
	        List trans = session.createQuery(query).list(); 
	        
	        for (Iterator iterator1 = trans.iterator(); iterator1.hasNext();){
	           Transactions temp = (Transactions) iterator1.next();
	           history.add(temp);
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
    
    @POST //specifies which type of HTTP call the method accepts (get, post, put, delete)
    @Path("/newtran") //declares the routing information for the HTTP call to this method
    @Consumes(MediaType.APPLICATION_JSON) //declares the format of the data it receives as a parameter
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public boolean processNewTransaction(FormInfoDTO form) {
    	System.out.println(form.getAccount());
    	
    	//Processing for NACHA file
    	
    	return true;
    }
}