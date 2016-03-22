package org.jboss.tools.examples.rest.dto;


import javax.persistence.EntityManager;
import org.jboss.tools.examples.model.User;
import org.jboss.tools.examples.service.PasswordEncryptionService;

public class UserDTO extends ChangeTrackingDTO {
	
	private Long id;
	private String name;
	private String password;
	private String email;
	
	public UserDTO() {
	}

	public UserDTO(final User entity) {
		if (entity != null) {
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setEmail(entity.getEmail());
		}
	}

	public User fromDTO(User entity, EntityManager em) {
		if (entity == null) {
			entity = new User();
		}
		if (isChanged(this.id)) entity.setId(this.id);
		if (isChanged(this.name)) entity.setName(this.name);
		if (isChanged(this.email)) entity.setEmail(this.email);
		if (isChanged(this.password)) {
			try {
				entity.setEncryptedPassword(PasswordEncryptionService.getEncryptedPassword(this.password));
			}
			catch(Exception e) {
				throw new RuntimeException("Error encrypting user password.");
			}
		}
		
		entity = em.merge(entity);
		// Provided it was a new entity, copy the new ID to its DTO.
		this.setId(entity.getId());
		return entity;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
		this.setChanged(this.id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.setChanged(this.name);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		this.setChanged(this.password);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserDTO))
			return false;
		UserDTO other = (UserDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
