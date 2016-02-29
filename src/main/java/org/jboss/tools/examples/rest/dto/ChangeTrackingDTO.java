package org.jboss.tools.examples.rest.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class ChangeTrackingDTO {
	
	private List<Object> changedAttributes = new ArrayList<Object>();
	
	protected void setChanged(Object attr) {
		this.changedAttributes.add(attr);
	}
	
	protected boolean isChanged(Object attr) {
		return this.changedAttributes.contains(attr);
	}
}
