package org.jboss.tools.examples.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.jboss.tools.examples.model.Supply;

@Path("/supplies")
@Stateless
public class SupplyService extends BaseEntityService<Supply> {

	public SupplyService() {
		super(Supply.class);
	}
	
}