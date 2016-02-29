package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.RetailOption;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.Set;
import java.util.HashSet;
import org.jboss.tools.examples.model.Composition;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class RetailOptionDTO extends ChangeTrackingDTO implements Serializable {

	private Long id;
	private String name;
	private String shortName;
	private String description;
	private Double price;
	private Set<CompositionDTO> compositions = new HashSet<CompositionDTO>();

	public RetailOptionDTO() {
	}

	public RetailOptionDTO(final RetailOption entity) {
		if (entity != null) {
			this.setDescription(entity.getDescription());
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setShortName(entity.getShortName());
			this.setPrice(entity.getPrice());
			
			Set<CompositionDTO> compositionsDTO = new HashSet<CompositionDTO>();
			for (Composition composition : entity.getCompositions()) {
				compositionsDTO.add(new CompositionDTO(composition));
			}
			this.setCompositions(compositionsDTO);
		}
	}

	public RetailOption fromDTO(RetailOption entity, EntityManager em) {
		if (entity == null) {
			entity = new RetailOption();
		}
		if (isChanged(this.id)) entity.setId(this.id);
		if (isChanged(this.name)) entity.setName(this.name);
		if (isChanged(this.shortName)) entity.setShortName(this.shortName);
		if (isChanged(this.description)) entity.setDescription(this.description);
		if (isChanged(this.price)) entity.setPrice(this.price);
		if (isChanged(this.compositions)) {
			Set<Composition> newCompositions = new HashSet<Composition>();
			for (CompositionDTO compositionDTO : this.compositions) {
				Composition comp = null;
				if (compositionDTO.getId() != null)
					comp = em.find(Composition.class, compositionDTO.getId());
				
				newCompositions.add(compositionDTO.fromDTO(comp, em));
			}
			entity.setCompositions(newCompositions);
		}
		entity = em.merge(entity);
		// Provided it was a new entity, copy the new ID to its DTO.
		this.setId(entity.getId());
		return entity;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
		this.setChanged(this.description);
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

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
		this.setChanged(this.shortName);
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
		this.setChanged(this.price);
	}

	public Set<CompositionDTO> getCompositions() {
		return this.compositions;
	}

	public void setCompositions(final Set<CompositionDTO> compositions) {
		this.compositions = compositions;
		this.setChanged(this.compositions);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compositions == null) ? 0 : compositions.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RetailOptionDTO))
			return false;
		RetailOptionDTO other = (RetailOptionDTO) obj;
		if (compositions == null) {
			if (other.compositions != null)
				return false;
		} else if (!compositions.equals(other.compositions))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
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
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetailOptionDTO#"+ this.hashCode() + " [id=" + id + ", name=" + name + ", shortName=" + shortName + ", description="
				+ description + ", price=" + price + ", compositions=" + compositions + "]";
	}
	
}