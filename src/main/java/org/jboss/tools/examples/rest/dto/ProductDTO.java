package org.jboss.tools.examples.rest.dto;

import java.io.Serializable;
import org.jboss.tools.examples.model.Product;
import javax.persistence.EntityManager;
import java.util.Set;
import java.util.HashSet;
import org.jboss.tools.examples.model.Category;
import org.jboss.tools.examples.model.Composition;

import java.util.Iterator;
import org.jboss.tools.examples.model.Supply;
import org.jboss.tools.examples.model.RetailOption;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ProductDTO extends ChangeTrackingDTO implements Serializable {

	private Long id;
	private String name;
	private String imageUrl;
	private Set<CategoryDTO> categories = new HashSet<CategoryDTO>();
	private Set<SupplyDTO> ingredients = new HashSet<SupplyDTO>();
	private Set<RetailOptionDTO> retailOptions = new HashSet<RetailOptionDTO>();

	public ProductDTO() {
	}

	public ProductDTO(final Product entity) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setImageUrl(entity.getImageUrl());
			
			Set<CategoryDTO> catDTOs = new HashSet<CategoryDTO>();
			for (Category category : entity.getCategories()) {
				catDTOs.add(new CategoryDTO(category));
			}
			this.setCategories(catDTOs);
			
			Set<SupplyDTO> supDTOs = new HashSet<SupplyDTO>();
			for (Supply supply : entity.getIngredients()) {
				supDTOs.add(new SupplyDTO(supply));
			}
			this.setIngredients(supDTOs);
			
			Set<RetailOptionDTO> roDTOs = new HashSet<RetailOptionDTO>();
			for (RetailOption ro : entity.getRetailOptions()) {
				roDTOs.add(new RetailOptionDTO(ro));
			}
			this.setRetailOptions(roDTOs);
		}
	}

	public Product fromDTO(Product entity, EntityManager em) {
		if (entity == null) {
			entity = new Product();
		}
		
		if (isChanged(this.id)) entity.setId(this.id);
		if (isChanged(this.name)) entity.setName(this.name);
		if (isChanged(this.imageUrl)) entity.setImageUrl(this.imageUrl);
		
		if (isChanged(this.categories)) {
			Set<Category> newCategories = new HashSet<Category>();
			for (CategoryDTO categoryDTO : this.categories) {
				Category cat = null;
				
				if(categoryDTO.getId() != null)
					cat = em.find(Category.class, categoryDTO.getId());
				
				newCategories.add(categoryDTO.fromDTO(cat, em));
			}
			entity.setCategories(newCategories);
		}
		
		if (isChanged(this.ingredients)) {
			Set<Supply> newIngredients = new HashSet<Supply>();
			for (SupplyDTO supplyDTO : this.ingredients) {
				Supply ingred = null;
				
				if (supplyDTO.getId() != null)
					ingred = em.find(Supply.class, supplyDTO.getId());
				
				newIngredients.add(supplyDTO.fromDTO(ingred, em));
			}
			entity.setIngredients(newIngredients);
		}
		
		if (isChanged(this.retailOptions)) {
			Set<RetailOption> newRetailOptions = new HashSet<RetailOption>();
			for (RetailOptionDTO roDTO : this.retailOptions) {
				// Client request doesn't set supplies in composition, to avoid duplicity of data. Instead, the respective
				// supplies are populated with {this.ingredients} data. The order of both sets is always the same.
				this.populateCompositionsWithSupplies(roDTO.getCompositions());
				RetailOption ro = null;
				
				if (roDTO.getId() != null) 
					ro = em.find(RetailOption.class, roDTO.getId());
				
				newRetailOptions.add(roDTO.fromDTO(ro, em));
			}
			entity.setRetailOptions(newRetailOptions);
		}
		entity = em.merge(entity);
		// Provided it was a new entity, copy the new ID to its DTO.
		this.setId(entity.getId());
		return entity;
	}

	public Set<CategoryDTO> getCategories() {
		return this.categories;
	}

	public void setCategories(final Set<CategoryDTO> categories) {
		this.categories = categories;
		this.setChanged(this.categories);
	}

	public Set<SupplyDTO> getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(final Set<SupplyDTO> ingredients) {
		this.ingredients = ingredients;
		this.setChanged(this.ingredients);
	}

	public Set<RetailOptionDTO> getRetailOptions() {
		return this.retailOptions;
	}

	public void setRetailOptions(final Set<RetailOptionDTO> retailOptions) {
		this.retailOptions = retailOptions;
		this.setChanged(this.retailOptions);
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
		this.setChanged(this.imageUrl);
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
	
	private void populateCompositionsWithSupplies(Set<CompositionDTO> compDTOs) {
		Iterator<SupplyDTO> supplyIterator = this.getIngredients().iterator();
		for (CompositionDTO compDTO : compDTOs) {
			compDTO.setSupply(supplyIterator.next());
		}		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((retailOptions == null) ? 0 : retailOptions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductDTO))
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (retailOptions == null) {
			if (other.retailOptions != null)
				return false;
		} else if (!retailOptions.equals(other.retailOptions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO#" + this.hashCode() + " [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + ", categories=" + categories
				+ ", ingredients=" + ingredients + ", retailOptions=" + retailOptions + "]";
	}
	
	
}