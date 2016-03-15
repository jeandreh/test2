package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.tools.examples.model.Supply;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class SupplyDTO extends ChangeTrackingDTO implements Serializable {

	private Long id;
	private String name;
	private Float price;
	private String unity;
	private Float stock;
	
	public SupplyDTO() {
		
	}

	public SupplyDTO(final Supply entity) {
		if (entity != null) {
			this.setUnity(entity.getUnity());
			this.setStock(entity.getStock());
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setPrice(entity.getPrice());
		}
	}

	public Supply fromDTO(Supply entity, EntityManager em) {
		if (entity == null) {
			entity = new Supply();
		}
		if (this.isChanged(this.id)) {
			entity.setId(this.id);
		}
		if (this.isChanged(this.name)) {
			entity.setName(this.name);
		}
		if (this.isChanged(this.unity)) {
			entity.setUnity(this.unity);
		}
		if (this.isChanged(this.price)) {
			entity.setPrice(this.price);
		}
		if (this.isChanged(this.stock)) {
			entity.setStock(this.stock);
		}
		entity = em.merge(entity);
		// Provided it was a new entity, copy the new ID to its DTO.
		this.setId(entity.getId());
		return entity;
	}

	public String getUnity() {
		return this.unity;
	}

	public void setUnity(final String unity) {
		this.unity = unity;
		this.setChanged(this.unity);
	}

	public Float getStock() {
		return this.stock;
	}

	public void setStock(final Float stock) {
		this.stock = stock;
		this.setChanged(this.stock);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
		this.setChanged(this.id);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
		this.setChanged(this.name);
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(final Float price) {
		this.price = price;
		this.setChanged(this.price);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((unity == null) ? 0 : unity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SupplyDTO))
			return false;
		SupplyDTO other = (SupplyDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (unity == null) {
			if (other.unity != null)
				return false;
		} else if (!unity.equals(other.unity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SupplyDTO#" + this.hashCode() + " [id=" + id + ", name=" + name + ", price=" 
				+ price + ", unity=" + unity + ", stock=" + stock + "]";
	}
	
	
}