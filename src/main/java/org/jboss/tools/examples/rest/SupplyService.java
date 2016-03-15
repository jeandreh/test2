package org.jboss.tools.examples.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.tools.examples.model.Supply;
import org.jboss.tools.examples.rest.dto.SupplyDTO;

@Path("/supplies")
@Stateless
public class SupplyService extends BaseEntityService<Supply> {

	public SupplyService() {
		super(Supply.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSupply(SupplyDTO supplyDTO) {
		try {
			Supply sup = supplyDTO.fromDTO(null, this.getEntityManager());
			sup = this.getEntityManager().merge(sup);
			return Response.ok().entity(sup.getId()).build();
		}
		catch (ConstraintViolationException e) {
			// If validation of the data failed using Bean Validation, then send an error
			Map<String, String> errors = new HashMap<String, String>();
			for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
				errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
			}
			// A WebApplicationException can wrap a response
			// Throwing the exception causes an automatic rollback
//			e.printStackTrace();
			throw new RestServiceException(Response.status(Response.Status.BAD_REQUEST).entity(errors).build());
		} 
		catch (Exception e) {
			// Finally, handle unexpected exceptions
			Map<String, Object> errors = new HashMap<String, Object>();
			errors.put("errors", Collections.singletonList(e.getMessage()));
			// A WebApplicationException can wrap a response
			// Throwing the exception causes an automatic rollback
//			e.printStackTrace();
			throw new RestServiceException(Response.status(Response.Status.BAD_REQUEST).entity(errors).build());
		}
	}
	
}