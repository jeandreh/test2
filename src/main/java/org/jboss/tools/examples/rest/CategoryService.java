package org.jboss.tools.examples.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.marshalling.Pair;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Category;
import org.jboss.tools.examples.rest.dto.CategoryDTO;

@Path("/categories")
@Stateless
public class CategoryService extends BaseEntityService<Category> {

	public CategoryService() {
		super(Category.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCategory(CategoryDTO categoryDTO) {
		try {
			Category cat = categoryDTO.fromDTO(null, this.getEntityManager());
			cat = this.getEntityManager().merge(cat);
			return Response.ok().entity(cat.getId()).build();
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
