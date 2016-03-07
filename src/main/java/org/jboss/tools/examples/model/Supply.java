package org.jboss.tools.examples.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Supply implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	@Size(min = 1, max = 3)
	private String unity;

	@Column(nullable = false)
	@DecimalMin(value = "0.0")
	@DecimalMax(value = "9999.99")
	private Float price;

	@Column
	private Float stock;

	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public Float getStock() {
		return stock;
	}

	public void setStock(Float stock) {
		this.stock = stock;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Supply [id=" + id + ", name=" + name + ", unity=" + unity
				+ ", price=" + price + ", stock=" + stock + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Supply))
			return false;
		Supply other = (Supply) obj;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((unity == null) ? 0 : unity.hashCode());
		return result;
	}

}