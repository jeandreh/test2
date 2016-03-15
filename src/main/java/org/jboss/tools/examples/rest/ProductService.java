package org.jboss.tools.examples.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.Bean;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.jboss.tools.examples.rest.RestServiceException;
import org.jboss.tools.examples.rest.dto.ProductDTO;
import org.jboss.tools.examples.rest.dto.RetailOptionDTO;
import org.jboss.logging.Logger;
import org.jboss.tools.examples.model.Product;

@Path("/products")
@Stateless
public class ProductService extends BaseEntityService<Product> {

	public ProductService() {
		super(Product.class);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProduct(ProductDTO prodDTO) {
		try {
			Product prod = prodDTO.fromDTO(null, this.getEntityManager());
			prod = this.getEntityManager().merge(prod);
			return Response.ok().entity(prod.getId()).build();
		}
		catch (ConstraintViolationException e) {
			// If validation of the data failed using Bean Validation, then send an error
			Map<String, Object> errors = new HashMap<String, Object>();
			List<String> errorMessages = new ArrayList<String>();
			for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
				errorMessages.add(constraintViolation.getMessage());
			}
			errors.put("errors", errorMessages);
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
