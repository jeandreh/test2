package org.jboss.tools.examples.rest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.tools.examples.model.User;
import org.jboss.tools.examples.rest.dto.UserCredentialsDTO;
import org.jboss.tools.examples.service.PasswordEncryptionService;
import org.jboss.tools.examples.util.RandomString;

@Path("/auth")
public class AuthenticationService {

    @Inject
    private EntityManager entityManager;
	
    @POST
    @Consumes("application/json")
    public Response authenticateUser(UserCredentialsDTO userCredentials) {

    	ResponseBuilder rb = Response.status(Response.Status.UNAUTHORIZED);
    	
        try {
            // Authenticate the user using the credentials provided
            User user = authenticate(userCredentials);
        	if (user != null){
            	// Issue a token for the user
                String token = issueToken(user);

                // Return the token on the response
                rb = Response.ok(token);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return rb.build();
    }

    private User authenticate(UserCredentialsDTO userCredentials) {
    	
    	User user = null;
    	try {
    		user = (User) this.entityManager.createQuery(
        			"SELECT u "+
        			"FROM User u WHERE u.email = :email")
        			.setParameter("email", userCredentials.getEmail())
        			.getSingleResult();
        		
        	boolean authStatus = PasswordEncryptionService.authenticate(
        		userCredentials.getPassword(), user.getEncryptedPassword());
        	
        	if (!authStatus) {
        		user = null;
        	}
    	}
    	catch(Exception e) {
    		user = null;
    	}
    	return user;
    }

    private String issueToken(User user) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	String token = RandomString.getString(200);
    	user.setAuthenticationToken(token);
    	this.entityManager.merge(user);
    	return token;
    }
}