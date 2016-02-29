package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Category;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class CategoryDTO extends ChangeTrackingDTO implements Serializable {

	private Long id;
	private String name;
	private String description;

	public CategoryDTO() {
	}

	public CategoryDTO(final Category entity) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setDescription(entity.getDescription());
		}
	}

	public Category fromDTO(Category entity, EntityManager em) {
		if (entity == null) {
			entity = new Category();
		}
		
		if (isChanged(this.id)) entity.setId(id);
		if (isChanged(this.name)) entity.setName(name);
		if (isChanged(this.description)) entity.setDescription(description);
		
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

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
		this.setChanged(name);
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
		this.setChanged(description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CategoryDTO))
			return false;
		CategoryDTO other = (CategoryDTO) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "CategoryDTO#" + this.hashCode() +" [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}