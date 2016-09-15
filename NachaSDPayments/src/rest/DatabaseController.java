package rest;

import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/data") //specifies the routing information for the HTTP calls to this class
public class DatabaseController {
    
    @POST //specifies which type of HTTP call the method accepts (get, post, put, delete)
    @Path("/history") //declares the routing information for the HTTP call to this method
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public ArrayList<Transaction> getTransactionHistory() {
    	ArrayList<Transaction> history = new ArrayList<Transaction>();
    	history.add(new Transaction(987654321, new Date(101, 11, 15), 13.45)); //12/15/2001
    	history.add(new Transaction(987654321, new Date(105, 3, 4), 9.23));
    	history.add(new Transaction(987654321, new Date(105, 5, 22), 435.98));
    	history.add(new Transaction(987654321, new Date(107, 7, 30), 34.12));
    	history.add(new Transaction(987654321, new Date(116, 9, 14), 98.16));
        return history;
    }
}
