package org.jboss.tools.examples.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class RetailOption implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Size(min = 3, max = 25)
	private String name;

	@Column(nullable = false)
	@Size(min = 1, max = 2)
	private String shortName;

	@Column
	@Size(min = 5, max = 100)
	private String description;

	@Column(nullable = false)
	@DecimalMin(value = "0.0")
	@DecimalMax(value = "9999.99")
	private Double price;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "retailOption_id", referencedColumnName = "id")
	private Set<Composition> compositions;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<Composition> getCompositions() {
		return compositions;
	}

	public void setCompositions(Set<Composition> compositions) {
		this.compositions = compositions;
	}

	@Override
	public String toString() {
		return "RetailOption [id=" + id + ", name=" + name + ", shortName="
				+ shortName + ", description=" + description + ", price="
				+ price + ", compositions=" + compositions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compositions == null) ? 0 : compositions.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RetailOption))
			return false;
		RetailOption other = (RetailOption) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

}
