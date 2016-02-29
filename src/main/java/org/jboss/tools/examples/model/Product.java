package org.jboss.tools.examples.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column
	private String imageUrl;

	@ManyToMany(
		fetch = FetchType.EAGER, 
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
		joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "supply_id", referencedColumnName = "id")}
	)
	private Set<Supply> ingredients;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Set<RetailOption> retailOptions;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE})
	@JoinTable(joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
	private Set<Category> categories;

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Supply> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Supply> ingredients) {
		this.ingredients = ingredients;
	}

	public Set<RetailOption> getRetailOptions() {
		return retailOptions;
	}

	public void setRetailOptions(Set<RetailOption> retailOptions) {
		this.retailOptions = retailOptions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", imageUrl="
				+ imageUrl + ", ingredients=" + ingredients
				+ ", retailOptions=" + retailOptions + ", categories="
				+ categories + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

}