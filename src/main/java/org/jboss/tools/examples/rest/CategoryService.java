package org.jboss.tools.examples.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.jboss.tools.examples.model.Category;

@Path("/categories")
@Stateless
public class CategoryService extends BaseEntityService<Category> {

	public CategoryService() {
		super(Category.class);
	}
	
}
