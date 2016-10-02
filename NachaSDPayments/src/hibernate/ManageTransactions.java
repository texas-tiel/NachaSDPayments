/**
 * 
 */
package hibernate;

import java.util.List;
import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author srivenperumal
 *
 */
public class ManageTransactions {
	//create session factory - needed
	private static SessionFactory factory;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String x = "--------------------------------------------";
		
		System.out.println(x);
		System.out.println("This module is for Hibernate Control"); //test for java compile
		System.out.println(x);
			
		// needed to configure session factory
		  try{
		         factory = new Configuration().configure().buildSessionFactory();
		         System.out.println("Session factory created"); //test for session factory	         
		      }
		  catch (Throwable ex) // error handle if session is not there
		  	  { 
		         System.err.println("Failed to create sessionFactory object." + ex); // fail safe
		         throw new ExceptionInInitializerError(ex); 
		      }
		  
		  //create local copy
		  ManageTransactions MT = new ManageTransactions();
	
		  //
		  System.out.println(x);
		  
		  /* List all elements */
		  MT.listAll();
		  System.out.println(x);
		  
		//add a new transaction with id 7 date  1-2-2017  amount 99.99
		  MT.addTransaction(8,"4-2-2017",23.33);
		//   Print new table 
		  MT.listAll();
		  System.out.println(x);
		  
		  /*update transaction 5*/
		  MT.updateTransaction(5,"11-4-2016", 124.22);
		  /* List all elements */
		  MT.listAll();
		  System.out.println(x);
		  
		  /*delete a transaction*/
		  MT.deleteTransaction(8);
		  /* List all elements */
		  MT.listAll();
		  System.out.println(x);
		  
		  
	}// end of MAIN

	//method to list all elements -- > basically print all
	public void listAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List trans = session.createQuery("FROM hibernate.Transactions").list(); 
	         for (Iterator iterator1 = trans.iterator(); iterator1.hasNext();)
	         {
	            Transactions temp = (Transactions) iterator1.next(); 
	            System.out.print("ID: " + temp.getId()); 
	            System.out.print("  Date: " + temp.getDate()); 
	            System.out.println("  Amount: " + temp.getAmount());
	        
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	
	//method to create a new Transaction - > need to add error handling (id duplicate data)
	  public Integer addTransaction(int id, String date, double amount){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer tranID = null;
	      try{
	         tx = session.beginTransaction();
	         Transactions trans = new Transactions(id, date, amount);
	         tranID = (Integer) session.save(trans); 
	         tx.commit();
	      }
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }
	      finally 
	      {
	         session.close(); 
	      }
	      return tranID;
	   }
	  
	  /* Method to UPDATE Transaction details based on ID */
	   public void updateTransaction(Integer id, String date, double amount ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Transactions temp = (Transactions)session.get(Transactions.class, id); 
	         temp.setAmount(amount);
	         temp.setDate(date);
			 session.update(temp); 
	         tx.commit();
	      }
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }
	      finally 
	      {
	         session.close(); 
	      }
	   }
	   
	   /* Method to DELETE a transaction from the records */
	   public void deleteTransaction(int id){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Transactions temp =  (Transactions)session.get(Transactions.class, id); 
	         session.delete(temp); 
	         tx.commit();
	      }
	      catch (HibernateException e) 
	      {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }
	      finally 
	      {
	         session.close(); 
	      }
	   }

}	
		  
		  
	
	