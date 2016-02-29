package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Composition;
import org.jboss.tools.examples.model.Supply;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class CompositionDTO extends ChangeTrackingDTO implements Serializable {

	private Long id;
	private Double quantity;
	private SupplyDTO supply;

	public CompositionDTO() {
	}

	public CompositionDTO(final Composition entity) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setQuantity(entity.getQuantity());
			this.setSupply(new SupplyDTO(entity.getSupply()));
		}
	}

	public Composition fromDTO(Composition entity, EntityManager em) {
		if (entity == null) {
			entity = new Composition();
		}
		if (isChanged(this.id)) entity.setId(id);
		if (isChanged(this.quantity)) entity.setQuantity(quantity);
		if (isChanged(this.supply)) {
			if (this.supply != null) {
				Supply s = null;
				if (this.supply.getId() != null)
					s = em.find(Supply.class, this.supply.getId());
				entity.setSupply(this.supply.fromDTO(s, em));
			}
		}
		entity = em.merge(entity);
		// Provided it was a new entity, copy the new ID to its DTO.
		this.setId(entity.getId());
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
		this.setChanged(id);
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
		this.setChanged(quantity);
	}

	public SupplyDTO getSupply() {
		return this.supply;
	}

	public void setSupply(final SupplyDTO supply) {
		this.supply = supply;
		this.setChanged(supply);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((supply == null) ? 0 : supply.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CompositionDTO))
			return false;
		CompositionDTO other = (CompositionDTO) obj;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (supply == null) {
			if (other.supply != null)
				return false;
		} else if (!supply.equals(other.supply))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompositionDTO#" + this.hashCode() + " [id=" + id + ", quantity=" + quantity + ", supply=" + supply + "]";
	}
	
}