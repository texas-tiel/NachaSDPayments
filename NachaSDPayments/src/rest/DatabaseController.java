package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/data") //specifies the routing information for the http calls to this class
public class DatabaseController {
    
    @GET //specifies which type of http call the method accepts (get, post, put, delete)
    @Path("/test") //declares the routing information for the http call to this method
    @Produces(MediaType.APPLICATION_JSON) //declares the format of the returned data, in this case a JSON Object
    public Test getText() {
        return new Test("Hi, I'm a JSON Object.", "I came from the Java backend of the app!");
    }
}
