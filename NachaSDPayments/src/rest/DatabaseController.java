package rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Path("/data") //specifies the routing information for the HTTP calls to this class
public class DatabaseController {
	
    @POST //specifies which type of HTTP call the method accepts (get, post, put, delete)
    @Path("/history") //declares the routing information for the HTTP call to this method
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public ArrayList<Transactions> getTransactionHistory() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = null;
	    ArrayList<Transactions> history = new ArrayList<Transactions>();
	    
	    try{
	    	tx = session.beginTransaction();
	    	String query = "FROM rest.Transactions";
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
}
