package org.jboss.tools.examples.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	@NotNull(message = "must not be null")
	@Size(min = 5, max = 100)
	@Email
	private String email;
	
	@Column
	@NotNull(message = "must not be null")
	@Size(min = 5, max = 100)
	private String name;
	
	@Basic
	@NotNull(message = "must not be null")
	@Size(min = 28, max = 28)
	private byte[] encryptedPassword;

	@Column
	@Size(min = 200, max = 200)
	private String authToken;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(byte[] password) {
		this.encryptedPassword = password;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public void setAuthenticationToken(String token) {
		this.authToken = token;
	}
	
	public String getAuthenticationToken() {
		return this.authToken;
	}
	
}
